package com.example.wanqian.main.personage.xiugai;

import com.example.wanqian.main.personage.xiugai.bean.XiugaiBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface XiugaiService {
    //     修改密码
    //        http://112.126.100.73:9092/hyjj_back/AppChangePass
    //      {
    //    "account":"zhangyu",
    //    "oldPass":"888888"
    //    "newPass":""
    //      }

    @POST("AppChangePass")
    Observable<XiugaiBean> postFiled(@Body RequestBody requestBody);

}
