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
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.ImageAdapter;

/**
 * 项目名称：huel
 * 类描述:
 * 创建时间:2017/2/13 0013.
 * 创建人：${ZHANGHAO}.
 * 修改人：${ZHANGHAO}.
 */

public class TwoPaperFragment extends BaseFragment {
    private ImageView mImageView;
    private GridView paperGridview;
    private Integer []images = {R.drawable.paper1,R.drawable.paper2,R.drawable.paper3,R.drawable.paper4};
    public TwoPaperFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_two_paper, container, false);
    }

    protected void initView(View view) {
        super.initView(view);
        mImageView = (ImageView) view.findViewById(R.id.two_paper_return);
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
        paperGridview = (GridView)view.findViewById(R.id.paper_gridview);
        paperGridview.setAdapter(new ImageAdapter(mContext,images));
        paperGridview.setOnItemClickListener(new AdapterView.OnItemClickListener(){//监听事件
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getActivity(), ""+position,Toast.LENGTH_SHORT).show();//显示信息;
            }
        });
    }
}


