package com.hueljk.ibeacon.ui.setting;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;

public class AboutFrament extends BaseFragment implements View.OnClickListener {
    private ImageView aboutreturn;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_about_frament,container,false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        aboutreturn = (ImageView) view.findViewById(R.id.about_return);
    }

    @Override
    protected void setListener() {
        super.setListener();
        aboutreturn.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        super.setData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.about_return:popSelf();break;
        }
    }
}
