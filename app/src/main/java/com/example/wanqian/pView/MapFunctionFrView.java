package com.example.wanqian.pView;

import android.content.Context;

import com.example.wanqian.bean.TaskListBean;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseView;

import java.util.List;

public interface MapFunctionFrView  extends BaseView {

    void getTaskListSuccess(List<TaskListBean> listBaseModel);
    void getTaskListFail(String msg);

    void startTaskSuccess(BaseModel baseModel);
    void startTaskFail(String msg);

    void endTaskSuccess(BaseModel baseModel);
    void endTaskFail(String msg);

    void clearComplete(int index);

    void clearTaskFail(String msg);

    /**
     * 处理服务结束结束&&车辆未开启
     */
    void processServiceEndOrCarNotTurnOn(String code);

    /**
     * 获取IMEI
     */
    String getImei();

    /**
     * 获取activuty
     */
    Context getContext();

    /**
     * 播放声音
     */
    void speak(String msg);
}
