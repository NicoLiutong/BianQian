package com.example.bianqian.activity;

import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bianqian.R;
import com.example.bianqian.db.User;
import com.example.bianqian.util.AllStatic;
import com.example.bianqian.util.ShowError;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ChangeUserMessageActivity extends BasicActivity {

    private Button backButton;

    private TextView titleText;

    private Button completeButton;

    private LinearLayout changeMessageItemLayout;

    private TextView changeMessageItem;

    private EditText changeMessageContent;

    private LinearLayout changePasswordLayout;

    private EditText oldPassword;

    private EditText newPassword;

    private EditText confirmPassword;

    private String type;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_user_message);
    }

    @Override
    public void initViews() {
        backButton = (Button) findViewById(R.id.change_message_backbutton);
        titleText = (TextView) findViewById(R.id.change_message_texttitle);
        completeButton = (Button) findViewById(R.id.change_complete);
        changeMessageItemLayout = (LinearLayout) findViewById(R.id.change_message_item_layout);
        changeMessageItem = (TextView) findViewById(R.id.change_message_item);
        changeMessageContent = (EditText) findViewById(R.id.change_message_content);
        changePasswordLayout = (LinearLayout) findViewById(R.id.change_password_layout);
        oldPassword = (EditText) findViewById(R.id.change_password_oldpassword);
        newPassword = (EditText) findViewById(R.id.change_password_newpassword);
        confirmPassword = (EditText) findViewById(R.id.change_password_confirmpassword);
    }

    @Override
    public void initListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(type.equals(AllStatic.CHANGENAME)){
                    User user = new User();
                    user.setMyUsername(changeMessageContent.getText().toString());
                    BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                ShowToast("昵称已更新");
                                finish();
                            }else {
                                ShowToast(ShowError.showError(e));
                            }
                        }
                    });
                }else if(type.equals(AllStatic.CHANGEINDIVIDUALITY)){
                    User user = new User();
                    user.setIndividuality(changeMessageContent.getText().toString());
                    BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                        @Override
                        public void done(BmobException e) {
                            if(e == null){
                                ShowToast("个性签名已更新");
                                finish();
                            }else {
                                ShowToast(ShowError.showError(e));
                            }
                        }
                    });
                }else if(type.equals(AllStatic.CHANGEPASSWORD)){
                    if(confirmPassword.getText().toString().equals(newPassword.getText().toString())){
                        User.updateCurrentUserPassword(oldPassword.getText().toString(), newPassword.getText().toString(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if(e==null){
                                    ShowToast("密码修改成功，可以用新密码进行登录啦");
                                    Intent intent = new Intent(ChangeUserMessageActivity.this,LoginActivity.class);
                                    ChangeUserMessageActivity.this.startActivity(intent);
                                    finish();
                                }else{
                                    ShowToast(ShowError.showError(e));
                                }
                            }
                        });
                    }
                }
            }
        });
    }

    @Override
    public void initData() {
        Intent intent = getIntent();
        type = intent.getStringExtra(AllStatic.CHANGETAG);

        if(type.equals(AllStatic.CHANGENAME)){
            changePasswordLayout.setVisibility(View.GONE);
            titleText.setText("修改昵称");
            changeMessageItem.setText("昵称：");
        }else if(type.equals(AllStatic.CHANGEINDIVIDUALITY)){
            changePasswordLayout.setVisibility(View.GONE);
            titleText.setText("修改个性签名");
            changeMessageItem.setText("个性签名：");
        }else {
            changeMessageItemLayout.setVisibility(View.GONE);
            titleText.setText("修改密码");
        }
    }
}
