package com.example.bianqian.adapter;

import com.example.bianqian.db.UserNote;

/**
 * Created by 刘通 on 2017/7/6.
 */

public class AdapterDateList {

    private UserNote userNote;

    private Boolean dataTile;

    private String date;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Boolean isDataTile() {
        return dataTile;
    }

    public void setDataTile(boolean dataTile) {
        this.dataTile = dataTile;
    }

    public UserNote getUserNote() {
        return userNote;
    }

    public void setUserNote(UserNote userNote) {
        this.userNote = userNote;
    }
}
