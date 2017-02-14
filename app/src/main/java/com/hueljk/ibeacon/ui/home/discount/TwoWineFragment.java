package com.hueljk.ibeacon.ui.home.discount;

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
 * 创建时间:2017/2/10 0010.
 * 创建人：${ZHANGHAO}.
 * 修改人：${ZHANGHAO}.
 */

public class TwoWineFragment extends BaseFragment {
    private ImageView mImageView;


    public TwoWineFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_two_wine, container, false);
    }

    protected void initView(View view) {
        super.initView(view);
        mImageView = (ImageView) view.findViewById(R.id.two_wine_return);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });

    }
}

