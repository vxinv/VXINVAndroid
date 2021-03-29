package com.example.wanqian.ui.carMessage;

import android.content.Context;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.example.wanqian.R;
import com.example.wanqian.base.BaseRcyAdapter;
import com.example.wanqian.base.BaseViewHolder;
import com.example.wanqian.bean.CarMessageBean;
import com.example.wanqian.ui.deviceList.viewHolder.MyEmptyViewHolder;

public class CarMessageAdapter extends BaseRcyAdapter<CarMessageBean, BaseViewHolder> {
    private final int DATA_EMPTY = 1;
    private final int DATA_ITEM = 2;
    public CarMessageAdapter(Context context) {
        super(context);
    }

    @Override
    protected void onBindViewHolderEX(BaseViewHolder holder, int position) {
        if (mData!=null&&mData.size()>0){
            CarMessageBean myWorkBean =  mData.get(position);
            holder.setData(myWorkBean);
        }

    }
    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case DATA_ITEM:
                return new CarMessageHolder(mInflater, parent, R.layout.fragment_historylist);
            case DATA_EMPTY:
                return new MyEmptyViewHolder(mInflater, parent, R.layout.view_empty);
        }
        return null;
    }

    @Override
    public int getItemCount() {
        return getData()==null||getData().size()==0?1:getData().size();
    }
    @Override
    public int getItemViewType(int position) {
        return getData()==null||getData().size()==0?DATA_EMPTY:DATA_ITEM;
    }
}
