package com.example.bianqian.util;

import android.util.Log;

import com.example.bianqian.db.LocalUserNote;

import java.util.List;

/**
 * Created by 刘通 on 2017/8/5.
 */

public class LogUtils {

    public static boolean isShow = false;
    public static void d(String s,List<LocalUserNote> userNotes) {
        if(isShow){
            if(userNotes != null && userNotes.size() != 0) {
                for (int i = 0; i < userNotes.size(); i++) {
                    Log.d(s, "id " + userNotes.get(i).getId() + "\n" + "noteId " + userNotes.get(i).getNoteId() + "\n" +
                            "userId " + userNotes.get(i).getUser() + "\n" +
                            "moodColor " + userNotes.get(i).getMoonColor() + "\n" + "note " + userNotes.get(i).getNote() + "\n" +
                            "date " + userNotes.get(i).getUpdateDate().toString() + "\n" + "type " + userNotes.get(i).getUpdateType());
                }
            }
        }

    }
}
