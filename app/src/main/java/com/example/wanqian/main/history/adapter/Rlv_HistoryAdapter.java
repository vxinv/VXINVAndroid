package com.example.wanqian.main.history.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.wanqian.R;
import com.example.wanqian.main.history.bean.HistoryBean;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Rlv_HistoryAdapter extends RecyclerView.Adapter<Rlv_HistoryAdapter.ViewHolder> {
    private Context context;
    private List<HistoryBean.DataBean.ListBean> list;

    public Rlv_HistoryAdapter(Context context, List<HistoryBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = View.inflate(context, R.layout.fragment_historylist, null);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
        ViewHolder holder = viewHolder;
        holder.history_serial_tv.setText(list.get(i).getCri()+"");
        holder.history_plate_tv2.setText(list.get(i).getCarNum());
        holder.history_operating_tv3.setText(list.get(i).getName());
        holder.history_working_tv4.setText(list.get(i).getStartTime()+"-"+list.get(i).getEndTime());
        holder.history_working_tv5.setText("1小时30分钟");
        holder.history_state_tv6.setText(list.get(i).getStatus()+"");
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView history_serial_tv;
        private TextView history_plate_tv2;
        private TextView history_operating_tv3;
        private TextView history_working_tv4;
        private TextView history_working_tv5;
        private TextView history_state_tv6;
        LinearLayout mTerminallistLinear;
        ImageView mTerminallistImg;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            history_serial_tv = itemView.findViewById(R.id.terminal_serial_tv1);
            history_plate_tv2 = itemView.findViewById(R.id.terminal_plate_tv2);
            history_operating_tv3 = itemView.findViewById(R.id.terminal_operating_tv3);
            history_working_tv4 = itemView.findViewById(R.id.terminal_working_tv4);
            history_working_tv5 = itemView.findViewById(R.id.terminal_working_tv5);
            history_state_tv6 = itemView.findViewById(R.id.terminal_state_tv6);
            mTerminallistLinear = itemView.findViewById(R.id.terminallist_linear);
            mTerminallistImg = itemView.findViewById(R.id.terminallist_img);
        }
    }
}