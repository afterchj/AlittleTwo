package com.example.after.alittletwo;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.StrictMode;
import android.preference.PreferenceManager;
import android.provider.MediaStore;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.after.alittletwo.entity.Constant;
import com.example.after.alittletwo.entity.Translation;
import com.example.after.alittletwo.util.PostRequest_Interface;
import com.example.after.alittletwo.util.RetrofitUtil;
import com.example.after.alittletwo.views.MyImageView;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by after on 2018/7/21.
 */
public class MineFragment extends Fragment {

    private SharedPreferences pref;
    private TextView nameView;
    private View setting;

    private String path = Environment.getExternalStorageDirectory() + "/aaa/";
    private String fileName = "chat_icon.jpg";
    private MyImageView mImage;
    private View view;
    protected static final int CHOOSE_PICTURE = 0;
    protected static final int TAKE_PICTURE = 1;
    protected static Uri tempUri;
    private static final int CROP_SMALL_PICTURE = 2;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.mine_fragment, container, false);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
            StrictMode.setVmPolicy(builder.build());
        }
        initUI();
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        nameView = view.findViewById(R.id.showName);
        setting = view.findViewById(R.id.mySetting);
        nameView.setText(pref.getString("account", "王大锤"));
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
            }
        });
        mImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showChoosePicDialog();
            }
        });
        return view;
    }

    private void initUI() {
        mImage = view.findViewById(R.id.iv_image);
        Bitmap bitmap = BitmapFactory.decodeFile(path + fileName);
        if (bitmap != null) {
            mImage.setImageBitmap(bitmap);
        } else {
            mImage.setImageURL(Constant.HEAD.getBaseUrl());
        }
    }

    /**
     * 显示修改图片的对话框
     */
    protected void showChoosePicDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setTitle("添加图片");
        String[] items = {"选择本地照片", "拍照"};
        builder.setNegativeButton("取消", null);
        builder.setItems(items, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                switch (which) {
                    case CHOOSE_PICTURE: // 选择本地照片
                        Intent intent = new Intent(Intent.ACTION_PICK);//返回被选中项的URI
                        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");//得到所有图片的URI
                        startActivityForResult(intent, CHOOSE_PICTURE);
                        break;
                    case TAKE_PICTURE: // 拍照
                        Intent openCameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                        tempUri = Uri.fromFile(new File(Environment.getExternalStorageDirectory(), "temp_image.jpg"));
                        // 将拍照所得的相片保存到SD卡根目录
                        openCameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, tempUri);
                        startActivityForResult(openCameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });
        builder.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == MainActivity.RESULT_OK) {
            switch (requestCode) {
                case TAKE_PICTURE:
                    cutImage(tempUri); // 对图片进行裁剪处理
                    break;
                case CHOOSE_PICTURE:
                    cutImage(data.getData()); // 对图片进行裁剪处理
                    break;
                case CROP_SMALL_PICTURE:
                    if (data != null) {
                        try {
                            setImageToView(data); // 让刚才选择裁剪得到的图片显示在界面上
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                    break;
            }
        }
    }

    /**
     * 裁剪图片方法实现
     */
    protected void cutImage(Uri uri) {
        if (uri == null) {
            Log.i("tip", "The uri is not exist.");
        }
        tempUri = uri;
        Intent intent = new Intent("com.android.camera.action.CROP");
        //com.android.camera.action.CROP这个action是用来裁剪图片用的
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");
        // aspectX aspectY 是宽高的比例
        intent.putExtra("aspectX", 1);
        intent.putExtra("aspectY", 1);
        // outputX outputY 是裁剪图片宽高
        intent.putExtra("outputX", 150);
        intent.putExtra("outputY", 150);
        intent.putExtra("return-data", true);
        startActivityForResult(intent, CROP_SMALL_PICTURE);
    }

    /**
     * 保存裁剪之后的图片数据
     */
    protected void setImageToView(Intent data) throws IOException {
        Bundle extras = data.getExtras();
        if (extras != null) {
            Bitmap mBitmap = extras.getParcelable("data");
            File file = saveFile(mBitmap);
            mImage.setImageBitmap(mBitmap);//显示图片
            upload(file);
        }
    }

    public void upload(File file) {
        //创建Retrofit对象
        //创建 网络请求接口 的实例
        String descriptionString = "This is a params";
        RequestBody uid = RequestBody.create(MediaType.parse("text/plain"), "14715689");
        RequestBody requestFile = RequestBody.create(MediaType.parse("multipart/form-data"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), requestFile);

        Call<Translation> call = new RetrofitUtil(Constant.UMS3_CLIENT2.getBaseUrl()).create(PostRequest_Interface.class).upload(uid, body);

        call.enqueue(new Callback<Translation>() {
            @Override
            public void onResponse(Call<Translation> call, Response<Translation> response) {
                System.out.println("result=" + response.body().toString());
                if (response.body() != null) {
                    Toast.makeText(getActivity(), "头像上传成功！", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<Translation> call, Throwable t) {
                Toast.makeText(getActivity(), "头像上传失败！", Toast.LENGTH_SHORT).show();
                t.printStackTrace();
            }
        });
    }

    public File saveFile(Bitmap bitmap) {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        File myCaptureFile = new File(path + fileName);
        BufferedOutputStream bos;
        try {
            bos = new BufferedOutputStream(new FileOutputStream(myCaptureFile));
            bitmap.compress(Bitmap.CompressFormat.JPEG, 80, bos);
            bos.flush();
            bos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return myCaptureFile;
    }

}
