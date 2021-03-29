package com.example.wanqian.widget;

import android.animation.ObjectAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.CountDownTimer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.wanqian.R;
import com.example.wanqian.main.current.animation.RightMarkView;

public class MFTaskClearPopWindow extends PopupWindow implements View.OnClickListener  {

    private Context context;
    private Button mBtnYes;
    private DoWhatCallBack callBack;
    private View bg_view;
    private RelativeLayout parent;
    private ImageView animal_png;
    private RightMarkView markView;
    private TextView tx_id;
    private int id;
    private static final String TAG = "MFTaskClearPopWindow";
    private CountDownTimer timer;

    public MFTaskClearPopWindow(Context context, int id) {
        super(context);
        this.context = context;
        this.id = id;
        initalize();
    }

    private void initalize() {
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(R.layout.popwindow, null);
        setContentView(view);
        initWindow(view);
    }

    private void initWindow(View view) {
        tx_id = view.findViewById(R.id.tx_id);
        tx_id.setText("垃圾桶ID为" + id);
        markView = view.findViewById(R.id.right);
        animal_png = view.findViewById(R.id.animal_png);
        parent = view.findViewById(R.id.parent);
        bg_view = view.findViewById(R.id.view);
        mBtnYes = view.findViewById(R.id.end_collect);
        mBtnYes.setOnClickListener(this);
        mBtnYes.setClickable(false);
        markView.setClickable(false);
        //bg_view.setOnClickListener(this);
        setWidth(WindowManager.LayoutParams.MATCH_PARENT);
        setHeight(WindowManager.LayoutParams.MATCH_PARENT);
        setFocusable(false);
        setOutsideTouchable(false);
        update();
        setOnDismissListener(new OnDismissListener() {
            @Override
            public void onDismiss() {
                backgroundAlpha((Activity) context, 1f);
            }
        });
        startCountDownTime(21);
        //下拉动画
        ObjectAnimator.ofFloat(parent, "translationY", -800f, 0f).setDuration(500).start();
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
            case R.id.end_collect:
                animal_png.setVisibility(View.GONE);
                markView.setColor(Color.parseColor("#FF4081"), Color.YELLOW);
                mBtnYes.setEnabled(false);
                markView.startAnimator();
                timer.cancel();
                if (callBack!= null){
                    callBack.startTask();
                }
                break;
            case R.id.view:

                break;

        }
    }

    private void startCountDownTime(long time) {
        /**
         * 最简单的倒计时类，实现了官方的CountDownTimer类（没有特殊要求的话可以使用）
         * 即使退出activity，倒计时还能进行，因为是创建了后台的线程。
         * 有onTick，onFinsh、cancel和start方法
         */
        timer = new CountDownTimer(time * 1000, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                //每隔countDownInterval秒会回调一次onTick()方法
                // Log.d(TAG, "onTick  " + millisUntilFinished / 1000);
                mBtnYes.setText(context.getString(R.string.countdown) + " " + millisUntilFinished / 1000);
            }

            @Override
            public void onFinish() {
                mBtnYes.setText("清收结束");
                mBtnYes.setClickable(true);
            }
        };
        timer.start();// 开始计时
        //timer.cancel(); // 取消
    }

    public interface DoWhatCallBack {
        void startTask();
    }

    @Override
    public void dismiss() {
        timer.cancel();
        super.dismiss();
    }

    public void setDoWhatCallBack(DoWhatCallBack callBack) {
        this.callBack = callBack;
    }
}
