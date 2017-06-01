package com.example.bianqian.db;

import cn.bmob.v3.BmobUser;
import cn.bmob.v3.datatype.BmobFile;

/**
 * Created by 刘通 on 2017/5/18.
 */

public class User extends BmobUser {

    private String myUsername;

    private String sex;

    private BmobFile uesrImage;

    private String individuality;

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
