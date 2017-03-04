package com.hueljk.ibeacon.ui.navigation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.manager.PreferenceManager;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.sensoro.beacon.kit.Beacon;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * 导购模式页面
 */
public class NavFragment extends BaseFragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav, container, false);
    }

    private PreferenceManager mPreferenceManager;
    public List<String> bs = new ArrayList<>();

    @Override
    protected void initView(View view) {
        super.initView(view);
        //初次进入该fragment拿到当前的所有ibeacon的sn信息
        bs = mMainActivity.allBeacons;

        if (bs.size() > 0) {
            Log.i("++++++++", "第一次拿到的数据: " + bs.toString());
            getGoodsFromServer();
        } else {
            //告诉用户当前没有导购商品
            Log.i("++++++++", "initView: 附近没有导购商品");
            Toast.makeText(mContext, "附近没有导购商品", Toast.LENGTH_SHORT).show();
        }

        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(String messageEvent) {
        bs = mMainActivity.allBeacons;
        Log.i("++++++++", "刷新后的数据：" + bs.toString());
        getGoodsFromServer();
    }

    @Override
    public void onDestroy() {
        EventBus.getDefault().unregister(this);
    }


    /**
     * 去服务器获取商品信息列表
     */
    private void getGoodsFromServer() {
        //url?sn=a&sn=b&sn=c
        StringBuffer sb = new StringBuffer();
        sb.append("url?");
        for (String s : bs) {
            sb.append("sn=" + s + "&");
        }

        sb.deleteCharAt(sb.lastIndexOf("&"));

        Log.i("===", "getGoodsFromServer: " + sb.toString());
    }
}
