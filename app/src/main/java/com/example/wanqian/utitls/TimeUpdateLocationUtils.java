package com.example.wanqian.utitls;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;

import com.example.wanqian.BaseApp;

import java.util.concurrent.TimeUnit;

public class TimeUpdateLocationUtils {
    private Context mContext;
    public static boolean IsOn = false;
    private Handler handler = new Handler(Looper.getMainLooper()); // 全局变量
    private HandlerTimer handlerTimer = new HandlerTimer(handler);
    private HandlerTimer.TimerTask timerTask;
    private OnchangeDataClickListener mListener;

    private TimeUpdateLocationUtils(Context context) {
        mContext = context;
    }

    public static TimeUpdateLocationUtils getInstance() {
        return TimeUpdateLocationUtils.AdManagerHolder.INSTANCE;
    }

    public void startPolling() {
        IsOn = true;
        timerTask = handlerTimer.schedule(new Runnable() {
            @Override
            public void run() {
                if (mListener != null) {
                    mListener.GetLocation();
                }
            }
        }, 0, 20, TimeUnit.SECONDS);

    }

    /**
     * 停止轮询
     */
    public void stopPolling() {
        IsOn = false;
        if (handlerTimer != null && timerTask != null) {
            handlerTimer.cancel(timerTask);
        }
    }

    public void destroy() {
        stopPolling();
    }

    public void setOnTimeClickListener(OnchangeDataClickListener listener) {
        mListener = listener;
    }

    public interface OnchangeDataClickListener {
        void GetLocation();
    }

    private static class AdManagerHolder {
        private static final TimeUpdateLocationUtils INSTANCE = new TimeUpdateLocationUtils(BaseApp.mApplicationContext);
    }
}
