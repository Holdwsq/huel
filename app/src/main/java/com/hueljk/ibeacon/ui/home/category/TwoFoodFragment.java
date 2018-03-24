package com.hueljk.ibeacon.ui.home.category;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.Goods;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.ImageAdapter;
import com.hueljk.ibeacon.ui.adapter.MyAdapter;
import com.hueljk.ibeacon.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2017/2/7.
 */
public class TwoFoodFragment extends BaseFragment{
    private ImageView home_img;
    private GridView gridView;
    private Integer[]img={R.drawable.foodp1,R.drawable.foodp2,R.drawable.foodp3
                            ,R.drawable.foodp4,R.drawable.foodp5,R.drawable.foodp6};
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two_food, container, false);
    }

    @Override
   protected void initView(View view) {
        super.initView(view);
   //     mGridView = (GridView) view.findViewById(R.id.shipin_gridView);
        home_img=(ImageView)view.findViewById(R.id.two_shipinhome_img);
        home_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                popSelf();
            }
        });
        gridView = (GridView)view.findViewById(R.id.food_gridview);
        gridView.setAdapter(new ImageAdapter(mContext,img));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener(){//监听事件
            public void onItemClick(AdapterView<?> parent, View view, int position, long id)
            {
                Toast.makeText(getActivity(), ""+position,Toast.LENGTH_SHORT).show();//显示信息;
            }
        });
    }

   /*  @Override
  protected void setData() {
        super.setData();
        List<Goods> foods = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Goods mFoods = new Goods(i,"", "维达抽纸", 1000, "",80);
            foods.add(mFoods);
        }
        MyAdapter adapter = new MyAdapter(getContext(), foods);

        mGridView.setAdapter(adapter);
        int height = 99 / 3 * 220;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mGridView.getLayoutParams();
        DisplayUtils.init(getContext());

        params.height = DisplayUtils.dip2px(height);
        mGridView.setLayoutParams(params);*/

    }

