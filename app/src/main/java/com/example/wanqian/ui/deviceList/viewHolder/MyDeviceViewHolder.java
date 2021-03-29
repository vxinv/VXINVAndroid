package com.example.wanqian.ui.deviceList.viewHolder;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.wanqian.R;
import com.example.wanqian.base.BaseViewHolder;
import com.example.wanqian.bean.TerminalNewBean;

public class MyDeviceViewHolder extends BaseViewHolder<TerminalNewBean.ListBean> {

    private TextView terminal_serial_tv, terminal_plate_tv2, terminal_operating_tv3, terminal_working_tv4, terminal_working_tv5, terminal_state_tv6;
    private LinearLayout mTerminallistLinear;
    private ImageView mTerminallistImg;
    private OnchangeDataClickListener mListener;

    public MyDeviceViewHolder(LayoutInflater mInflater, ViewGroup parent, int resId) {
        super(mInflater, parent, resId);
    }

    @Override
    protected void initView() {
        terminal_serial_tv = itemView.findViewById(R.id.terminal_serial_tv1);
        terminal_plate_tv2 = itemView.findViewById(R.id.terminal_plate_tv2);
        terminal_operating_tv3 = itemView.findViewById(R.id.terminal_operating_tv3);
        terminal_working_tv4 = itemView.findViewById(R.id.terminal_working_tv4);
        terminal_working_tv5 = itemView.findViewById(R.id.terminal_working_tv5);
        terminal_state_tv6 = itemView.findViewById(R.id.terminal_state_tv6);
        mTerminallistLinear = itemView.findViewById(R.id.terminallist_linear);
        mTerminallistImg = itemView.findViewById(R.id.terminallist_img);
    }

    @Override
    public void setData(TerminalNewBean.ListBean data) {
        TerminalNewBean.ListBean bean = data;
        mTerminallistLinear.setOnClickListener(v -> {
            if (mListener != null) {
                mListener.onchangeDataClick(bean);
            }
        });
        terminal_serial_tv.setText(data.getPosition() + 1 + "");
        terminal_plate_tv2.setText(bean.getGarbageId() + "号");
        terminal_operating_tv3.setText(bean.getDevId());
        String[] eventTime = bean.getEventTime().split(" ");
        terminal_working_tv4.setText(eventTime[0] + "\n" + eventTime[1]);
        String reportType = bean.getReportType();
        String[] dataArray = reportType.split(",");

        int max = Integer.valueOf(dataArray[dataArray.length - 1]);
        StringBuilder type = new StringBuilder();

        for (int j = 0; j < dataArray.length; j++) {
            switch (dataArray[j]) {
                case "0":
                    if (max == 0) {
                        type.append("实时上报");
                    } else {
                        type.append("实时上报" + "\n");
                    }
                    break;
                case "1":
                    if (max == 1) {
                        type.append("垃圾溢满");
                    } else {
                        type.append("垃圾溢满" + "\n");
                    }
                    break;
                case "2":
                    if (max == 2) {
                        type.append("垃圾清理");
                    } else {
                        type.append("垃圾清理" + "\n");
                    }
                    break;
                case "4":
                    if (max == 4) {
                        type.append("超温上报");
                    } else {
                        type.append("超温上报" + "\n");
                    }
                    break;
                case "8":
                    if (max == 8) {
                        type.append("倾斜上报");
                    } else {
                        type.append("倾斜上报" + "\n");
                    }
                    break;
                case "16":
                    if (max == 16) {
                        type.append("电池欠压");
                    } else {
                        type.append("电池欠压" + "\n");
                    }
                    break;
                case "32":
                    if (max == 32) {
                        type.append("部件故障");
                    } else {
                        type.append("部件故障" + "\n");
                    }
                    break;
                case "64":
                    if (max == 64) {
                        type.append("多报数据");
                    } else {
                        type.append("多报数据" + "\n");
                    }
                    break;
                case "128":
                    if (max == 128) {
                        type.append("心跳信号");
                    } else {
                        type.append("心跳信号" + "\n");
                    }
                    break;
            }
            terminal_working_tv5.setText(type);
        }
        String status = bean.getDevStatus();
        zy2(status);
    }

    private void zy2(String status) {
        switch (status) {
            case "0":
                terminal_state_tv6.setText("正常");
                StatusSuccess();
                break;
            case "1":
                terminal_state_tv6.setText("打开/倾斜");
                StatusSuccess();
                break;
            case "2":
                terminal_state_tv6.setText("距离传感器异常?");
                StatusError();
                break;
            case "4":
                terminal_state_tv6.setText("三轴传感器异常?");
                StatusError();
                break;
            case "8":
                terminal_state_tv6.setText("温度传感器异常?");
                StatusError();
                break;
            case "16":
                terminal_state_tv6.setText("外部存储异常?");
                StatusError();
                break;
            case "32":
                terminal_state_tv6.setText("RTC时钟异常?");
                StatusError();
                break;
            case "64":
                terminal_state_tv6.setText("电池异常?");
                StatusError();
                break;
            case "128":
                terminal_state_tv6.setText("GPS异常?");
                StatusError();
                break;
        }
    }

    public void setOnchangeDataClickListener(OnchangeDataClickListener listener) {
        mListener = listener;
    }

    /**
     * 设备正常状态的背景颜色
     */
    private void StatusSuccess() {
        terminal_state_tv6.setTextColor(Color.parseColor("#52C41A"));
        terminal_state_tv6.setBackgroundColor(Color.parseColor("#EDF9E8"));
        mTerminallistLinear.setBackgroundColor(Color.parseColor("#ffffff"));
        mTerminallistImg.setImageResource(R.mipmap.icon_xuhao);
    }

    /**
     * 设备异常状态的背景颜色
     */
    public void StatusError() {
        mTerminallistLinear.setBackgroundColor(Color.parseColor("#fffbf3"));
        terminal_state_tv6.setTextColor(Color.parseColor("#fbb741"));
        terminal_state_tv6.setBackgroundColor(Color.parseColor("#fff2d9"));
        mTerminallistImg.setImageResource(R.mipmap.icon_yichang);
    }

    public interface OnchangeDataClickListener {
        void onchangeDataClick(TerminalNewBean.ListBean bean);
    }
}
