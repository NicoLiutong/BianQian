package com.example.bianqian.adapter;

import com.example.bianqian.db.LocalUserNote;

/**
 * Created by 刘通 on 2017/7/6.
 */

public class AdapterDateList {

    private LocalUserNote localUserNote;

    private Boolean dataTile;

    private String date;

    public LocalUserNote getLocalUserNote() {
        return localUserNote;
    }

    public void setLocalUserNote(LocalUserNote localUserNote) {
        this.localUserNote = localUserNote;
    }

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

}
