package com.example.bianqian.activity.userabout;

import android.app.ProgressDialog;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.bianqian.R;
import com.example.bianqian.activity.BasicActivity;
import com.example.bianqian.db.User;
import com.example.bianqian.util.PictureOperation;
import com.example.bianqian.util.ShowError;
import com.example.bianqian.view.LineEditText;

import java.io.File;

import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;

public class RegisterActivity extends BasicActivity {

    private Button backButton;

    private TextView titleText;

    private LineEditText userNameEdit, emailEdit, passwordEdit, confirmPasswordEdit;

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
                final ProgressDialog dialog = ProgressDialog.show(RegisterActivity.this, null, "正在注册，请稍后…", true, false);
                //判断密码是否为4-16个字符
                if(passwordEdit.getText().toString().length() >= 4 && passwordEdit.getText().toString().length() <= 16) {
                    //判断确认密码和密码是否一样
                    if (passwordEdit.getText().toString().equals(confirmPasswordEdit.getText().toString())) {
                        //将用户的初始头像放于（getExternalFilesDir(null), "user_picture.jpg"）路径下
                        PictureOperation.creatExternalStaragePrivateFile(RegisterActivity.this);
                        //设置图片的路径
                        final BmobFile file = new BmobFile(new File(getExternalFilesDir(null), "user_picture.jpg"));
                        //创建user
                        final User user = new User();
                        //先上传头像，头像上传成功后才可以注册
                        file.uploadblock(new UploadFileListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    //头像上传成功后进行注册，设置相应的信息
                                    user.setMyUsername(userNameEdit.getText().toString());
                                    user.setUsername(emailEdit.getText().toString());
                                    user.setEmail(emailEdit.getText().toString());
                                    user.setPassword(passwordEdit.getText().toString());
                                    user.setBirthday("1970-01-01");
                                    user.setIndividuality("这个人好懒，神马都没有");
                                    user.setSex("未知生物");
                                    user.setUesrImage(file);
                                    user.signUp(new SaveListener<User>() {
                                        @Override
                                        public void done(User user, BmobException e) {
                                            if (e == null) {
                                                //注册成功
                                                ShowToast("注册成功,请去邮箱验证");
                                                dialog.dismiss();
                                                finish();
                                            } else {
                                                //注册失败要删除上传的头像，否则会重复上传
                                                dialog.dismiss();
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
                                    //头像上传失败
                                    dialog.dismiss();
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

}
