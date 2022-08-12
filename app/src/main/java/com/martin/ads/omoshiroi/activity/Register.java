package com.martin.ads.omoshiroi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.martin.ads.omoshiroi.R;

/**
 * Created by sun on 2022/8/12.
 */

public class Register extends AppCompatActivity {

    private EditText name,password,num;
    private Button register;


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
                Toast.makeText(Register.this,"注册成功",Toast.LENGTH_SHORT).show();
                finish();
            }
        });

    }
}
