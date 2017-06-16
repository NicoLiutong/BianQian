package com.example.bianqian.activity.userabout;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.content.FileProvider;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.bianqian.R;
import com.example.bianqian.activity.BasicActivity;
import com.example.bianqian.db.User;
import com.example.bianqian.util.AllStatic;
import com.example.bianqian.util.ShowError;
import com.oginotihiro.cropview.CropUtil;
import com.oginotihiro.cropview.CropView;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import cn.bmob.v3.listener.UploadFileListener;
import pub.devrel.easypermissions.EasyPermissions;

import static android.content.Intent.FLAG_GRANT_READ_URI_PERMISSION;
import static android.content.Intent.FLAG_GRANT_WRITE_URI_PERMISSION;

public class ChangeUserMessageActivity extends BasicActivity implements EasyPermissions.PermissionCallbacks {

    private Button backButton, completeButton;

    private TextView titleText, changeMessageItem;

    private CropView changeMessagePicture;

    private LinearLayout changeMessageItemLayout, changePasswordLayout;

    private EditText changeMessageContent, oldPassword, newPassword, confirmPassword;

    private String type, camerPathUri;

    private String[] perms;

    private static final int SELECT_THROW_CAMER = 100;
    private static final int SELECT_THROW_PHOTO = 101;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_change_user_message);
        //所需要申请的权限数组
        perms = new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE,Manifest.permission.CAMERA};
    }

    @Override
    public void initViews() {
        //获取各个组件
        backButton = (Button) findViewById(R.id.change_message_backbutton);
        titleText = (TextView) findViewById(R.id.change_message_texttitle);
        completeButton = (Button) findViewById(R.id.change_complete);
        changeMessagePicture = (CropView) findViewById(R.id.change_message_picture);
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
        //完成按钮所对应的操作
        completeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //新建user这个是一个空对象，用于数据的更新
                final User user = new User();
                //获取当前用户，用于更新数据时获取id
                final User bmobUser = BmobUser.getCurrentUser(User.class);
                //新建BmobFile用于删除之前的头像
                final BmobFile deletFile = new BmobFile();
                //设置progress
                final ProgressDialog dialog = ProgressDialog.show(ChangeUserMessageActivity.this, null, "请等待…", true, false);
                switch (type) {
                    case AllStatic.CHANGECAMERPICTURE:
                    case AllStatic.CHANGEPHOTOPICTURE:
                        //获取之前图像的url
                        deletFile.setUrl(bmobUser.getUesrImage().getUrl());
                        new Thread() {
                            public void run() {
                                //开启线程，处理裁剪，将图片输出为Bitmap
                                Bitmap croppedBitmap = changeMessagePicture.getOutput();
                                //创建输出的文件路径
                                File file = new File(ChangeUserMessageActivity.this.getExternalFilesDir(null),"user_picture.jpg");
                                //判断该文件是否存在，存在先删除
                                if(file != null){
                                    file.delete();
                                }
                                //获取输出的文件Uri
                                Uri destination = Uri.fromFile(file);
                                //调用CropUtil的saveOutput将图片输出到指定的头像目录下
                                CropUtil.saveOutput(ChangeUserMessageActivity.this, destination, croppedBitmap, 90);
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        //上传新的头像，成功的话更新用户的数据，并删除旧的头像
                                        final BmobFile file = new BmobFile(new File(getExternalFilesDir(null), "user_picture.jpg"));
                                        file.uploadblock(new UploadFileListener() {
                                            @Override
                                            public void done(BmobException e) {
                                                if (e == null) {
                                                    //更新用户头像
                                                    user.setUesrImage(file);
                                                    user.update(bmobUser.getObjectId(), new UpdateListener() {
                                                        @Override
                                                        public void done(BmobException e) {
                                                            if (e == null) {
                                                                ShowToast("头像已更新");
                                                                //头像跟更新完成后删除之前的头像,并关闭progress
                                                                deletFile.delete(new UpdateListener() {
                                                                    @Override
                                                                    public void done(BmobException e) {
                                                                    }
                                                                });
                                                                dialog.dismiss();
                                                                finish();
                                                            } else {
                                                                dialog.dismiss();
                                                                ShowToast(ShowError.showError(e));
                                                            }
                                                        }
                                                    });
                                                }else {
                                                    dialog.dismiss();
                                                    ShowToast(ShowError.showError(e));
                                                }
                                            }
                                        });

                                    }
                                });
                            }
                        }.start();
                        break;

                    case AllStatic.CHANGENAME:
                        //更新用户名
                        user.setMyUsername(changeMessageContent.getText().toString());
                        user.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ShowToast("昵称已更新");
                                    dialog.dismiss();
                                    finish();
                                } else {
                                    dialog.dismiss();
                                    ShowToast(ShowError.showError(e));
                                }
                            }
                        });
                        break;
                    case AllStatic.CHANGEINDIVIDUALITY:
                        //更新个性签名
                        user.setIndividuality(changeMessageContent.getText().toString());
                        user.update(bmobUser.getObjectId(), new UpdateListener() {
                            @Override
                            public void done(BmobException e) {
                                if (e == null) {
                                    ShowToast("个性签名已更新");
                                    dialog.dismiss();
                                    finish();
                                } else {
                                    dialog.dismiss();
                                    ShowToast(ShowError.showError(e));
                                }
                            }
                        });
                        break;
                    case AllStatic.CHANGEPASSWORD:
                        //更新用户密码，并进入Login界面
                        if (confirmPassword.getText().toString().equals(newPassword.getText().toString())) {
                            User.updateCurrentUserPassword(oldPassword.getText().toString(), newPassword.getText().toString(), new UpdateListener() {
                                @Override
                                public void done(BmobException e) {
                                    if (e == null) {
                                        ShowToast("密码修改成功，可以用新密码进行登录啦");
                                        dialog.dismiss();
                                        Intent intent = new Intent(ChangeUserMessageActivity.this, LoginActivity.class);
                                        ChangeUserMessageActivity.this.startActivity(intent);
                                        finishAll();
                                    } else {
                                        dialog.dismiss();
                                        ShowToast(ShowError.showError(e));
                                    }
                                }
                            });
                            break;
                        }
                    }
                }
            });
        }

    @Override
    public void initData() {
        //获取点击的类型，用于控制更新的内容
        Intent intent = getIntent();
        type = intent.getStringExtra(AllStatic.CHANGETAG);
        //根据传入的类型不同，显示不同的view，实现不同的更新
        switch (type){
            case AllStatic.CHANGECAMERPICTURE:
            case AllStatic.CHANGEPHOTOPICTURE:
                changeMessageItemLayout.setVisibility(View.GONE);
                changePasswordLayout.setVisibility(View.GONE);
                titleText.setText("修改头像");
                //更改头像
                getPermissionAndChangePicture();
                break;
            case AllStatic.CHANGENAME:
                changeMessagePicture.setVisibility(View.GONE);
                changePasswordLayout.setVisibility(View.GONE);
                titleText.setText("修改昵称");
                changeMessageItem.setText("昵称：");
                break;
            case AllStatic.CHANGEINDIVIDUALITY:
                changeMessagePicture.setVisibility(View.GONE);
                changePasswordLayout.setVisibility(View.GONE);
                titleText.setText("修改个性签名");
                changeMessageItem.setText("个性签名：");
                break;
            case AllStatic.CHANGEPASSWORD:
                changeMessagePicture.setVisibility(View.GONE);
                changeMessageItemLayout.setVisibility(View.GONE);
                titleText.setText("修改密码");
                break;
        }
    }
    //先进行权限申请，如果有权限直接根据传入的类型判断要调用相机还是图库，在进行不同的操作
    private void getPermissionAndChangePicture(){
        if(EasyPermissions.hasPermissions(this,perms)){
            switch (type){
                case AllStatic.CHANGECAMERPICTURE:
                    openCamerChangePicture();
                    break;
                case AllStatic.CHANGEPHOTOPICTURE:
                    openPhotoChangePicture();
                    break;
            }
        } else {
            //申请权限
            EasyPermissions.requestPermissions(this, "必要的权限", 0, perms);
        }
    }
    //调用相机
    private void openCamerChangePicture(){
        //指定输出文件的路径
        File file = new File(Environment.getExternalStorageDirectory().getAbsolutePath(), "com.miaoji");
        if(!file.exists()){
            file.mkdir();
        }
        File camerPath = new File(file,"camer_picture.jpg");
        //获取输出文件的路径，为了后面传入到裁剪图片用
        camerPathUri = camerPath.toString();

        //Log.d("camerUri",camerPathUri);
        //构建Intent
        Intent intentFromCapture = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        //android7.0以上要用FileProvider
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
            Uri uriForFile = FileProvider.getUriForFile(this, "com.example.miaoji", camerPath);
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, uriForFile);
            intentFromCapture.addFlags(FLAG_GRANT_READ_URI_PERMISSION);
            intentFromCapture.addFlags(FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            intentFromCapture.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(camerPath));
        }
        //开启Intent
        startActivityForResult(intentFromCapture, SELECT_THROW_CAMER);
    }
    //调用相册
    private void openPhotoChangePicture(){
        Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
        intent.setType("image/*");
        startActivityForResult(intent,SELECT_THROW_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        switch (requestCode){
            //camer返回的结果，7.0之上还要用FileProvider
            case SELECT_THROW_CAMER:
                if (resultCode == RESULT_OK) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                        //Uri inputUri = Uri.fromFile(new File(camerPathUri));
                        Uri inputUri = FileProvider.getUriForFile(this, "com.example.miaoji",new File(camerPathUri));//通过FileProvider创建一个content类型的Uri
                        //调用裁剪CropView的方法裁剪
                        changeMessagePicture.of(inputUri).asSquare().initialize(this);

                    } else {
                        Uri inputUri = Uri.fromFile(new File(camerPathUri));
                        changeMessagePicture.of(inputUri).asSquare().initialize(this);
                    }
                }else finish();
                break;
            //相册的返回
            case SELECT_THROW_PHOTO:
                if(resultCode == RESULT_OK){
                    Uri imageUri = data.getData();
                    changeMessagePicture.of(imageUri).asSquare().initialize(this);
                }else finish();
                break;
        }
    }
    //权限申请的返回
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode,permissions,grantResults,this);
    }
    //申请成功的权限
    @Override
    public void onPermissionsGranted(int i, List<String> list) {
        int a =0;
        for (String permiss:list) {
            if(permiss.equals("android.permission.WRITE_EXTERNAL_STORAGE")){
                a++;
            }
            if(permiss.equals("android.permission.CAMERA")){
                a++;
            }
        }
        if(a == 2){
            getPermissionAndChangePicture();
        }
    }
    //申请失败的权限
    @Override
    public void onPermissionsDenied(int i, List<String> list) {
        for (String permiss:list) {
            if(permiss.equals("android.permission.WRITE_EXTERNAL_STORAGE")){
                ShowToast("请去设置读写手机存储权限");
            }
            if(permiss.equals("android.permission.CAMERA")){
                ShowToast("请去设置相机权限");
            }
        }
        finish();


    }
}
