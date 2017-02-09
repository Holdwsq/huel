package com.hueljk.ibeacon.ui.home;


import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.TwoCloActivity;
import com.hueljk.ibeacon.TwoFoodActivity;
import com.hueljk.ibeacon.TwoRyActivity;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.mode.BaseEntity;
import com.hueljk.ibeacon.mode.Goods;
import com.hueljk.ibeacon.mode.Home;
import com.hueljk.ibeacon.mode.CartPro;
import com.hueljk.ibeacon.mode.Product;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.MyAdapter;
import com.hueljk.ibeacon.ui.twoClo.TwoCloFragment;
import com.hueljk.ibeacon.ui.twoFood.TwoFoodFragment;
import com.hueljk.ibeacon.ui.twoRy.TwoRyFragment;
import com.hueljk.ibeacon.utils.DisplayUtils;
import com.hueljk.ibeacon.utils.JsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 首页
 */
public class HomeFragment extends BaseFragment {
    private GridView mGridView;
    private static OkHttpClient client;
    private MyAdapter mAdapter;
    private EditText mEditText;
    private TextView shipin_tv;
    private TextView riyong_tv;
    private TextView clothes_tv;
    private TextView shengxian_tv;
    private ImageView homezp_img;
    private ImageView homemj_img;
    private ImageView homeph_img;


    static {
        client = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).build();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        //hideSoftKeyboard();
        mGridView = (GridView) view.findViewById(R.id.product_gridView);


        //ImageView category_iv = (ImageView) view.findViewById(R.id.category_img);
        shipin_tv = (TextView) view.findViewById(R.id.shipin_tv);
        riyong_tv = (TextView) view.findViewById(R.id.riyong_tv);
        clothes_tv = (TextView) view.findViewById(R.id.clothes_tv);
        shengxian_tv = (TextView) view.findViewById(R.id.shengxian_tv);
        homezp_img = (ImageView) view.findViewById(R.id.homezp_img);
        homemj_img = (ImageView) view.findViewById(R.id.homemj_img);
        homeph_img = (ImageView) view.findViewById(R.id.homeph_img);


    }

    @Override
    protected void setData() {
        super.setData();
        List<Goods> goods = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Goods mGoods = new Goods(i, "", "维达抽纸", 1000, "", 80);
            goods.add(mGoods);
        }
        mAdapter = new MyAdapter(getContext(), goods);

        mGridView.setAdapter(mAdapter);
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();

        clothes_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "--", Toast.LENGTH_SHORT).show();
                mMainActivity.showFragment(TwoCloFragment.class, "Home_2_Clo");
                //Intent intent = new Intent(getContext(), TwoCloActivity.class);
                //startActivity(intent);
            }
        });

        riyong_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "--", Toast.LENGTH_SHORT).show();
                mMainActivity.showFragment(TwoRyFragment.class, "Home_2_Ry");
                //Intent intent = new Intent(getContext(), TwoRyActivity.class);
                //startActivity(intent);
            }
        });
        shipin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "--", Toast.LENGTH_SHORT).show();
                mMainActivity.showFragment(TwoFoodFragment.class, "Home_2_Ry");

                // Intent intent = new Intent(getContext(), TwoFoodActivity.class);
                //startActivity(intent);

            }
        });
    }

    public void execute() throws Exception {

        Request request = new Request.Builder()
                .url(UrlConstants.HomeUrl)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("tag", "error");
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                final String ret = response.body().string();
                getActivity().runOnUiThread(new Runnable() {
                    @Override
                    public void run() {

                        //Type listType = new TypeToken<List<BaseEntity>>(){}.getType();
                        //List<BaseEntity> list = JsonUtils.parse(ret,listType);

                        //Log.d("-------", ret);
                        Type listType = new TypeToken<Result<Home>>() {
                        }.getType();
                        Result<Home> listResult = JsonUtils.parse(ret, listType);
                        if (listResult.mCode == 200) {
                            Home homelist = listResult.mData;
                            //更新banner
                            //更新dicounts
                            List<BaseEntity> discounts = homelist.getDiscounts();
                            Log.d("----",discounts.toString());

                            Glide
                                    .with(mContext)
                                    .load(UrlConstants.BannerDisUrl + discounts.get(0).getUrl())
                                    .placeholder(R.drawable.paper)
                                    .error(R.drawable.paper)
                                    .into(homemj_img);
                            Glide
                                    .with(mContext)
                                    .load(UrlConstants.BannerDisUrl + discounts.get(1).getUrl())
                                    .placeholder(R.drawable.shangpin1)
                                    .error(R.drawable.shangpin1)
                                    .into(homezp_img);
                            Glide
                                    .with(mContext)
                                    .load(UrlConstants.BannerDisUrl + discounts.get(2).getUrl())
                                    .placeholder(R.drawable.shangpin1)
                                    .error(R.drawable.shangpin1)
                                    .into(homeph_img);


                            //更新goods
                            List<Goods> goods = homelist.getGoods();
                            int height = goods.size() / 2 * 225 + 60;
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mGridView.getLayoutParams();
                            DisplayUtils.init(getContext());
                            params.height = DisplayUtils.dip2px(height);
                            mGridView.setLayoutParams(params);
                            //Log.d("-------", goods.toString());
                            mAdapter.update(goods);

                        }

                        // 解析json数据得到bean

                    }
                });

            }
        });
    }
}
