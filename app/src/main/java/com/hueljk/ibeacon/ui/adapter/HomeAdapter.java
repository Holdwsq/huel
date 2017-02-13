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
import com.hueljk.ibeacon.mode.Goods;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by zc on 2017/2/9.
 */
public class HomeAdapter extends BaseAdapter implements View.OnClickListener{
    private LayoutInflater mInflater;
    private List<Goods> mGoodses = new ArrayList<>();
    private Context mContext;

    public HomeAdapter(Context context, List<Goods> products) {
        mInflater = LayoutInflater.from(context);
        mContext=context;
        if (products != null) {
            mGoodses.addAll(products);
        }
    }

    @Override
    public int getCount() {
        return mGoodses.size();
    }

    @Override
    public Goods getItem(int position) {
        return mGoodses.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Holder holder;
        if (convertView == null) {
            convertView = mInflater.inflate(R.layout.item_gv, parent, false);
            holder = new Holder();
            holder.mPIconUrl = (ImageView) convertView.findViewById(R.id.itemProImage);
            holder.mPdec = (TextView) convertView.findViewById(R.id.itemDecText);
            holder.mNum = (TextView) convertView.findViewById(R.id.itemNumText);
            holder.mPrice = (TextView) convertView.findViewById(R.id.itemPriText);
            holder.mCIon = (ImageView) convertView.findViewById(R.id.itemCartImage);
            convertView.setTag(holder);
            //使用setTag把查找的view缓存起来方便多次重用
        } else {
            holder = (Holder) convertView.getTag();
        }
        Goods goods = getItem(position);
        //holder.mPIconUrl.setImageResource(product.getPurl());
        //把点击的位置传递给点击事件
        holder.mCIon.setTag(position);
        holder.mCIon.setOnClickListener(this);
        Glide
                .with(mContext)
                .load(UrlConstants.picBaseUrl+goods.getPurl())
                .placeholder(R.drawable.shangpin1)
                .error(R.drawable.shangpin1)
                .into(holder.mPIconUrl);
        holder.mPdec.setText(goods.getPdesc());
        holder.mNum.setText(goods.getSold() + "已买");
        holder.mPrice.setText("￥" + goods.getPrice());
        holder.mCIon.setImageResource(R.drawable.cart);
        return convertView;
    }
    public void update(List<Goods> data) {
        mGoodses.clear();
        if (data != null) {
            mGoodses.addAll(data);
        }
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        //把点击事件  回调 給fragment
        mCallBack.onCartClick(v,position);
    }
    /**
     * 回调函數
     * 作用：将点击事件从一个地方转移的另一个地方
     *
     * 1.定义接口
     * 2.创建接口對象引用
     * 3.在内部相應地方調用，在外部創建對象
     */

    public interface CallBack{


        void onCartClick(View v, int position);
    }

    private CallBack mCallBack;

    public void setOnCartClickListener(CallBack mCallBack){
        this.mCallBack = mCallBack;
    }


    private static class Holder {
        private ImageView mPIconUrl;
        private TextView mPdec;
        private TextView mNum;
        private TextView mPrice;
        private ImageView mCIon;
    }
}
