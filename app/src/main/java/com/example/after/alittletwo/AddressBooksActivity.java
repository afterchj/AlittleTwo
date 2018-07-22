package com.example.after.alittletwo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by after on 2018/7/21.
 */

public class AddressBooksActivity extends AppCompatActivity implements GestureDetector.OnGestureListener{

    GestureDetector detector;
    ListView listView;
    Button chatBtn;
    Button bookBtn;
    Button moreBtn;
    Button mineBtn;
    private int[] imageIds = new int[]{
            R.drawable.tiger,
            R.drawable.nongyu,
            R.drawable.qingzhao,
            R.drawable.libai};

    public void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.address_activity);
        initBtn();
        chatBtn.setOnClickListener(new PublicOnClickListen(this, new HomeActivity()));
        listView = (ListView) findViewById(R.id.myList);

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {

                return 10;
            }

            @Override
            public Object getItem(int position) {
                return null;
            }

            @Override
            public long getItemId(int position) {
                return position;
            }

            @Override
            public View getView(int position, View convertView, ViewGroup parent) {
                LinearLayout line = new LinearLayout(AddressBooksActivity.this);
                line.setOrientation(LinearLayout.HORIZONTAL);
                ImageView imageView = new ImageView(AddressBooksActivity.this);
                imageView.setImageResource(imageIds[position % 3]);
                line.addView(imageView);
                TextView textView = new TextView(AddressBooksActivity.this);
                textView.setText("第" + (position + 1) + "个好友");
                line.addView(textView);
                return line;
            }
        };
        listView.setAdapter(adapter);
        detector = new GestureDetector(this, this);
        detector.setIsLongpressEnabled(true);
    }
    public void initBtn() {
        chatBtn = (Button) findViewById(R.id.weChat);
        bookBtn = (Button) findViewById(R.id.friends);
        moreBtn = (Button) findViewById(R.id.more);
        mineBtn = (Button) findViewById(R.id.mine);
    }
    //重写Activity的onTouchEvent，将Activity的事件
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return detector.onTouchEvent(event);
    }

    // 用户轻触触摸屏，由1个MotionEvent ACTION_DOWN触发
    @Override
    public boolean onDown(MotionEvent motionEvent) {
        Log.i("tag00", "================================");
        Log.i("tag00", "onDown");
        return false;
    }

    /*
    * 用户轻触触摸屏，尚未松开或拖动，由一个1个MotionEvent ACTION_DOWN触发
    * 注意和onDown()的区别，强调的是没有松开或者拖动的状态
    *
    * 而onDown也是由一个MotionEventACTION_DOWN触发的，但是他没有任何限制，
    * 也就是说当用户点击的时候，首先MotionEventACTION_DOWN，onDown就会执行，
    * 如果在按下的瞬间没有松开或者是拖动的时候onShowPress就会执行，如果是按下的时间超过瞬间
    * （这块我也不太清楚瞬间的时间差是多少，一般情况下都会执行onShowPress），拖动了，就不执行onShowPress。
    */
    @Override
    public void onShowPress(MotionEvent motionEvent) {
        Log.i("tag00", "onShowPress");
    }

    // 用户（轻触触摸屏后）松开，由一个1个MotionEvent ACTION_UP触发
    // 轻击一下屏幕，立刻抬起来，才会有这个触发
    // 从名子也可以看出,一次单独的轻击抬起操作,当然,如果除了Down以外还有其它操作,那就不再算是Single操作了,所以这个事件 就不再响应
    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        Log.i("tag00", "onSingleTapUp");
        return false;
    }

    // 用户按下触摸屏，并拖动，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE触发
    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent1, float v, float v1) {
        Log.i("tag00", "onScroll");
        return false;
    }

    // 用户长按触摸屏，由多个MotionEvent ACTION_DOWN触发
    @Override
    public void onLongPress(MotionEvent motionEvent) {
        Log.i("tag00", "onLongPress");
    }

    // 用户按下触摸屏、快速移动后松开，由1个MotionEvent ACTION_DOWN, 多个ACTION_MOVE, 1个ACTION_UP触发
    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float v, float v1) {
        if (e1.getX() - e2.getX() > 20) {//向左滑，右边显示
            //this.flipper.setInAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_in));
            //this.flipper.setOutAnimation(AnimationUtils.loadAnimation(this, R.anim.push_left_out));
            Log.e("result", "向左滑，右边显示");
        }
        if (e1.getX() - e2.getX() < -20) {//向右滑，左边显示
            Log.e("result", "/向右滑，左边显示");
        }
        return false;
    }
}
