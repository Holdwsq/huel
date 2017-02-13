package com.hueljk.ibeacon.ui.home.banner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;

/**
 * 项目名称：huel
 * 类描述:
 * 创建时间:2017/2/13 0013.
 * 创建人：${ZHANGHAO}.
 * 修改人：${ZHANGHAO}.
 */

public class BannerHfpFragment extends BaseFragment {
    private ImageView mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_banner_hfp1, container, false);}


    protected void initView(View view) {
        super.initView(view);
        mView = (ImageView) view.findViewById(R.id.hfp_return);
        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getContext(), "==", Toast.LENGTH_SHORT).show();
                popSelf();
            }
        });


    }
}


