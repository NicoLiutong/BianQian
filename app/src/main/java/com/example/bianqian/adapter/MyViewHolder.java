package com.example.bianqian.adapter;

import android.content.Context;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.SparseArray;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

/**
 * Created by 刘通 on 2017/6/15.
 */

public class MyViewHolder extends RecyclerView.ViewHolder {

    private SparseArray<View> mViews;
    private Context mContext;
    private View mConvertView;

    public MyViewHolder(Context context, View itemView, ViewGroup parent) {
        super(itemView);
        mContext = context;
        mConvertView = itemView;
        mViews = new SparseArray<View>();
    }

    public static MyViewHolder get(Context context,ViewGroup parent,int layoutId){
        View itenView = LayoutInflater.from(context).inflate(layoutId,parent,false);
        MyViewHolder holder = new MyViewHolder(context,itenView,parent);
        return holder;
    }

    public <T extends View> T getView(int viewId){
        View view = mViews.get(viewId);
        if(view == null){
            view = mConvertView.findViewById(viewId);
            mViews.put(viewId,view);
        }
        return (T) view;
    }

    public MyViewHolder setText(int viewId,String text){
        TextView tv = getView(viewId);
        tv.setText(text);
        return this;
    }

    public MyViewHolder setBackGround(int viewId, int color){
        View view = getView(viewId);
        view.setBackgroundColor(ContextCompat.getColor(mContext, color));
        return this;
    }

    public MyViewHolder setCardBackGround(int viewId, int color){
        CardView view = getView(viewId);
        view.setCardBackgroundColor(ContextCompat.getColor(mContext, color));
        return this;
    }

    public MyViewHolder setTextColor(int viewId, int color){
        TextView tv = getView(viewId);
        tv.setTextColor(ContextCompat.getColor(mContext,color));
        return this;
    }

    public MyViewHolder setCircleColor(int viewId,int drawable){
        View view = getView(viewId);
        view.setBackgroundResource(drawable);
        return this;
    }

    public MyViewHolder setOnClickListener(int viewId,View.OnClickListener listener){
        View view = getView(viewId);
        view.setOnClickListener(listener);
        return this;
    }

    public MyViewHolder setOnLongClickListener(int viewId,View.OnLongClickListener listener){
        View view = getView(viewId);
        view.setOnLongClickListener(listener);
        return this;
    }

    public MyViewHolder setOnCheckedChangeLister(int viewId, CheckBox.OnCheckedChangeListener listener){
        CheckBox checkBox = getView(viewId);
        checkBox.setOnCheckedChangeListener(listener);
        return this;
    }

    public MyViewHolder setVisiblity(int viewId){
        View view = getView(viewId);
        view.setVisibility(View.VISIBLE);
        return this;
    }

    public MyViewHolder setInvisiblity(int viewId){
        View view = getView(viewId);
        view.setVisibility(View.INVISIBLE);
        return this;
    }

    public MyViewHolder setChecked(int viewId,boolean checked){
        CheckBox checkBox = getView(viewId);
        checkBox.setChecked(checked);
        return this;
    }
}
