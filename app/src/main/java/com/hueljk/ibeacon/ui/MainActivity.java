package com.hueljk.ibeacon.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.RelativeLayout;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.cart.CartFragment;
import com.hueljk.ibeacon.ui.home.HomeFragment;
import com.hueljk.ibeacon.ui.navigation.NavFragment;
import com.hueljk.ibeacon.ui.setting.SettingFragment;

import java.util.ArrayList;
import java.util.List;

/**
 *
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {
    private RelativeLayout mLayoutHome;
    private RelativeLayout mLayoutNav;
    private RelativeLayout mLayoutCart;
    private RelativeLayout mLayoutSetting;
    private HomeFragment mHomeFragment;
    private NavFragment mNavFragment;
    private CartFragment mCartFragment;
    private SettingFragment mSettingFragment;
    private List<Fragment> mFragments = new ArrayList<>();


    /**
     * 保存当前的状态@param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        setListener();
        setData();
    }


    /**
     * 初始化定义
     */
    private void initView() {
        mLayoutHome = (RelativeLayout) findViewById(R.id.layout_home);
        mLayoutNav = (RelativeLayout) findViewById(R.id.layout_navigation);
        mLayoutCart = (RelativeLayout) findViewById(R.id.layout_cart);
        mLayoutSetting = (RelativeLayout) findViewById(R.id.layout_setting);
    }

    /**
     * 设置监听事件
     */
    private void setListener() {
        mLayoutHome.setOnClickListener(this);
        mLayoutNav.setOnClickListener(this);
        mLayoutCart.setOnClickListener(this);
        mLayoutSetting.setOnClickListener(this);

    }

    /**
     * 显示主页
     */
    private void setData() {
        mFragments.add(mHomeFragment = new HomeFragment());
        FragmentManager fg = getSupportFragmentManager();
        FragmentTransaction ft = fg.beginTransaction();
        ft.add(R.id.container, mHomeFragment, "home");
        ft.commit();

    }

    @Override
    public void onClick(View view) {
        FragmentManager fg = getSupportFragmentManager();
        FragmentTransaction ft = fg.beginTransaction();
        switch (view.getId()) {
            case R.id.layout_home:
                show(ft, mHomeFragment);
                break;
            case R.id.layout_navigation:
                if (mNavFragment == null) {
                    mNavFragment = new NavFragment();
                    mFragments.add(mNavFragment);
                    ft.add(R.id.container, mNavFragment, "navigation");
                }
                show(ft, mNavFragment);

                break;
            case R.id.layout_cart:
                if (mCartFragment == null) {
                    mCartFragment = new CartFragment();
                    mFragments.add(mCartFragment);
                    ft.add(R.id.container, mCartFragment, "cart");
                }
                show(ft, mCartFragment);

                break;
            case R.id.layout_setting:

                if (mSettingFragment == null) {
                    mSettingFragment = new SettingFragment();
                    mFragments.add(mSettingFragment);
                    ft.add(R.id.container, mSettingFragment, "setting");
                }
                show(ft, mSettingFragment);

                break;
        }
        ft.commit();
    }

    /**
     * @param fragment : 被点击的tab所对应的Fragment
     */
    private void show(FragmentTransaction ft, Fragment fragment) {
        for (Fragment e :
                mFragments) {
            if (fragment == null) {
                continue;
            }
            if (fragment.equals(e)) {
                ft.show(fragment);
            } else {
                ft.hide(e);
            }

        }
    }

}


