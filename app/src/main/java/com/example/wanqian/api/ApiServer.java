package com.example.wanqian.api;

import com.example.wanqian.bean.AllMessageDeviceBean;
import com.example.wanqian.bean.BindCarMsBean;
import com.example.wanqian.bean.CarMessageBaseBean;
import com.example.wanqian.bean.NewLocationBean;
import com.example.wanqian.bean.SplashBean;
import com.example.wanqian.bean.TaskListBean;
import com.example.wanqian.bean.TerminalNewBean;
import com.example.wanqian.main.personage.login.bean.LoginBean;
import com.example.wanqian.newbase.mvp.BaseModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
/**
 *
 *描述：api接口
 *
 */
public interface ApiServer {

    @Headers({"Content-Type:application/json"})//需要添加头
    @GET("getJob")
    Observable<BaseModel<List<TaskListBean>>> current_postFiled(@Query("phoneImei") String imei);

    /**
     * 开始清收任务请求
     */
    @Headers({"Content-Type:application/json"})//需要添加头
    @GET("startClear")
    Observable<BaseModel> current_StartClear(@Query("phoneImei") String imei, @Query("account") String account);

    /**
     * 结束清收任务请求
     */
    @Headers({"Content-Type:application/json"})//需要添加头
    @POST("endClear")
    Observable<BaseModel> current_EndClear(@Body RequestBody requestBody);

    /**
     * 每次清收的数据上报
     *
     * @param imei
     * @return
     */
    @Headers({"Content-Type:application/json"})//需要添加头
    @GET("clearUp")
    Observable<BaseModel> current_ClearUp(@Query("phoneImei") String imei, @Query("groupId") String groupId, @Query("type") int type, @Query("cid") int cid, @Query("lat") double lat, @Query("lon") double lon);

    /**
     * 每次清收的数据上报
     *
     * @param imei
     * @return
     */
    @Headers({"Content-Type:application/json"})//需要添加头
    @GET("updateCurrLocation")
    Observable<BaseModel> current_UpdateCurrLocation(@Query("imei") String imei, @Query("lat") double lat, @Query("lon") double lon);
    /**
     * 历史信息
     * @param map
     * @return
     */
    @GET("appHistoryWork")
    Observable<BaseModel<CarMessageBaseBean>> getHistoryWork(@QueryMap HashMap<String, String> map);
    /**
     * 聚合点接口
     * @return
     */
    @GET("AppMapShow")
    Observable<BaseModel<List<List<NewLocationBean>>>> locationFiled(@Header("id") String id);
    /**
     * 聚合点接口
     * @return
     */
    @POST("selectOneNewDataByDevId")
    Observable<BaseModel<List<AllMessageDeviceBean>>> getNewInfo(@Body RequestBody requestBody);

    @POST("uploadFeedBack")
    Observable<BaseModel> UserFeedBack(@Body MultipartBody multipartBody);

    @POST("AppChangePass")
    Observable<BaseModel> updatePassword(@Body RequestBody requestBody);

    @POST("CarBind")
    Observable<BaseModel> carpostFiled(@Body RequestBody requestBody);
    /**
     * 闪屏页面处理
     */
    @GET("getFlashPage")
    Observable<BaseModel<SplashBean>> getSplash(@Header("id") String id);

    @POST("TerminalMonitoring")
    Observable<BaseModel<TerminalNewBean>> getTerminalList(@Body RequestBody requestBody, @HeaderMap HashMap<String, String> map);

    @POST("AppUserLogin")
    Observable<BaseModel<LoginBean>> UserLogin(@Body  RequestBody requestBody);

    @GET("getCarNumByImei")
    Observable<BaseModel<BindCarMsBean>> getBindCar(@QueryMap HashMap<String, String> map);

    @POST("checkReset")
    Observable<BaseModel<LoginBean>> checkResetRoute(@Body  RequestBody requestBody);
}
