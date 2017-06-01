package com.example.bianqian.activity;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bianqian.R;
import com.example.bianqian.db.User;

import cn.bmob.v3.BmobUser;

public class ApplicationMainActivity extends BasicActivity {

    private DrawerLayout drawerLayout;

    //private CircleImageView userPicture;

    private ImageView userPicture;

    private TextView userName;

    private TextView userIndividuality;

    private NavigationView navigationView;

    private View headView;

    private Button menuButton;

    private User user;

    private boolean isExit = false;

    private boolean isLogin = true;

    private static final int EXIT_APPLICATION = 1;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_application_main);
    }

    @Override
    public void initViews() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        headView = navigationView.getHeaderView(0);
        userPicture = (ImageView) headView.findViewById(R.id.main_user_picture);
        userName = (TextView) headView.findViewById(R.id.main_user_name);
        userIndividuality = (TextView) headView.findViewById(R.id.main_user_individuality);
        menuButton = (Button) findViewById(R.id.drawer_menu);
    }

    @Override
    public void initListeners() {
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        userPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isLogin){
                    Intent intent = new Intent(ApplicationMainActivity.this,UserMessageActivity.class);
                    ApplicationMainActivity.this.startActivity(intent);
                }else {
                    Intent intent = new Intent(ApplicationMainActivity.this,LoginActivity.class);
                    ApplicationMainActivity.this.startActivity(intent);
                    finish();
                }
            }
        });
        //需要处理
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_important_message:
                        ShowToast("重要记录");
                        break;
                    case R.id.menu_normal_message:
                        ShowToast("普通记录");
                        break;
                    case R.id.menu_all_message:
                        ShowToast("所有记录");
                        break;
                    case R.id.menu_setting:
                        ShowToast("设置");
                        break;
                    case R.id.menu_update_application:
                        ShowToast("更新");
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void initData() {

    }

    @Override
    protected void onStart() {
        super.onStart();
        user = BmobUser.getCurrentUser(User.class);
        if(user != null){
            isLogin = true;
        Glide.with(ApplicationMainActivity.this).load(user.getUesrImage().getUrl()).into(userPicture);
        userName.setText(user.getMyUsername());
        userIndividuality.setText(user.getIndividuality());
        }else {
            isLogin = false;
            Glide.with(ApplicationMainActivity.this).load(R.drawable.user_picture).into(userPicture);
            userName.setText("未登录");
            userIndividuality.setText("点击头像进行登录");
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }else {
        return super.onKeyDown(keyCode, event);
        }
    }

    private void exit(){
        if(!isExit){
            isExit = true;
            ShowToast("再按一次退出程序");
            mHandle.sendEmptyMessageDelayed(EXIT_APPLICATION,2000);
        }else {
            System.exit(0);
        }
    }
    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case EXIT_APPLICATION:isExit = false;
                    break;
            }
        }
    };
}
