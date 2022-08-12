package com.martin.ads.omoshiroi.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.martin.ads.omoshiroi.R;

import java.util.Timer;
import java.util.TimerTask;


/**
 * Created by sun on 2022/8/11.
 */

public class Splash extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        //延时操作
        Timer timer = new Timer();
        timer.schedule(timetast, 2000);


    }

    TimerTask timetast = new TimerTask() {
        @Override
        public void run() {
            startActivity(new Intent(Splash.this, Login.class));
        }
    };

}
