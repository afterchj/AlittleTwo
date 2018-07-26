package com.example.after.alittletwo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.ListView;

import com.example.after.alittletwo.adapeter.SPAdapter;
import com.example.after.alittletwo.entity.Constant;
import com.example.after.alittletwo.entity.ResponseContent;
import com.example.after.alittletwo.util.PostRequest_Interface;
import com.example.after.alittletwo.util.RetrofitUtil;
import com.google.gson.Gson;
import com.google.gson.JsonArray;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by after on 2018/7/21.
 */
public class FindFragment extends Fragment {


    private String filePath = Environment.getExternalStorageDirectory() + "/aaa/chat_icon.jpg";
    private Bitmap bitmap;
    private ListView listView;
    private SPAdapter spAdapter;
    private int hotPage = 1;
    private int newPage = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        listView = view.findViewById(R.id.spList);

        bitmap = BitmapFactory.decodeFile(filePath);
        initList(1, true);
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            int lastItem;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    if (lastItem == view.getCount() - 1) {
                        hotPage++;
                        initList(hotPage, true);
//                        Toast.makeText(getActivity(), "下拉加载更多数据", Toast.LENGTH_SHORT).show();
                    }
                    if (lastItem == 0) {
                        newPage++;
                        initList(newPage, false);
                    }
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                lastItem = firstVisibleItem + visibleItemCount - 1;
                Log.e("tip", "firstVisibleItem=" + firstVisibleItem + ",lastItem=" + lastItem);
            }
        });
        return view;
    }

    public void initList(int page, final boolean flag) {
        Call<ResponseContent> responseBodyCall;
        if (flag) {
            responseBodyCall = new RetrofitUtil(Constant.UMS3_CLIENT2.getBaseUrl()).create(PostRequest_Interface.class).getHottest(page);
        } else {
            responseBodyCall = new RetrofitUtil(Constant.UMS3_CLIENT2.getBaseUrl()).create(PostRequest_Interface.class).getNewest(page);
        }
        responseBodyCall.enqueue(new Callback<ResponseContent>() {
            @Override
            public void onResponse(Call<ResponseContent> call, final Response<ResponseContent> response) {
                List<ResponseContent.Content> list = new ArrayList<>();
                Gson gson = new Gson();
                JsonArray data = response.body().getData();
//                Log.e("size", "data.size=" + data.size());
                if (data != null && data.size() > 0) {
                    for (int i = 0; i < data.size(); i++) {
                        ResponseContent.Content content = gson.fromJson(data.get(i), ResponseContent.Content.class);
                        content.setDownloadPath(content.getDownloadPath());
                        content.setIconPath(content.getIconPath());
//                        System.out.println(content.getName() + ":" + content.getDownloadPath());
                        list.add(content);
                    }
                    spAdapter = new SPAdapter(getActivity(), bitmap, list);
                    listView.setAdapter(spAdapter);
                    if (flag && hotPage > 1) {
//                        list.subList(list.size() - 2, list.size());
                        listView.setSelection(1);
                        spAdapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ResponseContent> call, Throwable t) {
                t.printStackTrace();
                Log.e("result", t.getMessage() + "  " + t.toString());
            }
        });
    }

}
