package com.hueljk.ibeacon.ui.cart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.CartPro;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.CartAdapter;
import com.hueljk.ibeacon.ui.navigation.NavFragment;

import java.util.ArrayList;
import java.util.List;

import static com.hueljk.ibeacon.R.id.textView;


/**
 * 购物车
 */
public class CartFragment extends BaseFragment {

    private ListView mListView;
    private List<CartPro> mProducts = new ArrayList<>();

    private CartAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cart, container, false);
    }


    protected void initView(View view) {
        super.initView(view);
        mListView = (ListView) view.findViewById(R.id.cart_listview);
    }

    @Override
    protected void setData() {
        super.setData();
        for (int i = 1; i < 10; i++) {
            CartPro p = new CartPro(i + "", "女式毛衣", "url", 88, R.drawable.clothes1, R.drawable.checkbox1, "红色", "+", "-", "10");
            p.setSelected(false);
            mProducts.add(p);
        }
        mAdapter = new CartAdapter(getContext(), mProducts);
        if (mListView != null) {
            mListView.setAdapter(mAdapter);
        }


        //设置listview的点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "listview的item被点击了！ --- " + i + " -- " + l,
                        Toast.LENGTH_SHORT).show();
                switch (i) {
                    case 0:
                        //跳转会员专区
                        mMainActivity.showFragment(NavFragment.class, "购物车商品");
                        break;
                    case 1:
                        mMainActivity.showFragment(NavFragment.class, "购物车商品");
                        break;
                    case 2:
                        break;
                }

            }
        });


        //写listview内部item子控件的点击事件
        mAdapter.setOnBoxClickListener(new CartAdapter.CallBack() {
            @Override
            public void onBoxClick(View v, int position) {
                //如果当前不是被选中的状态就设置状态选中，如果是选中状态就取消
                mProducts.get(position).setSelected(!mProducts.get(position).getSelected());
                mAdapter.update(mProducts);


            }
        });
    }
}













