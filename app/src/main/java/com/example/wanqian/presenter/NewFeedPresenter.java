package com.example.wanqian.presenter;

import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.newbase.mvp.BaseObserver;
import com.example.wanqian.newbase.mvp.BasePresenter;
import com.example.wanqian.pView.BaseBckDataView;
import com.example.wanqian.utitls.SPUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class NewFeedPresenter extends BasePresenter<BaseBckDataView> {

    public NewFeedPresenter(BaseBckDataView baseView) {
        super(baseView);
    }

    /**
     * 地图展示接口
     */
    public void UserFeedBack(String description, String linkMethods, List<File> files) {

        MultipartBody.Builder builder = new MultipartBody.Builder();
        if (files.size() == 0) {
            builder.addFormDataPart("pics", "");
        } else {
            for (int i = 0; i < files.size(); i++) {
                RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), files.get(i));
                MultipartBody.Part part = MultipartBody.Part.createFormData("pics", files.get(i).getName(), requestBody);
                builder.addPart(part);
            }
        }
        builder.addFormDataPart("description", description);
        builder.addFormDataPart("linkMethods", linkMethods);
        MultipartBody build = builder.build();
        addDisposable(apiServer.UserFeedBack(build), new BaseObserver(baseView) {

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
