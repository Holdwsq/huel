package com.hueljk.ibeacon.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.home.HomeFragment;

import static com.hueljk.ibeacon.R.drawable.return_sc;

/**
 * 项目名称：HuelJk
 * 类描述:
 * 创建时间:2017/2/9 0009.
 * 创建人：${ZHANGHAO}.
 * 修改人：${ZHANGHAO}.
 */

public class HomeSec extends BaseFragment {
    private ImageView return_sc;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.home_sec, container, false);
    }

    protected void initView(View view) {
        super.initView(view);
        return_sc = (ImageView) view.findViewById(R.id.return_sc);
        return_sc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "您好", Toast.LENGTH_SHORT);
                mMainActivity.showFragment(HomeFragment.class, "");

            }
        });
    }
}



