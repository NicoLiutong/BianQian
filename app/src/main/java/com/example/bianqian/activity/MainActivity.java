package com.example.bianqian.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;

import com.example.bianqian.R;
import com.example.bianqian.db.User;
import com.example.bianqian.util.AllSharedPreference;

import cn.bmob.v3.BmobUser;

public class MainActivity extends BasicActivity {

    private static final int GO_HOME = 100;

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
        mHandler.sendEmptyMessageDelayed(GO_HOME, 3000);

    }
    public void goHome() {
        AllSharedPreference preference = new AllSharedPreference(this);
        if(preference.getAutomaticLogin()){
            User user = BmobUser.getCurrentUser(User.class);
            if(user != null){
                Intent intent = new Intent(MainActivity.this,ApplicationMainActivity.class);
                MainActivity.this.startActivity(intent);
                finish();
            }else {
                Intent intent = new Intent(MainActivity.this,LoginActivity.class);
                MainActivity.this.startActivity(intent);
                this.finish();
            }
        }else {
        Intent intent = new Intent(MainActivity.this,LoginActivity.class);
        MainActivity.this.startActivity(intent);
        this.finish();
        }
    }

    private Handler mHandler = new Handler() {
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
