package com.example.wanqian.main.current.inter;

import com.example.wanqian.main.current.bean.CurrentBean;
import com.example.wanqian.main.current.bean.StartClearBean;

import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface CurrentService {
    //登录后获取当前任务
    //   http://112.126.100.73:9092/hyjj_back/endClear
    //        {
    //    phoneImei	是	string	平板imei
    //      }

    @Headers({"Content-Type:application/json"})//需要添加头
    @GET("getJob")
    Observable<CurrentBean> current_postFiled(@Query("phoneImei") String imei);


    /**
     * 开始清收任务请求
     *
     * @param imei
     * @return
     */
    @Headers({"Content-Type:application/json"})//需要添加头
    @GET("startClear")
    Observable<StartClearBean> current_StartClear(@Query("phoneImei") String imei);

    /**
     * 结束清收任务请求
     */
    @Headers({"Content-Type:application/json"})//需要添加头
    @POST("endClear")
    Observable<StartClearBean> current_EndClear(@Body RequestBody requestBody);

    /**
     * 每次清收的数据上报
     *
     * @param imei
     * @return
     */
    @Headers({"Content-Type:application/json"})//需要添加头
    @GET("clearUp")
    Observable<StartClearBean> current_ClearUp(@Query("phoneImei") String imei, @Query("groupId") String groupId, @Query("cid") int cid, @Query("lat") double lat, @Query("lon") double lon);

    /**
     * 每次清收的数据上报
     *
     * @param imei
     * @return
     */
    @Headers({"Content-Type:application/json"})//需要添加头
    @GET("updateCurrLocation")
    Observable<StartClearBean> current_UpdateCurrLocation(@Query("imei") String imei, @Query("lat") double lat, @Query("lon") double lon);
}
