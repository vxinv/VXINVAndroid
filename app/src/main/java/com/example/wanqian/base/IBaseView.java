package com.example.wanqian.base;

import android.app.Activity;

/*
 * created by zhangyu on 2019-11-1
 **/
public interface IBaseView<T extends IBasePresenter> {
    void setPresenter(T presenter);
    Activity getActivityObject();

}
