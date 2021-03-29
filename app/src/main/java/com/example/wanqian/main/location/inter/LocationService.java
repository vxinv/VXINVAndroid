package com.example.wanqian.main.location.inter;

import com.example.wanqian.main.location.bean.LocationBean;

import java.util.Map;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;

public interface LocationService {
    //设备定位
    //   http://112.126.100.73:9092/hyjj_back/AppMapShow

    @GET("AppMapShow")
    Observable<LocationBean> locationpostFiled(@Header("id") String id);

}
