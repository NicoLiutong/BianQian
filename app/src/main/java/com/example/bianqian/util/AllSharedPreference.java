package com.example.bianqian.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

/**
 * Created by 刘通 on 2017/5/21.
 */

public class AllSharedPreference {

    private SharedPreferences preferences;

    private SharedPreferences.Editor editor;

    public AllSharedPreference(Context context){
        preferences = PreferenceManager.getDefaultSharedPreferences(context);
        editor = preferences.edit();
    }

    public void setUserName(String userName){
        editor.putString("userName",userName);
        editor.apply();
    }
    public String getUseName(){
        return preferences.getString("userName","");
    }

    public void setPassword(String password){
        editor.putString("password",password);
        editor.apply();
    }
    public String getPassword(){
        return preferences.getString("password","");
    }

    public void setAutomaticLogin(boolean antomaticLogin){
        editor.putBoolean("antomaticLogin",antomaticLogin);
        editor.apply();
    }
    public boolean getAutomaticLogin(){
        return preferences.getBoolean("antomaticLogin",false);
    }

    public void setShareAddress(String shareAddress){
        editor.putString("shareAddress",shareAddress);
        editor.apply();
    }

    public String getShareAddress(){
        return preferences.getString("shareAddress","");
    }
}
