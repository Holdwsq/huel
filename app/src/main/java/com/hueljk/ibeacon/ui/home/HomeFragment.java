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

import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.TwoCloActivity;
import com.hueljk.ibeacon.TwoFoodActivity;
import com.hueljk.ibeacon.TwoRyActivity;
import com.hueljk.ibeacon.constants.UrlConstants;
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

    }

    @Override
    protected void setData() {
        super.setData();
        List<Product> products = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Product product = new Product("", "维达抽纸", 1000, 80, R.drawable.cart);
            products.add(product);
        }
        mAdapter = new MyAdapter(getContext(), products);

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
        int height = 99 / 3 * 220;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mGridView.getLayoutParams();
        DisplayUtils.init(getContext());
        params.height = DisplayUtils.dip2px(height);
        mGridView.setLayoutParams(params);
        clothes_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "--", Toast.LENGTH_SHORT).show();
                mMainActivity.showFragment(TwoCloFragment.class,"Home_2_Clo");
                //Intent intent = new Intent(getContext(), TwoCloActivity.class);
                //startActivity(intent);
            }
        });

        riyong_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "--", Toast.LENGTH_SHORT).show();
                mMainActivity.showFragment(TwoRyFragment.class,"Home_2_Ry");
                //Intent intent = new Intent(getContext(), TwoRyActivity.class);
                //startActivity(intent);
            }
        });
        shipin_tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "--", Toast.LENGTH_SHORT).show();
                mMainActivity.showFragment(TwoFoodFragment.class,"Home_2_Ry");

               // Intent intent = new Intent(getContext(), TwoFoodActivity.class);
                //startActivity(intent);

            }
        });
    }

    public void execute() throws Exception {

        Request request = new Request.Builder()
                .url(UrlConstants.clothing)
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
                        Type listType = new TypeToken<Result<Home>>() {
                        }.getType();
                        Result<Home> listResult = JsonUtils.parse(ret, listType);
                        if (listResult.mCode == 200) {
                            Home homelist = listResult.mData;
                            List<Product> products = homelist.getProducts();
                            mAdapter.update(products);

                        }

                        // 解析json数据得到bean

                    }
                });

            }
        });
    }
}
