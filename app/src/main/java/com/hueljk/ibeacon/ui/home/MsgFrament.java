package com.hueljk.ibeacon.ui.home;

import android.content.Context;
import android.media.Image;
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


public class MsgFrament extends BaseFragment implements View.OnClickListener{
    private ImageView return_msg;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_msg_frament,container,false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        return_msg = (ImageView)view.findViewById(R.id.msg_return);
    }

    @Override
    protected void setListener() {
        super.setListener();
        return_msg.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        super.setData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.msg_return:popSelf();return;
        }
    }
}
