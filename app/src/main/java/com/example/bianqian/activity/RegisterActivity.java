package com.example.bianqian.activity;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bianqian.R;
import com.example.bianqian.db.User;
import com.example.bianqian.util.ShowError;
import com.example.bianqian.view.LineEditText;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RegisterActivity extends BasicActivity {

    private Button backButton;

    private TextView titleText;

    private LineEditText userNameEdit;

    private LineEditText emailEdit;

    private LineEditText passwordEdit;

    private LineEditText confirmPasswordEdit;

    private Button registerButton;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_register);
    }

    @Override
    public void initViews() {
        backButton = (Button) findViewById(R.id.basic_backbutton);
        titleText = (TextView) findViewById(R.id.basic_texttitle);
        userNameEdit = (LineEditText) findViewById(R.id.register_uesrname);
        emailEdit = (LineEditText) findViewById(R.id.register_email);
        passwordEdit = (LineEditText) findViewById(R.id.register_password);
        confirmPasswordEdit = (LineEditText) findViewById(R.id.register_confirm_password);
        registerButton = (Button) findViewById(R.id.register_button);
    }

    @Override
    public void initListeners() {
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        registerButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(passwordEdit.getText().toString().length() >= 4 && passwordEdit.getText().toString().length() <= 16) {
                    if (passwordEdit.getText().toString().equals(confirmPasswordEdit.getText().toString())) {
                        if (!hasExternalStaragePrivateFile()) {
                            creatExternalStaragePrivateFile();
                        }
                        //注册,设定图片路径
                        final BmobFile file = new BmobFile(new File(getExternalFilesDir(null), "user_picture.jpg"));
                        final User user = new User();
                        file.uploadblock(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    user.setMyUsername(userNameEdit.getText().toString());
                                    user.setUsername(emailEdit.getText().toString());
                                    user.setEmail(emailEdit.getText().toString());
                                    user.setPassword(passwordEdit.getText().toString());
                                    user.setSex(" ");
                                    user.setUesrImage(file);
                                    user.signUp(new SaveListener<User>() {
                                        @Override
                                        public void done(User user, BmobException e) {
                                            if (e == null) {
                                                ShowToast("注册成功,请去邮箱验证");
                                                finish();
                                            } else {
                                                ShowToast(ShowError.showError(e));
                                                BmobFile deletFile = new BmobFile();
                                                deletFile.setUrl(file.getUrl());
                                                deletFile.delete(new UpdateListener() {
                                                    @Override
                                                    public void done(BmobException e) {
                                                    }
                                                });
                                            }
                                        }
                                    });
                                } else {
                                    ShowToast(ShowError.showError(e));
                                }
                            }
                        });

                    } else {
                        ShowToast("(确认密码与密码不同");
                    }
                }else {
                    ShowToast("密码长度不正确");
                }
            }
        });
    }

    @Override
    public void initData() {
        titleText.setText("注册");

    }

    private boolean hasExternalStaragePrivateFile(){
        File file = new File(getExternalFilesDir(null),"user_picture.jpg");
        if(file != null){
            return file.exists();
        }
        return false;
    }

    private void creatExternalStaragePrivateFile(){
        File file = new File(getExternalFilesDir(null),"user_picture.jpg");
        try{
            InputStream is = RegisterActivity.this.getResources().getAssets().open("user_picture.jpg");
            OutputStream os = new FileOutputStream(file);
            byte[] date = new byte[is.available()];
            is.read(date);
            os.write(date);
            is.close();
            os.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
