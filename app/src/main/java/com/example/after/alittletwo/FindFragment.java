package com.example.after.alittletwo;

import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

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

    private List<ResponseContent.Content> list = new ArrayList<>();
    private ListView listView;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.home_fragment, container, false);
        listView = view.findViewById(R.id.spList);
        initList();
        return view;
    }

    public void initList() {
        Call<ResponseContent> responseBodyCall = new RetrofitUtil(Constant.UMS3_CLIENT2.getBaseUrl()).create(PostRequest_Interface.class).getHottest(1);
        responseBodyCall.enqueue(new Callback<ResponseContent>() {
            @Override
            public void onResponse(Call<ResponseContent> call, final Response<ResponseContent> response) {
                Gson gson = new Gson();
                JsonArray data = response.body().getData();
//                list = new Gson().fromJson(data, ArrayList.class);
                Log.e("size", "data.size=" + data.size());
                for (int i = 0; i < data.size(); i++) {
                    ResponseContent.Content content = gson.fromJson(data.get(i), ResponseContent.Content.class);
                    content.setDownloadPath(content.getDownloadPath());
                    content.setIconPath(content.getIconPath());
                    System.out.println("--DownloadPath--" + content.getDownloadPath());
                    list.add(content);
                }
                SPAdapter adapter = new SPAdapter(getActivity(), list);
                listView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<ResponseContent> call, Throwable t) {
                t.printStackTrace();
                Log.e("result", t.getMessage() + "  " + t.toString());
            }
        });
    }

}
