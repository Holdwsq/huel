package com.hueljk.ibeacon.ui;

import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.navigation.NavFragment;
import com.hueljk.ibeacon.ui.setting.SettingFragment;

public class WelcomeActivity extends FragmentActivity implements View.OnClickListener {
    private RelativeLayout mShopGuideLy;
    private RelativeLayout mShopLy;
    private RelativeLayout mSettingLy;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_welcome);
        mShopGuideLy=(RelativeLayout) findViewById(R.id.ShopGuideLy);
        mShopLy=(RelativeLayout)findViewById(R.id.ShopLy);
        mSettingLy=(RelativeLayout)findViewById(R.id.SettingLy);
        mShopGuideLy.setOnClickListener(this);
        mShopLy.setOnClickListener(this);
        mSettingLy.setOnClickListener(this);
    }
    public void showFragment(Class<? extends BaseFragment> fragmentClass, String fragmentTag) {
        showFragment(fragmentClass, fragmentTag);
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.ShopGuideLy:{
                Intent show= new Intent(WelcomeActivity.this,GaviActivity.class);
                WelcomeActivity.this.startActivity(show);
                //showFragment(NavFragment.class,"ShopGuideFragment");
                Toast.makeText(this,"导购页面",Toast.LENGTH_SHORT);
               break;
            }
            case R.id.ShopLy:{
                Intent mIntent= new Intent(WelcomeActivity.this,MainActivity.class);
                Toast.makeText(this,"主页面",Toast.LENGTH_SHORT);
                WelcomeActivity.this.startActivity(mIntent);
                break;
            }
            case R.id.SettingLy:{
               // showFragment(SettingFragment.class,"SettingFragment");
                Toast.makeText(this,"个人中心",Toast.LENGTH_SHORT);
                Intent show= new Intent(WelcomeActivity.this,MainActivity.class);
                show.putExtra("fragid",2);
                startActivity(show);
                finish();
                break;
            }
        }

    }
}
