package com.example.wanqian.pView;

import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseView;

public interface UpdateCarView extends BaseView {

    void getBaseData(BaseModel baseModel);
    void getErrorMsg(String msg);

    void getCarMs(BaseModel baseModel);
}
