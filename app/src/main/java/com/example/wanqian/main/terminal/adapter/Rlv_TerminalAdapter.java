package com.example.wanqian.main.terminal.adapter;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.wanqian.R;
import com.example.wanqian.main.terminal.bean.TerminalBean;

import java.util.ArrayList;

public class Rlv_TerminalAdapter extends RecyclerView.Adapter<Rlv_TerminalAdapter.ViewHolder> {
    private static final String TAG = "Rlv_TerminalAdapter";
    private Context context;
    private ArrayList<TerminalBean.DataBean.ListBean> list;

    public Rlv_TerminalAdapter(Context context, ArrayList<TerminalBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.fragment_terminallist, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ViewHolder holder = viewHolder;

        TerminalBean.DataBean.ListBean bean = list.get(i);
        holder.mTerminallistLinear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener!=null){
                    mListener.onchangeDataClick(bean);
                }
            }
        });
        holder.terminal_serial_tv.setText(i + 1 + "");
        holder.terminal_plate_tv2.setText(bean.getGarbageId() + "号");
        holder.terminal_operating_tv3.setText(bean.getDevId());
        String[] eventTime = bean.getEventTime().split(" ");
        holder.terminal_working_tv4.setText(eventTime[0] + "\n" + eventTime[1]);

        String reportType = bean.getReportType();
        Log.d(TAG, "onBindViewHolder: " + reportType);
        String[] shu = reportType.split(",");

        String type = "";

        for (int j = 0; j < shu.length; j++) {

            switch (shu[j]) {
                case "0":
                    type = type + "实时上报，";
                    break;
                case "1":
                    type = type + "垃圾溢满上报" + "\n";
                    break;
                case "2":
                    type = type + "垃圾清理上报" + "\n";
                    break;
                case "4":
                    type = type + "超温上报，";
                    break;
                case "8":
                    type = type + "倾斜上报，";
                    break;
                case "16":
                    if (j == shu.length - 1)
                        type = type + "电池欠压上报";
                    else
                        type = type + "电池欠压上报" + "\n";
                    break;
                case "32":
                    if (j == shu.length - 1)
                        type = type + "部件故障上报";
                    else
                        type = type + "部件故障上报" + "\n";
                    break;
                case "64":
                    type = type + "多报数据，";
                    break;
                case "128":
                    type = type + "心跳信号，";
                    break;
            }
            if (type.length() == 0) {
                viewHolder.terminal_working_tv5.setText(type);
            } else {
                viewHolder.terminal_working_tv5.setText(type.substring(0, type.length() - 1));
            }
        }

        String status = bean.getOnlineStatus();
        zy2(holder, status);
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        TextView terminal_serial_tv, terminal_plate_tv2, terminal_operating_tv3, terminal_working_tv4, terminal_working_tv5, terminal_state_tv6;
        LinearLayout mTerminallistLinear;
        ImageView mTerminallistImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            terminal_serial_tv = itemView.findViewById(R.id.terminal_serial_tv1);
            terminal_plate_tv2 = itemView.findViewById(R.id.terminal_plate_tv2);
            terminal_operating_tv3 = itemView.findViewById(R.id.terminal_operating_tv3);
            terminal_working_tv4 = itemView.findViewById(R.id.terminal_working_tv4);
            terminal_working_tv5 = itemView.findViewById(R.id.terminal_working_tv5);
            terminal_state_tv6 = itemView.findViewById(R.id.terminal_state_tv6);
            mTerminallistLinear = itemView.findViewById(R.id.terminallist_linear);
            mTerminallistImg = itemView.findViewById(R.id.terminallist_img);
        }
    }

    private void zy2(ViewHolder holder, String status) {
        switch (status) {
            case "1":
                holder.terminal_state_tv6.setText("正常");
                StatusSuccess(holder);
                break;
            case "2":
                holder.terminal_state_tv6.setText("打开/倾斜");
                break;
            case "3":
                holder.terminal_state_tv6.setText("距离传感器异常?");
                StatusError(holder);
                break;
            case "5":
                holder.terminal_state_tv6.setText("三轴传感器异常?");
                StatusError(holder);
                break;
            case "9":
                holder.terminal_state_tv6.setText("温度传感器异常?");
                StatusError(holder);
                break;
            case "17":
                holder.terminal_state_tv6.setText("外部存储异常?");
                StatusError(holder);
                break;
            case "33":
                holder.terminal_state_tv6.setText("RTC时钟异常?");
                StatusError(holder);
                break;
            case "65":
                holder.terminal_state_tv6.setText("电池异常?");
                StatusError(holder);
                break;
            default:
                holder.terminal_state_tv6.setText("GPS异常?");
                StatusError(holder);
                break;
        }
    }

    /**
     * 设备正常状态的背景颜色
     */
    private void StatusSuccess(ViewHolder holder) {
        holder.terminal_state_tv6.setTextColor(Color.parseColor("#52C41A"));
        holder.terminal_state_tv6.setBackgroundColor(Color.parseColor("#EDF9E8"));
        holder.mTerminallistLinear.setBackgroundColor(Color.parseColor("#ffffff"));
        holder.mTerminallistImg.setImageResource(R.mipmap.icon_xuhao);
    }

    /**
     * 设备异常状态的背景颜色
     */
    public void StatusError(ViewHolder holder) {
        holder.mTerminallistLinear.setBackgroundColor(Color.parseColor("#fffbf3"));
        holder.terminal_state_tv6.setTextColor(Color.parseColor("#fbb741"));
        holder.terminal_state_tv6.setBackgroundColor(Color.parseColor("#fff2d9"));
        holder.mTerminallistImg.setImageResource(R.mipmap.icon_yichang);
    }

    private OnchangeDataClickListener mListener;

    public interface OnchangeDataClickListener {
        void onchangeDataClick( TerminalBean.DataBean.ListBean data);
    }

    public void setOnchangeDataClickListener(OnchangeDataClickListener listener) {
        mListener = listener;
    }
}