package com.example.after.alittletwo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.after.alittletwo.entity.Constant;
import com.example.after.alittletwo.util.PostRequest_Interface;
import com.example.after.alittletwo.util.RetrofitUtil;
import com.example.after.alittletwo.views.MyImageView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by after on 2018/7/23.
 */

public class SettingActivity extends AppCompatActivity {
    private TextView logout;

    private MyImageView imageView;

    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.setting_activity);

        logout = (TextView) findViewById(R.id.logout);
        imageView = (MyImageView) findViewById(R.id.head_icon);
        imageView.setImageURL(Constant.WEB_SSM.getBaseUrl());
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(SettingActivity.this, LoginActivity.class));
            }
        });
    }


}
