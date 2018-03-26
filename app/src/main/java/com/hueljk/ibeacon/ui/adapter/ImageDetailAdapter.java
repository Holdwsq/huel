package com.hueljk.ibeacon.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.hueljk.ibeacon.R;

import java.util.List;

/**
 * 商品详情中图片列表
 * Created by wsq on 2018/3/24.
 */

public class ImageDetailAdapter extends RecyclerView.Adapter<ImageDetailAdapter.ImageDetailViewHolder>{
    private Context mContext;
    private List<String> imageUrls;

    @Override
    public ImageDetailViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        final ImageDetailAdapter.ImageDetailViewHolder viewHolder = new ImageDetailAdapter.ImageDetailViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_image_detail, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageDetailViewHolder holder, int position) {
        Glide.with(mContext)
                .load(imageUrls.get(position))
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrls != null ? imageUrls.size() : 0;
    }

    public void setData(List<String> list) {
        this.imageUrls = list;
        notifyDataSetChanged();
    }

    public void updata(List<String> list) {
        this.imageUrls = list;
        notifyDataSetChanged();
    }

    static class ImageDetailViewHolder extends RecyclerView.ViewHolder{
        private ImageView imageView;

        public ImageDetailViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.item_image_detail);
        }


    }
}
