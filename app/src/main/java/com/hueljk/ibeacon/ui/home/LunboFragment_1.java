package com.hueljk.ibeacon.ui.home;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.ui.BaseFragment;

/**
 * A simple {@link Fragment} subclass.
 */
public class LunboFragment_1 extends BaseFragment {


    public LunboFragment_1() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        TextView textView = new TextView(getActivity());
        textView.setText(R.string.hello_blank_fragment);
        return textView;
    }

}
