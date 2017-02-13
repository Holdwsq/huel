package com.hueljk.ibeacon.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
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
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zc on 2017/2/13.
 */
public class LoginFragment extends BaseFragment implements View.OnClickListener{
    private TextView mRegister;
    private EditText mUsernameET;
    private EditText mPasswordET;
    private Button mLoginButton;
    private PreferenceManager mPreferenceManager;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRegister=(TextView)view.findViewById(R.id.register);
        mUsernameET= (EditText) view.findViewById(R.id.username);
        mPasswordET = (EditText) view.findViewById(R.id.password);
        mLoginButton = (Button) view.findViewById(R.id.login);

    }

    @Override
    protected void setListener() {
        super.setListener();
        mRegister.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
    }

    @Override
    protected void setData() {
        super.setData();
    }

    public void login() {
        //取得用户输入的账号和密码
        if (!isInputValid()) {
            return;
        }
        String user=mUsernameET.getText().toString();
        String pass=mPasswordET.getText().toString();
        //okhttp  get方法访问服务器
        String url = UrlConstants.loginUrl + "?name=" + user + "&password=" + pass;
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
                        //解析，提示注册成功或失败，跳转登录或者个人中心
                        Type type = new TypeToken<Result<User>>(){}.getType();
                        Result<User> UserResult= JsonUtils.parse(jsonStr ,type);
                        if(UserResult.mCode == 200){
                            Toast.makeText(getContext(),"登录成功！",Toast.LENGTH_SHORT).show();
                            popSelf();
                            mPreferenceManager.saveLoginStatus(true);
                            mPreferenceManager.saveUserId(UserResult.mData.getId());
                            mPreferenceManager.saveUserName(UserResult.mData.getUserName());
                        }else{
                            Toast.makeText(getContext(),"登录失败，请重新登录！",Toast.LENGTH_SHORT).show();
                            mUsernameET.setText("");
                            mPasswordET.setText("");
                        }

                    }
                });

            }
        });
    }


    private boolean isInputValid() {
        //检查用户输入的合法性，这里暂且默认用户输入合法
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login:
                login();
                break;
            case R.id.register:
                mMainActivity.showFragment(RegisterFragment.class,"Login_2_register");
                break;
        }
    }
}
