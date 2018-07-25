package com.example.after.alittletwo.adapeter;

import android.app.Activity;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Environment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.after.alittletwo.R;
import com.example.after.alittletwo.entity.ResponseContent;
import com.example.after.alittletwo.views.MyImageView;

import java.util.List;

/**
 * Created by hongjian.chen on 2018/7/25.
 */

public class SPAdapter extends BaseAdapter {

    private String path = Environment.getExternalStorageDirectory() + "/test.mp4";
    private Activity activity;
    private LayoutInflater mInflater;
    private List<ResponseContent.Content> list;
//    private TextView textView;
//    private VideoView videoView;


    private int[] imageIds = new int[]{
            R.drawable.tiger,
            R.drawable.nongyu,
            R.drawable.qingzhao,
            R.drawable.libai};

    public SPAdapter(Activity activity, List<ResponseContent.Content> list) {
        this.activity = activity;
        this.list = list;
        mInflater = LayoutInflater.from(activity);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        VileHold hold;
        Uri uri = Uri.parse(list.get(i).getDownloadPath());
        if (view == null) {
            view = mInflater.inflate(R.layout.listitem_sp, null);
            hold = new VileHold();
//        textView = view.findViewById(R.id.sp_text);
//        videoView = view.findViewById(R.id.sp);
            hold.sp_name = view.findViewById(R.id.sp_name);
            hold.sp_desc = view.findViewById(R.id.sp_desc);
            hold.sp_img = view.findViewById(R.id.sp_img);
            hold.sp_icon = view.findViewById(R.id.sp_icon);
            view.setTag(hold);
        } else {
            hold = (VileHold) view.getTag();
        }
        hold.sp_name.setText(list.get(i).getName());
        hold.sp_desc.setText(list.get(i).getLable() + list.get(i).getDownloadPath());
        hold.sp_img.setImageResource(imageIds[i % 4]);
        hold.sp_icon.setImageURL(list.get(i).getIconPath());
//        textView.setText(list.get(i).getLable());
//        //设置视频控制器
//        videoView.setMediaController(new MediaController(activity));
//        //播放完成回调
//        videoView.setOnCompletionListener(new MyPlayerOnCompletionListener());
//        //设置视频路径
//        videoView.setVideoPath(path);
//        videoView.setVideoURI(uri);
        //开始播放视频
//        videoView.start();
        return view;
    }

    class MyPlayerOnCompletionListener implements MediaPlayer.OnCompletionListener {

        @Override
        public void onCompletion(MediaPlayer mp) {
            Toast.makeText(activity, "播放完成了", Toast.LENGTH_SHORT).show();
        }
    }

    public final class VileHold {
        public ImageView sp_img;
        public MyImageView sp_icon;
        public TextView sp_name;
        public TextView sp_desc;
    }
}
