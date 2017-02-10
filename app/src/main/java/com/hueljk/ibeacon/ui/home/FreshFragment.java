package com.hueljk.ibeacon.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class FreshFragment extends BaseFragment {
    private ImageView mImageView;


    public FreshFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_fresh, container, false);
    }

    protected void initView(View view){
        super.initView(view);
        mImageView = (ImageView) view.findViewById(R.id.return_sc_img);


        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"--",Toast.LENGTH_SHORT).show();
                popSelf();
            }
        });

    }


}
