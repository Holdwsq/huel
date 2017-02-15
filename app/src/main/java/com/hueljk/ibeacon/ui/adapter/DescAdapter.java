package com.hueljk.ibeacon.ui.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.mode.Desc;
import com.hueljk.ibeacon.mode.DescImg;
import java.util.List;


/**
 * Created by zc on 2017/2/15.
 */
public class DescAdapter extends BaseAdapter implements View.OnClickListener {
    private LayoutInflater mInflater;
    private Desc mDesc;
    private List<DescImg> mDescImgs;
    private Context mContext;

    private int mType = 1;

    public DescAdapter(Context context, Desc desc) {
        mInflater = LayoutInflater.from(context);
        mContext = context;
        mDesc = desc;
        if (mDesc == null) {
            mDesc = new Desc();
            mDesc.setLength(0);
        }
    }

    @Override
    public int getCount() {
        return mDesc.getLength();
    }

    @Override
    public Desc getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_desc, parent, false);
            holder = new Holder();
            holder.mDescImg = (ImageView) convertView.findViewById(R.id.desc_img);
            holder.mDescName = (TextView) convertView.findViewById(R.id.desc_name);
            holder.mDescTx = (TextView) convertView.findViewById(R.id.desc_tx);
            convertView.setTag(holder);
        } else {
            holder = (Holder) convertView.getTag();
        }

        if (mType == 1) {
            holder.mDescImg.setVisibility(View.VISIBLE);
            Glide.with(mContext).load(UrlConstants.DescImgUrl + mDesc.getDescImgs().get(position).getUrl())
                    .centerCrop()
                    .placeholder(R.drawable.caomeii)
                    .error(R.drawable.caomeii)
                    .into(holder.mDescImg);
            Log.d("-----","descUrl:"+UrlConstants.DescImgUrl +mDesc.getDescImgs().get(position).getUrl());
        } else {
            holder.mDescImg.setVisibility(View.GONE);
            switch (position) {
                case 0:
                    holder.mDescName.setText("SIZE");
                    holder.mDescTx.setText(mDesc.getDescPrameters().getSize());
                    break;
                case 1:
                    holder.mDescName.setText("庫存");
                    holder.mDescTx.setText(mDesc.getDescPrameters().getStock() + "");
                    break;
            }
        }

        return convertView;
    }


    public void update(Desc desc, int type) {
        if (desc != null) {
            mDesc = desc;
            mDescImgs = mDesc.getDescImgs();
            mType = type;
            mDesc.setLength(mDescImgs.size());
        }
        notifyDataSetChanged();
    }

    public void setType(int type) {
        mType = type;
        if (type == 1) {
            mDesc.setLength(mDesc.getDescImgs().size());
        } else {
            mDesc.setLength(2);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {

    }

    private class Holder {
        private ImageView mDescImg;
        private TextView mDescName;
        private TextView mDescTx;
    }
}
