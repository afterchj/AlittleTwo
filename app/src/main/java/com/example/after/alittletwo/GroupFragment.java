package com.example.after.alittletwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
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
                return friendView;
            }
        };
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), ActivityChat.class);
                intent.putExtra("mineid", "b4d41be2c70144eca7001e95b6078114");
                intent.putExtra("userid", "f8d95cf3a35e412099b333e1cd32f850");
                intent.putExtra("taskid", "532ac0f3844444e0bd334818953c5474");
                intent.putExtra("name", "陌生人");
                intent.putExtra("icon", R.drawable.meinv1);
                intent.putExtra("flag", true);
                startActivity(intent);
            }
        });
        return view;
    }
}
