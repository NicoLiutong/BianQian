package com.example.bianqian.activity.userabout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.example.bianqian.R;
import com.example.bianqian.activity.ApplicationMainActivity;
import com.example.bianqian.activity.BasicActivity;
import com.example.bianqian.bmobbasic.User;
import com.example.bianqian.util.AllSharedPreference;
import com.example.bianqian.util.ShowError;
import com.example.bianqian.view.LineEditText;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;

public class LoginActivity extends BasicActivity {

    private LineEditText userNameEdit, passwordEdit;

    private CheckBox canSeePasswordCheckBox, automaticLoginChenkBox;

    private TextView forgetPasswordText, registerText;

    private Button loginButton;

    private String userName = "";

    private String password = "";

    private boolean automaticLogin = false;

    private AllSharedPreference loginPreference;
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
        //是否可见密码设置，改变passwordEdit的输入类型
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
        //是否记住密码：如果选择记住将automaticLogin设置为true；否则设置为false，然后在登陆按钮点击的时候根据此值对其进行相应设置
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
        //忘记密码直接进入忘记密码界面
        forgetPasswordText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,ForgetPasswordActivity.class);
                LoginActivity.this.startActivity(intent);

            }
        });
        //注册直接进入注册界面
        registerText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });
        //登陆按钮，先获取输入的账号和密码，然后判断automiatiLogin是否为true，并对preference进行相应设置，最后进行登陆
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = userNameEdit.getText().toString();
                password = passwordEdit.getText().toString();
                if(automaticLogin){
                    loginPreference.setAutomaticLogin(true);
                    loginPreference.setUserName(userName);
                    loginPreference.setPassword(password);
                }
                //登陆
                final ProgressDialog dialog = ProgressDialog.show(LoginActivity.this, null, "登陆中…", true, false);
                User user = new User();
                user.setUsername(userName);
                user.setPassword(password);
                user.login(new SaveListener<User>() {
                    @Override
                    public void done(User user, BmobException e) {
                        if(e == null){
                            ShowToast("登陆成功");
                            dialog.dismiss();
                            Intent intent = new Intent(LoginActivity.this,ApplicationMainActivity.class);
                            LoginActivity.this.startActivity(intent);
                            finish();
                            //进入主activity
                        }else {
                            dialog.dismiss();
                            loginPreference.setAutomaticLogin(false);
                            loginPreference.setUserName(null);
                            loginPreference.setPassword(null);
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
        loginPreference = new AllSharedPreference(LoginActivity.this);
        loginPreference.setAutomaticLogin(false);
        loginPreference.setUserName("");
        loginPreference.setPassword("");
    }
}
