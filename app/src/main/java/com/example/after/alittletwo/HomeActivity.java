package com.example.after.alittletwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;

import com.example.after.alittletwo.adapeter.TabFragmentAdapter;
import com.example.after.alittletwo.views.TabContainerView;

/**
 * Created by after on 2018/7/21.
 */

public class HomeActivity extends AppCompatActivity implements ViewPager.OnPageChangeListener {

    //    Button chatBtn;
//    Button bookBtn;
//    Button moreBtn;
//    Button mineBtn;
    private SharedPreferences pref;
    private String[] names = new String[]
            {"虎头", "弄玉", "李清照", "李白"};
    private String[] descs = new String[]
            {"可爱的小孩", "一个擅长音乐的女孩", "一个擅长文学的女性", "浪漫主义诗人"};
    private int[] imageIds = new int[]{
            R.drawable.tiger,
            R.drawable.nongyu,
            R.drawable.qingzhao,
            R.drawable.libai};

    private final int ICONS_RES[][] = {
            {
                    R.drawable.interactive,
                    R.drawable.interactive_fill
            },
            {
                    R.drawable.group,
                    R.drawable.group_fill
            },

            {
                    R.drawable.find,
                    R.drawable.find_fill
            },

            {
                    R.drawable.mine,
                    R.drawable.mine_fill
            }
    };

    /**
     * tab 颜色值
     */
    private final int[] TAB_COLORS = new int[]{
            R.color.main_bottom_tab_textcolor_normal,
            R.color.main_bottom_tab_textcolor_selected};

    private Fragment[] fragments = {
            new MessageFragment(),
            new GroupFragment(),
            new FindFragment(),
            new MineFragment()
    };

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        Boolean isLogin = pref.getBoolean("isLogin", false);
        if (isLogin) {
            setContentView(R.layout.home_activity);
            initViews();
        } else {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        Log.e("result", "test message");
//        initBtn();
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
            Intent intent = new Intent(HomeActivity.this, LoginActivity.class);
            startActivity(intent);
            return true;
        }
        if (id == R.id.home) {
            Intent intent = new Intent(HomeActivity.this, MainActivity.class);
            startActivity(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

//    public void initBtn() {
//        chatBtn = (Button) findViewById(R.id.weChat);
//        bookBtn = (Button) findViewById(R.id.friends);
//        moreBtn = (Button) findViewById(R.id.more);
//        mineBtn = (Button) findViewById(R.id.mine);
//    }

    private void initViews() {
        TabFragmentAdapter mAdapter = new TabFragmentAdapter(getSupportFragmentManager(), fragments);
        ViewPager mPager = (ViewPager) findViewById(R.id.tab_pager);
        //设置当前可见Item左右可见page数，次范围内不会被销毁
        mPager.setOffscreenPageLimit(1);
        mPager.setAdapter(mAdapter);

        TabContainerView mTabLayout = (TabContainerView) findViewById(R.id.ll_tab_container);
        mTabLayout.setOnPageChangeListener(this);

        mTabLayout.initContainer(getResources().getStringArray(R.array.tab_main_title), ICONS_RES, TAB_COLORS, true);

        int width = getResources().getDimensionPixelSize(R.dimen.tab_icon_width);
        int height = getResources().getDimensionPixelSize(R.dimen.tab_icon_height);
        mTabLayout.setContainerLayout(R.layout.tab_container_view, R.id.iv_tab_icon, R.id.tv_tab_text, width, height);
//        mTabLayout.setSingleTextLayout(R.layout.tab_container_view, R.id.tv_tab_text);
//        mTabLayout.setSingleIconLayout(R.layout.tab_container_view, R.id.iv_tab_icon);
        mTabLayout.setViewPager(mPager);

        mPager.setCurrentItem(getIntent().getIntExtra("tab", 0));

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {

    }

    @Override
    public void onPageScrollStateChanged(int state) {
    }


    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //启动一个意图,回到桌面
            Intent backHome = new Intent(Intent.ACTION_MAIN);
            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(backHome);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
