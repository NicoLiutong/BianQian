package com.example.bianqian.activity.userabout;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bianqian.R;
import com.example.bianqian.activity.BasicActivity;
import com.example.bianqian.db.User;
import com.example.bianqian.util.AllSharedPreference;
import com.example.bianqian.util.ShowError;
import com.wx.wheelview.adapter.ArrayWheelAdapter;
import com.wx.wheelview.widget.WheelView;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.UpdateListener;
import de.hdodenhof.circleimageview.CircleImageView;

public class UserMessageActivity extends BasicActivity implements View.OnClickListener {

    private LinearLayout titleLayout;

    private Button backButton, changePasswordButton, logoutButton, dateSelectButton;

    private TextView titleText, userNmae, userSex, userBirthday, userEmailVerified, userIndividuality;

    private RelativeLayout userPictureLayout, userNameLayout, userSexLayout, userBirthdayLayout, userEmailVerifiedLayout, userIndividualityLayout;

    private CircleImageView userPicture;

    private WheelView yearWheelView,mounthWheelView,dayWheelView;

    private String year = "1960",mounth = "1",day = "1";

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
        userBirthdayLayout = (RelativeLayout) findViewById(R.id.user_message_birthday_layout);
        userBirthday = (TextView) findViewById(R.id.user_message_birthday);
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
        userBirthdayLayout.setOnClickListener(this);
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
        //每一次进入都要设置个人信息，获取user，设置头像等信息
        User user = BmobUser.getCurrentUser(User.class);
        Glide.with(UserMessageActivity.this).load(user.getUesrImage().getUrl()).into(userPicture);
        userNmae.setText(user.getMyUsername());
        userSex.setText(user.getSex());
        userBirthday.setText(user.getBirthday());
        emailVerified = user.getEmailVerified();
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
            //点击了头像那栏弹出“拍照”和“相册”选择框，点击进入相应的设置
            case R.id.user_message_picture_layout:
                changeUserPicture();
                break;
            //更改名字
            case R.id.user_message_name_layout:
                startChangeActivity(ChangeUserMessageActivity.CHANGETAG,ChangeUserMessageActivity.CHANGENAME);
                break;
            //更改性别
            case R.id.user_message_sex_layout:
                changeUserSex();
                break;
            case R.id.user_message_birthday_layout:
                changeUserBirthday();
                break;
            //发送验证
            case R.id.user_message_verified_layout:
                changeUserEmailVerified();
                break;
            //更改签名
            case R.id.user_message_individuality_layout:
                startChangeActivity(ChangeUserMessageActivity.CHANGETAG,ChangeUserMessageActivity.CHANGEINDIVIDUALITY);
                break;
            //更改密码
            case R.id.change_password_button:
                startChangeActivity(ChangeUserMessageActivity.CHANGETAG,ChangeUserMessageActivity.CHANGEPASSWORD);
                break;
            //退出登录
            case R.id.logout_button:
                User user = BmobUser.getCurrentUser(User.class);
                user.logOut();
                AllSharedPreference preference = new AllSharedPreference(this);
                preference.setAutomaticLogin(false);
                preference.setUserName(null);
                preference.setPassword(null);
                Intent intent = new Intent(UserMessageActivity.this,LoginActivity.class);
                UserMessageActivity.this.startActivity(intent);
                finishAll();
                break;
        }
    }

    private void changeUserPicture(){
        AlertDialog.Builder pictureBuilder = new AlertDialog.Builder(UserMessageActivity.this,R.style.MyDialog);
        final String[] choosePicture = {"拍照","相册"};
        pictureBuilder.setItems(choosePicture, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, final int which) {
                if(choosePicture[which].equals("拍照")){
                    //处理拍照
                    startChangeActivity(ChangeUserMessageActivity.CHANGETAG,ChangeUserMessageActivity.CHANGECAMERPICTURE);
                }else {
                    //处理相册
                    startChangeActivity(ChangeUserMessageActivity.CHANGETAG,ChangeUserMessageActivity.CHANGEPHOTOPICTURE);
                }
            }
        });
        pictureBuilder.show();
    }

    private void changeUserSex(){
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
    }

    private void changeUserBirthday(){
        final AlertDialog.Builder birthdayBuider = new AlertDialog.Builder(this, R.style.MyDialog);
        birthdayBuider.setCancelable(true);
        View selectView = View.inflate(this,R.layout.select_date_wheel,null);
        birthdayBuider.setView(selectView);
        final AlertDialog dialog = birthdayBuider.create();
        Window window = dialog.getWindow();
        window.setGravity(Gravity.BOTTOM);
        dateSelectButton = (Button) selectView.findViewById(R.id.affirm_button);

        dateSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dialog.dismiss();
                User user = new User();
                user.setBirthday(year + "-" + mounth + "-" + day);
                BmobUser bmobUser = BmobUser.getCurrentUser(User.class);
                user.update(bmobUser.getObjectId(), new UpdateListener() {
                    @Override
                    public void done(BmobException e) {
                        if(e == null){
                            userBirthday.setText(year + "-" + mounth + "-" + day);
                        }else {
                            ShowToast(ShowError.showError(e));
                        }
                    }
                });
            }
        });

        WheelView.WheelViewStyle style = new WheelView.WheelViewStyle();
        style.selectedTextSize = 16;
        style.textSize = 13;
        style.selectedTextColor = ContextCompat.getColor(this, R.color.colorAccent);

        yearWheelView = (WheelView) selectView.findViewById(R.id.year_wheelview);
        yearWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        yearWheelView.setStyle(style);
        yearWheelView.setExtraText("年", ContextCompat.getColor(this, R.color.colorAccent),40,100);
        yearWheelView.setSkin(WheelView.Skin.None);
        yearWheelView.setWheelData(creatYear());
        yearWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                year = creatYear().get(position);
                dayWheelView.setWheelData(creatDay(year,mounth));
            }
        });

        mounthWheelView = (WheelView) selectView.findViewById(R.id.mounth_wheelview);
        mounthWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        mounthWheelView.setStyle(style);
        mounthWheelView.setExtraText("月", ContextCompat.getColor(this, R.color.colorAccent),40,60);
        mounthWheelView.setSkin(WheelView.Skin.None);
        mounthWheelView.setLoop(true);
        mounthWheelView.setWheelData(creatMounth());
        mounthWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                mounth = creatMounth().get(position);
                dayWheelView.setWheelData(creatDay(year,mounth));
            }
        });

        dayWheelView = (WheelView) selectView.findViewById(R.id.day_wheelview);
        dayWheelView.setWheelAdapter(new ArrayWheelAdapter(this));
        dayWheelView.setStyle(style);
        dayWheelView.setExtraText("日", ContextCompat.getColor(this, R.color.colorAccent),40,60);
        dayWheelView.setSkin(WheelView.Skin.None);
        dayWheelView.setLoop(true);
        dayWheelView.setWheelData(creatDay(year,mounth));
        dayWheelView.setOnWheelItemSelectedListener(new WheelView.OnWheelItemSelectedListener() {
            @Override
            public void onItemSelected(int position, Object o) {
                day = creatDay(year,mounth).get(position);
            }
        });
        dialog.show();
    }

    private void changeUserEmailVerified(){
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
    }

    private List<String> creatYear(){
        List<String> year = new ArrayList<>();
        for(int i = 1960;i < 2051 ; i++){
            year.add(String.valueOf(i));
        }
        return year;
    }

    private List<String> creatMounth(){
        List<String> mounth = new ArrayList<>();
        for(int i = 1; i < 13;i++){
            mounth.add(String.valueOf(i));
        }
        return mounth;
    }

    private List<String> creatDay(String year,String mounth){
        int years = Integer.parseInt(year);
        int mounths = Integer.parseInt(mounth);
        List<String> day30 = new ArrayList<>();
        List<String> day31 = new ArrayList<>();
        List<String> day28 = new ArrayList<>();
        List<String> day29 = new ArrayList<>();

        for(int i = 1;i < 31; i++){
            day30.add(String.valueOf(i));
        }
        for(int i = 1;i < 32; i++){
            day31.add(String.valueOf(i));
        }

        for(int i = 1;i < 30; i++){
            day29.add(String.valueOf(i));
        }

        for(int i = 1;i < 29; i++){
            day28.add(String.valueOf(i));
        }

        if(years%4 == 0){
            switch (mounths){
                case 1:case 3:case 5:case 7:case 8:case 10:case 12:
                    return day31;
                case 2:
                    return day29;
                default:
                    return day30;
            }
        }else {
            switch (mounths){
                case 1:case 3:case 5:case 7:case 8:case 10:case 12:
                    return day31;
                case 2:
                    return day28;
                default:
                    return day30;
            }
        }
    }

    private void startChangeActivity(String tag,String type){
        Intent intent = new Intent(UserMessageActivity.this,ChangeUserMessageActivity.class);
        intent.putExtra(tag,type);
        UserMessageActivity.this.startActivity(intent);

    }
}
