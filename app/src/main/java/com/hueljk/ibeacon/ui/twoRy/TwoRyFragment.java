package com.hueljk.ibeacon.ui.twoRy;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.Product;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.MyAdapter;
import com.hueljk.ibeacon.ui.home.HomeFragment;
import com.hueljk.ibeacon.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2017/2/7.
 */
public class TwoRyFragment extends BaseFragment{
    private GridView mGridView;
    private ImageView home_img;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two_ry, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mGridView = (GridView) view.findViewById(R.id.riyong_gridView);
        home_img = (ImageView) view.findViewById(R.id.two_riyohome_img);
    }

    @Override
    protected void setData() {
        super.setData();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Product product = new Product("", "维达抽纸", 1000, 80, R.drawable.cart);
            products.add(product);
        }
        MyAdapter adapter = new MyAdapter(getContext(), products);
        mGridView.setAdapter(adapter);
        int height = 99 / 3 * 220;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mGridView.getLayoutParams();
        DisplayUtils.init(getContext());

        params.height = DisplayUtils.dip2px(height);
        mGridView.setLayoutParams(params);
        home_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.returnHomeFragment(HomeFragment.class,"Ry_2_Home");
               // Intent intent = new Intent(TwoRyActivity.this, HomeFragment.class);
                //startActivity(intent);
            }
        });

    }
}