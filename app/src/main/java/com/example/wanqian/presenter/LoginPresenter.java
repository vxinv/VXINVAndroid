package com.example.wanqian.presenter;

import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseObserver;
import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.pView.BaseBckDataView;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class LoginPresenter extends BasePresenter<BaseBckDataView> {

    public LoginPresenter(BaseBckDataView baseView) {
        super(baseView);
    }

    public void Login(String Username,String Password) {

        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", Username);
            jsonObject.put("password", Password);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        addDisposable(apiServer.UserLogin(requestBody), new BaseObserver(baseView) {

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
