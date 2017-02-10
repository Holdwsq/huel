package com.hueljk.ibeacon.ui.home.banner;

import android.view.View;

/**
 * Author ： LongWeiHu on 2016/5/12.
 */
public interface ImageCycleViewListener<T> {
    /**
     * 单击图片事件
     *
     * @param position
     * @param imageView
     */
    void onImageClick(T info, int position, View imageView);
}
