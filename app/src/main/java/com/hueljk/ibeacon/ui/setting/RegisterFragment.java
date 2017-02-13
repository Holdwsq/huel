package com.hueljk.ibeacon.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.constants.UrlConstants;
import com.hueljk.ibeacon.manager.PreferenceManager;
import com.hueljk.ibeacon.mode.Result;
import com.hueljk.ibeacon.mode.User;
import com.hueljk.ibeacon.ui.BaseFragment;
import com.hueljk.ibeacon.ui.home.HomeFragment;
import com.hueljk.ibeacon.utils.JsonUtils;

import java.io.IOException;
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
    private PreferenceManager mPreferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.register, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRegisterButton = (Button) view.findViewById(R.id.register_button);
        mUserET = (EditText) view.findViewById(R.id.register_username);
        mPassET = (EditText) view.findViewById(R.id.register_pass);
        mRepassET = (EditText) view.findViewById(R.id.register_repass);


    }

    @Override
    protected void setListener() {
        super.setListener();
        mRegisterButton.setOnClickListener(this);
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


        if (!"".equals(user) && !"".equals(pass)) {
            //判断两次输入的密码是否一致
            if (!pass.equals(repass)) {
                Toast.makeText(getContext(), "两次输入的密码不一致，请重新输入！", Toast.LENGTH_LONG).show();
                mPassET.setText("");//清空“密码”编辑框
                mRepassET.setText("");//清空“确认密码”编辑框
                mPassET.requestFocus(); //让“密码”编辑框获得焦点
            } else {
                //执行注册操作
                register(user, pass);
            }

        } else {
            Toast.makeText(getContext(), "用户名密码不能为空！", Toast.LENGTH_LONG).show();
        }
    }

    private void register(String user, String pass) {
        //okhttp  get方法访问服务器
        String url = UrlConstants.registerUrl + "?name=" + user + "&password=" + pass;
        Log.d("-----------", "register: "+url);
        Request request = new Request.Builder()
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
                            Toast.makeText(getContext(),"注册成功！",Toast.LENGTH_SHORT).show();
                            mMainActivity.showFragment(LoginFragment.class,"register_2_login");
                            mPreferenceManager.saveUserId(result.mData.intValue());
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
        });
    }
}
