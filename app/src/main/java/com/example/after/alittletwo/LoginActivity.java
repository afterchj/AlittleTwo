package com.example.after.alittletwo;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener {

    Button loginBtn;
    Button registerBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        supportRequestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.login_activity);
        initBtn();
        loginBtn.setOnClickListener(this);
        registerBtn.setOnClickListener(this);
    }

    public void initBtn() {
        loginBtn = (Button) findViewById(R.id.login);
        registerBtn = (Button) findViewById(R.id.register);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.login) {
            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
            startActivityForResult(intent,1);
        }
        if (v.getId() == R.id.register) {
            Toast.makeText(LoginActivity.this, "--on click test--", Toast.LENGTH_SHORT).show();
        }
    }
}
