package com.example.wanqian.base;

/*
 * created by zhangyu on 2019-11-1
 **/
public interface IBasePresenter<T extends IBaseView> {

    void attachView(T view);
    void detachView(T view);

}
