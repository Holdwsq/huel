package com.hueljk.ibeacon.ui.home;

import android.annotation.SuppressLint;
import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.mode.Desc;
import com.hueljk.ibeacon.mode.DescImg;
import com.hueljk.ibeacon.mode.DescPrameter;
import com.hueljk.ibeacon.mode.GoodsCommentBean;
import com.hueljk.ibeacon.mode.GoodsInfo;
import com.hueljk.ibeacon.mode.ResponseBean;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.CommentAdapter;
import com.hueljk.ibeacon.ui.adapter.DescAdapter;
import com.hueljk.ibeacon.ui.adapter.GoodsAdapter;
import com.hueljk.ibeacon.ui.adapter.ImageDetailAdapter;
import com.hueljk.ibeacon.utils.JsonUtils;
import com.hueljk.ibeacon.view.SpacesItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 商品详情Fragment
 * Created by wsq on 2017/2/14.
 */
public class ProductDescFragment extends BaseFragment implements View.OnClickListener {
    /**
     * 返回按钮
     */
    private ImageView mDescReturnImg;
    /**
     * bundle 跳转
     */
    private Bundle mBundle;
    /**
     * 商品id
     */
    private String mGoodId;
    private TextView userName, userName2;
    private TextView timeTx;
    private ImageView userPortrait, userPortrait2;
    private TextView price;
    private TextView desc;
    private RecyclerView imageRecycler;
    private RecyclerView commentRecycler;
    private ImageDetailAdapter imageDetailAdapter;
    private CommentAdapter commentAdapter;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_desc, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mDescReturnImg = view.findViewById(R.id.desc_return);
        userName = view.findViewById(R.id.publishName_detail);
        userName2 = view.findViewById(R.id.username_de2);
        timeTx = view.findViewById(R.id.publishInfo_detail);
        userPortrait = view.findViewById(R.id.userPortrait_detail);
        userPortrait2 = view.findViewById(R.id.userPortrait2);
        price = view.findViewById(R.id.price_detail);
        desc = view.findViewById(R.id.desc_detail);
        imageRecycler = view.findViewById(R.id.imageRecycler_detail);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setSmoothScrollbarEnabled(true);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        imageRecycler.setLayoutManager(mLinearLayoutManager);
        imageRecycler.setHasFixedSize(true);
        imageRecycler.setNestedScrollingEnabled(false);
        imageRecycler.addItemDecoration(new SpacesItemDecoration(10));
        imageDetailAdapter = new ImageDetailAdapter();
        List<String> list = new ArrayList<>();
        List<GoodsCommentBean> list2 = new ArrayList<>();
        initData(list, list2);
//        imageDetailAdapter.setData(list);
        imageRecycler.setAdapter(imageDetailAdapter);
        commentRecycler = view.findViewById(R.id.commentRecycler_detail);
        LinearLayoutManager mLinearLayoutManager2 = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setSmoothScrollbarEnabled(true);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        commentRecycler.setLayoutManager(mLinearLayoutManager2);
        commentRecycler.setHasFixedSize(true);
        commentRecycler.setNestedScrollingEnabled(false);
        commentRecycler.addItemDecoration(new SpacesItemDecoration(1));
        commentAdapter = new CommentAdapter();
        commentAdapter.setData(list2);
        commentRecycler.setAdapter(commentAdapter);

    }

    private void initData(List<String> list, List<GoodsCommentBean> list2) {
        String fileUrls = "[\"http://p5khs9up1.bkt.clouddn.com/a23c3d9d16a04f0cb39e03146075da3c.jpg\", \"http://p5khs9up1.bkt.clouddn.com/fc15f8826de24ac8a2fac546371dc0ee.jpg\"]";
        List<String> fileList = JSON.parseObject(fileUrls, new TypeReference<List<String>>() {});
        list.addAll(fileList);

    }

    @Override
    protected void setListener() {
        super.setListener();
        mDescReturnImg.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        super.setData();
        mBundle = getArguments();
        if (mBundle != null) {
            mGoodId = mBundle.getString("goodsId");
        }
        //去服务器获取数据
        show();
    }


    private void show() {
        String url = UrlConstants.DescUrl + "/" + mGoodId;
        Log.i("-----------", "Desc:" + url);
        OkGo.<String>get(url)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                        String data = response.body();
                        // 转为响应数据
                        final ResponseBean<GoodsInfo> resp = JSON.parseObject(data, new TypeReference<ResponseBean<GoodsInfo>>() {});
                        if (response.code() == 200){
                            mMainActivity.runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    if (ResponseBean.STATUS_SUCCESS.equals(resp.getStatus())){
                                        GoodsInfo info = resp.getData();
                                        userName.setText(info.getUserName());
                                        userName2.setText(info.getUserName());
                                        @SuppressLint("SimpleDateFormat")
                                        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
                                        timeTx.setText(simpleDateFormat.format(new Date(info.getCreateTime())));
                                        Glide.with(getContext())
                                                .load(info.getUserPortrait())
                                                .into(userPortrait);
                                        Glide.with(getContext())
                                                .load(info.getUserPortrait())
                                                .into(userPortrait2);
                                        price.setText(String.valueOf(info.getPrice()));
                                        desc.setText(info.getDescription());
                                        imageDetailAdapter.updata(info.getFileUrls());
                                    }
                                }
                            });
                        }else{
                            Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                        }

                    }
                });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.desc_return:
                popSelf();
                break;
            default:
                // 暂无任何处理
                break;
        }
    }
}
