package com.example.after.alittletwo;

import android.app.Activity;
import android.content.Intent;
import android.view.View;

/**
 * Created by after on 2018/7/21.
 */

public class PublicOnClickListen implements View.OnClickListener {

    Activity from;
    Activity to;

    public PublicOnClickListen(Activity from,Activity to) {
        this.from = from;
        this.to = to;
    }


    @Override
    public void onClick(View v) {
        Intent intent = new Intent(from, to.getClass());
        from.startActivity(intent);

    }
}
