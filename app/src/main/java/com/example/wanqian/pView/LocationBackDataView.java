package com.example.wanqian.pView;

import com.example.wanqian.bean.AllMessageDeviceBean;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseView;

import java.util.List;

public interface LocationBackDataView extends BaseView {

    void getBaseData(BaseModel baseModel);
    void getErrorMsg(String msg);
    void getSingleData(List<AllMessageDeviceBean> allMessageDeviceBeans);
}
