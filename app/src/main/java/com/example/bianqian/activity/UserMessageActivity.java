package com.example.bianqian.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bianqian.R;
import com.example.bianqian.db.User;
import com.example.bianqian.util.AllStatic;
import com.example.bianqian.util.ShowError;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
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

    private RelativeLayout userEmailVerifiedLayout;

    private TextView userEmailVerified;

    private RelativeLayout userIndividualityLayout;

    private TextView userIndividuality;

    private Button changePasswordButton;

    private Button logoutButton;

    private boolean emailVerified;

    private String userEmail;

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
        userEmailVerifiedLayout = (RelativeLayout) findViewById(R.id.user_message_verified_layout);
        userEmailVerified = (TextView) findViewById(R.id.user_email_verified);
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
        userPictureLayout.setOnClickListener(this);
        userNameLayout.setOnClickListener(this);
        userSexLayout.setOnClickListener(this);
        userEmailVerifiedLayout.setOnClickListener(this);
        userIndividualityLayout.setOnClickListener(this);
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
        emailVerified = user.getEmailVerified();
        BmobQuery<User> query = new BmobQuery<User>();
        userEmail = user.getEmail();
        if(emailVerified){
            userEmailVerified.setText("已验证");
        }else {
            userEmailVerified.setText("未验证");
        }
        userIndividuality.setText(user.getIndividuality());
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.basic_backbutton:
                finish();
                break;
            case R.id.user_message_picture_layout:
                AlertDialog.Builder pictureBuilder = new AlertDialog.Builder(UserMessageActivity.this,R.style.MyDialog);
                final String[] choosePicture = {"拍照","相册"};
                pictureBuilder.setItems(choosePicture, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        if(choosePicture[which].equals("拍照")){
                            //处理拍照
                            startChangeActivity(AllStatic.CHANGETAG,AllStatic.CHANGECAMERPICTURE);
                        }else {
                            //处理相册
                            startChangeActivity(AllStatic.CHANGETAG,AllStatic.CHANGEPHOTOPICTURE);
                        }
                    }
                });
                pictureBuilder.show();
                //更改图像
                break;
            case R.id.user_message_name_layout:
                startChangeActivity(AllStatic.CHANGETAG,AllStatic.CHANGENAME);
                break;
            case R.id.user_message_sex_layout:
                AlertDialog.Builder sexBuilder = new AlertDialog.Builder(UserMessageActivity.this,R.style.MyDialog);
                final String[] sexs = {"男","女","未知生物"};
                sexBuilder.setItems(sexs, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, final int which) {
                        User user = new User();
                        user.setSex(sexs[which]);
                        BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
                        user.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e == null){
                                    userSex.setText(sexs[which]);
                                }else {
                                    ShowToast(ShowError.showError(e));
                                }
                            }
                        });
                    }
                });
                sexBuilder.show();
                break;
            case R.id.user_message_verified_layout:
                if(emailVerified){
                    ShowToast("已验证，请不要重复验证");
                }else {
                    BmobUser.requestEmailVerify(userEmail, new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                ShowToast("请到"+ userEmail + "邮箱验证，验证后请重新登陆。");

                            }else {
                                ShowToast(ShowError.showError(e));
                            }
                        }
                    });
                }
                break;
            case R.id.user_message_individuality_layout:
                startChangeActivity(AllStatic.CHANGETAG,AllStatic.CHANGEINDIVIDUALITY);
                break;
            case R.id.change_password_button:
                startChangeActivity(AllStatic.CHANGETAG,AllStatic.CHANGEPASSWORD);
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

    private void startChangeActivity(String tag,String type){
        Intent intent = new Intent(UserMessageActivity.this,ChangeUserMessageActivity.class);
        intent.putExtra(tag,type);
        UserMessageActivity.this.startActivity(intent);

    }
}
