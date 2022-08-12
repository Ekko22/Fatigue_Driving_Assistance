package com.martin.ads.omoshiroi.Fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.martin.ads.omoshiroi.R;
import com.martin.ads.omoshiroi.activity.Login;
import com.martin.ads.omoshiroi.activity.MyInfo;

/**
 * Created by sun on 2022/8/12.
 */

public class FragmentMine extends Fragment {
    private TextView name;
    private Button loginout;
    private LinearLayout myinfo;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmentmine,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        initView();

        //退出监听事件
        loginout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), Login.class));
                getActivity().finish();
            }
        });

        //个人信息监听事件
        myinfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //跳转到我的信息界面
                startActivity(new Intent(getActivity(),MyInfo.class));
            }
        });
    }

    private void initView() {
        loginout = (Button) getActivity().findViewById(R.id.loginout);
        myinfo  = (LinearLayout) getActivity().findViewById(R.id.myinfo);
        name = (TextView)getActivity().findViewById(R.id.name);
    }
}
