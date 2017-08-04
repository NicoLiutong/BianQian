package com.example.bianqian.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.bianqian.bmobbasic.User;
import com.example.bianqian.bmobbasic.UserNote;
import com.example.bianqian.db.LocalUserNote;
import com.example.bianqian.impl.GetFindData;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.bmob.v3.BmobBatch;
import cn.bmob.v3.BmobObject;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.datatype.BatchResult;
import cn.bmob.v3.datatype.BmobDate;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.QueryListListener;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;

/**
 * Created by 刘通 on 2017/6/26.
 */

public class UpdateUserNote {

    private static void showToast(String s,Context context){
        Toast.makeText(context,s,Toast.LENGTH_SHORT).show();
    }
    public static void creatNewNote(UserNote userNote, final Context context,final GetFindData<UserNote> creatResult){
        userNote.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    showToast("保存成功",context);

                    creatResult.creatDataResult(true);
                }else {
                    showToast(ShowError.showError(e),context);
                    Log.d("error",ShowError.showError(e));
                }
            }
        });
    }

    public static void updateNote(UserNote userNote, String noteId, final Context context,final GetFindData<UserNote> updateResult){
        userNote.update(noteId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    showToast("保存成功",context);

                    updateResult.upDataResult(true);
                }else {
                    showToast(ShowError.showError(e),context);
                    Log.d("error",ShowError.showError(e));
                }
            }
        });
    }

    public static void creatNotes(final List<LocalUserNote> notes,final User user, final GetFindData<UserNote> creatNotesResult){
        final List<BmobObject> creatNotes = new ArrayList<BmobObject>();
        final List<LocalUserNote> creatSuccessItems = new ArrayList<LocalUserNote>();
        for (LocalUserNote note : notes){
            UserNote creatNote = new UserNote();
            creatNote.setUser(user);
            creatNote.setNote(note.getNote());
            creatNote.setUpdateDate(note.getUpdateDate());
            creatNote.setMoodColor(note.getMoonColor());
            creatNotes.add(creatNote);
        }

        new BmobBatch().insertBatch(creatNotes).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if(e == null){
                    for (int i = 0;i < list.size();i ++){
                        BatchResult result = list.get(i);
                        BmobException ex = result.getError();
                        if(ex == null){
                            LocalUserNote successItem = notes.get(i);
                            successItem.setNoteId(result.getObjectId());
                            creatSuccessItems.add(successItem);
                        }
                    }
                }
            }
        });
            creatNotesResult.creatNotesResult(creatSuccessItems);
    }

    public static void updateNotes(final List<LocalUserNote> notes,final GetFindData<UserNote> updateNotesResult){
        final List<BmobObject> updateNotes = new ArrayList<BmobObject>();
        final List<LocalUserNote> updateSuccessItems = new ArrayList<LocalUserNote>();
        for (LocalUserNote note : notes){
            UserNote updateNote = new UserNote();
            updateNote.setObjectId(note.getNoteId());
            updateNote.setMoodColor(note.getMoonColor());
            updateNote.setNote(note.getNote());
            updateNote.setUpdateDate(note.getUpdateDate());
            updateNotes.add(updateNote);
        }
        new BmobBatch().updateBatch(updateNotes).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if(e == null){
                    for (int i = 0;i < list.size();i++){
                        BatchResult result = list.get(i);
                        BmobException ex = result.getError();
                        if(ex == null){
                            updateSuccessItems.add(notes.get(i));
                        }
                    }
                }
            }
        });
        updateNotesResult.updateNotesResult(updateSuccessItems);
    }

    public static void deletNote(final List<LocalUserNote> notes, final GetFindData<UserNote> deletResult){
        final List<BmobObject> deletNotes = new ArrayList<BmobObject>();
        final List<LocalUserNote> deletSuccessItems = new ArrayList<LocalUserNote>();
        for (LocalUserNote note : notes){
            UserNote deletNote = new UserNote();
            deletNote.setObjectId(note.getNoteId());
            deletNotes.add(deletNote);
        }
        new BmobBatch().deleteBatch(deletNotes).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if(e==null){
                    for(int i=0;i<list.size();i++){
                        BatchResult result = list.get(i);
                        BmobException ex =result.getError();
                        if(ex==null){
                            deletSuccessItems.add(notes.get(i));
                        }
                    }
                }
            }
        });
        deletResult.deletDataResult(deletSuccessItems);
    }

    public static void getAuthorNote(User user, final Context context, final GetFindData<UserNote> findData){
        BmobQuery<UserNote> query = new BmobQuery<UserNote>();
        query.addWhereEqualTo("user",user);
        query.order("-updatedAt");
        query.setLimit(1000);
        query.findObjects(new FindListener<UserNote>() {
            @Override
            public void done(List<UserNote> list, BmobException e) {
              if(e == null){
                  findData.returnFindData(list,true);
              } else {
                  showToast(ShowError.showError(e),context);
                  findData.returnFindData(list,false);
                  Log.d("error",ShowError.showError(e));
              }
            }
        });
    }

    public static void getAuthorNoteWithDate(User user, String date, final Context context, final GetFindData<UserNote> findData){
        BmobQuery<UserNote> query = new BmobQuery<UserNote>();
        List<BmobQuery<UserNote>> and = new ArrayList<BmobQuery<UserNote>>();
        //大于00：00：00
        BmobQuery<UserNote> q1 = new BmobQuery<UserNote>();
        String start = date + " 00:00:00";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date1 = null;
        try {
            date1 = sdf.parse(start);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        q1.addWhereGreaterThanOrEqualTo("updatedAt",new BmobDate(date1));
        and.add(q1);
        //小于23：59：59
        BmobQuery<UserNote> q2 = new BmobQuery<UserNote>();
        String end = date + " 23:59:59";
        SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date date2  = null;
        try {
            date2 = sdf1.parse(end);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        q2.addWhereLessThanOrEqualTo("createdAt",new BmobDate(date2));
        and.add(q2);

        BmobQuery<UserNote> q3 = new BmobQuery<UserNote>();
        q3.addWhereEqualTo("User",user);
        and.add(q3);
        //添加复合与查询
        query.and(and);
        query.findObjects(new FindListener<UserNote>() {
            @Override
            public void done(List<UserNote> list, BmobException e) {
                if(e == null){
                    findData.returnFindData(list,true);
                }else {
                    showToast(ShowError.showError(e),context);
                    findData.returnFindData(list,false);
                    Log.d("error",ShowError.showError(e));
                }
            }
        });
    }
}
