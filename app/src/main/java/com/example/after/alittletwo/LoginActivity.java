package com.example.after.alittletwo;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    private SharedPreferences pref;
    private SharedPreferences.Editor editor;
    private EditText account;
    private EditText passwd;
    private Button loginBtn;
    private Button registerBtn;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);

        pref = PreferenceManager.getDefaultSharedPreferences(this);
        editor = pref.edit();
        Boolean isLogin = pref.getBoolean("isLogin", false);
        if (isLogin) {
            startActivity(new Intent(this, HomeActivity.class));
        }
        setContentView(R.layout.login_activity);
        initBtn();
        Boolean isRemember = pref.getBoolean("remember_me", false);
        if (isRemember) {
            String name = pref.getString("name", "admin");
            String pwd = pref.getString("passwd", "admin123");
            account.setText(name);
            passwd.setText(pwd);
            checkBox.setChecked(true);
        }
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);

    }

    public void initBtn() {
        account = (EditText) findViewById(R.id.login_name);
        passwd = (EditText) findViewById(R.id.login_pwd);
        loginBtn = (Button) findViewById(R.id.login);
        registerBtn = (Button) findViewById(R.id.register);
        checkBox = (CheckBox) findViewById(R.id.remember_me);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            String name = account.getText().toString();
            String pwd = passwd.getText().toString();
            if (checkBox.isChecked()) {
                editor.putBoolean("remember_me", true);
                editor.putString("name", name);
                editor.putString("passwd", pwd);
                editor.putBoolean("isLogin", true);
            } else {
                editor.clear();
            }
            editor.commit();
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivity(intent);
        }
        if (v.getId() == R.id.register) {
            Toast.makeText(LoginActivity.this, "--on click test--", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
            //启动一个意图,回到桌面
            Intent backHome = new Intent(Intent.ACTION_MAIN);
            backHome.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            backHome.addCategory(Intent.CATEGORY_HOME);
            startActivity(backHome);
            finish();
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
