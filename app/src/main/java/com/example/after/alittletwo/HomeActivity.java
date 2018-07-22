package com.example.after.alittletwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
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
        setContentView(R.layout.home_activity);
        initViews();

//        initBtn();
//        bookBtn.setOnClickListener(new PublicOnClickListen(this, new AddressBooksActivity()));

        // 创建一个List集合，List集合的元素是Map
//        List<Map<String, Object>> listItems = new ArrayList<Map<String, Object>>();
//        for (int i = 0; i < names.length; i++) {
//            Map<String, Object> listItem = new HashMap<String, Object>();
//            listItem.put("header", imageIds[i]);
//            listItem.put("personName", names[i]);
//            listItem.put("desc", descs[i]);
//            listItems.add(listItem);
//        }
//        // 创建一个SimpleAdapter
//        SimpleAdapter simpleAdapter = new SimpleAdapter(this, listItems,
//                R.layout.simple_item,
//                new String[]{"personName", "header", "desc"},
//                new int[]{R.id.name, R.id.header, R.id.desc});
//
//        ListView list = (ListView) findViewById(R.id.mylist);
//        // 为ListView设置Adapter
//        list.setAdapter(simpleAdapter);
//        // 为ListView的列表项的单击事件绑定事件监听器
//        list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            // 第position项被单击时激发该方法
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Toast.makeText(HomeActivity.this, names[position], Toast.LENGTH_SHORT).show();
//            }
//        });
//        // 为ListView的列表项的选中事件绑定事件监听器
//        list.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            // 第position项被选中时激发该方法
//            @Override
//            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
//                System.out.println(names[position] + "被选中了");
//            }
//
//            @Override
//            public void onNothingSelected(AdapterView<?> parent) {
//            }
//        });
    }


    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement

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
}
