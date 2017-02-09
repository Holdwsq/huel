package com.hueljk.ibeacon.ui.adapter;

/**
 * Created by zc on 2017/2/9.
 */
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
import com.hueljk.ibeacon.mode.BaseEntity;


import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2017/1/19.
 */
public class BaAdapter extends BaseAdapter {
    private LayoutInflater mInflater;
    private List<BaseEntity> mList = new ArrayList<>();
    private Context mContext;
    public BaAdapter(Context context, List<BaseEntity> list ) {
        mInflater = LayoutInflater.from(context);
        mContext=context;
        if(list  != null){
            mList .addAll(list);
        }

    }

    @Override
    public int getCount() {
        return mList .size();
    }

    @Override
    public BaseEntity getItem(int position) {
        return mList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if(convertView == null){
            //convertView=mInflater.inflate(R.layout.item_test,parent,false);
            holder=new Holder();
            //holder.mId=(TextView)convertView.findViewById(R.id.id_tx);
            //holder.mName=(TextView)convertView.findViewById(R.id.name_tx);
            //holder.mUrl=(ImageView)convertView.findViewById(R.id.sp_img);
            convertView.setTag(holder);
        }else{
            holder = (Holder) convertView.getTag();
        }
        BaseEntity baseEntity=getItem(position);
        holder.mId.setText(baseEntity.getId());
        holder.mName.setText(baseEntity.getName());
        Glide
                .with(mContext)
                .load(UrlConstants.picBaseUrl+baseEntity.getUrl())
                .into(holder.mUrl);
        //holder.mUrl.setImageResource();
        return convertView;
    }

    public void update(List<BaseEntity> data) {
        mList.clear();
        if (data != null) {
            mList.addAll(data);
        }
        notifyDataSetChanged();
    }

    private static class Holder{
        private TextView mId;
        private TextView mName;
        private ImageView mUrl;
    }
}

