package com.example.bianqian.activity;

import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bianqian.R;
import com.example.bianqian.db.User;

import cn.bmob.v3.BmobUser;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserMessageActivity extends BasicActivity implements View.OnClickListener {

    private LinearLayout titleLayout;

    private Button backButton;

    private TextView titleText;

    private RelativeLayout userPictureLayout;

    private CircleImageView userPicture;

    private RelativeLayout userNameLayout;

    private TextView userNmae;

    private RelativeLayout userSexLayout;

    private TextView userSex;

    private RelativeLayout userIndividualityLayout;

    private TextView userIndividuality;

    private Button changePasswordButton;

    private Button logoutButton;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_user_message);
    }

    @Override
    public void initViews() {
        titleLayout = (LinearLayout) findViewById(R.id.basic_title_layout);
        backButton = (Button) findViewById(R.id.basic_backbutton);
        titleText = (TextView) findViewById(R.id.basic_texttitle);
        userPictureLayout = (RelativeLayout) findViewById(R.id.user_message_picture_layout);
        userPicture = (CircleImageView) findViewById(R.id.user_message_picture);
        userNameLayout = (RelativeLayout) findViewById(R.id.user_message_name_layout);
        userNmae = (TextView) findViewById(R.id.user_message_name);
        userSexLayout = (RelativeLayout) findViewById(R.id.user_message_sex_layout);
        userSex = (TextView) findViewById(R.id.user_message_sex);
        userIndividualityLayout = (RelativeLayout) findViewById(R.id.user_message_individuality_layout);
        userIndividuality = (TextView) findViewById(R.id.user_message_individuality);
        changePasswordButton = (Button) findViewById(R.id.change_password_button);
        logoutButton = (Button) findViewById(R.id.logout_button);
    }

    @Override
    public void initListeners() {
        backButton.setOnClickListener(this);
        changePasswordButton.setOnClickListener(this);
        logoutButton.setOnClickListener(this);
    }

    @Override
    public void initData() {
        titleLayout.setBackgroundColor(ContextCompat.getColor(this, R.color.colorAccent));
        titleText.setText("个人资料");
    }

    @Override
    protected void onStart() {
        super.onStart();
        User user = BmobUser.getCurrentUser(User.class);
        Glide.with(UserMessageActivity.this).load(user.getUesrImage().getUrl()).into(userPicture);
        userNmae.setText(user.getMyUsername());
        userSex.setText(user.getSex());
        userIndividuality.setText(user.getIndividualitySignature());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.basic_backbutton:
                finish();
                break;
            case R.id.change_password_button:
                ShowToast("改密码");

                break;
            case R.id.logout_button:
                User user = BmobUser.getCurrentUser(User.class);
                user.logOut();
                Intent intent = new Intent(UserMessageActivity.this,LoginActivity.class);
                UserMessageActivity.this.startActivity(intent);
                finishAll();
                break;
        }
    }
}
