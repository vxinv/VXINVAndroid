package com.example.wanqian.ui.carMessage;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;
import com.example.wanqian.R;
import com.example.wanqian.base.BaseViewHolder;
import com.example.wanqian.bean.CarMessageBean;
import com.example.wanqian.utitls.DateUtil;

public class CarMessageHolder extends BaseViewHolder<CarMessageBean> {

    private TextView history_serial_tv;
    private TextView history_plate_tv2;
    private TextView history_operating_tv3;
    private TextView history_working_tv4;
    private TextView history_working_tv5;
    private TextView history_state_tv6;

    public CarMessageHolder(LayoutInflater mInflater, ViewGroup parent, int resId) {
        super(mInflater, parent, resId);
    }
    @Override
    protected void initView() {
        history_serial_tv = itemView.findViewById(R.id.terminal_serial_tv1);
        history_plate_tv2 = itemView.findViewById(R.id.terminal_plate_tv2);
        history_operating_tv3 = itemView.findViewById(R.id.terminal_operating_tv3);
        history_working_tv4 = itemView.findViewById(R.id.terminal_working_tv4);
        history_working_tv5 = itemView.findViewById(R.id.terminal_working_tv5);
        history_state_tv6 = itemView.findViewById(R.id.terminal_state_tv6);
    }

    @Override
    public void setData(CarMessageBean data) {
        history_serial_tv.setText(data.getCri()+"");
        history_plate_tv2.setText(data.getCarNum());
        history_operating_tv3.setText(data.getName());
        history_working_tv4.setText(data.getStartTime()+"-"+data.getEndTime());
        long startTime=DateUtil.getStringToDate(data.getStartTime(),"yyyy/MM/dd HH:mm:ss");
        long endTime=DateUtil.getStringToDate(data.getEndTime(),"yyyy/MM/dd HH:mm:ss");
        String curTime=DateUtil.getDatePoor(endTime,startTime);
        history_working_tv5.setText(curTime);
        if (data.getStatus()==2){
            history_state_tv6.setText("已完成");
            history_state_tv6.setTextColor(Color.parseColor("#52C41A"));
            history_state_tv6.setBackgroundResource(R.drawable.car_state_finish);
        }else {
            history_state_tv6.setTextColor(Color.parseColor("#FAAD14"));
            history_state_tv6.setText("未完成");
            history_state_tv6.setBackgroundResource(R.drawable.car_state);
        }

    }

}
