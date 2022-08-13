package com.martin.ads.omoshiroi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.martin.ads.omoshiroi.DBServe.Dao.UserDao;
import com.martin.ads.omoshiroi.DBServe.Domain.User;
import com.martin.ads.omoshiroi.MainActivity;
import com.martin.ads.omoshiroi.R;

/**
 * Created by sun on 2022/8/12.
 */

public class Login extends AppCompatActivity {

    private EditText num, password;
    private Button login, register;

    public static String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        num = (EditText) findViewById(R.id.num);
        password = (EditText) findViewById(R.id.password);
        login = (Button) findViewById(R.id.login);
        register = (Button) findViewById(R.id.register);

        //登录监听事件
        login.setOnClickListener(new View.OnClickListener() {
            UserDao userDao = new UserDao(Login.this);

            @Override
            public void onClick(View v) {
                //输入不能为空
                if (num.getText().toString().equals("") || password.getText().toString().equals("")) {
                    Toast.makeText(Login.this, "输入不能为空,请重新输入", Toast.LENGTH_SHORT).show();
                }else {
                    //如果报出异常，说明用户不存在
                    try {
                        if (userDao.findUser(Integer.parseInt(num.getText().toString())).getPassword().equals(password.getText().toString())) {
                            Toast.makeText(Login.this, "登录成功，即将进入页面", Toast.LENGTH_SHORT).show();
                            INFO.ID = Integer.parseInt(num.getText().toString());
                            name = userDao.findUser(Integer.parseInt(num.getText().toString())).getName();
                            startActivity(new Intent(Login.this, MainActivity.class));
                            finish();
                        } else {
                            Toast.makeText(Login.this, "登录失败，请检查账号是否有无", Toast.LENGTH_SHORT).show();
                        }
                    } catch (Exception e) {
                        Toast.makeText(Login.this, "用户不存在,正在跳转注册", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(Login.this, Register.class));
                    }


                }
            }
        });

        //注册监听事件
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this, Register.class));
            }
        });

    }
}
