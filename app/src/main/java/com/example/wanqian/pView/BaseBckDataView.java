package com.example.wanqian.pView;


import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseView;

public interface BaseBckDataView extends BaseView {

    void getBaseData(BaseModel baseModel);
    void getErrorMsg(String msg);
}
