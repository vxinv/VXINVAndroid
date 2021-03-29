package com.example.wanqian.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.drawable.ColorDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.example.wanqian.R;

public class MFTaskStartPopWindow extends PopupWindow implements View.OnClickListener  {

    private Context context;
    private TextView mBtnYes;
    private DoWhatCallBack callBack;
    private View bg_view;

    public MFTaskStartPopWindow(Context context) {
        super(context);
        this.context = context;
        initalize();
    }

    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.current_pop, null);
        setContentView(view);
        initWindow(view);
    }

    private void initWindow(View view) {
        bg_view=view.findViewById(R.id.view);
        mBtnYes = view.findViewById(R.id.btn_yes);
        mBtnYes.setOnClickListener(this);
        bg_view.setOnClickListener(this);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setFocusable(true);
        setOutsideTouchable(true);
        update();
        //实例化一个ColorDrawable颜色为半透明
//        ColorDrawable dw = new ColorDrawable(0x00000000);
//        //设置SelectPicPopupWindow弹出窗体的背景
//        setBackgroundDrawable(dw);
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha((Activity) context, 1f);
            }
        });
    }
    //设置添加屏幕的背景透明度
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_yes:
                if (callBack!= null){
                    callBack.startTask();
                }
                break;
            case R.id.view:
                dismiss();
                break;

        }
    }

    public interface  DoWhatCallBack{
        void startTask();
    }

    public void setDoWhatCallBack(DoWhatCallBack callBack){
        this.callBack = callBack;
    }
}
