package com.example.wanqian.base;

/*
 * created by zhangyu on 2019-11-1
 **/
public interface IBaseCallBack<T> {

    void onSuccess(T data);
    void onFail(String msg);
}
