package com.hueljk.ibeacon.ui.cart;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.CartPro;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.CartAdapter;

import java.util.ArrayList;
import java.util.List;


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
                Toast.makeText(getActivity(),"listview的item被点击了！",
                Toast.LENGTH_SHORT).show();
            }
        });
    }
}
