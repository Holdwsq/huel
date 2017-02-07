package com.hueljk.ibeacon.ui.twoClo;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.mode.Clothes;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.MyAdpter_Clothes;
import com.hueljk.ibeacon.ui.home.HomeFragment;
import com.hueljk.ibeacon.utils.DisplayUtils;
import com.hueljk.ibeacon.utils.JsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zc on 2017/2/7.
 */
public class TwoCloFragment extends BaseFragment{
    private GridView mGridView;
    private static OkHttpClient client;
    private MyAdpter_Clothes adapter;
    private ImageView home_img;
    static {
        client = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).build();
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_two_clo, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mGridView=(GridView)view.findViewById(R.id.clothes_gridView);

        home_img=(ImageView)view.findViewById(R.id.two_cloreturn_img);
    }

    @Override
    protected void setData() {

        super.setData();
        List<Clothes> clothes = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Clothes clothes1 = new Clothes("",1000,"女式上衣");
            clothes.add(clothes1);
        }
        /*MyAdpter_Clothes adapter = new MyAdpter_Clothes(this,clothes);
        mGridView.setAdapter(adapter);*/
        adapter=new MyAdpter_Clothes(getContext(),clothes);
        mGridView.setAdapter(adapter);
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
        home_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMainActivity.showFragment(HomeFragment.class,"Clo_2_Home");
                //Intent intent=new Intent(TwoCloActivity.this,HomeFragment.class);
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
                        Type listType = new TypeToken<Result<List<Clothes>>>() {
                        }.getType();
                        Result<List<Clothes>> listResult = JsonUtils.parse(ret, listType);
                        if (listResult.mCode == 200) {
                            List<Clothes> clothes = listResult.mData;
                            adapter .update(clothes);

                        }

                        // 解析json数据得到bean

                    }
                });

            }
        });
    }
}
