package com.example.wanqian.widget;


import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatDialog;

import com.example.wanqian.R;
import com.example.wanqian.utitls.ScreenUtils;
import com.example.wanqian.utitls.Utils;


/**
 * 确认
 */
public class CheckPremissionDialog extends AppCompatDialog {

    private Context context;
    private TextView tv_confirm;
    private OnConfirmClickListener mListener;
    private String text;

    public CheckPremissionDialog(Context context,String text) {
        super(context,R.style.full_screen_dialog);
        this.context=context;
        this.text=text;
        initalize();
    }
    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.dialog_confirm_check_premission, null);
        tv_confirm=view.findViewById(R.id.tv_confirm);
        setContentView(view);
        initWindow();
        initData();
    }

    private void initData() {
        if (!TextUtils.isEmpty(text)){
            tv_confirm.setText(text);
        }
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
                if (mListener!=null){
                    mListener.onConfirmClick();
                }
            }
        });
    }
    private void initWindow() {
        Window window = getWindow();
        WindowManager.LayoutParams p = window.getAttributes();
        p.width = ScreenUtils.dp2px(getContext(),400);
        p.height = WindowManager.LayoutParams.WRAP_CONTENT;
        //p.dimAmount = 0.5f;
        window.setGravity(Gravity.CENTER);
        window.setAttributes(p);
        setCanceledOnTouchOutside(false);
        // backgroundAlpha((Activity) context, 0.5f);

    }

    //设置添加屏幕的背景透明度
    public void backgroundAlpha(Activity context, float bgAlpha) {
        WindowManager.LayoutParams lp = context.getWindow().getAttributes();
        lp.alpha = bgAlpha;
        context.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);
        context.getWindow().setAttributes(lp);
    }

    public interface OnConfirmClickListener {
        void onConfirmClick();
    }

    public void setOnConfirmClickListener(OnConfirmClickListener listener) {
        mListener = listener;
    }

}