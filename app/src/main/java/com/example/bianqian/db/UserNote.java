package com.example.bianqian.db;

import org.litepal.crud.DataSupport;

import java.util.Date;

/**
 * Created by 刘通 on 2017/8/1.
 */

public class UserNote extends DataSupport {

    private String noteId;

    private String note;

    private Date updateDate;

    private String updateType;

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
