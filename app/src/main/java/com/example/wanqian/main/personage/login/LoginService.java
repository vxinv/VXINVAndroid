package com.example.wanqian.main.personage.login;

import com.example.wanqian.main.personage.login.bean.LoginBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface LoginService {
    //        登录页面
    //        http://112.126.100.73:9092/hyjj_back/AppUserLogin

    //     {
    //    "account":"zhangyu",
    //    "password":"888888"
    //      }

    @POST("AppUserLogin")
    Observable<LoginBean> postFiled(@Body  RequestBody requestBody);
}
