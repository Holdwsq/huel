package com.hueljk.ibeacon.ui.home;

import android.media.Image;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.mode.Desc;
import com.hueljk.ibeacon.mode.DescImg;
import com.hueljk.ibeacon.mode.DescPrameter;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.DescAdapter;
import com.hueljk.ibeacon.utils.JsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zc on 2017/2/14.
 */
public class ProductDescFragment extends BaseFragment implements View.OnClickListener {
    private View mDescMenu1, mDescMenu2;
    private View mDescLine1, mDescLine2;
    private ImageView mDescReturnImg;
    private View mCurLine;
    private Bundle mBundle;
    private int mGoodId;
    private Desc mDesc;
    List<DescImg> descImgs = new ArrayList<>();
    private DescPrameter descPrameters;

    private ListView mListView;
    private DescAdapter mAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.product_desc, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mDescReturnImg = (ImageView) view.findViewById(R.id.desc_return);
        mDescMenu1 = view.findViewById(R.id.desc_menu1);
        mDescMenu2 = view.findViewById(R.id.desc_menu2);
        mDescLine1 = view.findViewById(R.id.desc_line_1);
        mDescLine2 = view.findViewById(R.id.desc_line_2);
        mCurLine = mDescLine1;

        mListView = (ListView) view.findViewById(R.id.desc_lv);

        mAdapter = new DescAdapter(mContext, null);
        mListView.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mDescReturnImg.setOnClickListener(this);
        mDescMenu1.setOnClickListener(this);
        mDescMenu2.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        super.setData();
        mBundle = getArguments();
        if (mBundle != null) {
            mGoodId = mBundle.getInt("goodsId");
        }
        //去服务器获取数据
        show();
    }

    @Override
    public void onClick(View v) {
        mCurLine.setVisibility(View.INVISIBLE);
        switch (v.getId()) {
            case R.id.desc_menu1:
                mDescLine1.setVisibility(View.VISIBLE);
                mAdapter.setType(1);
                mCurLine = mDescLine1;
                break;
            case R.id.desc_menu2:
                mDescLine2.setVisibility(View.VISIBLE);
                mAdapter.setType(2);
                mCurLine = mDescLine2;
                break;
            case R.id.desc_return:
                popSelf();
                break;

        }
    }

    private void show() {
        String url = UrlConstants.DescUrl + "?goodsid=" + mGoodId;
        Log.i("-----------", "Desc:" + url);
        Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = mOkHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String jsonStr = response.body().string();
                mMainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.d("------------", "descrun:" + jsonStr);
                        Type type = new TypeToken<Result<Desc>>() {
                        }.getType();
                        Result<Desc> DescResult = JsonUtils.parse(jsonStr, type);
                        if (DescResult.mCode == 200) {
                            mDesc = DescResult.mData;


                            Log.d("---------", "run: " + mDesc.toString());
                            mDesc.setLength(mDesc.getDescImgs().size());
                            mAdapter.update(mDesc, 1);
                        }

                    }
                });
            }
        });
    }
}
