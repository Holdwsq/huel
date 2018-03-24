package com.hueljk.ibeacon.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.GoodsCommentBean;

import java.util.List;

/**
 * 评论适配器
 * Created by wsq on 2018/3/24.
 */

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentListViewHolder>{
    /**
     * 应用上下文
     */
    private Context mContext;
    /**
     * 评论列表
     */
    private List<GoodsCommentBean> goodsCommentBeans;

    @Override
    public CommentListViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.mContext = parent.getContext();
        CommentAdapter.CommentListViewHolder viewHolder = new CommentAdapter.CommentListViewHolder(LayoutInflater
                .from(mContext).inflate(R.layout.item_comment_list, parent, false));
        return viewHolder;
    }

    /**
     * 传入数据
     * @param beanList
     */
    public void setData(List<GoodsCommentBean> beanList){
        this.goodsCommentBeans = beanList;
    }
    @Override
    public void onBindViewHolder(CommentListViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return goodsCommentBeans != null ? goodsCommentBeans.size() : 0;
    }

    static class CommentListViewHolder extends RecyclerView.ViewHolder{

        public CommentListViewHolder(View itemView) {
            super(itemView);
        }
    }
}
