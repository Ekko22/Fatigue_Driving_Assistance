package com.martin.ads.omoshiroilib.debug.teststmobile;

import android.app.Activity;
import android.content.pm.ActivityInfo;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.PointF;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import com.martin.ads.INFO.INFO;
import com.martin.ads.omoshiroilib.R;
import com.sensetime.stmobileapi.STMobile106;

import java.util.Calendar;



public class FaceCollectActivity extends Activity {
    static FaceCollectActivity instance = null;

    private SoundPool soundPool;
    private static int musicId, streamId;
    private static final int FPS = 30;
    private static final int DETECTION_FRAMES = 90;
    private static double normalYaw, normalPitch;
    private static boolean FACE_COLLECTED = false;
    private static boolean IS_FATIGUE = false;
    //采集1秒的人脸特征
    STMobile106[] faceLandmarks = new STMobile106[FPS];
    //3s内的人脸特征点
    STMobile106[] faceLandmarksPer3s = new STMobile106[DETECTION_FRAMES];
    private static double yawPer3s, pitchPer3s;
    private static double eyeDistance, lipDistance;

    TextView newfpstText, newactionText;

    static Accelerometer acc;


    //记录疲劳驾驶的次数
    int fatiguePerWeekCount = 0;

    //引入sqlite数据库
    SQLiteDatabase db;

    int week;

    //用于显示当前周疲劳次数
    int curWeekTimes = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //隐藏标题栏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        //设置全屏
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        //设置全屏显示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        //设置布局
        setContentView(R.layout.activity_facecollect);
        //获取控件
        newfpstText = (TextView) findViewById(R.id.newfpstext);
        newactionText = (TextView) findViewById(R.id.newactionText);
        //初始化加速度计
        newfpstText.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        soundPool.stop(streamId);
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                newfpstText.setText("采集完成，本周疲劳驾驶次数：" + curWeekTimes);
                                newactionText.setText("上下眼平均距离："+eyeDistance + "\n上下唇平均距离："+lipDistance);
                            }
                        });
                    }
                }
        );
        instance = this;
        soundPool = new SoundPool(5, 0, 5);
        musicId = soundPool.load(this, R.raw.collide, 1);
        acc = new Accelerometer(this);
        acc.start();
        //初始化数据库
        db = openOrCreateDatabase("fatigue.db", MODE_PRIVATE, null);
        db.execSQL("create table if not exists fatigue"+ INFO.ID+ "(id integer primary key autoincrement, week integer, times integer)");
        //获取当前周数
        week = getWeek();
        //获取当前周疲劳次数
        curWeekTimes = getCurWeekTimes();
    }
//查询当前周疲劳次数
    private int getCurWeekTimes() {
        Cursor cursor = db.rawQuery("select * from fatigue"+INFO.ID+" where week = ?", new String[]{String.valueOf(week)});
        if (cursor.moveToFirst()) {
            curWeekTimes = cursor.getInt(cursor.getColumnIndex("times"));
        }else {
            curWeekTimes = 0;
        }
        return curWeekTimes;
    }
    private int getWeek() {
        Calendar cal = Calendar.getInstance();
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    @Override
    public void onResume() {
        FACE_COLLECTED = false;
        IS_FATIGUE = false;
        super.onResume();
        //开始采集人脸特征
        final FaceOverlapFragment fragment = (FaceOverlapFragment) getFragmentManager()
                .findFragmentById(R.id.newoverlapFragment);
        fragment.registTrackCallback(new FaceOverlapFragment.TrackCallBack() {
            int frameNum = 0;

            @Override
            public void onTrackdetected(final int value, final float pitch, final float roll, final float yaw, final int eye_dist,
                                        final int id, final int eyeBlink, final int mouthAh, final int headYaw, final int headPitch, final int browJump, final STMobile106 Landmarks) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //计算眼之间的距离
                        if (frameNum == 0) {
                            eyeDistance = eye_dist;
                        } else {
                            eyeDistance = eye_dist - eyeDistance;
                        }
                        //计算嘴之间的距离
                        if (frameNum == 0) {
                            lipDistance = eye_dist;
                        } else {
                            lipDistance = eye_dist - lipDistance;
                        }
                    }
                });
//              脸部特征未采集
                if (!FACE_COLLECTED) {
                    if (frameNum < FPS) {
                        faceLandmarks[frameNum] = Landmarks;
                        frameNum++;
                        normalYaw += yaw;
                        normalPitch += pitch;
                    } else {
                        getFaceFeature();
                        FACE_COLLECTED = true;
                        frameNum = 0;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                newfpstText.setText("采集完成，本周疲劳驾驶次数：" + curWeekTimes);
                                newactionText.setText("上下眼平均距离："+eyeDistance + "\n上下唇平均距离："+lipDistance);
                            }
                        });
                    }
                }
//                实时疲劳检测
                else {
                    if (frameNum < DETECTION_FRAMES) {
                        //记录3s内的人脸特征点
                        faceLandmarksPer3s[frameNum] = Landmarks;
                        //记录3s内的人脸yaw角度
                        yawPer3s += yaw;
                        //记录3s内的人脸pitch角度
                        pitchPer3s += pitch;
                        //记录3s内的人脸roll角度
                        frameNum++;
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                PointF[] tempFace = Landmarks.getPointsArray();
                                newactionText.setText("上下眼平均距离：\n"+ (int)((tempFace[72].x - tempFace[73].x)+(tempFace[75].x - tempFace[76].x))/2+ "\n上下唇平均距离：\n"+ (int)((tempFace[86].x - tempFace[94].x)+(tempFace[87].x - tempFace[93].x)+(tempFace[88].x - tempFace[92].x))/3);
                            }
                        });
                    } else {
                        IS_FATIGUE = isFatigue();
                        if (IS_FATIGUE) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    //记录疲劳驾驶的次数
                                    fatiguePerWeekCount++;
                                    streamId = soundPool.play(musicId, 1, 1, 0, 1, 1);
                                    newfpstText.setText("处于疲劳状态！\n本周疲劳次数增加\n点击停止响铃");
                                }
                            });
                        } else {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    newfpstText.setText("小心驾驶！");
                                }
                            });
                        }
                        frameNum = 0;
                        yawPer3s = 0;
                        pitchPer3s = 0;
                    }
                }

            }
        });
    }
//    疲劳次数记录
    private void recordFatigue() {
//        查询当前周次是否存在记录
        Cursor cursor = db.rawQuery("select * from fatigue"+INFO.ID+" where week = ?", new String[]{String.valueOf(week)});
        if (cursor.moveToNext()) {
            //存在记录，更新记录
            db.execSQL("update fatigue"+INFO.ID+" set times = ? where week = ?", new String[]{String.valueOf(fatiguePerWeekCount), String.valueOf(week)});
        } else {
            //不存在记录，插入记录
            db.execSQL("insert into fatigue"+INFO.ID+" (week, times) values (?, ?)", new String[]{String.valueOf(week), String.valueOf(fatiguePerWeekCount)});
        }
    }
    // 判断是否处于疲劳状态
    private boolean isFatigue() {
        int eyeCloseFrames = 0;
        int lipOpenFrames = 0;
        // 判断是否处于疲劳状态
        for (int i = 0; i < DETECTION_FRAMES; i++) {
            PointF[] tempFace = faceLandmarksPer3s[i].getPointsArray();
            double curEyeDistance = ((tempFace[72].x - tempFace[73].x) + (tempFace[75].x - tempFace[76].x)) / 2;
            double curLipDistance = ((tempFace[86].x - tempFace[94].x) + (tempFace[87].x - tempFace[93].x) + (tempFace[88].x - tempFace[92].x)) / 3;
            if (curEyeDistance < 0.4 * eyeDistance) {
                eyeCloseFrames++;
            }
            if (curLipDistance > 1.3 * lipDistance) {
                lipOpenFrames++;
            }
        }
        double yawAver = yawPer3s / DETECTION_FRAMES;
        double pitchAver = pitchPer3s / DETECTION_FRAMES;
        if (eyeCloseFrames >= 0.25 * DETECTION_FRAMES || lipOpenFrames >= 0.3 * DETECTION_FRAMES ||
                pitchAver >= normalPitch + 10 || Math.abs(yawAver) >= normalYaw + 20) {
            return true;
        } else return false;
    }

    //    获取脸部特征
    private void getFaceFeature() {
        double eyeSumDistance = 0, lipSumDistance = 0;
        for (int i = 0; i < FPS; i++) {
            PointF[] tempFace = faceLandmarks[i].getPointsArray();
            eyeSumDistance += ((tempFace[72].x - tempFace[73].x) + (tempFace[75].x - tempFace[76].x)) / 2;
            lipSumDistance += ((tempFace[86].x - tempFace[94].x) + (tempFace[87].x - tempFace[93].x) + (tempFace[88].x - tempFace[92].x)) / 3;
        }
        eyeDistance = eyeSumDistance / FPS;
        lipDistance = lipSumDistance / FPS;
        normalPitch = normalPitch / FPS;
        normalYaw = normalYaw / FPS;
    }
    //熄屏状态或后台状态下退出soundpool
    @Override
    protected void onPause() {
        super.onPause();
        if (soundPool != null) {
            soundPool.release();
            soundPool = null;
        }
    }
    //退出前将疲劳次数记录到数据库
    @Override
    protected void onDestroy() {
        super.onDestroy();
        //停止播放音频
        recordFatigue();
        db.close();
    }
}
