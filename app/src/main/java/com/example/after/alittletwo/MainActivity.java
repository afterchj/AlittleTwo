package com.example.after.alittletwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

/**
 * Created by after on 2018/7/21.
 */

public class MainActivity extends AppCompatActivity implements GestureDetector.OnGestureListener {

    GestureDetector detector;
//    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(2000);
                    Intent intent = new Intent(MainActivity.this, LoginActivity.class);
                    startActivity(intent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

//        button = (Button) findViewById(R.id.toLogin);
//        button.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(MainActivity.this, LoginActivity.class));
//            }
//        });
        detector = new GestureDetector(this, this);
        detector.setIsLongpressEnabled(true);
    }

    //重写Activity的onTouchEvent，将Activity的事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    // 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
    @Override
    public boolean onDown(MotionEvent motionEvent) {

        return false;
    }


    @Override
    public void onShowPress(MotionEvent motionEvent) {

    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    // 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        return false;
    }

    // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
    @Override
    public void onLongPress(MotionEvent motionEvent) {

    }

    // 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        if (e1.getX() - e2.getX() > 120) {//向左滑，右边显示
            //this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
            //this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            Log.e("result", "向左滑，右边显示");
            Toast.makeText(this, "向左滑，右边显示", Toast.LENGTH_SHORT).show();
        }
        if (e1.getX() - e2.getX() < -120) {//向右滑，左边显示
            Log.e("result", "向右滑，左边显示");
            Toast.makeText(this, "向右滑，左边显示", Toast.LENGTH_SHORT).show();
        }
        return false;
    }
}