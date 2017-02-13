package com.hueljk.ibeacon.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;

/**
 * Created by zc on 2017/2/13.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener{
    @Nullable
    private TextView mRegister;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRegister=(TextView)view.findViewById(R.id.register);

    }

    @Override
    protected void setListener() {
        super.setListener();
        mRegister.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        super.setData();
    }

    @Override
    public void onClick(View v) {
        mMainActivity.showFragment(RegisterFragment.class,"Login_2_register");
    }
}
