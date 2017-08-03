package com.example.bianqian.bmobbasic;

import java.util.Date;

import cn.bmob.v3.BmobObject;

/**
 * Created by 刘通 on 2017/6/19.
 */

public class UserNote extends BmobObject {

    private User user;

    private String note;

    private String moodColor;

    private Date updateDate;

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getMoodColor() {
        return moodColor;
    }

    public void setMoodColor(String moodColor) {
        this.moodColor = moodColor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

}
