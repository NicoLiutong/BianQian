package com.example.bianqian.bmobbasic;

import java.util.List;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 刘通 on 2017/5/18.
 */

public class User extends BmobUser {

    private String myUsername;

    private String sex;

    private BmobFile uesrImage;

    private String birthday;

    private Integer currentMonth;

    private Integer empircalValue;

    private List<String> signInDays;

    private String individuality;

    public Integer getEmpircalValue() {
        return empircalValue;
    }

    public void setEmpircalValue(Integer empircalValue) {
        this.empircalValue = empircalValue;
    }

    public Integer getCurrentMonth() {
        return currentMonth;
    }

    public void setCurrentMonth(Integer currentMonth) {
        this.currentMonth = currentMonth;
    }

    public List<String> getSignInDays() {
        return signInDays;
    }

    public void setSignInDays(List<String> signInDays) {
        this.signInDays = signInDays;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getIndividuality() {
        return individuality;
    }

    public void setIndividuality(String individuality) {
        this.individuality = individuality;
    }

    public BmobFile getUesrImage() {
        return uesrImage;
    }

    public void setUesrImage(BmobFile uesrImage) {
        this.uesrImage = uesrImage;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getMyUsername() {
        return myUsername;
    }

    public void setMyUsername(String myUsername) {
        this.myUsername = myUsername;
    }

}
