package com.hueljk.ibeacon.ui.home.discount;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.BaseEntity;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.ImageAdapter;

/**
 * 项目名称：huel
 * 类描述:
 * 创建时间:2017/2/13 0013.
 * 创建人：${ZHANGHAO}.
 * 修改人：${ZHANGHAO}.
 */

public class TwopuffFragment extends BaseFragment implements View.OnClickListener{
    private ImageView puffReturn;
    private GridView puffGridView;
    private Integer []images = {R.drawable.relax1,R.drawable.relax2,R.drawable.relax3,
        R.drawable.relax4,R.drawable.relax5,R.drawable.relax6};
    public TwopuffFragment() {
        // Required empty public constructor
    }

    @Override
    protected void setListener() {
        super.setListener();
        puffReturn.setOnClickListener(this);
        puffGridView.setAdapter(new ImageAdapter(mContext,images));
        puffGridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){//监听事件
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getActivity(), ""+position,Toast.LENGTH_SHORT).show();//显示信息;
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two_puff, container, false);
    }

    protected void initView(View view) {
        super.initView(view);
        puffReturn = (ImageView)view.findViewById(R.id.relax_return);
        puffGridView = (GridView)view.findViewById(R.id.relax_gridview);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.relax_return:popSelf();break;

        }
    }
}

