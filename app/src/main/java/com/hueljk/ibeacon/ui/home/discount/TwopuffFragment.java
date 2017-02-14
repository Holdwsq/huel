package com.hueljk.ibeacon.ui.home.discount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.BaseEntity;
import com.hueljk.ibeacon.ui.BaseFragment;

/**
 * 项目名称：huel
 * 类描述:
 * 创建时间:2017/2/13 0013.
 * 创建人：${ZHANGHAO}.
 * 修改人：${ZHANGHAO}.
 */

public class TwopuffFragment extends BaseFragment {
    private ImageView mImageView;


    public TwopuffFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_two_puff, container, false);
    }

    protected void initView(View view) {
        super.initView(view);
//        mImageView = (ImageView) view.findViewById(R.id.two_puff_return);
//        mImageView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                popSelf();
//            }
//        });


    }
}

