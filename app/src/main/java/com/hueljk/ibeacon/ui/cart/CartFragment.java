
package com.hueljk.ibeacon.ui.cart;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.manager.PreferenceManager;
import com.hueljk.ibeacon.mode.BaseEntity;
import com.hueljk.ibeacon.mode.CartPro;

import com.hueljk.ibeacon.mode.Home;
import com.hueljk.ibeacon.mode.Product;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.CartAdapter;
import com.hueljk.ibeacon.ui.navigation.NavFragment;
import com.hueljk.ibeacon.utils.JsonUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.jar.Attributes;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * 购物车
 */
public class CartFragment extends BaseFragment {

    private ListView mListView;
    private List<CartPro> mProducts = new ArrayList<>();
    private CartAdapter mAdapter;
    private TextView medit_tx;
    private TextView mjiesuan_button;
    private PreferenceManager mPreferenceManager;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        return inflater.inflate(R.layout.fragment_cart, container, false);

    }


    protected void initView(View view) {
        super.initView(view);
        mPreferenceManager = PreferenceManager.getInstance();
        mListView = (ListView) view.findViewById(R.id.cart_listview);
        medit_tx = (TextView) view.findViewById(R.id.edit_tx);
        mjiesuan_button = (TextView) view.findViewById(R.id.jiesuan_button);

    }

    @Override
    protected void setData() {
        super.setData();
        mAdapter = new CartAdapter(getContext(), null);
        mListView.setAdapter(mAdapter);

        //开启新线程，从网络上访问数据
        getDataFromServer();

        //设置listview的点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "listview的item被点击了！ --- " + i + " -- " + l,
                        Toast.LENGTH_SHORT).show();
                switch (i) {
                    case 0:
                        mMainActivity.showFragment(NavFragment.class, "购物车商品");
                        break;
                    case 1:
                        mMainActivity.showFragment(NavFragment.class, "购物车商品");

                        //1.数据更改全选状态
                        for (CartPro p :
                                mProducts) {
                            p.setSelected(true);
                        }
                        //2.刷新界面
                        mAdapter.update(mProducts);
                        break;
                    case 2:
                        break;
                }
            }
        });

        //写listview内部item子控件的点击事件，接收三个控件的点击事件
        mAdapter.setOnBoxClickListener(new CartAdapter.CallBack() {
            @Override
            public void onBoxClick(View v, int position) {
                //如果当前不是被选中的状态就设置状态选中，如果是选中状态就取消
                switch (v.getId()) {
                    case R.id.pro_checkbox:
                        mProducts.get(position).setSelected(!mProducts.get(position).getSelected());
                        //更新数据、刷新界面
                        mAdapter.update(mProducts);
                        Toast.makeText(getContext(), "--", Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.pro_add:
                        Toast.makeText(getContext(), "--1", Toast.LENGTH_SHORT).show();

                        /**
                         * 1.调用购物车商品增加接口
                         * 2.本地商品数据增加
                         * 3.刷新购物车数据
                         */
                        break;
                    case R.id.pro_minus:
                        Toast.makeText(getContext(), "--2", Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });

        //编辑和分享的点击事件
        medit_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "您点击了编辑", Toast.LENGTH_SHORT).show();

            }
        });

        mjiesuan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "您点击了结算", Toast.LENGTH_SHORT).show();

            }
        });

    }

    private void getDataFromServer() {
        //okhttp  get方法访问服务器
        String url = UrlConstants.baseUrl + "/Cart?userid=" + mPreferenceManager.getUserId();
        Log.d("-----------", "购物车: " + url);
        Request request = new Request.Builder()
                .url(url)
                .build();

        Call call = mOkHttpClient.newCall(request);
        //开启新线程执行网络耗时操作
        //以下就是新线程的操作，新线程和主线程之间的数据不能直接相互调用
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("-----", "error");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //拿到服务器json数据，但是本方法是在主线程之外，
                final String jsonStr = response.body().string();

                //使用以下方法将结果切换回主线程
                mMainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //run方法内部是主线程
                        Log.d("---------", "run: " + jsonStr);
                        mPreferenceManager = PreferenceManager.getInstance();
                        Type listType = new TypeToken<Result<List<CartPro>>>() {
                        }.getType();
                        //解析Json数据得到结果集
                        Result<List<CartPro>> listResult = JsonUtils.parse(jsonStr, listType);
                        if (listResult.mCode == 200) {
                            Toast.makeText(getContext(), "加载成功！", Toast.LENGTH_SHORT).show();
                            if (listResult.mData != null) {
                                Log.d("-----", "run: " + listResult.mData);
                                mAdapter.update(listResult.mData);
                            }
                        }
                    }
                });

            }
        });
    }

}