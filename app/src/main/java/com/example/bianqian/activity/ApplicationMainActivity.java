package com.example.bianqian.activity;

import android.os.Handler;
import android.os.Message;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
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

    private static final int EXIT_APPLICATION = 1;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_application_main);
    }

    @Override
    public void initViews() {
        user = BmobUser.getCurrentUser(User.class);
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
    }

    @Override
    public void initData() {

        Glide.with(ApplicationMainActivity.this).load(user.getUesrImage().getUrl()).into(userPicture);
        userName.setText(user.getMyUsername());
        userIndividuality.setText(user.getIndividualitySignature());

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
