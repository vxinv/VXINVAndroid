package com.example.wanqian.presenter;

import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseObserver;
import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.utitls.SPUtils;
import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.RequestBody;

public class TerminalPresenter extends BasePresenter<BaseBckDataView> {

    public TerminalPresenter(BaseBckDataView baseView) {
        super(baseView);
    }


    public void getTerminalList(int page) {
        HashMap<String, String> map = new HashMap<>();
        map.put("id", SPUtils.getInt("id") + "");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("pageNo", page);
            jsonObject.put("pageSize", 20);
            jsonObject.put("condition",0);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        addDisposable(apiServer.getTerminalList(requestBody,map), new BaseObserver(baseView) {

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

    public void SelectTerminal(List<String> devIds) {
        String jsonString = new Gson().toJson(devIds);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonString);
        addDisposable(apiServer.getNewInfo(requestBody), new BaseObserver(baseView) {

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
