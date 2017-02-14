package com.hueljk.ibeacon.ui.setting;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.hueljk.ibeacon.R;
import com.hueljk.ibeacon.manager.PreferenceManager;
import com.hueljk.ibeacon.ui.BaseFragment;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by zc on 2017/2/14.
 */
public class PersonSettingFragment extends BaseFragment implements View.OnClickListener{
    private String[] mItems = new String[]{"个人信息", "修改用户名", "修改密码", "关于", "消息通知", "通用",};
  private Button mLogoutButton;
    private PreferenceManager mPreferenceManager;
    private ImageView mSetReturn;
    private ListView mPerListView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.set_personal, container, false);
    }

    @Override
    protected void initView(View view) {
        super.initView(view);
        mLogoutButton=(Button)view.findViewById(R.id.logout_button);
        mSetReturn=(ImageView)view.findViewById(R.id.set_return);
        mPerListView=(ListView)view.findViewById(R.id.per_setlv);
        mPreferenceManager = PreferenceManager.getInstance();

    }

    @Override
    protected void setListener() {
        super.setListener();
        mLogoutButton.setOnClickListener(this);
        mSetReturn.setOnClickListener(this);
        mPerListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Toast.makeText(getContext(), "listview被点击了，位置是" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 1:
                        Toast.makeText(getContext(), "listview被点击了，位置是" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 2:
                        Toast.makeText(getContext(), "listview被点击了，位置是" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 3:
                        Toast.makeText(getContext(), "listview被点击了，位置是" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 4:
                        Toast.makeText(getContext(), "listview被点击了，位置是" + position, Toast.LENGTH_SHORT).show();
                        break;
                    case 5:
                        Toast.makeText(getContext(), "listview被点击了，位置是" + position, Toast.LENGTH_SHORT).show();
                        break;

                }
            }
        });
    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.logout_button:
                mPreferenceManager.saveLoginStatus(false);
                //返回一级界面
                popSelf();
                //发送一条消息，告诉个人中心：你需要刷新
                EventBus.getDefault().post("未登录");
                break;
            case R.id.set_return:
                popSelf();
        }




    }

    @Override
    protected void setData() {
        super.setData();
        mPerListView.setAdapter(new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, mItems));
        if(mPreferenceManager.getLoginStatus()){
            mLogoutButton.setVisibility(View.VISIBLE);
        }else {
            mLogoutButton.setVisibility(View.INVISIBLE);
        }
    }
}
