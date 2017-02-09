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
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.mode.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2017/2/9.
 */
public class HomeAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Goods> mGoodses = new ArrayList<>();
    private Context mContext;

    public HomeAdapter(Context context, List<Goods> products) {
        mInflater = LayoutInflater.from(context);
        mContext=context;
        if (products != null) {
            mGoodses.addAll(products);
        }
    }

    @Override
    public int getCount() {
        return mGoodses.size();
    }

    @Override
    public Goods getItem(int position) {
        return mGoodses.get(position);
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
        Goods goods = getItem(position);
        //holder.mPIconUrl.setImageResource(product.getPurl());
        Glide
                .with(mContext)
                .load(UrlConstants.picBaseUrl+goods.getPurl())
                .placeholder(R.drawable.shangpin1)
                .error(R.drawable.shangpin1)
                .into(holder.mPIconUrl);
        holder.mPdec.setText(goods.getPdesc());
        holder.mNum.setText(goods.getSold() + "已买");
        holder.mPrice.setText("￥" + goods.getPrice());
        holder.mCIon.setImageResource(R.drawable.cart);
        return convertView;
    }
    public void update(List<Goods> data) {
        mGoodses.clear();
        if (data != null) {
            mGoodses.addAll(data);
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
