package com.example.bianqian.impl;

import com.example.bianqian.db.LocalUserNote;

import java.util.List;

/**
 * Created by 刘通 on 2017/6/26.
 */

public interface GetFindData<T> {
    void returnFindData(List<T> findData,Boolean isSuccess);
    void deletDataResult(List<LocalUserNote> deletOkItem);
    void creatNotesResult(List<LocalUserNote> creatSuccessItems);
    void updateNotesResult(List<LocalUserNote> updateSuccessItems);
    void creatDataResult(Boolean isSuccess);
    void upDataResult(Boolean isSuccess);
}
