package com.hueljk.ibeacon.ui.adapter;
import com.bumptech.glide.Glide;
import android.content.Context;
import android.media.Image;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.mode.CartPro;
import com.hueljk.ibeacon.mode.Product;
import com.hueljk.ibeacon.constants.UrlConstants;
import java.net.URL;
import java.net.URLStreamHandler;
import java.net.URLStreamHandlerFactory;
import java.util.ArrayList;
import java.util.List;

import static android.R.id.list;
import static com.hueljk.ibeacon.R.drawable.clothes1;

/**
 * 项目名称：HuelJk
 * 类描述:
 * 创建时间:2017/1/18 0018.
 * 创建人：${ZHANGHAO}.
 * 修改人：${ZHANGHAO}.
 */


public class CartAdapter extends BaseAdapter implements View.OnClickListener {
    private List<CartPro> mData = new ArrayList<>();
    private LayoutInflater mLayoutInflater;
    private Context mContext;

    public CartAdapter(Context context, List<CartPro> data) {
        mLayoutInflater = LayoutInflater.from(context);
        if (data != null) {
            mData.addAll(data);
        }
        mContext = context;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    public void update(List<CartPro> data) {
        if (data != null) {
            mData.clear();
            mData.addAll(data);
            //刷新数据
            notifyDataSetChanged();
        }
    }

    @Override
    public CartPro getItem(int i) {
        return mData.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup viewGroup) {
        ViewHolder holder;
        if (convertView != null) {
            holder = (ViewHolder) convertView.getTag();
        } else {
            convertView = mLayoutInflater.inflate(R.layout.list_item, viewGroup, false);
            holder = new ViewHolder(convertView);
            holder.mCheck = (ImageView) convertView.findViewById(R.id.pro_checkbox);
            holder.mIcon = (ImageView) convertView.findViewById(R.id.pro_image);
            holder.mName = (TextView) convertView.findViewById(R.id.pro_name);
            holder.mColor = (TextView) convertView.findViewById(R.id.pro_color);
            holder.mCount = (TextView) convertView.findViewById(R.id.pro_count);
            holder.mAdd = (TextView) convertView.findViewById(R.id.pro_add);
            holder.mMinus = (TextView) convertView.findViewById(R.id.pro_minus);
            holder.mPrice = (TextView) convertView.findViewById(R.id.pro_Price);
        }
        CartPro cartPro = mData.get(i);

        if (cartPro.getSelected() != null && cartPro.getSelected()) {
            holder.mCheck.setImageDrawable(mContext.getResources().getDrawable(R.drawable.checkbox2));
        } else {
            holder.mCheck.setImageDrawable(mContext.getResources().getDrawable(R.drawable.checkbox1));
        }

        //把點擊的位置傳遞給點擊事件---i
        holder.mCheck.setTag(i);
        holder.mCheck.setOnClickListener(this);

        Glide
                .with(mContext)
                .load(UrlConstants.HomePicUrl+cartPro.getUrl())
                .placeholder(R.drawable.shangpin1)
                .error(R.drawable.shangpin1)
                .into(holder.mIcon);
        holder.mName.setText(cartPro.getDes());
        holder.mColor.setText(cartPro.getColor());
        holder.mCount.setText(cartPro.getNumber());
        holder.mAdd.setText(cartPro.getmAdd());
        holder.mMinus.setText(cartPro.getmMinus());
        holder.mPrice.setText(cartPro.getPrice() + "");
        return convertView;
    }

    @Override
    public void onClick(View v) {
        int position = (int) v.getTag();
        //把点击事件  回调 給fragment
        mCallBack.onBoxClick(v, position);
    }

    /**
     * 回调函數
     * 作用：将点击事件从一个地方转移的另一个地方
     * <p>
     * 1.定义接口
     * 2.创建接口對象引用
     * 3.在内部相應地方調用，在外部創建對象
     */

    public interface CallBack {
        void onBoxClick(View v, int position);
    }

    private CallBack mCallBack;

    public void setOnBoxClickListener(CallBack mCallBack) {
        this.mCallBack = mCallBack;
    }

    private static class ViewHolder {
        private ImageView mCheck;
        private ImageView mIcon;
        private TextView mName;
        private TextView mColor;
        private TextView mCount;
        private TextView mAdd;
        private TextView mMinus;
        private TextView mPrice;

        public ViewHolder(View convertView) {
            if (convertView != null) {
                convertView.setTag(this);
            }
        }
    }
}
