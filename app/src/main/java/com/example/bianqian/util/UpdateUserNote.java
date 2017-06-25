package com.example.bianqian.util;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.example.bianqian.db.User;
import com.example.bianqian.db.UserNote;

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
    public static void creatNewNote(UserNote userNote, final Context context){
        final boolean[] state = new boolean[1];
        userNote.save(new SaveListener<String>() {
            @Override
            public void done(String s, BmobException e) {
                if(e == null){
                    showToast("保存成功",context);
                }else {
                    showToast(ShowError.showError(e),context);
                    Log.d("error",ShowError.showError(e));
                }
            }
        });
    }

    public static void updateNote(UserNote userNote, String noteId, final Context context){
        userNote.update(noteId, new UpdateListener() {
            @Override
            public void done(BmobException e) {
                if(e == null){
                    showToast("更新成功",context);
                }else {
                    showToast(ShowError.showError(e),context);
                    Log.d("error",ShowError.showError(e));
                }
            }
        });
    }

    public static void deletNote(List<String> noteIds, final Context context, final GetFindData<UserNote> deletResout){
        List<BmobObject> notes = new ArrayList<BmobObject>();
        for (String id : noteIds){
            UserNote note = new UserNote();
            note.setObjectId(id);
            notes.add(note);
        }
        new BmobBatch().deleteBatch(notes).doBatch(new QueryListListener<BatchResult>() {
            @Override
            public void done(List<BatchResult> list, BmobException e) {
                if(e==null){
                    for(int i=0;i<list.size();i++){
                        BatchResult result = list.get(i);
                        BmobException ex =result.getError();
                        if(ex==null){
                            deletResout.deletDataResout(true);
                        }else{
                            showToast("第"+i+"个数据批量删除失败：" + "失败原因：" + ShowError.showError(ex),context);
                        }
                    }
                }else{
                    showToast(ShowError.showError(e),context);
                    Log.d("error",ShowError.showError(e));
                }
            }
        });
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
                  findData.returnFindData(list);
              } else {
                  showToast(ShowError.showError(e),context);
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
                    findData.returnFindData(list);
                }else {
                    showToast(ShowError.showError(e),context);
                    Log.d("error",ShowError.showError(e));
                }
            }
        });
    }
}
