package com.hueljk.ibeacon.ui.navigation;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.manager.PreferenceManager;
import com.hueljk.ibeacon.mode.BaseEntity;
import com.hueljk.ibeacon.mode.Goods;
import com.hueljk.ibeacon.mode.Home;
import com.hueljk.ibeacon.mode.NevActoin;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.GuideAdapter;
import com.hueljk.ibeacon.ui.adapter.HomeAdapter;
import com.hueljk.ibeacon.ui.home.ProductDescFragment;
import com.hueljk.ibeacon.ui.home.ProductFragment;
import com.hueljk.ibeacon.ui.setting.LoginFragment;
import com.hueljk.ibeacon.utils.DisplayUtils;
import com.hueljk.ibeacon.utils.JsonUtils;
import com.sensoro.beacon.kit.Beacon;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zc on 2017/5/14.
 */
public class ShopGuideFragment extends BaseFragment implements View.OnClickListener {
    private GuideAdapter mAdapter;
    private ImageView mGuideReturnImg;
    private ListView mListView;
    private List<Goods> goods;
    private PreferenceManager mPreferenceManager;
    // public List<String> bs = new ArrayList<>();
//private List<Double> ds= new ArrayList<>();
    public Integer Bn;
    public Double Bd;
    private Button RefreshButton;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_nav, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mListView = (ListView) view.findViewById(R.id.guide_gridView);
        TextView emptyView = new TextView(mContext);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        emptyView.setText("请您先打开蓝牙，或者进入导购范围内！");
        emptyView.setGravity(Gravity.CENTER_HORIZONTAL | Gravity.CENTER_VERTICAL);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup) mListView.getParent()).addView(emptyView);
        mListView.setEmptyView(emptyView);

        mAdapter = new GuideAdapter(mContext, null);
        mListView.setAdapter(mAdapter);

        //初次进入该fragment拿到当前的所有ibeacon的sn信息
        Bn = mMainActivity.BeaconNumber;
        Bd = mMainActivity.MaxBeaconRssi;

        if (Bn != null) {
            Log.i("++++++++", "第一次拿到的数据: " + Bn);
            getGoodsFromServer();
        } else {
            //告诉用户当前没有导购商品
            Log.i("++++++++", "initView: 附近没有导购商品");
            //Toast.makeText(mContext, "附近没有导购商品", Toast.LENGTH_SHORT).show();
        }
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NevActoin messageEvent) {
        // bs = mMainActivity.allBeacons;
        Bn = mMainActivity.BeaconNumber;
        //Log.i("++++++++", "刷新后的数据：" + bs.toString());
        Log.i("++++++++", "刷新后的数据：" + Bn);
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
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //goods.get(position).getId()当前位置的商品id
                //Toast.makeText(mContext,"goods id is -- "+goods.get(position).getId(),Toast.LENGTH_SHORT).show();

                //跳转到详情页
                //fragment之间的传值，需要在showFragment方法中增加一个参数
                Bundle bundle = new Bundle();
                bundle.putParcelable("goodsdetail", goods.get(position));
                bundle.putInt("goodsId", goods.get(position).getId());

                mMainActivity.showFragment(ProductDescFragment.class, "goodslist_2_detail", bundle);
            }
        });
        mAdapter.setOnCartClickListener(new GuideAdapter.CallBack() {
            @Override
            public void onCartClick(View v, int position) {
                // Toast.makeText(getContext(),"----"+position,Toast.LENGTH_SHORT).show();
                mPreferenceManager = PreferenceManager.getInstance();

                if (mPreferenceManager.getLoginStatus()) {
                    addProductToCart(position);
                    Toast.makeText(getContext(), "成功加入购物车", Toast.LENGTH_SHORT).show();
                } else {
                    mMainActivity.showFragment(LoginFragment.class, "Home_2_Login");
                }
            }
        });
    }

    private void addProductToCart(int position) {
        String addCartUrl = UrlConstants.addCartUrl + "?userid=" + mPreferenceManager.getUserId() + "&goodsid=" + goods.get(position).getId() + "&number=" + 1;
        Log.d("-----------", "购物车: " + addCartUrl);
        Request request = new Request.Builder()
                .url(addCartUrl)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("-----", "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonStr = response.body().string();
                mMainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("---------", "addCart: " + jsonStr);
                        Type type = new TypeToken<Result<String>>() {
                        }.getType();
                        //解析Json数据得到结果集
                        Result<String> result = JsonUtils.parse(jsonStr, type);
                        if (result.mCode == 200) {
                            Toast.makeText(getContext(), "成功加入购物车", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "加入购物车失败！", Toast.LENGTH_SHORT).show();
                        }
                    }
                });

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
        /*StringBuffer guideUrl = new StringBuffer();
        guideUrl.append(UrlConstants.GuideUrl + "?");
        for (String s : bs) {
            guideUrl.append("sn=" + s + "&");
        }
        guideUrl.deleteCharAt(guideUrl.lastIndexOf("&"));*/
        String guideUrl = UrlConstants.GuideUrl + "?sn=" + Bn.toString();
        Log.i("===", "getGoodsFromServer: " + guideUrl.toString());
        Request request = new Request.Builder().url(guideUrl.toString()).build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String ret = response.body().string();
                mMainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("---", ret);
                        Type type = new TypeToken<Result<List<Goods>>>() {
                        }.getType();
                        //解析Json数据得到结果集
                        Result<List<Goods>> result = JsonUtils.parse(ret, type);
                        if (result.mCode == 200) {
                            goods = result.mData;
                            Log.d("-------", goods.size() + "");
                            mAdapter.update(goods);
                        }
                    }
                });

            }
        });

    }

    @Override
    public void onClick(View v) {
    }
}
