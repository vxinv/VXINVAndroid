package com.example.wanqian.base;

/*
 * created by zhangyu on 2019-11-1
 **/
public interface IBaseViewWithCache<T extends IBasePresenter,D > extends IBaseView<T> {
    // 缓存数数据回调接口。
    void onCacheReceived(D data, @IBaseCallBackWithCache.CallBackCacheType int type);

}
