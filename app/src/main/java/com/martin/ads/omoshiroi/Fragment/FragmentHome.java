package com.martin.ads.omoshiroi.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.martin.ads.omoshiroi.R;
import com.martin.ads.omoshiroi.HomeActivity;

/**
 * Created by sun on 2022/8/12.
 */

public class FragmentHome extends Fragment {

    private LinearLayout start;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragmenthome,container,false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //逻辑处理
        initView();

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), HomeActivity.class));
            }
        });
    }

    private void initView() {
        start = (LinearLayout)getActivity().findViewById(R.id.start);
    }
}
