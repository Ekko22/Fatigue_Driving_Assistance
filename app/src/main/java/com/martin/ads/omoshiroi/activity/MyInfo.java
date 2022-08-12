package com.martin.ads.omoshiroi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.martin.ads.omoshiroi.R;

/**
 * Created by sun on 2022/8/12.
 */

public class MyInfo extends AppCompatActivity {

    private ImageView my_back;
    private TextView my_name,my_num,my_password;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_myinfo);

        initView();

        //返回
        my_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initView() {

        my_back = (ImageView)findViewById(R.id.my_back);
        my_name = (TextView)findViewById(R.id.my_name);
        my_num = (TextView)findViewById(R.id.my_num);
        my_password = (TextView)findViewById(R.id.my_password);

    }
}
