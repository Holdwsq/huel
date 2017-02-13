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


    private HomeFragment mHomeFragment;
    private NavFragment mNavFragment;
    private CartFragment mCartFragment;
    private SettingFragment mSettingFragment;
    private List<Fragment> mFragments = new ArrayList<>();

    private FragmentManager fg;
    private FragmentTransaction ft;

    /**
     * 保存当前的状态@param savedInstanceState
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        fg = getSupportFragmentManager();

        initView();
        setData();
    }

    //底部菜单
    private View mMenuLayout1;
    private View mMenuLayout2;
    private View mMenuLayout3;
    private View mMenuLayout4;

    //当前被选中的菜单
    private View mSelectedMenu;

    /**
     * 底部被选中的菜单选项，需要被记住状态
     */
    private void initView() {
        mMenuLayout1 = findViewById(R.id.layout_home);
        mMenuLayout2 = findViewById(R.id.layout_navigation);
        mMenuLayout3 = findViewById(R.id.layout_cart);
        mMenuLayout4 = findViewById(R.id.layout_setting);

        mMenuLayout1.setOnClickListener(this);
        mMenuLayout2.setOnClickListener(this);
        mMenuLayout3.setOnClickListener(this);
        mMenuLayout4.setOnClickListener(this);

        //设置第一个菜单被选中
        mMenuLayout1.setSelected(true);
        mSelectedMenu = mMenuLayout1;
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
        //取消之前被选中的菜单选项的状态
        mSelectedMenu.setSelected(false);
        //更改当前的选中的菜单
        view.setSelected(true);
        mSelectedMenu = view;


        ft = fg.beginTransaction();
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
    public void showFragment(Class<? extends BaseFragment> fragmentClass, String fragmentTag) {
        showFragment(fragmentClass, fragmentTag, null);
    }

    public void showFragment(Class<? extends BaseFragment> fragmentClass, String fragmentTag, Bundle bundle) {
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

            //fragment之间传值通过bundle存储值，通过方法setArguments,getArguments获取传递的值
            //intent是activity之间跳转、传值使用，bundle和intent传值一样
            if (bundle != null) {
                fragment.setArguments(bundle);
            }

            ft.add(android.R.id.content, fragment, fragmentTag);
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


