package com.hueljk.ibeacon.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;

/**
 * Created by zc on 2017/2/13.
 */
public class RegisterFragment extends BaseFragment {
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    protected void setData() {
        super.setData();
    }
}
