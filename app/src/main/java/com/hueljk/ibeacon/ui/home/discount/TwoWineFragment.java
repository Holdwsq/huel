package com.hueljk.ibeacon.ui.home.discount;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.ImageAdapter;

/**
 * 项目名称：huel
 * 类描述:
 * 创建时间:2017/2/10 0010.
 * 创建人：${ZHANGHAO}.
 * 修改人：${ZHANGHAO}.
 */

public class TwoWineFragment extends BaseFragment {
    private ImageView mImageView;
    private GridView gridview;
    private Integer []img={R.drawable.two_wine_img5,R.drawable.two_wine_img6,R.drawable.two_wine_img7,
            R.drawable.two_wine_img8,R.drawable.two_wine_img9,R.drawable.two_wine_img10};
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
        gridview = (GridView)view.findViewById(R.id.wine_gridview);
        gridview.setAdapter(new ImageAdapter(mContext,img));
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){//监听事件
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getActivity(), ""+position,Toast.LENGTH_SHORT).show();//显示信息;
            }
        });
    }
}

