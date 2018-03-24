package com.hueljk.ibeacon.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.manager.PreferenceManager;
import com.hueljk.ibeacon.mode.ResponseBean;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.mode.User;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.MainActivity;
import com.hueljk.ibeacon.ui.home.HomeFragment;
import com.hueljk.ibeacon.utils.JsonUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import java.io.IOException;
import java.io.PipedInputStream;
import java.lang.reflect.Type;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zc on 2017/2/13.
 */
public class RegisterFragment extends BaseFragment implements View.OnClickListener {
    private Button mRegisterButton;
    private EditText mUserET;
    private EditText mPassET;
    private EditText mRepassET;
    private Button mClearButton;
    private ImageView mRegisterReturn;
    private PreferenceManager mPreferenceManager;
    private ImageView mReNameClear;
    private ImageView mRePassClear;
    private ImageView mRerePassClear;
    private TextWatcher unameWatcher;
    private  TextWatcher passwordWatcher;
    private TextWatcher repasswordWacther;
    private View.OnFocusChangeListener unameFoces;
    private View.OnFocusChangeListener upassFoces;
    private View.OnFocusChangeListener urepassFoces;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRegisterButton = view.findViewById(R.id.register_button);
        mUserET = view.findViewById(R.id.register_username);
        mPassET = view.findViewById(R.id.register_pass);
        mRepassET = view.findViewById(R.id.register_repass);
        mRegisterReturn= view.findViewById(R.id.register_return);
        mReNameClear = view.findViewById(R.id.re_name_clear);
        mRePassClear = view.findViewById(R.id.re_pass_clear);
        mRerePassClear = view.findViewById(R.id.re_repass_clear);
       // mClearButton = (Button)view.findViewById(R.id.clear_button);
    }

    @Override
    protected void setListener() {
        super.setListener();
        initWatcher();
        initFocus();
        mRegisterButton.setOnClickListener(this);
        mRegisterReturn.setOnClickListener(this);
        mUserET.addTextChangedListener(unameWatcher);
        mPassET.addTextChangedListener(passwordWatcher);
        mRepassET.addTextChangedListener(repasswordWacther);
        mReNameClear.setOnClickListener(this);
        mRePassClear.setOnClickListener(this);
        mRerePassClear.setOnClickListener(this);
        mUserET.setOnFocusChangeListener(unameFoces);
        mPassET.setOnFocusChangeListener(upassFoces);
        mRepassET.setOnFocusChangeListener(urepassFoces);
       // mClearButton.setOnClickListener(this);
    }
    private void initFocus(){
        unameFoces = new View.OnFocusChangeListener(){

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!"".equals(mUserET.getText().toString())){
                        mReNameClear.setVisibility(View.VISIBLE);
                    }
                }else{
                    mReNameClear.setVisibility(View.INVISIBLE);
                }
            }
        };
        upassFoces = new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!"".equals(mPassET.getText().toString())){
                        mRePassClear.setVisibility(View.VISIBLE);
                    }
                }else{
                    mRePassClear.setVisibility(View.INVISIBLE);
                }
            }
        };
        urepassFoces = new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if(hasFocus){
                    if(!"".equals(mRepassET.getText().toString())){
                        mRerePassClear.setVisibility(View.VISIBLE);
                    }
                }else{
                    mRerePassClear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }
    private void initWatcher(){
        unameWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mReNameClear.setVisibility(View.VISIBLE);
                } else {
                    mReNameClear.setVisibility(View.INVISIBLE);
                }
            }
        };
        passwordWatcher = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mRePassClear .setVisibility(View.VISIBLE);
                } else {
                    mRePassClear.setVisibility(View.INVISIBLE);
                }
            }
        };
        repasswordWacther = new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().length() > 0) {
                    mRerePassClear .setVisibility(View.VISIBLE);
                } else {
                    mRerePassClear.setVisibility(View.INVISIBLE);
                }
            }
        };
    }
    @Override
    protected void setData() {
        super.setData();
    }

    @Override
    public void onClick(View v) {
        String user = mUserET.getText().toString();
        String pass = mPassET.getText().toString();
        String repass = mRepassET.getText().toString();
        switch (v.getId()) {
            case R.id.register_return:
                popSelf();
                break;
            case R.id.register_button:
                if (!"".equals(user) && !"".equals(pass)) {
                    //判断两次输入的密码是否一致
                    if (!pass.equals(repass)) {
                        Toast.makeText(getContext(), "两次输入的密码不一致，请重新输入！", Toast.LENGTH_LONG).show();
                        mPassET.setText("");//清空“密码”编辑框
                        mRepassET.setText("");//清空“确认密码”编辑框
                        mPassET.requestFocus(); //让“密码”编辑框获得焦点
                    } else {
                        //执行注册操作
//                        register(user, pass, repass);
                        final String url = UrlConstants.testRequest;
                        OkGo.<String>get(url)
                                .tag(this)
                                .execute(new StringCallback() {
                                    @Override
                                    public void onSuccess(com.lzy.okgo.model.Response<String> response) {
                                        if (response.code() == 200){
                                            Toast.makeText(getContext(), "访问成功：" + url, Toast.LENGTH_SHORT).show();
                                        }else{
                                            Toast.makeText(getContext(), "访问失败：" + url, Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                    }

                } else {
                    Toast.makeText(getContext(), "用户名密码不能为空！", Toast.LENGTH_LONG).show();
                }

                break;
            case R.id.re_name_clear:
                mUserET.setText("");
                break;
            case R.id.re_pass_clear:
                mPassET.setText("");
                break;
            case R.id.re_repass_clear:
                mRepassET.setText("");
                break;
        }
    }

    private void register(String user, String pass, String repass) {
        //okhttp  get方法访问服务器
        String url = UrlConstants.registerUrl;
        Log.d("-----------", "register: "+url);
        OkGo.<String>get(url).tag(this)
                .params("accountName", user)
                .params("pwd", pass)
                .params("confirmPwd", repass)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(final com.lzy.okgo.model.Response<String> response) {
                        //拿到服务器json数据，但是本方法是在主线程之外，
                        final String jsonStr = response.body();

                        //使用以下方法将结果切换回主线程
                        mMainActivity.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                //run方法内部是主线程
                                Log.d("---------", "run: " + jsonStr);
                                ResponseBean result = JSON.parseObject(jsonStr, ResponseBean.class);
                                //解析，提示注册成功或失败，跳转登录或者个人中心
                                if (response.code() == 200){
                                    if(ResponseBean.STATUS_SUCCESS.equals(result.getStatus())){
                                        Toast.makeText(getContext(), "注册成功：sessionId:" + result.getData(), Toast.LENGTH_SHORT).show();
                                        popSelf();
                                        mPreferenceManager.saveSessionId((String)result.getData());
                                    }else {
                                        Toast.makeText(getContext(), result.getMessage(),Toast.LENGTH_SHORT).show();
                                        mUserET.setText("");//清空“用户名”编辑框
                                        mPassET.setText("");//清空“密码”编辑框
                                        mRepassET.setText("");//清空“确认密码”编辑框
                                    }
                                } else{
                                    Toast.makeText(getContext(),"请求失败！",Toast.LENGTH_SHORT).show();
                                }

                            }
                        });
                    }
                });
       /*Request request = new Request.Builder()
                .url(url)
                .build();

       Call call = mOkHttpClient.newCall(request);
        //开启新线程执行网络耗时操作
        //以下就是新线程的操作，新线程和主线程之间的数据不能直接相互调用
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                //拿到服务器json数据，但是本方法是在主线程之外，
                final String jsonStr = response.body().string();

                //使用以下方法将结果切换回主线程
                mMainActivity.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        //run方法内部是主线程
                        Log.d("---------", "run: " + jsonStr);
                        mPreferenceManager = PreferenceManager.getInstance();
                        Type type = new TypeToken<Result<Integer>>(){}.getType();
                        Result<Integer> result = JsonUtils.parse(jsonStr,type);
                        //解析，提示注册成功或失败，跳转登录或者个人中心
                        if(result.mCode==200){
                           // Toast.makeText(getContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                            //mMainActivity.showFragment(LoginFragment.class,"register_2_login");
                            popSelf();
                            mPreferenceManager.saveUserId(result.mData);
                        }else if(result.mCode == -1){
                            Toast.makeText(getContext(),"用户名已存在！",Toast.LENGTH_SHORT).show();
                            mUserET.setText("");//清空“用户名”编辑框
                            mPassET.setText("");//清空“密码”编辑框
                            mRepassET.setText("");//清空“确认密码”编辑框

                        }else{
                            Toast.makeText(getContext(),"请求失败！",Toast.LENGTH_SHORT).show();
                        }

                    }
                });

            }
        });*/
    }
}
