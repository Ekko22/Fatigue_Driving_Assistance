package com.martin.ads.omoshiroi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.martin.ads.omoshiroi.MainActivity;
import com.martin.ads.omoshiroi.R;

/**
 * Created by sun on 2022/8/12.
 */

public class Login extends AppCompatActivity {

    private EditText num,password;
    private Button login,register;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        num = (EditText)findViewById(R.id.num);
        password = (EditText)findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register =(Button) findViewById(R.id.register);

        //登录监听事件
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(Login.this,"登录成功",Toast.LENGTH_SHORT).show();
                startActivity(new Intent(Login.this, MainActivity.class));
                finish();
            }
        });

        //注册监听事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));
            }
        });

    }
}
