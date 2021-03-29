package com.example.wanqian.base;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseRcyAdapter<T, VH extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<VH> {

    protected Context mContext;
    protected List<T> mData;
    protected LayoutInflater mInflater;
    protected RecyclerViewCallBack<T> callBack;
    protected String TAG;

    public BaseRcyAdapter(Context context) {
        this.mContext = context;
        TAG = this.getClass().getSimpleName();
        if (mData == null) {
            mData = new ArrayList<>();
        }
        mInflater = LayoutInflater.from(context);
    }

    public void setRecyclerViewCallBack(RecyclerViewCallBack<T> callBack) {
        this.callBack = callBack;
    }

    @Override
    public int getItemCount() {
        return mData.size();
    }


    public void setData(List<T> data) {
        this.mData.clear();
        if (data != null) {
            this.mData.addAll(data);
        }
        notifyDataSetChanged();
    }

    public void addItem(T item) {
        int position = getItemCount();
        addItem(position, item);
    }

    public void addItem(int position, T item) {
        this.mData.add(position, item);
        notifyDataSetChanged();
    }

    public void removeItem(T item) {
        int position = this.mData.indexOf(item);
        removeItem(position);
    }

    public void removeItem(int position) {
        this.mData.remove(position);
        notifyDataSetChanged();
    }

    @Override
    public void onBindViewHolder(VH holder, final int position) {
        if (holder != null && holder.itemView != null) {
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (callBack != null && position < mData.size()) {
                        callBack.onItemClick(position, mData.get(position));
                    }
                }
            });
        }
        onBindViewHolderEX(holder, position);
    }

    protected abstract void onBindViewHolderEX(VH holder, final int position);

    public boolean containsItem(T item) {
        return this.mData.contains(item);
    }

    public T getItem(int position) {
        return mData.get(position);
    }

    public List<T> getData() {
        return this.mData;
    }

    public void clearData() {
        this.mData.clear();
        notifyDataSetChanged();
    }

    public void addData(List<T> data) {
        if (data != null) {
            mData.addAll(data);
            notifyDataSetChanged();
        }
    }

    public interface RecyclerViewCallBack<T> {
        void onItemClick(int position, T data);
    }
}
