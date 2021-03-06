package com.example.bianqian.activity;

import android.app.DownloadManager;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.KeyEvent;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.bianqian.R;
import com.example.bianqian.activity.userabout.LoginActivity;
import com.example.bianqian.activity.userabout.UserMessageActivity;
import com.example.bianqian.bmobbasic.MyAppVersion;
import com.example.bianqian.bmobbasic.User;
import com.example.bianqian.fragment.MoodNote;
import com.example.bianqian.util.AllSharedPreference;
import com.example.bianqian.util.GetAppVersionInformation;
import com.example.bianqian.util.LevelUtils;
import com.othershe.nicedialog.BaseNiceDialog;
import com.othershe.nicedialog.NiceDialog;
import com.othershe.nicedialog.ViewConvertListener;
import com.othershe.nicedialog.ViewHolder;

import org.litepal.LitePal;

import java.io.File;
import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;

public class ApplicationMainActivity extends BasicActivity {

    private DrawerLayout drawerLayout;

    //private CircleImageView userPicture;

    private ImageView userPicture;

    private TextView userName, userIndividuality, level;

    private NavigationView navigationView;

    private View headView;

    private Button menuButton;

    private ImageButton signInButton;

    private User user;

    private boolean isExit = false;

    private boolean isLogin = true;

    private MoodNote fragment;

    private static final int EXIT_APPLICATION = 1;

    private AllSharedPreference preference;

    @Override
    public void setContentView() {
        setContentView(R.layout.activity_application_main);
        SQLiteDatabase db = LitePal.getDatabase();
    }

    @Override
    public void initViews() {

        fragment = new MoodNote();
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment,fragment);
        transaction.commit();


        //获取drawerlayout
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        //获取navigationView
        navigationView = (NavigationView) findViewById(R.id.navigation_view);
        //从navigation获取headview
        headView = navigationView.getHeaderView(0);
        //从headview获取头像控件，textview控件
        userPicture = (ImageView) headView.findViewById(R.id.main_user_picture);
        level = (TextView) headView.findViewById(R.id.main_user_level);
        userName = (TextView) headView.findViewById(R.id.main_user_name);
        userIndividuality = (TextView) headView.findViewById(R.id.main_user_individuality);
        //获取menubutton，用于打开drawerlayout
        menuButton = (Button) findViewById(R.id.drawer_menu);
        //获取签到按钮
        signInButton = (ImageButton) findViewById(R.id.sign_in_button);

    }

    @Override
    public void initListeners() {
        //设置签到界面进入的监听
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               Intent intent = new Intent(ApplicationMainActivity.this,SignInActivity.class);
                ApplicationMainActivity.this.startActivity(intent);
            }
        });

        //设置监听打开drawerlayout
        menuButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });
        //设置userpicture的点击监听
        userPicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //如果现在是登录状态进入详细信息页面
                if(isLogin){
                    Intent intent = new Intent(ApplicationMainActivity.this,UserMessageActivity.class);
                    ApplicationMainActivity.this.startActivity(intent);
                    //如果是未登录状态进入登陆界面
                }else {
                    Intent intent = new Intent(ApplicationMainActivity.this,LoginActivity.class);
                    ApplicationMainActivity.this.startActivity(intent);
                    finish();
                }
            }
        });
        //menu的点击监听
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.menu_red:
                        fragment.updateWithMood("red");
                        drawerLayout.closeDrawers();
                        //ShowToast("姨妈红");
                        break;
                    case R.id.menu_pink:
                        fragment.updateWithMood("pink");
                        drawerLayout.closeDrawers();
                        //ShowToast("少女粉");
                        break;
                    case R.id.menu_yellow:
                        fragment.updateWithMood("yellow");
                        drawerLayout.closeDrawers();
                        //ShowToast("咸蛋黄");
                        break;
                    case R.id.menu_green:
                        fragment.updateWithMood("green");
                        drawerLayout.closeDrawers();
                        //ShowToast("早苗绿");
                        break;
                    case R.id.menu_blue:
                        fragment.updateWithMood("blue");
                        drawerLayout.closeDrawers();
                        //ShowToast("胖次蓝");
                        break;
                    case R.id.menu_purple:
                        fragment.updateWithMood("purple");
                        drawerLayout.closeDrawers();
                        //ShowToast("基佬紫");
                        break;
                    case R.id.menu_gray:
                        fragment.updateWithMood("gray");
                        drawerLayout.closeDrawers();
                        //ShowToast("暗夜灰");
                        break;
                    case R.id.menu_all:
                        fragment.updateWithMood("all");
                        drawerLayout.closeDrawers();
                        //ShowToast("缤纷彩");
                        break;

                    /*case R.id.menu_setting:
                        ShowToast("设置");
                        break;*/
                    case R.id.menu_update_application:
                        //设置版本更新监听
                        /*BmobUpdateAgent.setUpdateListener(new BmobUpdateListener() {
                            @Override
                            public void onUpdateReturned(int i, UpdateResponse updateResponse) {
                                if(i == UpdateStatus.Yes){

                                }else if(i == UpdateStatus.No){
                                    ShowToast("没有新版本");
                                }
                            }
                        });
                        BmobUpdateAgent.forceUpdate(ApplicationMainActivity.this);*/
                        //ShowToast("更新");
                        drawerLayout.closeDrawers();
                        String s = checkUpdate();
                        ShowToast(s);
                        break;
                    case R.id.menu_share:
                        drawerLayout.closeDrawers();
                        Intent shareIntent = new Intent(Intent.ACTION_SEND);
                        shareIntent.setType("text/plain");
                        shareIntent.putExtra(Intent.EXTRA_TEXT,"喵记  记录你每一天的快乐\n" + preference.getShareAddress());
                        shareIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(shareIntent,"喵记"));
                        break;
                }
                return true;
            }
        });
    }

    @Override
    public void initData() {
        //BmobUpdateAgent.initAppVersion();

        // 更新软件版本
        /*BmobUpdateAgent.setUpdateOnlyWifi(false);
        BmobUpdateAgent.update(this);*/
        preference = new AllSharedPreference(this);
        checkUpdate();
        navigationView.setItemIconTintList(null);

    }
    //每次进入该页面都要初始的一些个人信息
    @Override
    protected void onStart() {
        super.onStart();
        //获取当前的user
        user = BmobUser.getCurrentUser(User.class);
        //判断当前的user是否存在，存在则初始化用户头像、名字、个性签名
        if(user != null){
            isLogin = true;
            Glide.with(ApplicationMainActivity.this).load(user.getUesrImage().getUrl()).into(userPicture);
            userName.setText(user.getMyUsername());
            //Log.d("aaaaa",user.getEmpircalValue()+"");
            level.setText("Lv " + LevelUtils.getLevel(user.getEmpircalValue())[0]);
            userIndividuality.setText(user.getIndividuality());
            //不存在则进行不存在的初始化
        }else {
            isLogin = false;
            Glide.with(ApplicationMainActivity.this).load(R.drawable.user_picture).into(userPicture);
            userName.setText("未登录");
            userIndividuality.setText("点击头像进行登录");
        }
    }
    //拦截用户点击返回键的操作，用于实现双击退出程序
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //如果用户点击了返回键，执行退出方法，并返回false
        if(keyCode == KeyEvent.KEYCODE_BACK){
            exit();
            return false;
        }else {
        return super.onKeyDown(keyCode, event);
        }
    }
    //退出方法：先判断isExit是否为true，不为true则将其设置为true并显示“再按一次退出程序”；然后发送一条消息，在两秒后将isExit再次设置为false
    //如果isExit为true则直接退出
    private void exit(){
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START);
        }else if(!isExit){
            isExit = true;
            ShowToast("再按一次退出程序");
            mHandle.sendEmptyMessageDelayed(EXIT_APPLICATION,2000);
        }else {
            System.exit(0);
        }       
    }
    private Handler mHandle = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case EXIT_APPLICATION:isExit = false;
                    break;
            }
        }
    };

    private String checkUpdate(){
        //Log.d("1","1");
        final String[] versionCodes = {null};
        final String[] updateLogs = {null};
        final boolean[] isupdate = {false};
        final String[] url = {""};

        final Handler mHandler = new Handler() {
            //接收到消息后开始执行gohome（）
            @Override
            public void handleMessage(Message msg) {
                switch (msg.what) {
                    case 100:
                        NiceDialog.init()
                                .setLayoutId(R.layout.update_app_version)
                                .setConvertListener(new ViewConvertListener() {
                                    @Override
                                    public void convertView(ViewHolder viewHolder, final BaseNiceDialog baseNiceDialog) {
                                        viewHolder.setText(R.id.update_app_versioncode,"版本号：" + versionCodes[0]);
                                        String updatelog = updateLogs[0].replace("+","\n");
                                        viewHolder.setText(R.id.update_app_log,updatelog);
                                        viewHolder.setOnClickListener(R.id.update_app_download, new View.OnClickListener() {
                                            @Override
                                            public void onClick(View v) {
                                                String downloadUrl = url[0];
                                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
                                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                                request.setTitle("喵记");
                                                request.setDescription("");
                                                File saveFile = new File(getExternalFilesDir(null) , "喵记.apk");
                                                request.setDestinationUri(Uri.fromFile(saveFile));
                                                DownloadManager manager = (DownloadManager) ApplicationMainActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);

                                                // 将下载请求加入下载队列, 返回一个下载ID
                                                long downloadId = manager.enqueue(request);
                                                baseNiceDialog.dismiss();
                                            }
                                        });
                                    }
                                })
                                .setDimAmount(0.7f)
                                .setShowBottom(true)
                                .setMargin(5)
                                .setOutCancel(true)
                                .show(getSupportFragmentManager());


                        /*AlertDialog.Builder builder = new AlertDialog.Builder(ApplicationMainActivity.this,R.style.MyDialog);
                        builder.setCancelable(true);
                        View updateApp = View.inflate(ApplicationMainActivity.this,R.layout.update_app_version,null);
                        builder.setView(updateApp);
                        final AlertDialog dialog = builder.create();
                        Window window = dialog.getWindow();
                        window.setGravity(Gravity.BOTTOM);

                        TextView versionCodeText =(TextView) updateApp.findViewById(R.id.update_app_versioncode);
                        TextView updateLog = (TextView) updateApp.findViewById(R.id.update_app_log);
                        Button download = (Button) updateApp.findViewById(R.id.update_app_download);

                        versionCodeText.setText("版本号：" + versionCodes[0]);
                        String updatelog = updateLogs[0].replace("+","\n");
                        updateLog.setText(updatelog);

                        download.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                //Log.d("1111",url[0]);
                                String downloadUrl = url[0];
                                DownloadManager.Request request = new DownloadManager.Request(Uri.parse(downloadUrl));
                                request.setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED);
                                request.setTitle("喵记");
                                request.setDescription("");
                                File saveFile = new File(getExternalFilesDir(null) , "喵记.apk");
                                request.setDestinationUri(Uri.fromFile(saveFile));
                                DownloadManager manager = (DownloadManager) ApplicationMainActivity.this.getSystemService(Context.DOWNLOAD_SERVICE);

                                // 将下载请求加入下载队列, 返回一个下载ID
                                long downloadId = manager.enqueue(request);
                                dialog.dismiss();
                            }
                        });
                        dialog.show();
                        break;*/
                }
            }
        };

        final int versionCode = GetAppVersionInformation.getVersionCode(this);

        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
               // Log.d("2","2");
                BmobQuery<MyAppVersion> query = new BmobQuery<MyAppVersion>();
                query.clearCachedResult(MyAppVersion.class);
                query.findObjects(new FindListener<MyAppVersion>() {
                    @Override
                    public void done(List<MyAppVersion> list, BmobException e) {
                        if(e == null){
                            //Log.d("3","3");
                                //Log.d("4","4");
                                versionCodes[0] = list.get(0).getVersion();
                                updateLogs[0] = list.get(0).getUpdateLog();
                                url[0] = list.get(0).getPath().getUrl();
                                preference.setShareAddress(url[0]);
                            if(versionCode != list.get(0).getVersionCode()){
                                isupdate[0] = true;
                                //Log.d("code",versionCodes[0] + "");
                                //Log.d("log",updateLogs[0]);
                               // Log.d("url",url[0]);
                                Message msg = mHandler.obtainMessage();
                                msg.what = 100;
                                mHandler.sendEmptyMessage(100);
                            }
                        }else {
                            isupdate[0] = false;
                        }
                    }
                });
            }
        });
        thread.start();
        if(isupdate[0]){
            return null;
        }else {
            return "没有更新";
        }
    }
}
