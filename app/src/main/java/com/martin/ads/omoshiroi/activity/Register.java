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
import com.martin.ads.omoshiroi.DBServe.Service.UserService;
import com.martin.ads.omoshiroi.R;

import java.sql.SQLOutput;

/**
 * Created by sun on 2022/8/12.
 */

public class Register extends AppCompatActivity {

    private EditText name,password,num;
    private Button register;

int id;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        //获取姓名，身份证号，密码，注册按钮
        name = (EditText) findViewById(R.id.name);
        password = (EditText)findViewById(R.id.password);
        num = (EditText)findViewById(R.id.num);

        register = (Button) findViewById(R.id.register);



        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserDao userDao = new UserDao(Register.this);
//                根据num查询数据库，如果有该用户，则提示用户已存在，如果没有，则插入数据库
                if(userDao.findUser(Integer.parseInt(num.getText().toString())).getName() != null){
                    Toast.makeText(Register.this,"该用户已存在",Toast.LENGTH_SHORT).show();
                    System.out.println(userDao.findUser(Integer.parseInt(num.getText().toString())).getName());
                }else{
                    userDao.addUser(new User(Integer.parseInt(num.getText().toString()),name.getText().toString(),password.getText().toString()));
                    Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                    System.out.println(userDao.findUser(Integer.parseInt(num.getText().toString())).getName());
                    startActivity(new Intent(Register.this, Login.class));
                    finish();
                }
            }
        });
    }
}

