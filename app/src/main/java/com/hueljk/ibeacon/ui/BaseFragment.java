package com.hueljk.ibeacon.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hueljk.ibeacon.R;

/**
 *
 */
public class BaseFragment extends Fragment {

    /**
     * 首页四个fragment是加载到同一个framlayout里面的，前后叠加，容易出现两个问题
     * 1.页面的叠加: 在布局的根部加上背景颜色即可
     * 2.点击事件的穿透: view.setClickable(true);
     */
    protected MainActivity mMainActivity;

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mMainActivity = (MainActivity) getActivity();

        //处理点击事件的穿透
        view.setClickable(true);
        initView(view);
        setListener();
        setData();
    }

    protected void initView(View view) {

    }

    protected void setListener()  {

    }

    protected void setData() {
    }



}