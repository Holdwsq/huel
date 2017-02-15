package com.hueljk.ibeacon.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;
import android.widget.ImageView;

import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.mode.Goods;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.HomeAdapter;
import com.hueljk.ibeacon.ui.adapter.MyAdapter;
import com.hueljk.ibeacon.utils.JsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zc on 2017/2/14.
 */
public class SearchProductFragment extends BaseFragment implements  View.OnClickListener{
    private GridView mSearchProductGV;
    private MyAdapter mAdapter;
    private List<Goods> mGoodsList;
    private String mKeys;
    private Bundle mBundle;
    private ImageView mReturnImage;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
       return inflater.inflate(R.layout.search_product,container,false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mSearchProductGV=(GridView)view.findViewById(R.id.search_gv);
        mReturnImage=(ImageView)view.findViewById(R.id.search_return);
        mAdapter=new MyAdapter(mContext,null);
        mSearchProductGV.setAdapter(mAdapter);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mReturnImage.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        super.setData();
        mBundle = getArguments();
        if(mBundle!=null){
                mKeys=mBundle.getString("searchKeys");
                String url= UrlConstants.SearchUrl+"?keys="+mKeys;
            Log.d("---------","search:"+url);
            Request request = new Request.Builder().url(url).build();
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
                            Log.d("--------","searchRun:" + jsonStr);
                            Type type = new TypeToken<Result<List<Goods>>>(){}.getType();
                            Result<List<Goods>> SearchResult = JsonUtils.parse(jsonStr,type);
                            if(SearchResult.mCode == 200){
                                mGoodsList=SearchResult.mData;
                                mAdapter.update(mGoodsList);
                            }
                        }
                    });
                }
            });

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.search_return:
                popSelf();
                break;
        }
    }
}
