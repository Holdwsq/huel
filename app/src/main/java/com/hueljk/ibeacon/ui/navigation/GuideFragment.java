package com.hueljk.ibeacon.ui.navigation;

import android.app.AlertDialog;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.manager.PreferenceManager;
import com.hueljk.ibeacon.mode.Goods;
import com.hueljk.ibeacon.mode.NevActoin;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.GuideAdapter;
import com.hueljk.ibeacon.ui.home.ProductFragment;
import com.hueljk.ibeacon.ui.setting.LoginFragment;
import com.hueljk.ibeacon.utils.JsonUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zc on 2017/3/18.
 */
public class GuideFragment extends BaseFragment {
    private ImageView mGuideReturnImg;
    private List<Goods> goods;
    private PreferenceManager mPreferenceManager;
    // public List<String> bs = new ArrayList<>();
    //private List<Double> ds= new ArrayList<>();
    public String Bn;
    public Double Bd;
    private Goods mGoods;
    private int mGoodsId;
    private ImageView mProductImg;
    private TextView mproduct_desc;
    private TextView mproduct_price;
    private TextView mproduct_num;
    private TextView mDescTX;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mGuideReturnImg = (ImageView) view.findViewById(R.id.product_return);
        mProductImg=(ImageView)view.findViewById(R.id.product_img);
        mproduct_desc=(TextView)view.findViewById(R.id.product_desc);
        mproduct_price=(TextView) view.findViewById(R.id.product_price);
        mproduct_num=(TextView)view.findViewById(R.id.product_num);
        mDescTX=(TextView)view.findViewById(R.id.desc_tx);

        //初次进入该fragment拿到当前的所有ibeacon的sn信息
       // Bn = mMainActivity.BeaconNumber;
        //Bd=mMainActivity.MinBeaconDistance;

        if (Bn != null) {
            Log.i("++++++++", "第一次拿到的数据: " +Bn);
            getGoodsFromServer();
        } else {
            //告诉用户当前没有导购商品
            Log.i("++++++++", "initView: 附近没有导购商品");
            //Toast.makeText(mContext, "附近没有导购商品", Toast.LENGTH_SHORT).show();

            new AlertDialog.Builder(getContext())
                    .setTitle("")
                    .setMessage("请您先打开蓝牙或者进入导购范围内")
                    .setPositiveButton("确定",null)
                    .show();
        }
        EventBus.getDefault().register(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(NevActoin messageEvent) {
        // bs = mMainActivity.allBeacons;
       // Bn=mMainActivity.BeaconNumber;
        // Log.i("++++++++", "刷新后的数据：" + bs.toString());
        Log.i("++++++++", "刷新后的数据：" + Bn);
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
        /*StringBuffer guideUrl = new StringBuffer();
        guideUrl.append(UrlConstants.GuideUrl + "?");
        for (String s : bs) {
            guideUrl.append("sn=" + s + "&");
        }
        guideUrl.deleteCharAt(guideUrl.lastIndexOf("&"));*/
        String guideUrl= UrlConstants.GuideUrl+"?sn="+Bn;
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
                            mGoods=goods.get(0);
                            mGoodsId=mGoods.getId();
                            Glide
                                    .with(mContext)
                                    .load(UrlConstants.HomePicUrl+mGoods.getPurl())
                                    .placeholder(R.drawable.shangpin1)
                                    .error(R.drawable.shangpin1)
                                    .into(mProductImg);
                            mproduct_desc.setText(mGoods.getPdesc());
                            mproduct_num.setText("月销"+mGoods.getSold() + "笔");
                            mproduct_price.setText("￥" + mGoods.getPrice());


                            Log.i("------------", "GuideFragmentGoodsDetail: "+mGoods.toString());

                        }else{

                        }
                    }
                });

            }
        });

    }


}


