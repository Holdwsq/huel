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
import com.hueljk.ibeacon.mode.Clothes;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2017/1/17.
 */
public class MyAdpter_Clothes extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<Clothes> mClothes = new ArrayList<>();
    private Context mContext;
    public MyAdpter_Clothes(Context context, List<Clothes> clothes) {
        mInflater = LayoutInflater.from(context);
        mContext=context;
        if(clothes != null){
            mClothes.addAll(clothes);
        }

    }

    @Override
    public int getCount() {
        return mClothes.size();
    }

    @Override
    public Clothes getItem(int position) {
        return mClothes.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            convertView=mInflater.inflate(R.layout.item_clo,parent,false);
            holder=new Holder();
            holder.mCIonUrl=(ImageView)convertView.findViewById(R.id.itemCloImage);
            holder.mCdec=(TextView) convertView.findViewById(R.id.itemCloDecText);
            holder.mCPrice=(TextView)convertView.findViewById(R.id.itemCloPritx);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        Clothes clothes=getItem(position);
        //holder.mCIonUrl.setImageResource(clothes.getCIon());
        Glide.with(mContext).load(UrlConstants.cloPicUrl+clothes.getCloUrl()).centerCrop()
                .placeholder(R.drawable.clothes1)
                .error(R.drawable.clothes1)
                .into(holder.mCIonUrl);
        holder.mCdec.setText(clothes.getCloDesc());
        holder.mCPrice.setText("￥"+clothes.getCloPrice());
        return convertView;
    }

    public void update(List<Clothes> data) {
        mClothes.clear();
        if (data != null) {
            mClothes.addAll(data);
        }
        notifyDataSetChanged();
    }

    private static class Holder{
        private ImageView mCIonUrl;
        private TextView mCPrice;
        private TextView mCdec;
    }
}
