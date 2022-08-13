package com.martin.ads.omoshiroi.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;

import com.martin.ads.omoshiroi.R;

/**
 * Created by sun on 2022/8/13.
 */

public class Function extends AppCompatActivity {

    private ImageView my_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_function);

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
    }
}
