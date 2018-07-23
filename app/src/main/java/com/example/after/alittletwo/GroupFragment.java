package com.example.after.alittletwo;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by after on 2018/7/21.
 */
public class GroupFragment extends Fragment {

    private int[] imageIds = new int[]{
            R.drawable.tiger,
            R.drawable.nongyu,
            R.drawable.qingzhao,
            R.drawable.libai};

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.address_activity, container, false);
        ListView listView = view.findViewById(R.id.myList);

        BaseAdapter adapter = new BaseAdapter() {
            @Override
            public int getCount() {

                return 12;
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
                View friendView = LayoutInflater.from(getContext()).inflate(R.layout.friend_item, null);

                ImageView friendImage = friendView.findViewById(R.id.friend_icon);
                TextView friendName = friendView.findViewById(R.id.friend_name);
                friendImage.setImageResource(imageIds[position % 4]);
                friendName.setText("第" + (position + 1) + "个好友");

//                LinearLayout line = new LinearLayout(getActivity());
//                line.setOrientation(LinearLayout.HORIZONTAL);
//                ImageView imageView = new ImageView(getActivity());
//                imageView.setImageResource(imageIds[position % 4]);
//                line.addView(imageView);
//                TextView textView = new TextView(getActivity());
//                textView.setText("第" + (position + 1) + "个好友");
//                line.addView(textView);
                return friendView;
            }
        };
        listView.setAdapter(adapter);
        return view;
    }
}
