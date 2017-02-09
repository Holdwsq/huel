package com.hueljk.ibeacon;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hueljk.ibeacon.mode.Goods;
import com.hueljk.ibeacon.mode.Product;
import com.hueljk.ibeacon.ui.adapter.MyAdapter;
import com.hueljk.ibeacon.ui.home.HomeFragment;
import com.hueljk.ibeacon.utils.DisplayUtils;

import java.util.ArrayList;
import java.util.List;

public class TwoRyActivity extends FragmentActivity {
    private GridView mGridView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_ry);
        mGridView = (GridView) findViewById(R.id.riyong_gridView);
        List<Goods> rys = new ArrayList<>();
        for (int i = 0; i < 40; i++) {
            Goods mRys = new Goods(i,"", "维达抽纸", 1000, "",80);
            rys.add(mRys);
        }
        MyAdapter adapter = new MyAdapter(this, rys);
        mGridView.setAdapter(adapter);
        int height = 99 / 3 * 220;
        LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) mGridView.getLayoutParams();
        DisplayUtils.init(this);

        params.height = DisplayUtils.dip2px(height);
        mGridView.setLayoutParams(params);
        ImageView home_img = (ImageView) findViewById(R.id.two_riyohome_img);
        home_img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TwoRyActivity.this, HomeFragment.class);
                startActivity(intent);
            }
        });
    }
}