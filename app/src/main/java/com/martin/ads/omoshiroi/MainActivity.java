package com.martin.ads.omoshiroi;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;

import android.view.Window;
import android.view.WindowManager;
import com.ashokvarma.bottomnavigation.BottomNavigationBar;
import com.ashokvarma.bottomnavigation.BottomNavigationItem;
import com.martin.ads.omoshiroi.Adapter.SectionsPagerAdapter;
import com.martin.ads.omoshiroi.Fragment.FragmentHome;
import com.martin.ads.omoshiroi.Fragment.FragmentMine;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sun on 2022/8/12.
 */

public class MainActivity extends AppCompatActivity implements BottomNavigationBar.OnTabSelectedListener, ViewPager.OnPageChangeListener {

    private ViewPager viewPager;
    private BottomNavigationBar bottomNavigationBar;

    private List<Fragment> fragments;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FORCE_NOT_FULLSCREEN);
        //设置全屏显示
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN
                | WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        //去掉标题栏
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewPager = (ViewPager)findViewById(R.id.viewpager);
        bottomNavigationBar = (BottomNavigationBar)findViewById(R.id.bottom);

        initView();
        
    }

    private void initView() {
        //初始化
        initViewPager();
        initBottomNavigationBar();
    }

    private void initViewPager() {

        //配置viewpager
        viewPager.setOffscreenPageLimit(2);

        fragments =new ArrayList<Fragment>();
        fragments.add(new FragmentHome());
        fragments.add(new FragmentMine());

        viewPager.setAdapter(new SectionsPagerAdapter(getSupportFragmentManager(),fragments));
        viewPager.addOnPageChangeListener(this);
        viewPager.setCurrentItem(0);


    }

    private void initBottomNavigationBar() {

        bottomNavigationBar.setTabSelectedListener(this);
        bottomNavigationBar.clearAll();
        bottomNavigationBar.setMode(BottomNavigationBar.MODE_FIXED);   //自适应大小
        bottomNavigationBar.setBackgroundStyle(BottomNavigationBar.BACKGROUND_STYLE_DEFAULT);
        bottomNavigationBar.setBarBackgroundColor(R.color.white)
                .setActiveColor(R.color.bg_app_color)
                .setInActiveColor(R.color.black);
        bottomNavigationBar.addItem(new BottomNavigationItem(R.drawable.mobilephone_fill,"检测").setInactiveIconResource(R.drawable.mobilephone))
                .addItem(new BottomNavigationItem(R.drawable.mine_fill,"我的").setInactiveIconResource(R.drawable.mine))
                .setFirstSelectedPosition(0)
                .initialise();

    }

    @Override
    public void onTabSelected(int position) {
        //切换界面
        viewPager.setCurrentItem(position);
    }

    @Override
    public void onTabUnselected(int position) {

    }

    @Override
    public void onTabReselected(int position) {

    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int i) {
        //切换界面
        bottomNavigationBar.selectTab(i);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }
}
