package com.hueljk.ibeacon.callback;

import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Window;

import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Response;
import com.lzy.okgo.request.base.Request;

/**
 * Created by admin on 2018/3/26.
 */

public class StringDialogCallback extends StringCallback {

    private ProgressDialog dialog;

    @Override
    public void onSuccess(Response<String> response) {

    }

    public StringDialogCallback(Activity activity, String dsec) {
        dialog = new ProgressDialog(activity);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setMessage(dsec);
    }

    @Override
    public void onStart(Request<String, ? extends Request> request) {
        if (dialog != null && !dialog.isShowing()) {
            dialog.show();
        }
    }

    @Override
    public void onFinish() {
        if (dialog != null && dialog.isShowing()) {
            dialog.dismiss();
        }
    }
}
