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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        setListener();
        setData();
    }

    protected void initView() {

    }

    protected void setListener() {

    }

    protected void setData() {
    }
}