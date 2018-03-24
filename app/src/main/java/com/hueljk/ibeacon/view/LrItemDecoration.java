package com.hueljk.ibeacon.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by admin on 2018/3/18.
 */

public class LrItemDecoration extends RecyclerView.ItemDecoration {
    private int space;

    public LrItemDecoration(int space) {
        this.space = space;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view,
                               RecyclerView parent, RecyclerView.State state) {
        outRect.right = space;

        // Add top margin only for the first item to avoid double space between items
        if (parent.getChildPosition(view) == 0)
            outRect.left = space;
    }
}
