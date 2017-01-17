package com.hueljk.ibeacon.ui.setting;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;


/**
 *我的个人中心
 */
public class SettingFragment extends BaseFragment {



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_setting, container, false);
    }

}
