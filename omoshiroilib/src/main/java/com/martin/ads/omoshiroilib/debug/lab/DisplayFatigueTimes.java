package com.martin.ads.omoshiroilib.debug.lab;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import com.martin.ads.INFO.INFO;
import com.martin.ads.omoshiroilib.R;

import java.util.Calendar;

public class DisplayFatigueTimes extends AppCompatActivity {

    SQLiteDatabase db;

    static DisplayFatigueTimes instance = null;

    int curWeekTimes;

    int week;

    TextView tips;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //初始化数据库
        db = openOrCreateDatabase("fatigue.db", MODE_PRIVATE, null);
        db.execSQL("create table if not exists fatigue"+INFO.ID+" (id integer primary key autoincrement, week integer, times integer)");
        week = getWeek();
        curWeekTimes = getCurWeekTimes();
        instance = this;
        //设置布局
        setContentView(R.layout.debug_fatigue_times);
        tips = (TextView) findViewById(R.id.sleep_times);
        tips.setText("本周是第" + week + "周，\n本周已睡眠" + curWeekTimes + "次");
        findViewById(R.id.clear_button).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果表中有数据，则清空表
                Cursor cursor = db.rawQuery("select * from fatigue"+INFO.ID+"", null);
                if (cursor.moveToFirst()) {
                    db.execSQL("delete from fatigue"+INFO.ID+"");
                    db.execSQL("create table if not exists fatigue"+INFO.ID+" (id integer primary key autoincrement, week integer, times integer)");
                    tips.setText("数据已清空");
                } else {
                    tips.setText("没有数据");
                }
            }
        });

    }

    //查询当前周疲劳次数
    private int getCurWeekTimes() {
        try {
            Cursor cursor = db.rawQuery("select * from fatigue"+INFO.ID+" where week = ?", new String[]{String.valueOf(week)});
            if (cursor.moveToFirst()) {
                curWeekTimes = cursor.getInt(cursor.getColumnIndex("times"));
            } else {
                curWeekTimes = 0;
                tips.setText("您本周还未进行过疲劳检测！");
            }
            return curWeekTimes;
        }catch (Exception e){
            //如果查询失败,给出提示
            tips.setText("查询失败！");
        }
        return 0;
    }

    private int getWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

}
