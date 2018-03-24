package com.hueljk.ibeacon.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.hueljk.ibeacon.R;

/**
 * Created by zc on 2017/5/16.
 */
public class ImageAdapter extends BaseAdapter {
    private Context mContext;
    private LayoutInflater mInflater;
    public ImageAdapter(Context c,Integer[]img)
    {
        mInflater = LayoutInflater.from(c);
        mContext=c;
        this.mThumbIds=img;
    }
    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return mThumbIds.length;
    }
    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return null;
    }
    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub
        Holder holder=null;
        ImageView imageview;
        if(convertView==null)
        {
            holder= new Holder();
            convertView = mInflater.inflate(R.layout.imagefomat,parent,false);
            holder.imageurl = (ImageView)convertView.findViewById(R.id.imageitem);
            convertView.setTag(holder);
        }
        else
        {
            holder = (Holder) convertView.getTag();
        }
        holder.imageurl.setImageResource(mThumbIds[position]);
        return convertView;
    }
    private Integer[] mThumbIds;
    private static class Holder{
        ImageView   imageurl;
    }
}
