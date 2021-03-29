package com.example.wanqian.ui.deviceList.adapter;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.wanqian.R;
import com.example.wanqian.base.BaseRcyAdapter;
import com.example.wanqian.base.BaseViewHolder;
import com.example.wanqian.bean.TerminalNewBean;
import com.example.wanqian.ui.deviceList.viewHolder.MyDeviceViewHolder;
import com.example.wanqian.ui.deviceList.viewHolder.MyEmptyViewHolder;

public class DeviceListAdapter extends BaseRcyAdapter<TerminalNewBean.ListBean,BaseViewHolder>  {

    private final int DATA_EMPTY = 1;
    private final int DATA_ITEM = 2;


    public DeviceListAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolderEX(BaseViewHolder holder, int position) {
        if (mData!=null&&mData.size()>0){
            TerminalNewBean.ListBean myWorkBean = mData.get(position);
            myWorkBean.setPosition(position);
            if (holder instanceof MyDeviceViewHolder){
                MyDeviceViewHolder holder1= (MyDeviceViewHolder) holder;
                holder1.setOnchangeDataClickListener(bean -> {
                if (mListener!=null){
                    mListener.onchangeDataClick(bean);
                  }
                });
            }
            holder.setData(myWorkBean);

            //AppLog.d("ttttttt","111111111111111");
        }
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case DATA_ITEM:
                return new MyDeviceViewHolder(mInflater, parent, R.layout.fragment_terminallist);
            case DATA_EMPTY:
                return new MyEmptyViewHolder(mInflater, parent, R.layout.view_empty);
        }
        return null;
    }


    @Override
    public int getItemCount() {
        return mData==null||mData.size()==0?1:mData.size();
    }

    @Override
    public int getItemViewType(int position) {
        return mData==null||mData.size()==0?DATA_EMPTY:DATA_ITEM;
    }
    private OnchangeDataClickListener mListener;

    public interface OnchangeDataClickListener {
        void onchangeDataClick(TerminalNewBean.ListBean bean);
    }

    public void setOnchangeDataClickListener(OnchangeDataClickListener listener) {
        mListener = listener;
    }
}
