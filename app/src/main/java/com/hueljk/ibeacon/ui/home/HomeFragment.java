package com.hueljk.ibeacon.ui.home;


import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.bumptech.glide.Glide;
import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.manager.PreferenceManager;
import com.hueljk.ibeacon.mode.BaseEntity;
import com.hueljk.ibeacon.mode.Goods;
import com.hueljk.ibeacon.mode.GoodsInfo;
import com.hueljk.ibeacon.mode.Home;
import com.hueljk.ibeacon.mode.PageData;
import com.hueljk.ibeacon.mode.ResponseBean;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.adapter.GoodsAdapter;
import com.hueljk.ibeacon.ui.adapter.HomeAdapter;
import com.hueljk.ibeacon.ui.home.banner.BannerHfpFragment;
import com.hueljk.ibeacon.ui.home.banner.BannerImageViewUtils;
import com.hueljk.ibeacon.ui.home.banner.BannerNzFragment;
import com.hueljk.ibeacon.ui.home.banner.BannerXsFragment;
import com.hueljk.ibeacon.ui.home.banner.CycleViewPager;
import com.hueljk.ibeacon.ui.home.banner.ImageCycleViewListener;
import com.hueljk.ibeacon.ui.home.category.TwoFreshFragment;
import com.hueljk.ibeacon.ui.home.category.TwoCloFragment;
import com.hueljk.ibeacon.ui.home.category.TwoFoodFragment;
import com.hueljk.ibeacon.ui.home.category.TwoRyFragment;
import com.hueljk.ibeacon.ui.home.discount.TwoPaperFragment;
import com.hueljk.ibeacon.ui.home.discount.TwoWineFragment;
import com.hueljk.ibeacon.ui.home.discount.TwopuffFragment;
import com.hueljk.ibeacon.ui.setting.LoginFragment;
import com.hueljk.ibeacon.utils.DisplayUtils;
import com.hueljk.ibeacon.utils.JsonUtils;
import com.hueljk.ibeacon.view.SpacesItemDecoration;
import com.hueljk.ibeacon.wigdet.EditTextDrawableClick;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.w3c.dom.Text;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
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
public class HomeFragment extends BaseFragment implements View.OnClickListener{
    private String TAG = HomeFragment.class.getSimpleName();
    private static OkHttpClient client;
    private HomeAdapter mAdapter;
    private ImageView mCategoryImage;
    private ImageView msgImage;
    private TextView shipin_tv;
    private TextView riyong_tv;
    private TextView clothes_tv;
    private TextView shengxian_tv;
    private ImageView homezp_img;
    private ImageView homemj_img;
    private ImageView homeph_img;
    private Context mContext;
    private CycleViewPager mCycleViewPager;
    private List<Goods> goods;
    private GoodsAdapter adapter;
    private PreferenceManager mPreferenceManager;
    private String mSearchKeys;
    private EditTextDrawableClick mSearchET;
    /**
     * 商品发布话题列表
     */
    private RecyclerView recyclerView;
    static {
        client = new OkHttpClient.Builder().connectTimeout(20, TimeUnit.SECONDS).build();
    }

    public HomeFragment() {
        super();
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
        mPreferenceManager = PreferenceManager.getInstance();
        //hideSoftKeyboard();
        msgImage = view.findViewById(R.id.message_img);
        mCategoryImage= view.findViewById(R.id.category_img);
        shipin_tv = view.findViewById(R.id.shipin_tv);
        riyong_tv = view.findViewById(R.id.riyong_tv);
        clothes_tv = view.findViewById(R.id.clothes_tv);
        shengxian_tv = view.findViewById(R.id.shengxian_tv);
        homezp_img = view.findViewById(R.id.homezp_img);
        homemj_img = view.findViewById(R.id.homemj_img);
        homeph_img = view.findViewById(R.id.homeph_img);
        mSearchET= view.findViewById(R.id.SearchEt);
        recyclerView = view.findViewById(R.id.recyclerview);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(getContext());
        mLinearLayoutManager.setSmoothScrollbarEnabled(true);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        recyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(false);
        recyclerView.addItemDecoration(new SpacesItemDecoration(40));
        adapter = new GoodsAdapter();
        List<GoodsInfo> list = initData();
        adapter.setData(list);
        recyclerView.setAdapter(adapter);
        TextView []tv = {shipin_tv,riyong_tv,clothes_tv,shengxian_tv};
        for(TextView t:tv){
            Drawable top = t.getCompoundDrawables()[1];
            if (top!=null){
                top.setBounds(0,0,150,150);
                t.setCompoundDrawables(null,top,null,null);
            }
        }
        mContext = getContext();
        // 找到轮播控件
        mCycleViewPager = (CycleViewPager) getChildFragmentManager()
                .findFragmentById(R.id.main_fragment_banner);
        mAdapter = new HomeAdapter(getContext(), null);
//        mGridView.setAdapter(mAdapter);
        initBanner();
    }

    private List<GoodsInfo> initData() {
        String dataStr = "[{" +
                "\"createTime\": 1521615482250," +
                "\"deleteFlag\": \"0\"," +
                "\"fileUrls\": [\"http://p5khs9up1.bkt.clouddn.com/bef43e9214044e4a921e77ab08c93479.jpg\"]," +
                "\"id\": \"1b5c990994eb4530ae3962cffe817c00\"," +
                "\"pictureNames\": \"http://p5khs9up1.bkt.clouddn.com/bef43e9214044e4a921e77ab08c93479.jpg\"," +
                "\"title\": \"123\"," +
                "\"type\": \"图片\"," +
                "\"updateTime\": 1521615482250," +
                "\"userName\": \"无名氏\"" +
                "}, {" +
                "\"createTime\": 1521614591585," +
                "\"deleteFlag\": \"0\"," +
                "\"fileUrls\": [\"http://p5khs9up1.bkt.clouddn.com/fc9b7f81b1b44ac497c14b1fb97d3725.png\"]," +
                "\"id\": \"5f05a975ea7d4302933f05bbd67d6c7d\"," +
                "\"pictureNames\": \"http://p5khs9up1.bkt.clouddn.com/fc9b7f81b1b44ac497c14b1fb97d3725.png\"," +
                "\"title\": \"中山大学\"," +
                "\"type\": \"text\"," +
                "\"updateTime\": 1521614591585," +
                "\"userName\": \"无名氏\"" +
                "}, {" +
                "\"createTime\": 1521614277108," +
                "\"deleteFlag\": \"0\"," +
                "\"fileUrls\": [\"http://p5khs9up1.bkt.clouddn.com/c7fa7686dd9e4cbe84c5b6afe36e5b0a.jpg\"]," +
                "\"id\": \"b791d645a7de49f09954765701868841\"," +
                "\"pictureNames\": \"http://p5khs9up1.bkt.clouddn.com/c7fa7686dd9e4cbe84c5b6afe36e5b0a.jpg\"," +
                "\"title\": \"123\"," +
                "\"type\": \"图片\"," +
                "\"updateTime\": 1521614277108," +
                "\"userName\": \"无名氏\"" +
                "}, {" +
                "\"createTime\": 1521102129371," +
                "\"deleteFlag\": \"0\"," +
                "\"description\": \"这里是描述这里是描述这里是描述这里是描述这里是描述\"," +
                "\"fileUrls\": [\"http://p5khs9up1.bkt.clouddn.com/e71766bbc4b1405eac5b1f4157440d59.jpg\", \"http://p5khs9up1.bkt.clouddn.com/51cc90217b6549e1b011b2e8331f3e8c.jpg\"]," +
                "\"id\": \"05464845baf243b2b10d740d9ff6a6e9\"," +
                "\"pictureNames\": \"http://p5khs9up1.bkt.clouddn.com/e71766bbc4b1405eac5b1f4157440d59.jpg,http://p5khs9up1.bkt.clouddn.com/51cc90217b6549e1b011b2e8331f3e8c.jpg\"," +
                "\"title\": \"test\"," +
                "\"type\": \"图片\"," +
                "\"updateTime\": 1521102129371," +
                "\"userId\": \"210b346cf29a4577aa51fb077b04674b\"," +
                "\"userName\": \"Tom\"," +
                "\"userPortrait\": \"\"" +
                "}, {" +
                "\"createTime\": 1521102113543," +
                "\"deleteFlag\": \"0\"," +
                "\"description\": \"这里是描述这里是描述这里是描述\"," +
                "\"fileUrls\": [\"http://p5khs9up1.bkt.clouddn.com/ccea475263104390b32487930eeca884.jpg\", \"http://p5khs9up1.bkt.clouddn.com/26ae8701814e404ba7bfa455e0c2b1ad.jpg\"]," +
                "\"id\": \"fe602db5888e4321a8642ff52b9ea5e8\"," +
                "\"pictureNames\": \"http://p5khs9up1.bkt.clouddn.com/ccea475263104390b32487930eeca884.jpg,http://p5khs9up1.bkt.clouddn.com/26ae8701814e404ba7bfa455e0c2b1ad.jpg\"," +
                "\"title\": \"test\"," +
                "\"type\": \"图片\"," +
                "\"updateTime\": 1521102113543," +
                "\"userId\": \"210b346cf29a4577aa51fb077b04674b\"," +
                "\"userName\": \"Tom\"," +
                "\"userPortrait\": \"\"" +
                "}, {" +
                "\"createTime\": 1521102002611," +
                "\"deleteFlag\": \"0\"," +
                "\"description\": \"这里是描述这里是描述这里是描述这里是描述\"," +
                "\"fileUrls\": [\"http://p5khs9up1.bkt.clouddn.com/a23c3d9d16a04f0cb39e03146075da3c.jpg\", \"http://p5khs9up1.bkt.clouddn.com/fc15f8826de24ac8a2fac546371dc0ee.jpg\"]," +
                "\"id\": \"18b221c6f51d4629b69e8e312d262b18\"," +
                "\"pictureNames\": \"http://p5khs9up1.bkt.clouddn.com/a23c3d9d16a04f0cb39e03146075da3c.jpg,http://p5khs9up1.bkt.clouddn.com/fc15f8826de24ac8a2fac546371dc0ee.jpg\"," +
                "\"title\": \"test\"," +
                "\"type\": \"图片\"," +
                "\"updateTime\": 1521102002611," +
                "\"userId\": \"210b346cf29a4577aa51fb077b04674b\"," +
                "\"userName\": \"Tom\"," +
                "\"userPortrait\": \"\"" +
                "}]";
        List<GoodsInfo> goodsInfos = JSON.parseObject(dataStr, new TypeReference<List<GoodsInfo>>() {});
        return goodsInfos;
    }

    @Override
    protected void setListener() {
        super.setListener();
        msgImage.setOnClickListener(this);
        mCategoryImage.setOnClickListener(this);
        clothes_tv.setOnClickListener(this);
        riyong_tv.setOnClickListener(this);
        shipin_tv.setOnClickListener(this);
        shengxian_tv.setOnClickListener(this);
        homemj_img.setOnClickListener(this);
        homezp_img.setOnClickListener(this);
        homeph_img.setOnClickListener(this);

        mSearchET.setDrawableLeftListener(new EditTextDrawableClick.DrawableLeftListener() {
            @Override
            public void onDrawableLeftClick(View view) {
               // Toast.makeText(getContext(),"点击搜索",Toast.LENGTH_SHORT).show();
                mSearchKeys=mSearchET.getText().toString();
                mSearchET.setText("");
                Log.d("---------","SearchKeys:"+mSearchKeys);
                Bundle bundle = new Bundle();
                bundle.putString("searchKeys",mSearchKeys);
                mMainActivity.showFragment(SearchProductFragment.class,"Home_2_serach",bundle);

            }
        });

        mSearchET.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if(keyCode==KeyEvent.KEYCODE_ENTER){
                 //   Toast.makeText(getContext(),"回车搜索",Toast.LENGTH_SHORT).show();
                    mSearchKeys=mSearchET.getText().toString();
                    Log.d("---------","SearchKeys:"+mSearchKeys);
                    Bundle bundle = new Bundle();
                    bundle.putString("searchKeys",mSearchKeys);
                    mMainActivity.showFragment(SearchProductFragment.class,"Home_2_Search",bundle);
                    return true;
                }
                return false;
            }
        });
        /**
         * 点击事件
         *
         * 1.view控件的点击事件（botton，imageview等）：View.OnClickListener()
         * 2.列表的点击事件（item子控件的点击事件）：AdapterView.OnItemClickListener()
         */
        /*mGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //goods.get(position).getId()当前位置的商品id
                //Toast.makeText(mContext,"goods id is -- "+goods.get(position).getId(),Toast.LENGTH_SHORT).show();

                //跳转到详情页
                //fragment之间的传值，需要在showFragment方法中增加一个参数
                Bundle bundle = new Bundle();
                bundle.putParcelable("goodsdetail",goods.get(position));
                bundle.putInt("goodsId",goods.get(position).getId());

                mMainActivity.showFragment(ProductDescFragment.class,"goodslist_2_detail",bundle);
            }
        });*/
        adapter.setMyItemClickListener(new GoodsAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position, String goodsId) {
                Toast.makeText(getContext(), "onItemClick => position: " + position + ",商品id：" + goodsId, Toast.LENGTH_SHORT).show();
                //跳转到详情页
                //fragment之间的传值，需要在showFragment方法中增加一个参数
                Bundle bundle = new Bundle();
//                bundle.putParcelable("goodsdetail",goods.get(position));
                bundle.putString("goodsId", goodsId);

                mMainActivity.showFragment(ProductDescFragment.class,"goodslist_2_detail",bundle);
            }

            @Override
            public void onItemLongClick(View view, int position, String goodsId) {
                Toast.makeText(getContext(), "onItemLongClick => position : " + position + ",商品id：" + goodsId, Toast.LENGTH_LONG).show();
            }
        });
        mAdapter.setOnCartClickListener(new HomeAdapter.CallBack() {
            @Override
            public void onCartClick(View v, int position) {
               // Toast.makeText(getContext(),"----"+position,Toast.LENGTH_SHORT).show();
                if(mPreferenceManager.getLoginStatus()){
                    addProductToCart(position);

                }else{
                    mMainActivity.showFragment(LoginFragment.class,"Home_2_Login");
                }

            }
        });
    }

    private void addProductToCart(int position) {
        String addCartUrl=UrlConstants.addCartUrl+"?userid="+mPreferenceManager.getUserId()+"&goodsid="+goods.get(position).getId()+"&number="+1;
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.message_img:mMainActivity.showFragment(MsgFrament.class,"Home_2_msg");
                    break;
            case R.id.category_img:
                showPopupMenu(mCategoryImage);
                //Toast.makeText(getContext(),"类别选择",Toast.LENGTH_LONG).show();
                break;
            case R.id.clothes_tv:
                mMainActivity.showFragment(TwoCloFragment.class, "Home_2_Clo");
                break;
            case R.id.riyong_tv:
                mMainActivity.showFragment(TwoRyFragment.class, "Home_2_Ry");
                break;
            case R.id.shipin_tv:
                mMainActivity.showFragment(TwoFoodFragment.class, "Home_2_Ry");
                break;
            case R.id.shengxian_tv:
                mMainActivity.showFragment(TwoFreshFragment.class, "Home_2_sx");
                break;
            case R.id.homemj_img:
                mMainActivity.showFragment(TwoWineFragment.class,"Home_2_wine");
                break;
            case R.id.homezp_img:
                mMainActivity.showFragment(TwoPaperFragment.class,"Home_2_paper");
                break;
            case R.id.homeph_img:
                mMainActivity.showFragment(TwopuffFragment.class,"Home_2_puff");
                break;

        }

    }

    private void showPopupMenu(View view) {
        //View当前PopupMenu显示的相对View的位置
        PopupMenu popupMenu = new PopupMenu(getContext(),view);
        //menu布局
        popupMenu.getMenuInflater().inflate(R.menu.menu_home,popupMenu.getMenu());
        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                return false;
            }
        });
        popupMenu.setOnDismissListener(new PopupMenu.OnDismissListener() {
            @Override
            public void onDismiss(PopupMenu menu) {

            }
        });
        popupMenu.show();

    }

    @Override
    protected void setData() {
        super.setData();

       /* new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    execute();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();*/
    }

    public void execute() throws Exception {
        String goodsListUrl = UrlConstants.HomeUrl;
        // 将获取主页信息分为多个请求
        OkGo.<String>get(goodsListUrl)
                .tag(this)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(final com.lzy.okgo.model.Response<String> response) {
                        Log.i(TAG, "获取主页信息为：" + response.body());
                        final String ret = response.body();
                        if (ret == null
                                || getActivity() == null){
                            return;
                        }
                        if (response.code() != 200){
                            Toast.makeText(getContext(), "请求失败", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        getActivity().runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Log.i("---", ret);
                                //解析Json数据得到结果集
                                ResponseBean<Home> responseBean = JSON.parseObject(ret, new TypeReference<ResponseBean<Home>>() {});

                                if (responseBean == null){
                                    return;
                                }
                                if (ResponseBean.STATUS_SUCCESS.equals(responseBean.getStatus())) {
                                    Home data = responseBean.getData();
                                    if (data == null){
                                        return;
                                    }
                                    PageData<GoodsInfo> pageData = data.getPageData();
                                    updateGoodList(pageData.getData());
                                    List<BaseEntity> homeBanners = data.getHomeBanners();
                                    setBanner(homeBanners);
                                    List<BaseEntity> homeDiscounts = data.getHomeDiscounts();
                                    updateDiscounts(homeDiscounts);
                                }
                            }
                        });
                    }

                    @Override
                    public void onError(com.lzy.okgo.model.Response<String> response) {
                        super.onError(response);
                        Log.e(TAG, "获取主页信息失败：" + response.body());
                    }
                });
    }

    private void updateGoodList(List<GoodsInfo> goodsList) {
        if (goodsList == null || goodsList.size() <= 0){
            return;
        }
        Log.d("更新商品列表：", JSON.toJSONString(goodsList));
        adapter.updateData(goodsList);
    }

    private void updateDiscounts(List<BaseEntity> discounts) {
        if (discounts == null || discounts.size() <= 0){
            return;
        }
        Log.d("更新折扣信息----", JSON.toJSONString(discounts));
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
    }

    // 由banner工厂生产出来的imageview
    private ImageView bannerImageView;
    private List<ImageView> mImageViews = new ArrayList<>();

    private void initBanner() {
        bannerImageView = BannerImageViewUtils.getImageView(mContext);
        bannerImageView.setImageResource(R.drawable.sa);
        mImageViews.add(bannerImageView);
        // 设置循环，在调用setData方法前调用
        mCycleViewPager.setCycle(false);
        // 在加载数据前设置是否循环
        mCycleViewPager.setData(mImageViews, null, null);
        // 设置轮播
        mCycleViewPager.setWheel(false);
        // 设置圆点指示图标组居中显示，默认靠右
        mCycleViewPager.setIndicatorCenter();
        mImageViews.clear();
    }

    private void setBanner(List<BaseEntity> banners) {

        if (banners == null || banners.size() <= 0){
            return;
        }
        DisplayUtils.init(mContext);
        mImageViews.clear();
        for (BaseEntity banner : banners) {
            bannerImageView = BannerImageViewUtils.getImageView(mContext);
            Glide.with(mContext).load(UrlConstants.BannerDisUrl + banner.getUrl())
                    .placeholder(R.drawable.sa)
                    .centerCrop()
                    .into(bannerImageView);
            mImageViews.add(bannerImageView);
        }
        //重新添加第一个view
        bannerImageView = BannerImageViewUtils.getImageView(mContext);
        Glide.with(mContext).load(UrlConstants.BannerDisUrl + banners.get(banners.size() - 1).getUrl()).centerCrop()
                .into(bannerImageView);
        mImageViews.add(0, bannerImageView);
        //重新添加最后一个view
        bannerImageView = BannerImageViewUtils.getImageView(mContext);
        Glide.with(mContext).load(UrlConstants.BannerDisUrl + banners.get(0).getUrl()).centerCrop()
                .into(bannerImageView);
        mImageViews.add(bannerImageView);
        // 设置循环，在调用setData方法前调用
        mCycleViewPager.setCycle(true);
        // 在加载数据前设置是否循环
        mCycleViewPager.setData(mImageViews, banners, new ImageCycleViewListener() {
            @Override
            public void onImageClick(Object info, int position, View imageView) {
                //Toast.makeText(getContext(), "--"+position, Toast.LENGTH_SHORT).show();
                switch (position) {
                    case 1:
                        //Toast.makeText(getContext(), "--" + position, Toast.LENGTH_SHORT).show();
                        mMainActivity.showFragment(BannerNzFragment.class,"Home_2_bannerNz");
                        break;
                    case 2:
                        //Toast.makeText(getContext(), "--" + position, Toast.LENGTH_SHORT).show();
                        mMainActivity.showFragment(BannerXsFragment.class,"Home_2_bannerXs");
                        break;
                    case 3:
                        //Toast.makeText(getContext(), "--" + position, Toast.LENGTH_SHORT).show();
                        mMainActivity.showFragment(BannerHfpFragment.class,"Home_2_hfp");
                        break;

                }
            }
        });
        //设置轮播
        mCycleViewPager.setWheel(true);
        // 设置轮播时间，默认5000ms
        mCycleViewPager.setTime(2000);
        //设置圆点指示图标组居中显示，默认靠右
        mCycleViewPager.setIndicatorCenter();
    }
}
