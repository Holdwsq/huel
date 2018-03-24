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
 * 商品列表中图片列表
 * Created by wsq on 2018/3/18.
 */

public class ImageRecyclerAdapter extends RecyclerView.Adapter<ImageRecyclerAdapter.ImageListViewHolder> {
    /**
     * 上下文环境
     */
    private Context mContext;
    /**
     * 正常情况下应该传过来图片的下载路径
     */
    private List<String> imageUrlList;
    /**
     * 点击事件处理
     */
    private GoodsAdapter.OnItemClickListener onItemClickListener;
    /**
     * 商品id
     */
    private String goodsId;
    /**
     * 商品位置
     */
    private int position;

    public void setData(List<String> imageUrlList, int position, String goodsId){
        this.goodsId = goodsId;
        this.position = position;
        this.imageUrlList = imageUrlList;
    }

    public void setOnItemClickListener(GoodsAdapter.OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public ImageListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        ImageListViewHolder viewHolder = new ImageListViewHolder(LayoutInflater.from(mContext)
                .inflate(R.layout.item_image, parent, false));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onItemClickListener.onItemClick(view, position, goodsId);
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onItemClickListener.onItemLongClick(view, position, goodsId);
                return true;
            }
        });
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ImageListViewHolder holder, int position) {
        Glide.with(mContext)
                .load(imageUrlList.get(position))
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return imageUrlList != null ? imageUrlList.size() : 0;
    }

    static class ImageListViewHolder extends RecyclerView.ViewHolder {

        private ImageView image;
        ImageListViewHolder(View contentView) {
            super(contentView);
            image = contentView.findViewById(R.id.image_item);
        }
    }
}
