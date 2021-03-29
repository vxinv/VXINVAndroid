package com.example.wanqian.base;

/*
 *
 *
 * 作 者 :YangFan
 *
 * 版 本 :1.0
 *
 * 创建日期 :2018/11/8      18:53
 *
 * 描 述 :
 *
 * 修订日期 :
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

public abstract class BaseViewHolder<T> extends RecyclerView.ViewHolder {
    protected Context mContext;

    public BaseViewHolder(LayoutInflater mInflater, ViewGroup parent, int resId) {
        super(mInflater.inflate(resId, parent, false));
        mContext = mInflater.getContext();
        initView();
    }

    protected abstract void initView();

    public abstract void setData(T data);
}
