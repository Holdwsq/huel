package com.hueljk.ibeacon.ui.navigation;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.goyourfly.multi_picture.MultiPictureView;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.callback.StringDialogCallback;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.mode.ResponseBean;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.model.Progress;
import com.lzy.okgo.model.Response;
import com.tbruyelle.rxpermissions2.RxPermissions;
import com.zhihu.matisse.Matisse;
import com.zhihu.matisse.MimeType;
import com.zhihu.matisse.engine.impl.GlideEngine;
import com.zhihu.matisse.filter.Filter;
import com.zhihu.matisse.internal.entity.CaptureStrategy;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Fragment;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.jiguang.net.HttpRequest;
import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;
import okhttp3.internal.http.HttpHeaders;

/**
 * 发布旧物闲置
 * Created by wsq on 2018/3/25.
 */

public class PublishOldFragment extends BaseFragment implements View.OnClickListener {
    /**
     * 图片选择器
     */
    private MultiPictureView multiPictureView;
    private static final int REQUEST_ADD_IMAGE = 2;
    /**
     * multiPictureView 对应的文件路径
     */
    private List<String> pathList = new ArrayList<>();
    /**
     * 返回按钮
     */
    private ImageView publishReturn;
    /**
     * 商品标题
     */
    private EditText title;
    /**
     * 商品描述
     */
    private EditText desc;
    /**
     * 学校信息
     */
    private TextView school;
    /**
     * 商品价格
     */
    private EditText price;
    /**
     * 商品分类
     */
    private EditText type;
    /**
     * 发布按钮
     */
    private Button publishBt;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_publish_old, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        publishReturn = view.findViewById(R.id.publish_return);
        title = view.findViewById(R.id.publish_title);
        desc = view.findViewById(R.id.publish_desc);
        school = view.findViewById(R.id.publish_school);
        type = view.findViewById(R.id.publish_type);
        price = view.findViewById(R.id.publish_price);
        publishBt = view.findViewById(R.id.publish_bt);

        multiPictureView = view.findViewById(R.id.multiple_image);
        multiPictureView.setDeleteResource(R.drawable.ic_delete_24dp);

    }

    @Override
    protected void setListener() {
        super.setListener();
        publishBt.setOnClickListener(this);
        publishReturn.setOnClickListener(this);

        multiPictureView.setAddClickCallback(new MultiPictureView.AddClickCallback() {
            @Override
            public void onAddClick(View view) {
                addImage();
            }
        });
        multiPictureView.setDeleteClickCallback(new MultiPictureView.DeleteClickCallback() {
            @Override
            public void onDeleted(View view, int i) {
                // 获取视图中的图片列表
                multiPictureView.removeItem(i);
                pathList.remove(i);
            }
        });
    }

    @Override
    protected void setData() {
        super.setData();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.publish_return:
                popSelf();
                break;
            case R.id.publish_bt:
                // 校验商品信息是否完整
                boolean flag = checkInfo();
                if (flag){
                    publishGoods2Server();
                }
                break;
            default:
                Log.i(PublishOldFragment.class.getSimpleName(), "没有对应点击组件");
                break;
        }
    }

    /**
     * 发布商品信息到服务器
     */
    private void publishGoods2Server() {
        String publishGoodsUrl = UrlConstants.publishGoodsUrl;
        // 参数map
        Map<String, String> map = new HashMap<>();
        map.put("title", title.getText().toString());
        map.put("description", title.getText().toString());
        map.put("type", type.getText().toString());
        // 将文件路径转为File类型
        List<File> fileList = new ArrayList<>();
        for (String pathName : pathList) {
            fileList.add(new File(pathName));
        }

        OkGo.<String>post(publishGoodsUrl)
                .tag(this)
                .headers("Content-Type", "multipart/form-data")
                .params(map)
                .params("price", Float.parseFloat(price.getText().toString()))
                .addFileParams("files", fileList)
                .execute(new StringDialogCallback(getActivity(), "发布中...") {
                    @SuppressLint("ShowToast")
                    @Override
                    public void onSuccess(Response<String> response) {
                        // 请求成功 处理
                        String data = response.body();
                        if (response.code() == 200){
                            ResponseBean<String> resp = JSON.parseObject(data, new TypeReference<ResponseBean<String>>() {});
                            String status = resp.getStatus();
                            if (ResponseBean.STATUS_SUCCESS.equals(status)){
                                clearInputInfo();
                                Toast.makeText(getContext(), "发布成功", Toast.LENGTH_SHORT).show();
                            }else{
                                Toast.makeText(getContext(), resp.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    }
                });
    }

    /**
     * 清空输入框
     */
    private void clearInputInfo(){
        title.setText("");
        desc.setText("");
        multiPictureView.clearItem();
        price.setText("");
        type.setText("");
    }
    /**
     * 检测商品信息
     */
    private boolean checkInfo() {
        String titleData = title.getText().toString();
        String descData = desc.getText().toString();
        String schoolData = school.getText().toString();
        String priceData = price.getText().toString();
        String typeData = type.getText().toString();
        ArrayList<Uri> list = multiPictureView.getList();
        try{
            if ("".equals(titleData.trim())){
                throw new RuntimeException("商品标题不能为空");
            }
            if ("".equals(descData.trim())){
                throw new RuntimeException("商品描述不能为空");
            }
            if ("".equals(priceData.trim())){
                throw new RuntimeException("商品价格不能为空");
            }
            if ("".equals(typeData.trim())){
                throw new RuntimeException("商品分类不能为空");
            }
            if (list.size() <= 0){
                throw new RuntimeException("商品图片不能为空");
            }
        }catch (RuntimeException e){
            Toast.makeText(getContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    void addImage() {

        RxPermissions rxPermissions = new RxPermissions(this.getActivity());
        rxPermissions.request(Manifest.permission.WRITE_EXTERNAL_STORAGE)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Boolean aBoolean) {
                        if (aBoolean) {
                            Matisse.from(PublishOldFragment.this)
                                    .choose(MimeType.ofAll(), false)
                                    .countable(true)
                                    .capture(true)
                                    .captureStrategy(
                                            new CaptureStrategy(true, "com.hueljk.ibeacon.fileprovider"))
                                    .maxSelectable(9)
                                    .addFilter(new GifSizeFilter(320, 320, 5 * Filter.K * Filter.K))
                                    .gridExpectedSize(
                                            getResources().getDimensionPixelSize(R.dimen.grid_expected_size))
                                    .restrictOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT)
                                    .thumbnailScale(0.85f)
                                    .imageEngine(new GlideEngine())
                                    .forResult(REQUEST_ADD_IMAGE);
                        } else {
                            Toast.makeText(getContext(), R.string.permission_request_denied, Toast.LENGTH_LONG)
                                    .show();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_ADD_IMAGE && resultCode == Activity.RESULT_OK) {
            pathList.addAll(Matisse.obtainPathResult(data));
            multiPictureView.addItem(Matisse.obtainResult(data));
        }
    }
}
