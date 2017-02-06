package com.hueljk.ibeacon.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.Product;
import com.hueljk.ibeacon.constants.UrlConstants;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2017/1/16.
 */
public class MyAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Product> mProducts = new ArrayList<>();
    private Context mContext;

    public MyAdapter(Context context, List<Product> products) {
        mInflater = LayoutInflater.from(context);
        mContext=context;
        if (products != null) {
            mProducts.addAll(products);
        }
    }

    @Override
    public int getCount() {
        return mProducts.size();
    }

    @Override
    public Product getItem(int position) {
        return mProducts.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_gv, parent, false);
            holder = new Holder();
            holder.mPIconUrl = (ImageView) convertView.findViewById(R.id.itemProImage);
            holder.mPdec = (TextView) convertView.findViewById(R.id.itemDecText);
            holder.mNum = (TextView) convertView.findViewById(R.id.itemNumText);
            holder.mPrice = (TextView) convertView.findViewById(R.id.itemPriText);
            holder.mCIon = (ImageView) convertView.findViewById(R.id.itemCartImage);
            convertView.setTag(holder);
            //使用setTag把查找的view缓存起来方便多次重用
        } else {
            holder = (Holder) convertView.getTag();
        }
        Product product = getItem(position);
        //holder.mPIconUrl.setImageResource(product.getPurl());
        Glide
                .with(mContext)
                .load(UrlConstants.picBaseUrl+product.getPurl())
                .placeholder(R.drawable.shangpin1)
                .error(R.drawable.shangpin1)
                .into(holder.mPIconUrl);
        holder.mPdec.setText(product.getPdec());
        holder.mNum.setText(product.getNum() + "已买");
        holder.mPrice.setText("￥" + product.getPrice());
        holder.mCIon.setImageResource(product.getCIon());
        return convertView;
    }
    public void update(List<Product> data) {
        mProducts.clear();
        if (data != null) {
            mProducts.addAll(data);
        }
        notifyDataSetChanged();
    }

    private static class Holder {
        private ImageView mPIconUrl;
        private TextView mPdec;
        private TextView mNum;
        private TextView mPrice;
        private ImageView mCIon;
    }
}



