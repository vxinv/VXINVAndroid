package com.example.wanqian.presenter;

import android.util.Log;

import com.example.wanqian.main.personage.xiugai.XiugaiService;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseObserver;
import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.utitls.Myapi;
import com.example.wanqian.utitls.SPUtils;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdatePsPresenter  extends BasePresenter<BaseBckDataView> {

    public UpdatePsPresenter(BaseBckDataView baseView) {
        super(baseView);
    }


    public void UpdatePs(String xinString) {

        //之前的账号

//        //之前的密码
//        String psw = SPUtils.getValue("psw");
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Myapi.url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        XiugaiService service = retrofit.create(XiugaiService.class);

        String yhm = SPUtils.getValue("yhm");
        String psw = SPUtils.getValue("psw");
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("account", yhm);
            jsonObject.put("oldPass", psw);
            jsonObject.put("newPass", xinString);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());
        addDisposable(apiServer.updatePassword(requestBody), new BaseObserver(baseView) {

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
