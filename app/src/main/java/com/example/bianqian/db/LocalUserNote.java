package com.example.bianqian.db;

import com.example.bianqian.bmobbasic.User;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by 刘通 on 2017/8/2.
 */

public class LocalUserNote extends DataSupport {

    private String noteId;

    private String note;

    private Date updateDate;

    private String updateType;

    private User user;

    private String moonColor;

    public String getMoonColor() {
        return moonColor;
    }

    public void setMoonColor(String moonColor) {
        this.moonColor = moonColor;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getNoteId() {
        return noteId;
    }

    public void setNoteId(String noteId) {
        this.noteId = noteId;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }

    public String getUpdateType() {
        return updateType;
    }

    public void setUpdateType(String updateType) {
        this.updateType = updateType;
    }
}
