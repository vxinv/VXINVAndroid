package com.example.wanqian.main.personage.car;

import com.example.wanqian.main.personage.car.bean.CarBean;
import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface CarService {
    //车辆信息
    //   http://112.126.100.73:9092/hyjj_back/CarBind
//    {
//        "carNumber":"京CAA123",
//         "phoneImei":"867597041363059"
//    }

    @POST("CarBind")
    Observable<CarBean> carpostFiled(@Body RequestBody requestBody);

}
