package com.hueljk.ibeacon.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
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

import org.greenrobot.eventbus.EventBus;

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
public class LoginFragment extends BaseFragment implements View.OnClickListener {
    private TextView mRegister;
    private EditText mUsernameET;
    private EditText mPasswordET;
    private Button mLoginButton;
    private Button mUsernameClearBT;
    private Button mPasswdClearBT;
    private Button mEyeBT;
    private PreferenceManager mPreferenceManager;
    private TextWatcher mUsernameWatcher;
    private TextWatcher mPasswordWatcher;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.login, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mRegister = (TextView) view.findViewById(R.id.register);
        mUsernameET = (EditText) view.findViewById(R.id.username);
        mPasswordET = (EditText) view.findViewById(R.id.password);
        mLoginButton = (Button) view.findViewById(R.id.login);
        mUsernameClearBT=(Button)view.findViewById(R.id.bt_username_clear);
        mPasswdClearBT=(Button)view.findViewById(R.id.bt_pwd_clear);
        mEyeBT=(Button)view.findViewById(R.id.bt_pwd_eye);



    }

    @Override
    protected void setListener() {
        super.setListener();
        mRegister.setOnClickListener(this);
        mLoginButton.setOnClickListener(this);
        mUsernameClearBT.setOnClickListener(this);
        mPasswdClearBT.setOnClickListener(this);
        mEyeBT.setOnClickListener(this);
        initWatcher();
        mUsernameET.addTextChangedListener(mUsernameWatcher);
        mPasswordET.addTextChangedListener(mPasswordWatcher);



    }

    @Override
    protected void setData() {
        super.setData();

    }

    public void login() {
        //取得用户输入的账号和密码
        String user = mUsernameET.getText().toString();
        String pass = mPasswordET.getText().toString();
       if (isInputValid()) {
            Toast.makeText(getContext(), "请输入完整的登录信息！", Toast.LENGTH_SHORT).show();
            return;
        }

        //okhttp  get方法访问服务器
        String url = UrlConstants.loginUrl + "?name=" + user + "&password=" + pass;
        Log.d("-----------", "register: " + url);
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
                        //解析，提示登录成功或失败，跳转主页或者个人中心
                        Type type = new TypeToken<Result<User>>() {
                        }.getType();
                        Result<User> UserResult = JsonUtils.parse(jsonStr, type);
                        if (UserResult.mCode == 200) {
                            Toast.makeText(getContext(), "登录成功！", Toast.LENGTH_SHORT).show();
                            mPreferenceManager.saveLoginStatus(true);
                            mPreferenceManager.saveUserId(UserResult.mData.getId());
                            mPreferenceManager.saveUserName(UserResult.mData.getUserName());
                            //返回一级界面
                            popSelf();
                            //mMainActivity.toHomeFragment();
                            //发送一条消息，告诉个人中心：你需要刷新
                            EventBus.getDefault().post(UserResult.mData.getUserName());

                        } else {
                            Toast.makeText(getContext(), "登录失败，请重新登录！", Toast.LENGTH_SHORT).show();
                            mUsernameET.setText("");
                            mPasswordET.setText("");

                            //mUsernameET.setInputType(InputType.TYPE_TEXT_VARIATION_PASSWORD);
                        }

                    }
                });

            }
        });
    }


    private boolean isInputValid() {
        //检查用户输入的合法性，这里暂且默认用户输入合法
        if ( mUsernameET.getText().toString().equals("")||   mPasswordET.getText().toString().equals("")) {
            return true;
        }
        return false;

    }
    private void initWatcher() {
        mUsernameWatcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                mPasswordET.setText("");
                if(s.toString().length()>0){
                    mUsernameClearBT.setVisibility(View.VISIBLE);
                }else{
                    mUsernameClearBT.setVisibility(View.INVISIBLE);
                }
            }
        };

        mPasswordWatcher = new TextWatcher() {
            public void onTextChanged(CharSequence s, int start, int before, int count) {}
            public void beforeTextChanged(CharSequence s, int start, int count,int after) {}
            public void afterTextChanged(Editable s) {
                if(s.toString().length()>0){
                    mPasswdClearBT.setVisibility(View.VISIBLE);
                }else{
                    mPasswdClearBT.setVisibility(View.INVISIBLE);
                }
            }
        };
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.login:
                login();
                break;
            case R.id.register:
                mMainActivity.showFragment(RegisterFragment.class, "Login_2_register");
                break;
            case R.id.bt_username_clear:
                Toast.makeText(getContext(),"UsernameClear",Toast.LENGTH_SHORT).show();
                mUsernameET.setText("");
                mPasswordET.setText("");
                break;
            case R.id.bt_pwd_clear:
                Toast.makeText(getContext(),"PwdClear",Toast.LENGTH_SHORT).show();
                mPasswordET.setText("");
                break;
            case R.id.bt_pwd_eye:
                Toast.makeText(getContext(),"PwdEye",Toast.LENGTH_SHORT).show();
                if(mPasswordET.getInputType()==(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD)){
                    mPasswordET.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_NORMAL);
                }else{
                    mPasswordET.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }

                break;
        }
    }
}
