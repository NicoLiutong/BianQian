package com.example.bianqian.adapter;

/**
 * Created by 刘通 on 2017/6/15.
 */

public interface MultiItemTypeSupport<T> {
    int getLayoutId(int itenType);
    int getItemViewType(int position, T t);
}
