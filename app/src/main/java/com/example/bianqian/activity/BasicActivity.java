package com.example.bianqian.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import cn.bmob.v3.Bmob;

/**
 * Created by 刘通 on 2017/5/18.
 */

public abstract class BasicActivity extends AppCompatActivity {

    public static List<Activity> activities = new ArrayList<>();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView();
        Bmob.initialize(this,"3da138f19a8d8a32a5a64ac1c4e740df");

        initViews();
        initData();
        initListeners();
        addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        removeActivity(this);
    }

    /**
     * 设置布局文件
     */
    public abstract void setContentView();

    /**
     * 初始化布局文件中的控件
     */
    public abstract void initViews();

    /**
     * 初始化控件的监听
     */
    public abstract void initListeners();

    /** 进行数据初始化
     * initData
     */
    public abstract void initData();

    Toast mToast;

    public void ShowToast(String text) {
        if (!TextUtils.isEmpty(text)) {
            if (mToast == null) {
                mToast = Toast.makeText(getApplicationContext(), text,
                        Toast.LENGTH_SHORT);
            } else {
                mToast.setText(text);
            }
            mToast.show();
        }
    }

    public static void addActivity(Activity activity){
        activities.add(activity);
    }

    public static void removeActivity(Activity activity){
        activities.remove(activity);
    }

    public static void finishAll(){
        for(Activity activity:activities){
            activity.finish();
        }
    }
}
