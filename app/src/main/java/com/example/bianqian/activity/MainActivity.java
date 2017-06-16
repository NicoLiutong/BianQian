package com.example.bianqian.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.example.bianqian.R;
import com.example.bianqian.activity.userabout.LoginActivity;
import com.example.bianqian.db.User;
import com.example.bianqian.util.AllSharedPreference;
import com.example.bianqian.util.ShowError;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class MainActivity extends BasicActivity {

    private static final int GO_HOME = 0;

    private boolean canGohome = false;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_main);
    }

    @Override
    public void initViews() {

    }

    @Override
    public void initListeners() {

    }

    @Override
    public void initData() {
        //直接发送一条3秒后执行的消息
        mHandler.sendEmptyMessageDelayed(GO_HOME, 3000);
        //先判断是否记住密码，如果记住了然后再登陆，登陆成功将canGohome设置为true，否则设置为false，然后在goHome方法判断该值，确定进入的页面
        AllSharedPreference preference = new AllSharedPreference(this);
        if(preference.getAutomaticLogin()) {
            String userName = preference.getUseName();
            String password = preference.getPassword();
            User user = new User();
            user.setUsername(userName);
            user.setPassword(password);
            user.login(new SaveListener<User>() {
                @Override
                public void done(User user, BmobException e) {
                    if (e == null) {
                        canGohome = true;
                    } else {
                        ShowToast(ShowError.showError(e));
                        canGohome = false;
                    }
                }
            });
        }
    }

    public void goHome() {
        if(canGohome){
            Intent intent = new Intent(MainActivity.this, ApplicationMainActivity.class);
            MainActivity.this.startActivity(intent);
            finish();
        }else {
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            MainActivity.this.startActivity(intent);
            finish();
        }
    }

    private Handler mHandler = new Handler() {
        //接收到消息后开始执行gohome（）
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GO_HOME:
                   goHome();
                    break;
            }
        }
    };
}
