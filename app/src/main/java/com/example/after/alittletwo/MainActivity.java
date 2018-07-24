package com.example.after.alittletwo;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;

import com.example.after.alittletwo.entity.Constant;
import com.example.after.alittletwo.util.PostRequest_Interface;
import com.example.after.alittletwo.util.RetrofitUtil;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by after on 2018/7/21.
 */

public class MainActivity extends AppCompatActivity {
    private String filePath = Environment.getExternalStorageDirectory() + "/aaa/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.splash);

        saveFile();
        new Thread() {
            @Override
            public void run() {
                super.run();
                try {
                    Thread.sleep(1000);
                    Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(intent);
                    finish();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }.start();
    }

    /**
     * 根据网络url保存头像
     * desc测试功能
     *
     * @return
     * @throws IOException
     */
    public void saveFile() {
        File dirFile = new File(filePath);
        if (!dirFile.exists()) {
            dirFile.mkdir();
        }
        String downloadUrl = "user/14715689.jpg";
        Call<ResponseBody> responseBodyCall = new RetrofitUtil(Constant.DEFAULT.getBaseUrl()).create(PostRequest_Interface.class).downloadFile(downloadUrl);
        responseBodyCall.enqueue(new Callback<ResponseBody>() {
            @Override
            public void onResponse(Call<ResponseBody> call, final Response<ResponseBody> response) {
                Log.e("vivi", response.message() + "  length  " + response.body().contentLength() + "  type " + response.body().contentType());
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        InputStream is = response.body().byteStream();
                        try {
                            OutputStream os = new FileOutputStream(filePath + "chat_icon.jpg");
                            int len;
                            byte[] buff = new byte[1024];
                            while ((len = is.read(buff)) != -1) {
                                os.write(buff, 0, len);
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }

            @Override
            public void onFailure(Call<ResponseBody> call, Throwable t) {
                t.printStackTrace();
                Log.e("vivi", t.getMessage() + "  " + t.toString());
            }
        });
    }
}