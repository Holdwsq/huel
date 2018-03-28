package com.hueljk.ibeacon.ui.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.GoodsInfo;
import com.hueljk.ibeacon.view.LrItemDecoration;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * 主页商品列表适配器
 * Created by admin on 2018/3/17.
 */

public class GoodsAdapter extends RecyclerView.Adapter<GoodsAdapter.GoodsListViewHolder>{
    /**
     * 商品信息列表
     */
    private List<GoodsInfo> goodsInfoList;
    /**
     * 上下文
     */
    private Context mContext;
    /**
     * 内部嵌套 RecyclerView 共用一个viewPool
     */
    private RecyclerView.RecycledViewPool viewPool;
    /**
     * Item 点击事件
     */
    private OnItemClickListener myItemClickListener;

    public GoodsAdapter(){
        super();
        viewPool = new RecyclerView.RecycledViewPool();
    }

    public void setMyItemClickListener(OnItemClickListener itemClickListener){
            this.myItemClickListener = itemClickListener;
    }

    public void setData(List<GoodsInfo> list) {
        this.goodsInfoList = list;
        notifyDataSetChanged();
    }
    public void updateData(List<GoodsInfo> goodsInfos){
        if (goodsInfos == null || goodsInfos.size() <= 0){
            return;
        }
        this.goodsInfoList = goodsInfos;
        notifyDataSetChanged();
    }

    @Override
    public GoodsListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        final GoodsListViewHolder viewHolder = new GoodsListViewHolder(LayoutInflater.from(mContext).inflate(R.layout.item_goods_list, parent, false));
        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int position = viewHolder.getLayoutPosition();
                myItemClickListener.onItemClick(view, position, goodsInfoList.get(position).getId());
            }
        });
        viewHolder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                int position = viewHolder.getLayoutPosition();
                myItemClickListener.onItemLongClick(view, position, goodsInfoList.get(position).getId());
                return true;
            }
        });
        return viewHolder;
    }

    @SuppressLint({"SimpleDateFormat", "SetTextI18n"})
    @Override
    public void onBindViewHolder(GoodsListViewHolder holder, int position) {
        if (goodsInfoList == null
                || goodsInfoList.size() <= 0){
            return ;
        }
        GoodsInfo goodsInfo = goodsInfoList.get(position);
        holder.publishName.setText(goodsInfo.getUserName());
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date time = new Date(goodsInfo.getCreateTime());
        holder.publishTime.setText(simpleDateFormat.format(time));
        holder.price.setText("￥ 100");
        holder.schoolInfo.setText("来自 河南财经大学（郑东新区）");
        holder.goodsDesc.setText(goodsInfo.getDescription());
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        // 横向滑动
        mLinearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        holder.recyclerView.setLayoutManager(mLinearLayoutManager);
        if (holder.recyclerView.getAdapter() == null){
            ImageRecyclerAdapter imageAdapter = new ImageRecyclerAdapter();
            imageAdapter.setData(goodsInfo.getFileUrls(), position, goodsInfo.getId());
            imageAdapter.setOnItemClickListener(myItemClickListener);
            holder.recyclerView.setAdapter(imageAdapter);
        }else{
            //第二次不为时候需要更新holder 的listener
            ImageRecyclerAdapter adapter = (ImageRecyclerAdapter) holder.recyclerView.getAdapter();
            adapter.setData(goodsInfo.getFileUrls(), position, goodsInfo.getId());
            adapter.updateListener();
        }
        holder.recyclerView.addItemDecoration(new LrItemDecoration(10));
        // 内部recyclerView 功用一个viewPool
        holder.recyclerView.setRecycledViewPool(viewPool);
    }

    @Override
    public int getItemCount() {
//        return this.goodsInfoList.size();
        if (goodsInfoList == null
                || goodsInfoList.size() <= 0){
            return 0;
        }
        return goodsInfoList.size();
    }

    static class GoodsListViewHolder extends RecyclerView.ViewHolder {

        private TextView publishTime;
        private TextView publishName;
        private TextView price;
        private TextView schoolInfo;
        private RecyclerView recyclerView;
        private TextView goodsDesc;

        GoodsListViewHolder(View contentView) {
            super(contentView);
            recyclerView = contentView.findViewById(R.id.imageRecycler);
            goodsDesc = contentView.findViewById(R.id.goodsDesc);
            publishName = contentView.findViewById(R.id.publishName);
            publishTime = contentView.findViewById(R.id.publishTime);
            price = contentView.findViewById(R.id.price);
            schoolInfo = contentView.findViewById(R.id.schoolName);
        }
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position, String goodsId);
        void onItemLongClick(View view, int position, String goodsId);
    }
}
