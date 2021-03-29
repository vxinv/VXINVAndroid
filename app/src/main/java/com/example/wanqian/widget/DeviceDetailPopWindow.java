package com.example.wanqian.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.PopupWindow;
import android.widget.TextView;
import com.example.wanqian.R;
import com.example.wanqian.main.terminal.bean.TerminalBean;

public class DeviceDetailPopWindow extends PopupWindow implements View.OnClickListener {

    private Context context;
    private TextView rb_id,rb_sn,rb_online_state,rb_report,rb_r,rb_time;
    private TerminalBean.DataBean.ListBean  deviceAllMessageBean;

    public DeviceDetailPopWindow(Context context, TerminalBean.DataBean.ListBean bean) {
        super(context);
        this.context = context;
        this.deviceAllMessageBean=bean;
        initalize();
    }

    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.pop_devicedetail, null);
        rb_id=view.findViewById(R.id.rb_id);
        rb_sn=view.findViewById(R.id.rb_sn);
        rb_online_state=view.findViewById(R.id.rb_online_state);
        rb_report=view.findViewById(R.id.rb_report);
        rb_r=view.findViewById(R.id.rb_r);
        rb_time=view.findViewById(R.id.rb_time);
        setContentView(view);
        initWindow();
        initView();
    }

    private void initView() {
        if (deviceAllMessageBean!=null){
            rb_sn.setText(deviceAllMessageBean.getDeviceId()==null?"- -":deviceAllMessageBean.getDeviceId());
            rb_id.setText(deviceAllMessageBean.getGarbageId()+"");
            if (deviceAllMessageBean.getOnlineStatus()==null){
                rb_online_state.setText("- -");
            }else{
                if (deviceAllMessageBean.getOnlineStatus().equals("1")){
                    rb_online_state.setText("在线");
                }else if (deviceAllMessageBean.getOnlineStatus().equals("2")){
                    rb_online_state.setText("异常");
                }else {
                    rb_online_state.setText("离线");
                }
            }

            rb_r.setText(deviceAllMessageBean.getRubbishL()==null?"- -":deviceAllMessageBean.getRubbishL()+"mm");
            rb_time.setText(deviceAllMessageBean.getEventTime()+"");
            if (deviceAllMessageBean.getReportType()==null){
                rb_report.setText("- -");
            }else{
                String[] listData=deviceAllMessageBean.getReportType().split(",");
//                    '0': '实时上报',
//                            '1': '垃圾溢满',
//                            '2': '垃圾清理',
//                            '4': '超温上报',
//                            '8': '倾斜上报',
//                            '16': '电池欠压',
//                            '32': '部件故障',
//                            '64': '多报数据',
//                            '128': '心跳信号'
                String content="";
                for (int i=0;i<listData.length;i++){
                    if (listData[i].equals("0")){
                        content=content+"实时上报,";
                    }else if (listData[i].equals("1")){
                        content=content+"垃圾溢满,";
                    }else if (listData[i].equals("2")){
                        content=content+"垃圾清理,";
                    }else if (listData[i].equals("4")){
                        content=content+"超温上报,";
                    }else if (listData[i].equals("8")){
                        content=content+"倾斜上报,";
                    }else if (listData[i].equals("16")){
                        content=content+"电池欠压,";
                    }else if (listData[i].equals("32")){
                        content=content+"部件故障,";
                    }else if (listData[i].equals("64")){
                        content=content+"多报数据,";
                    }else{
                        content=content+"心跳信号,";
                    }
                }
                if (content.endsWith(",")){
                    String real=content.substring(0,content.length()-1);
                    rb_report.setText(real);
                }else{
                    rb_report.setText(content);
                }

            }
        }
    }

    private void initWindow() {
        DisplayMetrics d = context.getResources().getDisplayMetrics();
        this.setWidth(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setHeight(WindowManager.LayoutParams.WRAP_CONTENT);
        this.setFocusable(true);
        //this.setAnimationStyle(R.style.pop_add);
        this.setOutsideTouchable(true);
        this.update();
        //实例化一个ColorDrawable颜色为半透明
        ColorDrawable dw = new ColorDrawable(0x00000000);
        //设置SelectPicPopupWindow弹出窗体的背景
        this.setBackgroundDrawable(dw);
        this.setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha((Activity) context, 1f);
            }
        });
        backgroundAlpha((Activity) context,0.5f);
    }

    //设置添加屏幕的背景透明度
//    public void backgroundAlpha(Activity context, float bgAlpha) {
//        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
//        lp.alpha = bgAlpha;
//        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
//        context.getWindow().setAttributes(lp);
//    }

    // 设置屏幕透明度
    public void backgroundAlpha(Activity context,float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha; // 0.0~1.0
        context.getWindow().setAttributes(lp);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            default:
                break;
        }
    }
}
