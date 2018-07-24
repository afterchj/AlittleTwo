package com.example.after.alittletwo;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.after.alittletwo.entity.Constant;
import com.example.after.alittletwo.util.BitmapFileSetting;
import com.example.after.alittletwo.util.PostRequest_Interface;
import com.example.after.alittletwo.util.RetrofitUtil;
import com.lzy.okhttputils.OkHttpUtils;
import com.lzy.okhttputils.callback.StringCallback;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import okhttp3.Call;
import okhttp3.Response;
import okhttp3.ResponseBody;
import retrofit2.Callback;

import static android.graphics.BitmapFactory.decodeFile;

/**
 * Created by after on 2018/7/22.
 */
public class ActivityChat extends AppCompatActivity {

    Button sendBtn;
    EditText msg;
    TextView text;
    int iconId;
    private String filePath = Environment.getExternalStorageDirectory() + "/aaa/bbb/14715689.jpg";
    private ListView lvChat;
    private NyChattingListAdapter myChattingAdapter;
    private Data_ReceiverNews data_receiverNews;
    private List<Data_ReceiverNews.NewsBean> newsBeanList = new ArrayList<Data_ReceiverNews.NewsBean>();
    private String userid, taskid;
    private int allcount;
    private Bitmap bitmap;
//    private SwipeRefreshLayout uploadmore;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activitychat);
//        userid = "d8428fb4e425425db75c1c7d1e47bbc0";//安琪宝贝;
        userid = "d35558c810fd4b9ea3b7482af39ad51d";//王大锤
        taskid = "cd48e5b34c29448c98d20fa6869c3647";
        initView();
        new Thread(new Runnable() {
            @Override
            public void run() {
                bitmap = BitmapFileSetting.getByUrl(Constant.HEAD.getBaseUrl());
            }
        }).start();
        showNow(false);


        Intent intent = getIntent();
        final String name = intent.getStringExtra("name");
        iconId = intent.getIntExtra("icon", R.drawable.meinv2);
        final Boolean flag = intent.getBooleanExtra("flag", true);

        text.setText(name);
        sendBtn.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String str = msg.getText().toString();
                onSendBtnClick(str);
                msg.setText("");
            }
        });

        msg.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View view, boolean b) {
                if (b) {
                    Log.e("result", name + "\t" + flag);
                    showNow(true);
                }
            }
        });

        msg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showNow(true);
            }
        });
    }

    private void initView() {
        lvChat = (ListView) findViewById(R.id.lv_chat);
        sendBtn = (Button) findViewById(R.id.sendBtn);
        msg = (EditText) findViewById(R.id.message);
        text = (TextView) findViewById(R.id.toolbarmtit);
        uploadMoreAdd();
    }

    private void showNow(final boolean flag) {

        try {
            OkHttpUtils.get(Http_Api.URL_NewReceiver)
                    .params("userid", userid)
                    .params("taskid", taskid)
                    .execute(new StringCallback() {
                        @Override
                        public void onSuccess(String s, Call call, Response response) {
                            data_receiverNews = JsonUtil.parseJsonToBean(s, Data_ReceiverNews.class);
                            allcount = Integer.parseInt(data_receiverNews.getResult());
                            if (allcount != 0) {
                                if (newsBeanList != null) {
                                    newsBeanList.clear();
                                }
                                newsBeanList = data_receiverNews.getNews();
                                if (flag) {
                                    newsBeanList.subList(newsBeanList.size() - 3, newsBeanList.size());
                                }
                                myChattingAdapter = new NyChattingListAdapter(ActivityChat.this, bitmap, userid, iconId, newsBeanList);
                                lvChat.setAdapter(myChattingAdapter);
                                myChattingAdapter.notifyDataSetChanged();
                                lvChat.setSelection(newsBeanList.size());
                            } else {
                                Toast.makeText(ActivityChat.this, "对不起没有更多消息了", Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void uploadMoreAdd() {
        lvChat.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == SCROLL_STATE_IDLE) {
                    int lastPosition = lvChat.getLastVisiblePosition();//最后一个item的位置
                    if (lastPosition == lvChat.getCount() - 1) {
                        showNow(false);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    //发送信息联网
    private void onSendBtnClick(String msg) {
//        newsBeanList.subList(newsBeanList.size() - 5, newsBeanList.size());
        if (!TextUtils.isEmpty(msg)) {
            final Data_ReceiverNews.NewsBean bean = new Data_ReceiverNews.NewsBean();
            bean.setNewscontent(msg);
            bean.setCreatime(getTime());
            try {
                OkHttpUtils.post(Http_Api.URL_NewSend)
                        .params("taskid", taskid)
                        .params("userid", userid)
                        .params("newscontent", msg)
                        .execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
//                                LogUtil.d("返回值",s);
                                Data_uploadBack_tag back_tag = JsonUtil.parseJsonToBean(s, Data_uploadBack_tag.class);
                                if (back_tag.getResult().equals("0")) {
//                                    ToastUtil.showToast("非法的表情符号！");
                                    Toast.makeText(ActivityChat.this, "非法的表情符号!", Toast.LENGTH_SHORT).show();
                                } else if (back_tag.getResult().equals("1")) {
                                    showNow(false);
                                    myChattingAdapter.notifyDataSetChanged();
                                    scrollToBottom();
                                }
                            }

                        });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private String getTime() {
        Date date = new Date(System.currentTimeMillis());
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm ss");
        return dateFormat.format(date);
    }

    private void scrollToBottom() {//设置滚动到底部
        lvChat.requestLayout();
        lvChat.post(new Runnable() {
            @Override
            public void run() {
                lvChat.setSelection(lvChat.getBottom() + 2);
            }
        });
    }

}
