package com.hueljk.ibeacon.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseFragment {
    private ImageView mImageView;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product, container, false);

    }


    protected void initView(View view) {
        super.initView(view);
        mImageView = (ImageView) view.findViewById(R.id.product_return);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "--", 0).show();
                popSelf();
            }
        });


    }
}
