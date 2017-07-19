package com.example.bianqian.util;

import android.util.Log;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by 刘通 on 2017/5/22.
 */

public class ShowError {

    public static String showError(BmobException e){
        if(e.getErrorCode() == 203){
            Log.d("203",e.getMessage());
            return e.getMessage();
        }
        if(e.getErrorCode() == 301){
            Log.d("301",e.getMessage());
            if(e.getMessage().equals("email Must be a valid email address")){
                return "必须为有效的邮箱地址";
            }
            return e.getMessage();
        }
        if(e.getErrorCode() == 9001){
            Log.d("9001",e.getMessage());
            return "Application Id为空，请初始化";
        }
        if(e.getErrorCode() == 9002){
            Log.d("9002",e.getMessage());
            return "解析返回数据出错";
        }
        if(e.getErrorCode() == 9003){
            Log.d("9003",e.getMessage());
            return "上传文件出错";
        }
        if(e.getErrorCode() == 9004){
            Log.d("9004",e.getMessage());
            return "文件上传失败";
        }
        if(e.getErrorCode() == 9006){
            Log.d("9006",e.getMessage());
            return "objectId为空";
        }
        if(e.getErrorCode() == 9007){
            Log.d("9007",e.getMessage());
            return "文件大小超过10M";
        }
        if(e.getErrorCode() == 9008){
            Log.d("9008",e.getMessage());
            return "上传文件不存在";
        }
        if(e.getErrorCode() == 9010){
            Log.d("9010",e.getMessage());
            return "网络超时";
        }
        if(e.getErrorCode() == 9013){
            Log.d("9013",e.getMessage());
            return "（数据表名称）格式不正确";
        }
        if(e.getErrorCode() == 9016){
            Log.d("9016",e.getMessage());
            return "没有网络连接，请检查网络";
        }
        if(e.getErrorCode() == 9022){
            Log.d("9022",e.getMessage());
            return "文件上传失败，请重新上传";
        }
        if(e.getErrorCode() == 101){
            Log.d("101",e.getMessage());
            if(e.getMessage().equals("username or password incorrect.")){
                return "用户名或密码不正确";
            }
            return e.getMessage();
        }
        if(e.getErrorCode() == 202){
            Log.d("202",e.getMessage());
                return "此邮箱已经被注册";
        }
        if(e.getErrorCode() == 203){
            Log.d("203",e.getMessage());
            return e.getMessage();
        }
        if(e.getErrorCode() == 204){
            Log.d("204",e.getMessage());
            return e.getMessage();
        }
        if(e.getErrorCode() == 205){
            Log.d("205",e.getMessage());
                return "此邮箱不存在";
        }
        if(e.getErrorCode() == 210){
            Log.d("210",e.getMessage());
            return "旧密码不正确";
        }
        if(e.getErrorCode() == 104){
            Log.d("104",e.getMessage());
            return e.getMessage();
        }
        if(e.getErrorCode() == 108){
            Log.d("108",e.getMessage());
            return "用户名和密码是必需的";
        }
        return "";
    }


}
