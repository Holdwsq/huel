package com.hueljk.ibeacon.ui;

import android.content.Intent;
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
        mLayoutHome = (RelativeLayout) this.findViewById(R.id.layout_home);
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

    /**
     * activity加载fragment的四个步骤
     *
     * 1.获取fragmentmanager：FragmentManager fg = getSupportFragmentManager();
     * 2.开启fragment管理事务：FragmentTransaction ft = fg.beginTransaction();
     * 3.添加、删除、展示fragment： ft.add(R.id.container, mNavFragment, "navigation");|| ft.show(fragment);
     * 4.提交事务：ft.commit();
     */

    /**
     * fragment局部加载和全屏加载
     * 局部加载：就是将fragment加载到局部FramLayout之中
     * 全屏加载：就是将fragment加载到系统提供的全屏的framlayout之中，系统提供的framlayout id是anroid.R.id.content
     */
    public void showFragment(Class<? extends BaseFragment> fragmentClass,String fragmentTag){
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction ft = fm.beginTransaction();

        //通过类的class创建类的对象
//        Intent intent = new Intent(this,MainActivity.class);
        Fragment fragment = null;
        try {
            //创建类的对象：
            //1.new + 构造方法
            //2.通过class的newInstance创建对象
            fragment = fragmentClass.newInstance();
            ft.add(android.R.id.content,fragment,fragmentTag);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        //回退栈：可以返回到上级页面
        ft.addToBackStack(fragmentTag);
        ft.commit();
    }


}


