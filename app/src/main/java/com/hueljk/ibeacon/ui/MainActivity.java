package com.hueljk.ibeacon.ui;

import android.annotation.TargetApi;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.brtbeacon.sdk.BRTBeacon;
import com.brtbeacon.sdk.BRTBeaconManager;
import com.brtbeacon.sdk.BRTThrowable;
import com.brtbeacon.sdk.callback.BRTBeaconManagerListener;
import com.hueljk.ibeacon.App;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.NevActoin;
import com.hueljk.ibeacon.ui.cart.CartFragment;
import com.hueljk.ibeacon.ui.home.HomeFragment;
import com.hueljk.ibeacon.ui.navigation.GuideFragment;
import com.hueljk.ibeacon.ui.navigation.NavFragment;
import com.hueljk.ibeacon.ui.setting.SettingFragment;
import com.hueljk.ibeacon.utils.PermissionUtil;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;

/**
 *
 */
public class MainActivity extends FragmentActivity implements View.OnClickListener {


    private HomeFragment mHomeFragment;
    private NavFragment mNavFragment;
    //private GuideFragment mGuideFragment;
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
        beaconManager = BRTBeaconManager.getInstance(this);
        PermissionUtil.addPermission(this);
        checkBluetoothValid();

      /*  if (isBlueEnable()) {
            initIBeacon();
        }*/
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
                    ft.add(R.id.container, mNavFragment, "mGuideFragment");
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
                mCartFragment = null;//每次展示完将该Fragment设置为空，这样每次点击会重新加载该Fragment
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
        for (Fragment e : mFragments) {
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

    public void toHomeFragment() {
        //取消之前被选中的菜单选项的状态
        mSelectedMenu.setSelected(false);
        //更改当前的选中的菜单
        mMenuLayout1.setSelected(true);
        mSelectedMenu = mMenuLayout1;

        ft = fg.beginTransaction();
        show(ft, mHomeFragment);
        ft.commit();
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

    /**
     * ------------------------------------------------------------------------------------
     */
   //App app;
    /*
     * Sensoro Manager
	 */
   /* SensoroManager sensoroManager;
    BluetoothManager bluetoothManager;
    BluetoothAdapter bluetoothAdapter;

    //public List<String> allBeacons = new ArrayList<>();
    public String BeaconNumber;
    public double BeaconDistance;
    public double MinBeaconDistance=10.0;
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR2)
    private boolean isBlueEnable() {
        bluetoothManager = (BluetoothManager) getSystemService(BLUETOOTH_SERVICE);
        bluetoothAdapter = bluetoothManager.getAdapter();
        boolean status = bluetoothAdapter.isEnabled();
        if (!status) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setNegativeButton(R.string.yes, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                    startActivity(intent);
                }
            }).setPositiveButton(R.string.no, new DialogInterface.OnClickListener() {

                @Override
                public void onClick(DialogInterface dialog, int which) {
                    dialog.dismiss();
                }
            }).setTitle(R.string.ask_bt_open);
            builder.show();
        }

        return status;
    }

    private void initIBeacon() {
        app = App.getInstance();

        sensoroManager = app.sensoroManager;
        BeaconManagerListener beaconManagerListener = new BeaconManagerListener() {
            @Override
            public void onUpdateBeacon(ArrayList beacons) {

            }

            @Override
            public void onNewBeacon(Beacon beacon) {
                //发现一个新的传感器，将sn号
                Log.i("---------", "onUpdateBeacon: 发现一个设备" + beacon.getSerialNumber()+" 距离:"+beacon.getAccuracy());
               // allBeacons.add(beacon.getSerialNumber());
                BeaconDistance=beacon.getAccuracy();
                if(BeaconDistance < MinBeaconDistance){
                    MinBeaconDistance = BeaconDistance;
                    BeaconNumber = beacon.getSerialNumber();
                }
                EventBus.getDefault().post(new NevActoin());
            }

            @Override
            public void onGoneBeacon(Beacon beacon) {
                // 一个传感器消失
                Log.i("---------", "onUpdateBeacon: 去除一个设备" + beacon.getSerialNumber()+" 距离:"+beacon.getAccuracy());
                //allBeacons.remove(beacon.getSerialNumber());
                EventBus.getDefault().post(new NevActoin());
            }
        };
        sensoroManager.setBeaconManagerListener(beaconManagerListener);
    }*/
    private List<BRTBeacon> beaconList = new ArrayList<BRTBeacon>();
    private LinkedList<Integer> RssiList = new LinkedList<Integer>();
    private BRTBeaconManager beaconManager = null;
    public Integer BeaconNumber;
    public double BeaconRssi;
    public double MaxBeaconRssi=0.0;
    @Override
    protected void onResume() {
        super.onResume();
        beaconManager.setBRTBeaconManagerListener(scanListener);
        beaconManager.startRanging();
    }

    @Override
    protected void onPause() {
        super.onPause();
        beaconManager.stopRanging();
        beaconManager.setBRTBeaconManagerListener(null);
    }

    private void checkBluetoothValid() {
        final BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if(adapter == null) {
            android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this).setTitle("错误").setMessage("你的设备不具备蓝牙功能!").create();
            dialog.show();
            return;
        }

        if(!adapter.isEnabled()) {
            android.app.AlertDialog dialog = new android.app.AlertDialog.Builder(this).setTitle("提示")
                    .setMessage("蓝牙设备未打开,请开启此功能后重试!")
                    .setPositiveButton("确认", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface arg0, int arg1) {
                            Intent mIntent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
                            startActivityForResult(mIntent, 1);
                        }
                    })
                    .create();
            dialog.show();
        }
    }
    private BRTBeaconManagerListener scanListener = new BRTBeaconManagerListener() {

        @Override
        public void onUpdateBeacon(final ArrayList<BRTBeacon> arg0) {


            runOnUiThread(new Runnable() {

                @Override
                public void run() {
                    beaconList.clear();
                    beaconList.addAll(arg0);
                    if(beaconList.size()!=0)
                    {
                        BeaconNumber = beaconList.get(0).getMinor();
                        EventBus.getDefault().post(new NevActoin());
                    }

                }
            });

        }

        @Override
        public void onNewBeacon(BRTBeacon arg0) {
            Log.i("---------", "onUpdateBeacon: 发现一个设备:" +arg0.getMinor()+"   信号强度："+arg0.getRssi());
            BeaconNumber = arg0.getMinor();
            Log.i("-----------","BeaconNumber is "+BeaconNumber);
            Collections.sort(beaconList, new Comparator<BRTBeacon>() {
                @Override
                public int compare(BRTBeacon lhs, BRTBeacon rhs) {
                    if(lhs.getRssi()<rhs.getRssi()){
                        return 1;
                    }
                    if(lhs.getRssi()==rhs.getRssi()){
                        return 0;
                    }
                    return -1;
                }
            });
        }

        @Override
        public void onGoneBeacon(BRTBeacon arg0) {
            Log.i("---------", "onUpdateBeacon: 去除一个设备" +arg0.getMinor()+"   信号强度："+arg0.getRssi());
            Collections.sort(beaconList, new Comparator<BRTBeacon>() {
                @Override
                public int compare(BRTBeacon lhs, BRTBeacon rhs) {
                    if(lhs.getRssi()<rhs.getRssi()){
                        return 1;
                    }
                    if(lhs.getRssi()==rhs.getRssi()){
                        return 0;
                    }
                    return -1;
                }
            });
        }

        @Override
        public void onError(BRTThrowable arg0) {

        }
    };

}


