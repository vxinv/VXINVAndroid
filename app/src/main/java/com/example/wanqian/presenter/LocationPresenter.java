package com.example.wanqian.presenter;

import com.example.wanqian.bean.AllMessageDeviceBean;
import com.example.wanqian.bean.testBean;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseObserver;
import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.pView.LocationBackDataView;
import com.example.wanqian.utitls.AppLog;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LocationPresenter extends BasePresenter<LocationBackDataView> {

    public LocationPresenter(LocationBackDataView baseView) {
        super(baseView);
    }

    public void getMapList(String userid) {
        addDisposable(apiServer.locationFiled(userid), new BaseObserver(baseView) {

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

    /**
     * 請求詳細信息接口
     */
    public void getDetailData(List<String> devIds ) {

        testBean testBean=new testBean();
        testBean.setData(devIds);
        String jsonString = new Gson().toJson(devIds);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);
        addDisposable(apiServer.getNewInfo(requestBody), new BaseObserver(baseView) {

            @Override
            public void onSuccess(BaseModel o) {
                baseView.getSingleData((List<AllMessageDeviceBean>) o.getData());
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
