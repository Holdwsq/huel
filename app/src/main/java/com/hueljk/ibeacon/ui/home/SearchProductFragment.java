package com.hueljk.ibeacon.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;

/**
 * Created by zc on 2017/2/14.
 */
public class SearchProductFragment extends BaseFragment{
    private GridView mSearchProductGV;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.search_product,container,false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mSearchProductGV=(GridView)view.findViewById(R.id.search_gv);
    }

    @Override
    protected void setListener() {
        super.setListener();
    }

    @Override
    protected void setData() {
        super.setData();
    }
}
