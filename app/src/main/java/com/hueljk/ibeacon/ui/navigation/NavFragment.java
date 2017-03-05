package com.hueljk.ibeacon.ui.navigation;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.manager.PreferenceManager;
import com.hueljk.ibeacon.mode.Goods;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.GuideAdapter;
import com.hueljk.ibeacon.ui.adapter.HomeAdapter;
import com.hueljk.ibeacon.ui.home.ProductFragment;
import com.hueljk.ibeacon.ui.setting.LoginFragment;
import com.sensoro.beacon.kit.Beacon;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Request;

/**
 * 导购模式页面
 */
public class NavFragment extends BaseFragment implements View.OnClickListener{
    private GuideAdapter mGuideAdapter;
    private ImageView mGuideReturnImg;
    private GridView mGridView;
    private List<Goods> goods;
    private PreferenceManager mPreferenceManager;
    public List<String> bs = new ArrayList<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_nav, container, false);
    }
    @Override
    protected void initView(View view) {
        super.initView(view);
        mGuideReturnImg = (ImageView)view.findViewById(R.id.guide_return);
        mGridView=(GridView)view.findViewById(R.id.guide_gridView);
        mGuideAdapter = new GuideAdapter(mContext,null);
        mGridView.setAdapter(mGuideAdapter);

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
    protected void setListener() {
        super.setListener();
        /**
         * 点击事件
         *
         * 1.view控件的点击事件（botton，imageview等）：View.OnClickListener()
         * 2.列表的点击事件（item子控件的点击事件）：AdapterView.OnItemClickListener()
         */
        mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //goods.get(position).getId()当前位置的商品id
                //Toast.makeText(mContext,"goods id is -- "+goods.get(position).getId(),Toast.LENGTH_SHORT).show();

                //跳转到详情页
                //fragment之间的传值，需要在showFragment方法中增加一个参数
                Bundle bundle = new Bundle();
                bundle.putParcelable("goodsdetail",goods.get(position));
                bundle.putInt("goodsId",goods.get(position).getId());

                mMainActivity.showFragment(ProductFragment.class,"goodslist_2_detail",bundle);
            }
        });
        mGuideAdapter.setOnCartClickListener(new GuideAdapter.CallBack() {
            @Override
            public void onCartClick(View v, int position) {
                // Toast.makeText(getContext(),"----"+position,Toast.LENGTH_SHORT).show();
                mPreferenceManager = PreferenceManager.getInstance();

                if(mPreferenceManager.getLoginStatus()){
                    Toast.makeText(getContext(),"成功加入购物车",Toast.LENGTH_SHORT).show();
                }else{
                    mMainActivity.showFragment(LoginFragment.class,"Home_2_Login");
                }

            }
        });
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
        String url= UrlConstants.GuideUrl;
        StringBuffer guideUrl = new StringBuffer();
        guideUrl.append("url?");
        for (String s : bs) {
            guideUrl.append("sn=" + s + "&");
        }
        guideUrl.deleteCharAt(guideUrl.lastIndexOf("&"));
        Log.i("===", "getGoodsFromServer: " + guideUrl.toString());

    }

    @Override
    public void onClick(View v) {

    }
}
