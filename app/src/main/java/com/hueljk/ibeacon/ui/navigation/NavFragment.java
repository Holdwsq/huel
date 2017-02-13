package com.hueljk.ibeacon.ui.navigation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.manager.PreferenceManager;
import com.hueljk.ibeacon.ui.BaseFragment;

/**
 *导航页面
 */
public class NavFragment extends BaseFragment {


    public NavFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav, container, false);
    }

    private PreferenceManager mPreferenceManager;
    @Override
    protected void initView(View view) {
        super.initView(view);

        mPreferenceManager = PreferenceManager.getInstance();

        if(mPreferenceManager.getLoginStatus()){
            Toast.makeText(getContext(),"11111111111",0).show();
        }
    }

}
