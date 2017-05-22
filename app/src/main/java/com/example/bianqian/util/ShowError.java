package com.example.bianqian.util;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by 刘通 on 2017/5/22.
 */

public class ShowError {

    public static String showError(BmobException e){
        if(e.getErrorCode() == 203){
            return "邮箱已经存在";
        }
        if(e.getErrorCode() == 301){
            return "邮箱格式不正确";
        }
        if(e.getErrorCode() == 9008){
            return "上传文件不存在";
        }
        if(e.getErrorCode() == 9016){
            return "无网络连接，请检查您的手机网络";
        }
        if(e.getErrorCode() == 101){
            return "用户名或密码不正确";
        }
        if(e.getErrorCode() == 202){
            return "用户名已经存在";
        }
        if(e.getErrorCode() == 203){
            return "邮箱已经存在";
        }
        if(e.getErrorCode() == 204){
            return "必须提供一个邮箱地址";
        }
        if(e.getErrorCode() == 205){
            return "没有找到此用户名的用户";
        }
        if(e.getErrorCode() == 210){
            return "旧密码不正确";
        }

        return "";
    }

}
