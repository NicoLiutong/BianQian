package com.example.bianqian.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bianqian.R;
import com.example.bianqian.db.User;
import com.example.bianqian.util.ShowError;
import com.example.bianqian.view.LineEditText;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;

public class ForgetPasswordActivity extends BasicActivity{

    private Button backButton;

    private TextView titleText;

    private LineEditText forgetEmail;

    private Button forgetButton;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_forget_password);
    }

    @Override
    public void initViews() {
        backButton = (Button) findViewById(R.id.basic_backbutton);
        titleText = (TextView) findViewById(R.id.basic_texttitle);
        forgetEmail = (LineEditText) findViewById(R.id.forget_password_email);
        forgetButton = (Button) findViewById(R.id.forget_password_button);
    }

    @Override
    public void initListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        forgetButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //重置密码，获取email然后发送重置请求
                final String email = forgetEmail.getText().toString();
                User.resetPasswordByEmail(email, new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e==null){
                            ShowToast("重置密码请求成功，请到" + email + "邮箱进行密码重置操作");
                            finish();
                        }else{
                            ShowToast(ShowError.showError(e));
                        }
                    }
                });
            }
        });
    }

    @Override
    public void initData() {
        titleText.setText("重置密码");
    }
}
