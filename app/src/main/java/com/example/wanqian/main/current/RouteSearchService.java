package com.example.wanqian.main.current;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.example.wanqian.main.current.bean.StartClearBean;
import com.example.wanqian.main.current.inter.CurrentService;
import com.example.wanqian.utitls.Myapi;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * service耗时给服务器传递当前汽车位置
 */
public class RouteSearchService extends Service {
    private static final String TAG = "RouteSearchService";
    private String imei;
    private double lon;
    private double lat;

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        imei = intent.getStringExtra("imei");
        lon = intent.getDoubleExtra("lon", 0.0);
        lat = intent.getDoubleExtra("lat", 0.0);
        new Thread(new Runnable() {
            @Override
            public void run() {
                // 耗时操作
                Retrofit retrofit = new Retrofit.Builder()
                        .baseUrl(Myapi.url)
                        .addConverterFactory(GsonConverterFactory.create())
                        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                        .build();
                CurrentService currentService = retrofit.create(CurrentService.class);
                Observable<StartClearBean> startClearBeanObservable = currentService.current_UpdateCurrLocation(imei, lat, lon);
                startClearBeanObservable.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<StartClearBean>() {
                            @Override
                            public void onSubscribe(Disposable d) {
                                Log.i(TAG, d.toString() + ",onSubscribe");
                            }

                            @Override
                            public void onNext(StartClearBean startClearBean) {
                                Log.i(TAG, startClearBean.getCode() + ",onNext");
                            }

                            @Override
                            public void onError(Throwable e) {
                                Log.i(TAG, e.getMessage() + ",onError");
                            }

                            @Override
                            public void onComplete() {
                                Log.i(TAG, "onComplete");
                            }
                        });
            }
        }).start();
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }
}
