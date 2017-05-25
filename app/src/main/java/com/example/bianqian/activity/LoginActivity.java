package com.example.bianqian.activity;

import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.bianqian.R;
import com.example.bianqian.db.User;
import com.example.bianqian.util.AllSharedPreference;
import com.example.bianqian.util.ShowError;
import com.example.bianqian.view.LineEditText;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BasicActivity {

    private LineEditText userNameEdit;

    private LineEditText passwordEdit;

    private CheckBox canSeePasswordCheckBox;

    private CheckBox automaticLoginChenkBox;

    private TextView forgetPasswordText;

    private Button loginButton;

    private TextView registerText;

    private String userNmae = "";

    private String password = "";

    private boolean automaticLogin = false;
    @Override
    public void setContentView() {
        setContentView(R.layout.activity_login);
    }

    @Override
    public void initViews() {
        userNameEdit = (LineEditText) findViewById(R.id.login_username);
        passwordEdit = (LineEditText) findViewById(R.id.login_passowrd);
        canSeePasswordCheckBox = (CheckBox) findViewById(R.id.login_isseepassword);
        automaticLoginChenkBox = (CheckBox) findViewById(R.id.login_savepassword);
        forgetPasswordText = (TextView) findViewById(R.id.login_forgetpassword);
        loginButton = (Button) findViewById(R.id.login_button);
        registerText = (TextView) findViewById(R.id.login_register);
    }

    @Override
    public void initListeners() {
        canSeePasswordCheckBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    passwordEdit.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                }else {
                    passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                }
            }
        });

        automaticLoginChenkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    automaticLogin = true;
                }else {
                    automaticLogin = false;
                }
            }
        });

        forgetPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        });

        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userNmae = userNameEdit.getText().toString();
                password = passwordEdit.getText().toString();
                AllSharedPreference loginPreference = new AllSharedPreference(LoginActivity.this);
                if(automaticLogin){
                    loginPreference.setAutomaticLogin(true);
                    loginPreference.setUserName(userNmae);
                    loginPreference.setPassword(password);
                }else {
                    loginPreference.setAutomaticLogin(false);
                    loginPreference.setUserName("");
                    loginPreference.setPassword("");;
                }
                //登陆
                User user = new User();
                user.setUsername(userNmae);
                user.setPassword(password);
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if(e == null){
                            ShowToast("登陆成功");
                            Intent intent = new Intent(LoginActivity.this,ApplicationMainActivity.class);
                            LoginActivity.this.startActivity(intent);
                            finish();
                            //进入主activity
                        }else {
                            ShowToast(ShowError.showError(e));
                        }
                    }
                });
            }
        });
    }
    @Override
    public void initData() {
        automaticLogin = automaticLoginChenkBox.isChecked();
        passwordEdit.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);

    }
}
