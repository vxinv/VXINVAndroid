package com.example.wanqian.presenter;

import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseObserver;
import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.utitls.SPUtils;

public class SplashPresenter extends BasePresenter<BaseBckDataView> {
    public SplashPresenter(BaseBckDataView baseView) {
        super(baseView);
    }

    public void getSplash() {
        addDisposable(apiServer.getSplash(SPUtils.getInt("id")+""), new BaseObserver(baseView) {

            @Override
            public void onSuccess(BaseModel o) {
                baseView.getBaseData(o);
            }
            @Override
            public void onError(String msg) {
                baseView.getErrorMsg(msg);
            }

            @Override
            public void onCode(String code) {

            }
        });
    }

}
