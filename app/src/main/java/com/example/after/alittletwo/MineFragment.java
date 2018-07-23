package com.example.after.alittletwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * Created by after on 2018/7/21.
 */
public class MineFragment extends Fragment {

    private SharedPreferences pref;
    private TextView nameView;
    private View setting;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.mine_fragment, container, false);
        pref = PreferenceManager.getDefaultSharedPreferences(getContext());
        nameView = view.findViewById(R.id.showName);
        setting = view.findViewById(R.id.mySetting);
        nameView.setText(pref.getString("account", "王大锤"));
        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SettingActivity.class));
//                Toast.makeText(getActivity(), "设置界面待完成...", Toast.LENGTH_SHORT).show();
            }
        });
        return view;
    }
}
