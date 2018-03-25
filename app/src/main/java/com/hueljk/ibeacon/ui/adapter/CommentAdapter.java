package com.hueljk.ibeacon.ui.adapter;

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
import com.hueljk.ibeacon.mode.GoodsCommentBean;
import com.hueljk.ibeacon.view.SpacesItemDecoration;

import java.util.ArrayList;
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
    /**
     * 内部嵌套 RecyclerView 共用一个viewPool
     */
    private RecyclerView.RecycledViewPool viewPool;
    public CommentAdapter(){
        super();
        viewPool = new RecyclerView.RecycledViewPool();
    }
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
        /// todo 暂时不作处理
        Glide.with(mContext)
                .load(R.drawable.aaa)
                .into(holder.commenterTv);
        LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
        mLinearLayoutManager.setSmoothScrollbarEnabled(true);
        mLinearLayoutManager.setAutoMeasureEnabled(true);
        holder.recyclerView.setLayoutManager(mLinearLayoutManager);
        holder.recyclerView.setHasFixedSize(true);
        holder.recyclerView.setNestedScrollingEnabled(false);
        holder.recyclerView.addItemDecoration(new SpacesItemDecoration(1));
        holder.recyclerView.setRecycledViewPool(viewPool);
        SubCommentAdapter adapter = new SubCommentAdapter();
        holder.recyclerView.setAdapter(adapter);
    }

    @Override
    public int getItemCount() {
//        return goodsCommentBeans != null ? goodsCommentBeans.size() : 0;
        return 6;
    }

    static class CommentListViewHolder extends RecyclerView.ViewHolder{
        /**
         * 评论者头像
         */
        private ImageView commenterTv;
        /**
         * 评论者姓名
         */
        private TextView commenterName;
        /**
         * 评论内容
         */
        private TextView commentContent;
        /**
         * 评论时间
         */
        private TextView commentTime;
        /**
         * 子评论列表
         */
        private RecyclerView recyclerView;

        public CommentListViewHolder(View itemView) {
            super(itemView);
            commenterTv = itemView.findViewById(R.id.commenter_portrait);
            commenterName = itemView.findViewById(R.id.commenter_name);
            commentContent = itemView.findViewById(R.id.comment_content);
            commentTime = itemView.findViewById(R.id.comment_time);
            recyclerView = itemView.findViewById(R.id.sub_comment_list);
        }
    }
}
