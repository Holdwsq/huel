package com.hueljk.ibeacon.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.CartPro;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称：HuelJk
 * 类描述:
 * 创建时间:2017/1/18 0018.
 * 创建人：${ZHANGHAO}.
 * 修改人：${ZHANGHAO}.
 */

public class CartAdapter extends BaseAdapter {
    private List<CartPro> mData = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    public CartAdapter(){

    }

    public CartAdapter(Context context,List<CartPro> data) {
        mLayoutInflater = LayoutInflater.from(context);
        if (data != null) {
            mData.addAll(data);
        }

    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public void update(List<CartPro> data) {
        mData.clear();

        if (data != null) {
            mData.addAll(data);
        }

        //刷新数据
        notifyDataSetChanged();
    }

    @Override
    public CartPro getItem(int i) {
        if (i >= 0 && i < getCount()) {
            return mData.get(i);
        }
        return null;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mLayoutInflater.inflate(R.layout.list_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            holder.mCheck = (ImageView) convertView.findViewById(R.id.pro_checkbox);
            holder.mIcon = (ImageView) convertView.findViewById(R.id.pro_image);
            holder.mName = (TextView) convertView.findViewById(R.id.pro_name);
            holder.mColor = (TextView) convertView.findViewById(R.id.pro_color);
            holder.mCount = (TextView) convertView.findViewById(R.id.pro_count);
            holder.mAdd = (TextView) convertView.findViewById(R.id.pro_add);
            holder.mMinus = (TextView) convertView.findViewById(R.id.pro_minus);
            holder.mPrice = (TextView) convertView.findViewById(R.id.pro_Price);
        }
        CartPro product = getItem(i);



        holder.mCheck.setImageResource(product.getCheck());
        holder.mIcon.setImageResource(product.getIcon());
        holder.mName.setText(product.getName());
        holder.mColor.setText(product.getColor());
        holder.mCount.setText(product.getCount());
        holder.mAdd.setText(product.getAdd());
        holder.mMinus.setText(product.getMinus());
        holder.mPrice.setText(product.getPrice()+"");
        return convertView;
    }


    private static class ViewHolder {
        private ImageView mCheck;
        private ImageView mIcon;
        private TextView mName;
        private TextView mColor;
        private TextView mCount;
        private TextView mAdd;
        private TextView mMinus;
        private TextView mPrice;

        public ViewHolder(View convertView) {
            if (convertView != null) {
                convertView.setTag(this);
            }
        }
    }
}
