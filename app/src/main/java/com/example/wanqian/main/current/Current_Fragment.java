package com.example.wanqian.main.current;

import android.Manifest;
import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.location.Location;
import android.location.LocationManager;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.util.Pair;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;

import com.amap.api.location.AMapLocation;
import com.amap.api.location.AMapLocationClient;
import com.amap.api.location.AMapLocationClientOption;
import com.amap.api.location.AMapLocationListener;
import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.LocationSource;
import com.amap.api.maps.MapView;
import com.amap.api.maps.UiSettings;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.MyLocationStyle;
import com.amap.api.maps.model.Polyline;
import com.amap.api.maps.model.PolylineOptions;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import com.example.wanqian.R;
import com.example.wanqian.main.current.Interfaces.CanPullUp;
import com.example.wanqian.main.current.animation.RightMarkView;
import com.example.wanqian.main.current.bean.CurrentBean;
import com.example.wanqian.main.current.bean.StartClearBean;
import com.example.wanqian.main.current.inter.CurrentService;
import com.example.wanqian.main.current.map.LinePoint;
import com.example.wanqian.utitls.Myapi;
import com.example.wanqian.utitls.SPUtils;
import com.google.gson.Gson;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * 当前作业
 */
public class Current_Fragment extends Fragment implements EasyPermissions.PermissionCallbacks, AMapLocationListener, LocationSource, CanPullUp {

    private static final String TAG = "Current_Fragment";
    final int RC_CAMERA_AND_WIFI = 0x1;
    //显示地图需要的变量
    private MapView mapView;//地图控件
    private AMap aMap;//地图对象
    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient = null;
    //声明mLocationOption对象，定位参数
    public AMapLocationClientOption mLocationOption = null;
    //定位需要的声明
    private LocationSource.OnLocationChangedListener mListener = null;//定位监听器
    //标识，用于判断是否只显示一次定位信息和用户重新定位
    private boolean isFirstLoc = true;
    // 浮窗控制动画的容器
    private RelativeLayout anim_container;
    // 清收完全的btn
    private Button endCollect;
    // 悬浮框
    private RelativeLayout root;
    private List<CurrentBean.DataBean> mMData = new ArrayList<>();
    private Polyline mPolyline;
    //经纬度集合
    private List<LatLng> lngs = new ArrayList<>();
    private List<CurrentBean.DataBean> mLngs = new ArrayList<>();
    private List<LinePoint> mList = new ArrayList<>();
    private List<Double> mMin = new ArrayList<>();
    private String imei;
    private Button mBtnYes, mBtnJob;
    private boolean flag = true;
    private List<LatLng> mCarLatLng = new ArrayList<>();
    private RelativeLayout parent;
    private PopupWindow mWindow;
    private RightMarkView right;
    private List<Integer> mNotClear = new ArrayList<>();
    private SmoothMoveMarker smoothMarker;
    private double latitudeLocaTion;
    private double longitudeLocaTion;
    private TextView tx_id;
    private Polyline mPolyline1;
    private List<LatLng> mLocation = new ArrayList<>();
    private PopupWindow window;
    private View main;
    private Location location;
    private LocationManager locationManager;
    private float speed;


    public Current_Fragment() {

    }

    @SuppressLint("MissingPermission")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_current_, container, false);
        mapView = (MapView) view.findViewById(R.id.map);
        mBtnJob = (Button) view.findViewById(R.id.btn_job);
        root = (RelativeLayout) view.findViewById(R.id.amap_root_amap);
        return view;
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);
        chackPermission();
        getImei();
        initView();
        currentPopWindow();
        mBtnJob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag) {
                    doHttpCurrentDate();
                } else {
                    flag = true;
                    mBtnJob.setText("接受任务");
                    aMap.setMyLocationStyle(new MyLocationStyle().showMyLocation(true));
                    doEndClear();
                    aMap.clear(true);
                    mMData.clear();
                    smoothMarker.destroy();
                    lngs.clear();
                    mCarLatLng.clear();
                    Toast.makeText(getActivity(), "任务已结束,位置回到当前定位！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * 开始清收任务请求
     */
    private void doStartClear() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Myapi.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        CurrentService currentService = retrofit.create(CurrentService.class);
        Observable<StartClearBean> currentBeanObservable = currentService.current_StartClear(imei);
        currentBeanObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<StartClearBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onNext(StartClearBean currentBean) {
//                        Log.i(TAG, "onNext:请求成功！");
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /**
     * 结束清收任务请求
     */
    private void doEndClear() {
        mNotClear.clear();
        for (int j = 0; j < mMData.size(); j++) {
            if (mMData.get(j).getGarbageId() != -1)
                mNotClear.add(mMData.get(j).getGroupId());
        }
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Myapi.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        CurrentService currentService = retrofit.create(CurrentService.class);
        EndClearBean endClearBean = new EndClearBean();
        endClearBean.setImei(imei);
        endClearBean.setNotClear(mNotClear);
        endClearBean.setUserAccount(SPUtils.getValue("yhm"));
        String mNoClearJson = new Gson().toJson(endClearBean);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), mNoClearJson);
        Observable<StartClearBean> currentBeanObservable = currentService.current_EndClear(requestBody);
        currentBeanObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<StartClearBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onNext(StartClearBean currentBean) {
                        Toast.makeText(getActivity(), "任务已结束！" + currentBean.getCode(), Toast.LENGTH_SHORT).show();
//                        Log.i(TAG, "onNext:请求成功！");

                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /**
     * 弹出popupwindow弹框
     */
    private void currentPopWindow() {
        //设置布局
        View view = View.inflate(getActivity(), R.layout.current_pop, null);
        main = View.inflate(getActivity(), R.layout.activity_main, null);
        //找控件
        mBtnYes = view.findViewById(R.id.btn_yes);
        //新建popupwindow
        window = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setFocusable(true);//焦点
        //弹框后面控件不可点击
        window.setOutsideTouchable(false);
        //点击弹框外不消失
        window.setFocusable(false);
        mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smoothMarker = new SmoothMoveMarker(aMap);
                doStartClear();
                aMap.setMyLocationStyle(new MyLocationStyle().showMyLocation(false));
                mBtnJob.setText("结束任务");
                flag = false;
                initData();
                window.dismiss();
            }
        });
        window.setBackgroundDrawable(new BitmapDrawable());//透明背景
    }

    /**
     * 获取当前用户任务
     */
    private void doHttpCurrentDate() {
        mMData.clear();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Myapi.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        CurrentService currentService = retrofit.create(CurrentService.class);
        Observable<CurrentBean> currentBeanObservable = currentService.current_postFiled(imei);
        currentBeanObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<CurrentBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onNext(CurrentBean currentBean) {
//                        Log.i(TAG, "onNext:请求成功！");
                        if (currentBean.getData() != null) {
                            mMData = currentBean.getData();
                            for (int i = 0; i < mMData.size(); i++) {
                                if (mMData.get(i).getGarbageId() != -1) {
                                    mLngs.add(mMData.get(i));
                                }
//                                Log.i(TAG, mMData.get(i).getLatitude() + "===" + mMData.get(i).getLongitude() + "===" + mMData.get(i).getGroupId());
                            }
                            window.showAtLocation(main, Gravity.CENTER, 0, 0);//显示的位置
                        } else {
                            Toast.makeText(getActivity(), "当前没有任务哦，请稍后再过来试试吧！", Toast.LENGTH_SHORT).show();
                        }
                        if (flag) {
                            mBtnJob.setText("接受任务");
                        } else {
                            mBtnJob.setText("结束任务");
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
//                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /**
     * 点平滑移动
     * <p>
     * // * @param points
     * // * @param angle
     */
    //List<LatLng> points, double angle, double shortDistance
    public void setCarMove() {
        if (smoothMarker != null) {
            smoothMarker.removeMarker();
        }
        // 设置滑动的图标
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.icon_che_two));
        LatLng drivePoint = lngs.get(0);
        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(lngs, drivePoint);
        lngs.set(pair.first, drivePoint);
        List<LatLng> subList = lngs.subList(pair.first, lngs.size());
        //设置平滑移动的经纬度集合
        smoothMarker.setPoints(subList);
    }

    /**
     * 获取IMEI号
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void getImei() {
        TelephonyManager tm = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
        if (getActivity().checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return;
        }
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            imei = tm.getImei();
        }
    }

    /**
     * 显示marker
     */
    private void initData() {
        lngs.clear();
        if (mMData != null) {
            for (int i = 0; i < mMData.size(); i++) {
                lngs.add(new LatLng(mMData.get(i).getLatitude(), mMData.get(i).getLongitude()));
                mList.add(new LinePoint(mMData.get(i).getLatitude(), mMData.get(i).getLongitude()));
                if (mMData.get(i).getGarbageId() != -1)
                    aMap.addMarker(customMarker(mMData.get(i).getGarbageId()).position(lngs.get(i)).snippet("第" + i + "个溢满的垃圾箱" + lngs.get(i).longitude + "===" + lngs.get(i).latitude + ",garbageId为：" + mMData.get(i).getGarbageId()));
            }
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            //把每次线路的第一个点设在屏幕中间
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(lngs.get(0)));
            aMap.setOnMarkerClickListener(markerClickListener);
            PolylineOptions polylineOptions = new PolylineOptions()
                    .addAll(lngs)
                    .useGradient(true)
                    .width(25)
                    .setCustomTexture(BitmapDescriptorFactory.fromResource(R.mipmap.custtexture));
            mPolyline = aMap.addPolyline(polylineOptions);
            setCarMove();
        }
    }

    /**
     * 模拟线路导航
     */
    public void gpsNavigateEvent() {
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(mLocation)
                .useGradient(true)
                .width(25)
                .color(Color.argb(250, 150, 150, 150));
        if (null != mPolyline1) {
            mPolyline1.remove();
        }
        mPolyline1 = aMap.addPolyline(polylineOptions);
    }

    //定时每过30s向服务器发送当前位置
    private int mTime = 30;
    private Double[] mLine;

    /**
     * 判断两个经纬度之间的角度
     *
     * @param lon1
     * @param lat1
     * @param lon2
     * @param lat2
     * @return
     */
    public static double getAngle(double lon1, double lat1, double lon2, double lat2) {
        double fLat = Math.PI * (lat1) / 180.0;
        double fLng = Math.PI * (lon1) / 180.0;
        double tLat = Math.PI * (lat2) / 180.0;
        double tLng = Math.PI * (lon2) / 180.0;

        double degree = (Math.atan2(Math.sin(tLng - fLng) * Math.cos(tLat), Math.cos(fLat) * Math.sin(tLat) - Math.sin(fLat) * Math.cos(tLat) * Math.cos(tLng - fLng))) * 180.0 / Math.PI;
        if (degree >= 0) {
            return degree;
        } else {
            return 360 + degree;
        }
    }

    private int mAnglePosition = 1;

    /**
     * 判断某一个点是否在一条线段上
     *
     * @param mLocationLat 当前位置点的x轴（纬度）
     * @param mLocationLon 当前位置的y轴（精度）
     */
    public void WhetherOffset(double mLocationLat, double mLocationLon) {
        mLine = new Double[mList.size()];
        mMin.clear();
        SpatialRelationUtil spatialRelationUtil = new SpatialRelationUtil();
        Pair<Integer, LatLng> integerLatLngPair = SpatialRelationUtil.calShortestDistancePoint(lngs, new LatLng(mLocationLat, mLocationLon));
        Double shortDistanceNear = getShortDistance(integerLatLngPair.second.longitude, integerLatLngPair.second.latitude, mLocationLon, mLocationLat);
        Double shortDistance = getShortDistance(mList.get(mAnglePosition).getLatitude(), mList.get(mAnglePosition).getLongitude(), integerLatLngPair.second.longitude, integerLatLngPair.second.latitude);
        Double shortDistance11 = getShortDistance(mList.get(0).getLatitude(), mList.get(0).getLongitude(), mList.get(mList.size() - 1).getLatitude(), mList.get(mList.size() - 1).getLongitude());
        if (shortDistanceNear <= 20) {
            mCarLatLng.add(new LatLng(integerLatLngPair.second.latitude, integerLatLngPair.second.longitude));
            mCarLatLng.add(new LatLng(mList.get(mAnglePosition).getLongitude(), mList.get(mAnglePosition).getLatitude()));
            //setCarMove(mCarLatLng, angle, shortDistance);
            if (shortDistance <= 10) {
                mLocation.add(new LatLng(mList.get(mAnglePosition).getLatitude(), mList.get(mAnglePosition).getLongitude()));
                mLocation.add(new LatLng(integerLatLngPair.second.latitude, integerLatLngPair.second.longitude));
                mAnglePosition++;
            } else {
                mLocation.add(new LatLng(integerLatLngPair.second.latitude, integerLatLngPair.second.longitude));
            }
            //gpsNavigateEvent();
            if (a == 5) {
                //Toast.makeText(getActivity(), "路线行驶正确！", Toast.LENGTH_SHORT).show();
                a = 0;
            }
            a++;
        } else {
            mCarLatLng.add(new LatLng(mLocationLat, mLocationLon));
            mCarLatLng.add(new LatLng(mLocationLat, mLocationLon));
            //setCarMove(mCarLatLng, angle, 0);
            //语音提示
            if (a == 5) {
                playVideo(getActivity(), R.raw.tip);
                a = 0;
                Toast.makeText(getActivity(), "路线出现偏移！", Toast.LENGTH_SHORT).show();
            }
            a++;
        }
        if (speed == 0.0) {
            smoothMarker.stopMove();
        } else {
            smoothMarker.startSmoothMove();
            double time = shortDistance11 / speed;
            smoothMarker.setTotalDuration(100);
            Toast.makeText(getActivity(), time + "===" + speed + "===", Toast.LENGTH_LONG).show();
        }
    }

    private int a = 5;

    /**
     * VibrateUtils
     * 播放音频
     *
     * @param context 上下文
     * @param resId   在项目中的资源id，一般放在raw文件夹下，比如R.raw.dog
     */
    public void playVideo(Context context, int resId) {
        // 指定声音池的最大音频流数目为10，声音品质为5
        SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        // 获取资源ID（载入声音池）
        int sourceId = soundPool.load(context, resId, 0);
        // 加载完成则播放
        soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> {
            // 播放音频，
            // 第二个参数为左声道音量
            // 第三个参数为右声道音量
            // 第四个参数为优先级
            // 第五个参数为循环次数，0不循环，-1循环
            // 第六个参数为速率，速率    最低0.5最高为2，1代表正常速度
            soundPool1.play(sourceId, 1, 1, 0, 0, 1);
        });
    }

    /**
     * 定位回调函数
     *
     * @param aMapLocation
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //获取纬度
                latitudeLocaTion = aMapLocation.getLatitude();
                //获取经度
                longitudeLocaTion = aMapLocation.getLongitude();
                //获取速度
                speed = aMapLocation.getSpeed();
                MyLocationSys(aMapLocation);
                //Log.e("打印经纬度", "lat:" + latitudeLocaTion + "   lon:" + longitudeLocaTion + "===");
            } else {
                //显示错误信息ErrCode是错误码，errInfo是错误信息，详见错误码表。
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getActivity(), "定位失败：建议持设备到相对开阔的露天场所再次尝试。" + aMapLocation.getErrorCode(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * 定位返回信息设置
     *
     * @param aMapLocation
     */
    private void MyLocationSys(AMapLocation aMapLocation) {
        if (flag) {
            if (isFirstLoc) {
                //设置缩放级别
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                //将地图移动到定位点
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                // 显示系统小蓝点
                mListener.onLocationChanged(aMapLocation);
                isFirstLoc = false;
            }
        } else {
            if (mLngs.size() != 0) {
                Double shortDistance = getShortDistance(longitudeLocaTion, latitudeLocaTion, mLngs.get(mClearPosition).getLongitude(), mLngs.get(mClearPosition).getLatitude());
                if (shortDistance <= 20.0) {
                    if (pullPup) {
                        pullDownAnim(mClearPosition);
                    }
                }
            }
            WhetherOffset(latitudeLocaTion, longitudeLocaTion);
        }
        if (mTime == 30) {
            mTime = 1;
            //启动服务
            Intent intent = new Intent(getActivity(), RouteSearchService.class);
            //下面写自己的路径
            intent.setAction("com.example.wanqian.main.current.RouteSearchService");
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            intent.putExtra("lon", longitudeLocaTion);
            intent.putExtra("lat", latitudeLocaTion);
            intent.putExtra("imei", imei);
            getActivity().startService(intent);
        } else {
            mTime++;
        }
    }

    private double DEF_PI = 3.14159265359; // PI
    private double DEF_2PI = 6.28318530712; // 2*PI
    private double DEF_PI180 = 0.01745329252; // PI/180.0
    private double DEF_R = 6370693.5; // radius of earth

    /**
     * 返回为m，适合短距离测量
     *
     * @param lon1
     * @param lat1
     * @param lon2
     * @param lat2
     * @return
     */
    public Double getShortDistance(double lon1, double lat1, double lon2, double lat2) {
        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    /**
     * 初始化定位
     */
    private void initView() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);
        aMap.setLocationSource(this);//设置了定位的监听
        aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        //设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        aMap.setMyLocationStyle(new MyLocationStyle().showMyLocation(true));
        setMapCustomStyleFile(getContext());
    }

    /**
     * 初始化地图（勿动）
     *
     * @param context
     */
    private void setMapCustomStyleFile(Context context) {
        String styleName = "style_json.json";
        FileOutputStream outputStream = null;
        InputStream inputStream = null;
        String filePath = null;
        try {
            inputStream = context.getAssets().open(styleName);
            byte[] b = new byte[inputStream.available()];
            inputStream.read(b);

            filePath = context.getFilesDir().getAbsolutePath();
            File file = new File(filePath + "/" + styleName);
            if (file.exists()) {
                file.delete();
            }
            file.createNewFile();
            outputStream = new FileOutputStream(file);
            outputStream.write(b);

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (inputStream != null)
                    inputStream.close();

                if (outputStream != null)
                    outputStream.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        aMap.setCustomMapStylePath(filePath + "/" + styleName);
        aMap.showMapText(true);//是否显示地理位置名称
    }

    private boolean pullPup = true;

    /**
     * 开始下拉动画
     *
     * @param position
     */
    public void pullDownAnim(int position) {
        pullPup = false;
        createLayout(position);
        deactivate();
        ObjectAnimator.ofFloat(parent, "translationY", -800f, 0f).setDuration(500).start();
    }

    private int mClearPosition = 0;

    /**
     * 显示清理的浮框
     *
     * @param position
     */
    public void createLayout(int position) {
        //设置布局
        View view = View.inflate(getActivity(), R.layout.popwindow, null);
        View main = View.inflate(getActivity(), R.layout.fragment_current_, null);
        //找控件
        anim_container = view.findViewById(R.id.animal_container);
        parent = view.findViewById(R.id.parent);
        right = view.findViewById(R.id.right);
        tx_id = view.findViewById(R.id.tx_id);
        endCollect = view.findViewById(R.id.end_collect);
        ImageView animal_png = view.findViewById(R.id.animal_png);
        //新建popupwindow
        mWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWindow.setFocusable(true);//焦点
        //弹框后面控件不可点击
        mWindow.setOutsideTouchable(false);
        //点击弹框外不消失
        mWindow.setFocusable(false);
        tx_id.setText("垃圾桶编号：" + mLngs.get(position).getGarbageId());
        playVideo(getActivity(), R.raw.success);
        endCollect.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animal_png.setVisibility(View.GONE);
                right.setColor(Color.parseColor("#FF4081"), Color.YELLOW);
                endCollect.setEnabled(false);
                right.startAnimator();
                handler.sendEmptyMessageDelayed(0, 2500);
                doClearUp(position);
            }
        });
        mWindow.setBackgroundDrawable(new BitmapDrawable());//透明背景
        mWindow.showAtLocation(main, Gravity.CENTER, 0, 0);//显示的位置
    }

    /**
     * 每次清收完一个垃圾桶上报后台
     *
     * @param position
     */
    private void doClearUp(int position) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Myapi.url)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        CurrentService currentService = retrofit.create(CurrentService.class);
        Observable<StartClearBean> currentBeanObservable = currentService.current_ClearUp(imei, mLngs.get(position).getGroupId() + "", SPUtils.getInt("id"), latitudeLocaTion, longitudeLocaTion);
        currentBeanObservable.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<StartClearBean>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: " + d.toString());
                    }

                    @Override
                    public void onNext(StartClearBean currentBean) {
                        if (currentBean.getCode() == 200) {
                            pullPup = true;
                            Toast.makeText(getActivity(), "已清收！" + mLngs.get(mClearPosition).getGroupId(), Toast.LENGTH_SHORT).show();
                            mLngs.remove(position);
                            if (mLngs.size() == 0) {
                                doEndClear();
                                mBtnJob.setText("接受任务");
                                flag = true;
                            }
                            Log.i(TAG, "onNext:请求成功！" + currentBean.getCode());
                        }
                        endCollect.setEnabled(true);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.d(TAG, "onComplete: ");
                    }
                });
    }

    /**
     * 延迟关闭动画
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (msg.what == 0) {
                pullUp();
            }
        }
    };

    /**
     * 自定义marker的图标
     *
     * @return
     */
    public MarkerOptions customMarker(int groupId) {
        MarkerOptions markerOption = new MarkerOptions();
        if (groupId != -1) {
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_lajiyiman)));
            return markerOption;
        }
        return markerOption;
    }

    /**
     * marker点击事件的监听
     */
    AMap.OnMarkerClickListener markerClickListener = new AMap.OnMarkerClickListener() {

        @Override
        public boolean onMarkerClick(Marker marker) {
            if (marker.isInfoWindowShown()) {
                //setMarkerBehavior(marker);
                marker.hideInfoWindow();
            } else {
                marker.showInfoWindow();
            }
            return true;
        }
    };

    /**
     * 上行动画
     */
    @Override
    public void pullUp() {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(parent, "translationY", 0f, -800f);
        translationY.setDuration(500).start();
        translationY.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                mWindow.dismiss();
                activate(mListener);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

    }

    /**
     * 激活定位
     *
     * @param listener
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationClient == null) {
            //初始化定位
            mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
            //设置定位回调监听
            mLocationClient.setLocationListener(this);
            //初始化定位参数
            mLocationOption = new AMapLocationClientOption();
            //设置是否只定位一次,默认为false
            mLocationOption.setOnceLocation(false);
            //设置定位模式为AMapLocationMode.Hight_Accuracy，驾车模式（只用GPS导航）
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
            //设置定位间隔,单位毫秒,默认为2000ms
            mLocationOption.setInterval(2000);
            //给定位客户端对象设置定位参数
            mLocationClient.setLocationOption(mLocationOption);
            //启动定位
            mLocationClient.startLocation();
        }
    }

    /**
     * 停止定位
     */
    @Override
    public void deactivate() {
        mListener = null;
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
            mLocationClient.onDestroy();
        }
        mLocationClient = null;
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        // 必须回调MapView的onSaveInstanceState()方法
        mapView.onSaveInstanceState(outState);
    }

    @Override
    public void onResume() {
        super.onResume();
        activate(mListener);
        mapView.onResume();
        smoothMarker = new SmoothMoveMarker(aMap);
    }

    @Override
    public void onPause() {
        super.onPause();
        mLocationClient.stopLocation();
        mapView.onPause();
        smoothMarker.removeMarker();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        mapView.onDestroy();
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
    }

    /**
     * 定义所需要的权限
     * Manifest.permission.ACCESS_FINE_LOCATION,
     */
    String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

    /**
     * 检查权限
     */
    private void chackPermission() {
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            initView();
        } else {
            EasyPermissions.requestPermissions(this, "拍照需要摄像头权限", RC_CAMERA_AND_WIFI, perms);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, this);
    }

    @Override
    public void onPermissionsGranted(int requestCode, @NonNull List<String> perms) {

    }

    @Override
    public void onPermissionsDenied(int requestCode, @NonNull List<String> perms) {
        Toast.makeText(getContext(), "该应该需要开启定位及获取SD卡相关权限，请手动开启", Toast.LENGTH_SHORT).show();
    }
}



