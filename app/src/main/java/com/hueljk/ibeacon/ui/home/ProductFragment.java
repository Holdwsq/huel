package com.hueljk.ibeacon.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.mode.Goods;
import com.hueljk.ibeacon.ui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class ProductFragment extends BaseFragment{
    private ImageView mImageView;
    private Bundle mBundle;
    private Goods mGoods;
    private ImageView mProductImg;
    private TextView mproduct_desc;
    private TextView mproduct_price;
    private TextView mproduct_num;



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product, container, false);

    }



    protected void initView(View view) {
        super.initView(view);
        mImageView = (ImageView) view.findViewById(R.id.product_return);
        mProductImg=(ImageView)view.findViewById(R.id.product_img);
        mproduct_desc=(TextView)view.findViewById(R.id.product_desc);
        mproduct_price=(TextView) view.findViewById(R.id.product_price);
        mproduct_num=(TextView)view.findViewById(R.id.product_num);
    }

    @Override
    protected void setListener() {
        super.setListener();
        mImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getContext(), "--", Toast.LENGTH_SHORT).show();
                popSelf();
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
        mBundle = getArguments();
        if(mBundle!=null){
            mGoods =mBundle.getParcelable("goodsdetail");
            Glide
                    .with(mContext)
                    .load(UrlConstants.HomePicUrl+mGoods.getPurl())
                    .placeholder(R.drawable.shangpin1)
                    .error(R.drawable.shangpin1)
                    .into(mProductImg);
           mproduct_desc.setText(mGoods.getPdesc());
           mproduct_num.setText("月销"+mGoods.getSold() + "笔");
           mproduct_price.setText("￥" + mGoods.getPrice());


            Log.d("------------", "initView: "+mGoods.toString());
        }
    }


}
