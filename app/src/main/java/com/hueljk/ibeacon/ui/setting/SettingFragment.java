package com.hueljk.ibeacon.ui.setting;


import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.location.GpsStatus;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.navigation.NavFragment;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 我的个人中心
 */
public class SettingFragment extends BaseFragment implements View.OnClickListener {
    private String[] mItems = new String[]{"会员专区", "我的优惠券", "我的足迹", "邀请好友", "帮助与客服", "商家信息",};
    private ListView mListView;
    private View mSettingFragment;
    private TextView mset_tx;
    private ImageView mperson_headimg;
    private TextView mperson_name;
    private TextView mvip_value;
    private TextView mmy_collect;
    private TextView mmy_foucs;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_setting, container, false);


    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mset_tx = (TextView) view.findViewById(R.id.set_tx);
        mperson_headimg = (ImageView) view.findViewById(R.id.person_headimg);
        mperson_name = (TextView) view.findViewById(R.id.user_name);
        mvip_value = (TextView) view.findViewById(R.id.vip_value);
        mmy_collect = (TextView) view.findViewById(R.id.my_collect);
        mmy_foucs = (TextView) view.findViewById(R.id.my_foucs);
        mset_tx.setOnClickListener(this);
        mperson_headimg.setOnClickListener(this);
        mperson_name.setOnClickListener(this);
        mvip_value.setOnClickListener(this);
        mmy_collect.setOnClickListener(this);
        mmy_foucs.setOnClickListener(this);

        mListView = (ListView) view.findViewById(R.id.set_listview);
        mListView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mItems));
//listview 点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(), "listview被点击了，位置是" + position, Toast.LENGTH_SHORT).show();

                switch (position) {
                    case 0:
                        //跳转会员专区
                        mMainActivity.showFragment(NavFragment.class, "MINE_2_HUIYUAN");
                        break;
                    case 1:
                        mMainActivity.showFragment(NavFragment.class, "MINE_2_HUIYUAN");
                        break;
                    case 2:
                        break;
                }

            }
        });


    }


    public void onClick(View v) {
        //参数view是当前被点击的控件
        switch (v.getId()) {
            case R.id.set_tx:
                Toast.makeText(getContext(), "你点击了设置", Toast.LENGTH_SHORT).show();
                break;
            case R.id.person_headimg:
                Toast.makeText(getContext(), "你点击了头像", Toast.LENGTH_SHORT).show();
                break;
            case R.id.person_name:
                Toast.makeText(getContext(), "你点击了用户名", Toast.LENGTH_SHORT).show();
                break;
            case R.id.vip_value:
                Toast.makeText(getContext(), "你点击了我的会员值", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_collect:
                Toast.makeText(getContext(), "你点击了我的收藏", Toast.LENGTH_SHORT).show();
                break;
            case R.id.my_foucs:
                Toast.makeText(getContext(), "你点击了我关注的品牌", Toast.LENGTH_SHORT).show();
                break;

        }
    }
}












