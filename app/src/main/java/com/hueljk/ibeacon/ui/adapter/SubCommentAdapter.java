package com.hueljk.ibeacon.ui.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.GoodsCommentBean;

import java.util.List;

/**
 * 下级评论适配器，避免冲突不使用原有评论适配器
 * Created by wsq on 2018/3/24.
 */

public class SubCommentAdapter extends RecyclerView.Adapter<SubCommentAdapter.SubCommentViewHolder>{
    /**
     * 应用上下文
     */
    private Context myContext;
    /**
     * 下级评论列表
     */
    private List<GoodsCommentBean> commentBeanList;

    /**
     * 设置数据
     * @param commentBeans
     */
    public void setData(List<GoodsCommentBean> commentBeans){
        this.commentBeanList = commentBeans;
    }

    @Override
    public SubCommentViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        this.myContext = parent.getContext();
        SubCommentAdapter.SubCommentViewHolder viewHolder = new SubCommentAdapter.SubCommentViewHolder(LayoutInflater
                .from(myContext).inflate(R.layout.item_subcomment_list, parent, false));
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(SubCommentViewHolder holder, int position) {
        // 暂时不做处理
        Glide.with(myContext)
                .load(R.drawable.aaa)
                .into(holder.commenterTv);
    }

    @Override
    public int getItemCount() {
        return 6;
    }

    static class SubCommentViewHolder extends RecyclerView.ViewHolder {
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
        public SubCommentViewHolder(View itemView) {
            super(itemView);
            commenterTv = itemView.findViewById(R.id.subcommenter_portrait);
            commenterName = itemView.findViewById(R.id.subcommenter_name);
            commentContent = itemView.findViewById(R.id.subcomment_content);
            commentTime = itemView.findViewById(R.id.subcomment_time);
        }
    }
}
