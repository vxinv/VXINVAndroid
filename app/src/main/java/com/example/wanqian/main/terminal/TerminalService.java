package com.example.wanqian.main.terminal;

import com.example.wanqian.bean.AllMessageDeviceBean;
import com.example.wanqian.main.history.bean.HistoryBean;
import com.example.wanqian.main.terminal.bean.TerminalBean;
import com.example.wanqian.newbase.mvp.BaseModel;

import java.util.HashMap;
import java.util.List;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HeaderMap;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

public interface TerminalService {
    //终端监控
    //   http://112.126.100.73:9092/hyjj_back/TerminalMonitoring
    //        {
    //    "pageNo":2,
    //    "pageSize":1
    //      }

    @POST("TerminalMonitoring")
    Observable<TerminalBean> postFiled(@Body RequestBody requestBody, @HeaderMap HashMap<String, String> map);

    /**
     * 历史作业
     * @param map
     * @return
     */
    @GET("appHistoryWork")
    Observable<HistoryBean> getHistoryWork(@QueryMap HashMap<String, String> map);

    /**
     * 请求设备详细信息
     * @return
     */
    @POST("selectOneNewDataByDevId")
    Observable<BaseModel<List<AllMessageDeviceBean>>> getNewInfo(@Body RequestBody requestBody);
}
