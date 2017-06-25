package com.example.bianqian.util;

import cn.bmob.v3.exception.BmobException;

/**
 * Created by 刘通 on 2017/5/22.
 */

public class ShowError {

    public static String showError(BmobException e){
        if(e.getErrorCode() == 203){
            return e.getMessage();
        }
        if(e.getErrorCode() == 301){
            return e.getMessage();
        }
        if(e.getErrorCode() == 9008){
            return e.getMessage();
        }
        if(e.getErrorCode() == 9016){
            return e.getMessage();
        }
        if(e.getErrorCode() == 101){
            return e.getMessage();
        }
        if(e.getErrorCode() == 202){
            return e.getMessage();
        }
        if(e.getErrorCode() == 203){
            return e.getMessage();
        }
        if(e.getErrorCode() == 204){
            return e.getMessage();
        }
        if(e.getErrorCode() == 205){
            return e.getMessage();
        }
        if(e.getErrorCode() == 210){
            return e.getMessage();
        }
        if(e.getErrorCode() == 104){
            return e.getMessage();
        }
        return "";
    }

}
