package com.example.bianqian.util;

import java.util.List;

/**
 * Created by 刘通 on 2017/6/26.
 */

public interface GetFindData<T> {
    void returnFindData(List<T> findData);
    void deletDataResout(Boolean bool);
}