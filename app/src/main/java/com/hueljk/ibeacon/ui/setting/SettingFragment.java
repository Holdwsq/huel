package com.hueljk.ibeacon.ui.setting;


import android.app.Activity;
import android.app.ListFragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
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
public class SettingFragment extends BaseFragment {
    private String[] mItems = new String[]{"会员专区", "我的优惠券", "我的足迹","邀请好友","帮助与客服","商家信息",};
    private ListView mListView;
    private View mSettingFragment;


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return  inflater.inflate(R.layout.fragment_setting, container, false);

    }


    @Override
    protected void initView(View view) {
        super.initView(view);
        mListView = (ListView)view.findViewById(R.id.set_listview);
        mListView.setAdapter(new ArrayAdapter<String>(getContext(), android. R.layout.simple_list_item_1,mItems ));
//listview 点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Toast.makeText(getContext(),"listview被点击了，位置是"+position,Toast.LENGTH_SHORT).show();

                switch (position){
                    case 0:
                        //跳转会员专区
                        mMainActivity.showFragment(NavFragment.class,"MINE_2_HUIYUAN");
                        break;
                    case 1:
                        mMainActivity.showFragment(NavFragment.class,"MINE_2_HUIYUAN");
                        break;
                    case 2:break;
                }

            }
        });
    }






}










