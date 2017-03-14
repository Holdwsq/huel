
package com.hueljk.ibeacon.ui.cart;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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

import com.hueljk.ibeacon.mode.Goods;
import com.hueljk.ibeacon.mode.Home;
import com.hueljk.ibeacon.mode.Product;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.CartAdapter;
import com.hueljk.ibeacon.ui.home.ProductFragment;
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
    private CheckBox allselect;
    private TextView total_Price;
    private float totalprice;
    private Goods product = new Goods();
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
        allselect=(CheckBox)view.findViewById(R.id.allslecet);
        total_Price=(TextView)view.findViewById(R.id.total_price);
    }

    @Override
    protected void setData() {
        super.setData();
        totalprice = 0;


        TextView emptyView = new TextView(mContext);
        emptyView.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.FILL_PARENT));
        emptyView.setText("您还没有登录，请前去登录！");
        emptyView.setGravity(Gravity.CENTER_HORIZONTAL| Gravity.CENTER_VERTICAL);
        emptyView.setVisibility(View.GONE);
        ((ViewGroup)mListView.getParent()).addView(emptyView);
        mListView.setEmptyView(emptyView);

        mAdapter = new CartAdapter(getContext(), null);
        mListView.setAdapter(mAdapter);
        total_Price.setText(totalprice+"");
        //开启新线程，从网络上访问数据
        getDataFromServer();

        //设置listview的点击事件
        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(), "listview的item被点击了！ --- " + i + " -- " + l,
                        Toast.LENGTH_SHORT).show();
                getGoodsFromServer(i);

            }
        });
        /*  为全选按钮设置监听*/
        allselect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener(){

            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean isChecked) {
                if(isChecked){
                    //1.数据更改全选状态
                    for (CartPro p :
                            mProducts) {
                        p.setSelected(true);
                    }
                    totalprice=0;
                    for (CartPro p :mProducts){
                        totalprice+=p.getPrice()*Integer.parseInt(p.getNumber());
                    }
                    total_Price.setText(totalprice+"");
                    //2.刷新界面
                    mAdapter.update(mProducts);
                }else{
                    //1.数据更改全选状态
                    for (CartPro p :
                            mProducts) {
                        p.setSelected(false);
                    }
                    //2.刷新界面
                    totalprice=0;
                    total_Price.setText(totalprice+"");
                    mAdapter.update(mProducts);
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
                        if (mProducts.get(position).getSelected()){
                            totalprice-=mProducts.get(position).getPrice()*Integer.parseInt(mProducts.get(position).getNumber());
                            total_Price.setText(totalprice+"");
                        }else{
                            totalprice+=mProducts.get(position).getPrice()*Integer.parseInt(mProducts.get(position).getNumber());
                            total_Price.setText(totalprice+"");
                        }
                        mProducts.get(position).setSelected(!mProducts.get(position).getSelected());
                        //更新数据、刷新界面
                        mAdapter.update(mProducts);
                        break;
                    case R.id.pro_add:
                        Toast.makeText(getContext(), "--1", Toast.LENGTH_SHORT).show();
                        alterProductToCart("1",position);
                        mProducts.get(position).setNumber(Integer.parseInt(mProducts.get(position).getNumber())+1+"");
                        //更新数据、刷新界面
                        mAdapter.update(mProducts);
                        /**
                         * 1.调用购物车商品增加接口
                         * 2.本地商品数据增加
                         * 3.刷新购物车数据
                         */
                        break;
                    case R.id.pro_minus:
                        Toast.makeText(getContext(), "--2", Toast.LENGTH_SHORT).show();
                        if (Integer.parseInt(mProducts.get(position).getNumber())!=1){
                            alterProductToCart("2",position);
                            mProducts.get(position).setNumber((Integer.parseInt(mProducts.get(position).getNumber())-1)+"");
                            //更新数据、刷新界面
                            mAdapter.update(mProducts);
                        }
                        break;

                }
            }
        });

        //编辑和分享的点击事件
        medit_tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (medit_tx.getText().equals("编辑")){
                    medit_tx.setText("完成");
                    mjiesuan_button.setText("删除");
                }else{
                    medit_tx.setText("编辑");
                    mjiesuan_button.setText("结算");
                }
            }
        });
        /*
        * 结算和删除的实现
        * 类都是引用式传递，赋值过后的新变量还是指的之前的变量的地址
        * */
        mjiesuan_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<CartPro> templist = new ArrayList<CartPro>();
                if(mjiesuan_button.getText().equals("结算")){
                    Toast.makeText(getContext(), "您点击了结算", Toast.LENGTH_SHORT).show();
                }else{
                    Toast.makeText(getContext(), "您点击了删除", Toast.LENGTH_SHORT).show();
                    int i = 0;
                    for(CartPro p:mProducts){
                        if (p.getSelected()==true){
                            alterProductToCart("3",i);
                            templist.add(p);
                        }
                        i++;
                    }
                    mProducts.removeAll(templist);
                    mAdapter.update(mProducts);
                }
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
                                mProducts = listResult.mData;
                                for (CartPro p : mProducts) {
                                    p.setSelected(false);
                                }
                            }
                        }
                    }
                });

            }
        });
    }
    private void alterProductToCart(String type,int position) {
        String alterCartUrl=UrlConstants.AlterCartUrl+"?type="+type+"&userid="+mPreferenceManager.getUserId()+"&goodsid="+mProducts.get(position).getGoodsid()+"&number="+1;
        Log.d("-----------", "购物车: " + alterCartUrl);
        Request request = new Request.Builder()
                .url(alterCartUrl)
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
                        Log.i("---------", "alterCart: " + jsonStr);
                        Type type = new TypeToken<Result<String>>() {
                        }.getType();
                        //解析Json数据得到结果集
                        Result<String> result = JsonUtils.parse(jsonStr, type);
                        if(result.mCode==200){
                            Toast.makeText(getContext(),"成功加入购物车",Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(getContext(),"加入购物车失败！",Toast.LENGTH_SHORT).show();
                        }
                    }
                });

            }
        });
    }
    private void getGoodsFromServer(final int position){
        String alterCartUrl=UrlConstants.detailMainUrl+"?goodsid="+mProducts.get(position).getGoodsid();
        Log.d("-----------", "购物车: " + alterCartUrl);
        Request request = new Request.Builder()
                .url(alterCartUrl)
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
                        Log.i("---------", "alterCart: " + jsonStr);
                        Type type = new TypeToken<Result<List<Goods>>>() {
                        }.getType();
                        //解析Json数据得到结果集
                        Result<List<Goods>> result = JsonUtils.parse(jsonStr, type);
                        if(result.mCode==200){
                            Log.d("------------","获取商品成功！");
                            product = result.mData.get(0);
                            Bundle bundle = new Bundle();
                            bundle.putParcelable("goodsdetail",product);
                            bundle.putInt("goodsId",mProducts.get(position).getGoodsid());
                            mMainActivity.showFragment(ProductFragment.class,"goodslist_2_detail",bundle);
                        }
                    }
                });

            }
        });
    }
}