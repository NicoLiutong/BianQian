package com.example.bianqian.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 刘通 on 2017/6/15.
 */

public abstract class MyAdapter<T> extends RecyclerView.Adapter<MyViewHolder> {

    protected Context mContext;
    protected int mLayoutId;
    protected List<T> Mdatas;
    protected LayoutInflater mInflater;
    protected int viewType;
    protected MultiItemTypeSupport<T> mMultiItemTypeSupport;

    private List<Integer> selectItems = new ArrayList<>();

    private boolean isClickLong = false;

    public MyAdapter(Context context,int layoutId,List<T> datas){
        mContext = context;
        mInflater = LayoutInflater.from(context);
        mLayoutId = layoutId;
        Mdatas = datas;
    }

    public MyAdapter(Context context,List<T> datas,MultiItemTypeSupport<T> multiItemTypeSupport){
        this(context,-1,datas);
        mMultiItemTypeSupport = multiItemTypeSupport;
    }


    public List<Integer> getSelectItems() {
        return selectItems;
    }

    public boolean isClickLong() {
        return isClickLong;
    }

    public void setClickLong(boolean clickLong) {
        isClickLong = clickLong;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder viewHolder;
        this.viewType = viewType;
        if(mLayoutId == -1){
        int layoutId = mMultiItemTypeSupport.getLayoutId(viewType);
        viewHolder = MyViewHolder.get(mContext,parent,layoutId);
        }else {
            viewHolder = MyViewHolder.get(mContext,parent,mLayoutId);
        }
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        convert(holder,viewType,Mdatas.get(position), isClickLong(),position);
    }

    public abstract void convert(MyViewHolder holder,int viewType, T t, boolean isClickLong,int positoin);

    @Override
    public int getItemCount() {
        return Mdatas.size();
    }

    @Override
    public int getItemViewType(int position) {
        if(mLayoutId == -1){
            return mMultiItemTypeSupport.getItemViewType(position,Mdatas.get(position));
        }else {
        return super.getItemViewType(position);
        }
    }

    public boolean isSelectItems(Integer position){
        return getSelectItems().contains(position);
    }

    public void addCheck(Integer position){
        if(!getSelectItems().contains(position)){
            selectItems.add(position);
        }
        //notifyItemChanged(position);
    }

    public void removeCheck(Integer position){
        if(getSelectItems().contains(position)){
            selectItems.remove(position);
        }
        //notifyItemChanged(position);
    }

    public int getSelectItemCount(){
        return selectItems.size();
    }

    public void clearSelectItems(){
        selectItems.clear();
    }
}
