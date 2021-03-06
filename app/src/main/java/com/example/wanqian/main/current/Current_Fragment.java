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
 * ????????????
 */
public class Current_Fragment extends Fragment implements EasyPermissions.PermissionCallbacks, AMapLocationListener, LocationSource, CanPullUp {

    private static final String TAG = "Current_Fragment";
    final int RC_CAMERA_AND_WIFI = 0x1;
    //???????????????????????????
    private MapView mapView;//????????????
    private AMap aMap;//????????????
    //??????AMapLocationClient???????????????????????????
    private AMapLocationClient mLocationClient = null;
    //??????mLocationOption?????????????????????
    public AMapLocationClientOption mLocationOption = null;
    //?????????????????????
    private LocationSource.OnLocationChangedListener mListener = null;//???????????????
    //???????????????????????????????????????????????????????????????????????????
    private boolean isFirstLoc = true;
    // ???????????????????????????
    private RelativeLayout anim_container;
    // ???????????????btn
    private Button endCollect;
    // ?????????
    private RelativeLayout root;
    private List<CurrentBean.DataBean> mMData = new ArrayList<>();
    private Polyline mPolyline;
    //???????????????
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
                    mBtnJob.setText("????????????");
                    aMap.setMyLocationStyle(new MyLocationStyle().showMyLocation(true));
                    doEndClear();
                    aMap.clear(true);
                    mMData.clear();
                    smoothMarker.destroy();
                    lngs.clear();
                    mCarLatLng.clear();
                    Toast.makeText(getActivity(), "???????????????,???????????????????????????", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    /**
     * ????????????????????????
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
//                        Log.i(TAG, "onNext:???????????????");
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
     * ????????????????????????
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
                        Toast.makeText(getActivity(), "??????????????????" + currentBean.getCode(), Toast.LENGTH_SHORT).show();
//                        Log.i(TAG, "onNext:???????????????");

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
     * ??????popupwindow??????
     */
    private void currentPopWindow() {
        //????????????
        View view = View.inflate(getActivity(), R.layout.current_pop, null);
        main = View.inflate(getActivity(), R.layout.activity_main, null);
        //?????????
        mBtnYes = view.findViewById(R.id.btn_yes);
        //??????popupwindow
        window = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        window.setFocusable(true);//??????
        //??????????????????????????????
        window.setOutsideTouchable(false);
        //????????????????????????
        window.setFocusable(false);
        mBtnYes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                smoothMarker = new SmoothMoveMarker(aMap);
                doStartClear();
                aMap.setMyLocationStyle(new MyLocationStyle().showMyLocation(false));
                mBtnJob.setText("????????????");
                flag = false;
                initData();
                window.dismiss();
            }
        });
        window.setBackgroundDrawable(new BitmapDrawable());//????????????
    }

    /**
     * ????????????????????????
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
//                        Log.i(TAG, "onNext:???????????????");
                        if (currentBean.getData() != null) {
                            mMData = currentBean.getData();
                            for (int i = 0; i < mMData.size(); i++) {
                                if (mMData.get(i).getGarbageId() != -1) {
                                    mLngs.add(mMData.get(i));
                                }
//                                Log.i(TAG, mMData.get(i).getLatitude() + "===" + mMData.get(i).getLongitude() + "===" + mMData.get(i).getGroupId());
                            }
                            window.showAtLocation(main, Gravity.CENTER, 0, 0);//???????????????
                        } else {
                            Toast.makeText(getActivity(), "??????????????????????????????????????????????????????", Toast.LENGTH_SHORT).show();
                        }
                        if (flag) {
                            mBtnJob.setText("????????????");
                        } else {
                            mBtnJob.setText("????????????");
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
     * ???????????????
     * <p>
     * // * @param points
     * // * @param angle
     */
    //List<LatLng> points, double angle, double shortDistance
    public void setCarMove() {
        if (smoothMarker != null) {
            smoothMarker.removeMarker();
        }
        // ?????????????????????
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.icon_che_two));
        LatLng drivePoint = lngs.get(0);
        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(lngs, drivePoint);
        lngs.set(pair.first, drivePoint);
        List<LatLng> subList = lngs.subList(pair.first, lngs.size());
        //????????????????????????????????????
        smoothMarker.setPoints(subList);
    }

    /**
     * ??????IMEI???
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
     * ??????marker
     */
    private void initData() {
        lngs.clear();
        if (mMData != null) {
            for (int i = 0; i < mMData.size(); i++) {
                lngs.add(new LatLng(mMData.get(i).getLatitude(), mMData.get(i).getLongitude()));
                mList.add(new LinePoint(mMData.get(i).getLatitude(), mMData.get(i).getLongitude()));
                if (mMData.get(i).getGarbageId() != -1)
                    aMap.addMarker(customMarker(mMData.get(i).getGarbageId()).position(lngs.get(i)).snippet("???" + i + "?????????????????????" + lngs.get(i).longitude + "===" + lngs.get(i).latitude + ",garbageId??????" + mMData.get(i).getGarbageId()));
            }
            aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
            //????????????????????????????????????????????????
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
     * ??????????????????
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

    //????????????30s??????????????????????????????
    private int mTime = 30;
    private Double[] mLine;

    /**
     * ????????????????????????????????????
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
     * ??????????????????????????????????????????
     *
     * @param mLocationLat ??????????????????x???????????????
     * @param mLocationLon ???????????????y???????????????
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
                //Toast.makeText(getActivity(), "?????????????????????", Toast.LENGTH_SHORT).show();
                a = 0;
            }
            a++;
        } else {
            mCarLatLng.add(new LatLng(mLocationLat, mLocationLon));
            mCarLatLng.add(new LatLng(mLocationLat, mLocationLon));
            //setCarMove(mCarLatLng, angle, 0);
            //????????????
            if (a == 5) {
                playVideo(getActivity(), R.raw.tip);
                a = 0;
                Toast.makeText(getActivity(), "?????????????????????", Toast.LENGTH_SHORT).show();
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
     * ????????????
     *
     * @param context ?????????
     * @param resId   ?????????????????????id???????????????raw?????????????????????R.raw.dog
     */
    public void playVideo(Context context, int resId) {
        // ??????????????????????????????????????????10??????????????????5
        SoundPool soundPool = new SoundPool(10, AudioManager.STREAM_SYSTEM, 5);
        // ????????????ID?????????????????????
        int sourceId = soundPool.load(context, resId, 0);
        // ?????????????????????
        soundPool.setOnLoadCompleteListener((soundPool1, sampleId, status) -> {
            // ???????????????
            // ?????????????????????????????????
            // ?????????????????????????????????
            // ???????????????????????????
            // ?????????????????????????????????0????????????-1??????
            // ?????????????????????????????????    ??????0.5?????????2???1??????????????????
            soundPool1.play(sourceId, 1, 1, 0, 0, 1);
        });
    }

    /**
     * ??????????????????
     *
     * @param aMapLocation
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation != null) {
            if (aMapLocation.getErrorCode() == 0) {
                //????????????
                latitudeLocaTion = aMapLocation.getLatitude();
                //????????????
                longitudeLocaTion = aMapLocation.getLongitude();
                //????????????
                speed = aMapLocation.getSpeed();
                MyLocationSys(aMapLocation);
                //Log.e("???????????????", "lat:" + latitudeLocaTion + "   lon:" + longitudeLocaTion + "===");
            } else {
                //??????????????????ErrCode???????????????errInfo???????????????????????????????????????
                Log.e("AmapError", "location Error, ErrCode:"
                        + aMapLocation.getErrorCode() + ", errInfo:"
                        + aMapLocation.getErrorInfo());
                Toast.makeText(getActivity(), "???????????????????????????????????????????????????????????????????????????" + aMapLocation.getErrorCode(), Toast.LENGTH_LONG).show();
            }
        }
    }

    /**
     * ????????????????????????
     *
     * @param aMapLocation
     */
    private void MyLocationSys(AMapLocation aMapLocation) {
        if (flag) {
            if (isFirstLoc) {
                //??????????????????
                aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
                //???????????????????????????
                aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude())));
                // ?????????????????????
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
            //????????????
            Intent intent = new Intent(getActivity(), RouteSearchService.class);
            //????????????????????????
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
     * ?????????m????????????????????????
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
        // ?????????????????????
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // ?????????
        dew = ew1 - ew2;
        // ?????????????????????180 ??????????????????
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // ??????????????????(??????????????????????????????)
        dy = DEF_R * (ns1 - ns2); // ??????????????????(??????????????????????????????)
        // ????????????????????????
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    /**
     * ???????????????
     */
    private void initView() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //???????????????????????? ??????????????????
        UiSettings settings = aMap.getUiSettings();
        // ????????????????????????
        settings.setMyLocationButtonEnabled(true);
        aMap.setLocationSource(this);//????????????????????????
        aMap.setMyLocationEnabled(true);//???????????????????????????????????????,?????????flase
        //??????????????????????????????????????????????????????????????????????????????????????????????????????????????????false????????????????????????????????????????????????????????????????????????????????????
        aMap.setMyLocationStyle(new MyLocationStyle().showMyLocation(true));
        setMapCustomStyleFile(getContext());
    }

    /**
     * ???????????????????????????
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
        aMap.showMapText(true);//??????????????????????????????
    }

    private boolean pullPup = true;

    /**
     * ??????????????????
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
     * ?????????????????????
     *
     * @param position
     */
    public void createLayout(int position) {
        //????????????
        View view = View.inflate(getActivity(), R.layout.popwindow, null);
        View main = View.inflate(getActivity(), R.layout.fragment_current_, null);
        //?????????
        anim_container = view.findViewById(R.id.animal_container);
        parent = view.findViewById(R.id.parent);
        right = view.findViewById(R.id.right);
        tx_id = view.findViewById(R.id.tx_id);
        endCollect = view.findViewById(R.id.end_collect);
        ImageView animal_png = view.findViewById(R.id.animal_png);
        //??????popupwindow
        mWindow = new PopupWindow(view, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        mWindow.setFocusable(true);//??????
        //??????????????????????????????
        mWindow.setOutsideTouchable(false);
        //????????????????????????
        mWindow.setFocusable(false);
        tx_id.setText("??????????????????" + mLngs.get(position).getGarbageId());
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
        mWindow.setBackgroundDrawable(new BitmapDrawable());//????????????
        mWindow.showAtLocation(main, Gravity.CENTER, 0, 0);//???????????????
    }

    /**
     * ??????????????????????????????????????????
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
                            Toast.makeText(getActivity(), "????????????" + mLngs.get(mClearPosition).getGroupId(), Toast.LENGTH_SHORT).show();
                            mLngs.remove(position);
                            if (mLngs.size() == 0) {
                                doEndClear();
                                mBtnJob.setText("????????????");
                                flag = true;
                            }
                            Log.i(TAG, "onNext:???????????????" + currentBean.getCode());
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
     * ??????????????????
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
     * ?????????marker?????????
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
     * marker?????????????????????
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
     * ????????????
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
     * ????????????
     *
     * @param listener
     */
    @Override
    public void activate(LocationSource.OnLocationChangedListener listener) {
        mListener = listener;
        if (mLocationClient == null) {
            //???????????????
            mLocationClient = new AMapLocationClient(getActivity().getApplicationContext());
            //????????????????????????
            mLocationClient.setLocationListener(this);
            //?????????????????????
            mLocationOption = new AMapLocationClientOption();
            //???????????????????????????,?????????false
            mLocationOption.setOnceLocation(false);
            //?????????????????????AMapLocationMode.Hight_Accuracy????????????????????????GPS?????????
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Device_Sensors);
            //??????????????????,????????????,?????????2000ms
            mLocationOption.setInterval(2000);
            //??????????????????????????????????????????
            mLocationClient.setLocationOption(mLocationOption);
            //????????????
            mLocationClient.startLocation();
        }
    }

    /**
     * ????????????
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
        // ????????????MapView???onSaveInstanceState()??????
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
     * ????????????????????????
     * Manifest.permission.ACCESS_FINE_LOCATION,
     */
    String[] perms = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.RECORD_AUDIO, Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.READ_PHONE_STATE};

    /**
     * ????????????
     */
    private void chackPermission() {
        if (EasyPermissions.hasPermissions(getContext(), perms)) {
            initView();
        } else {
            EasyPermissions.requestPermissions(this, "???????????????????????????", RC_CAMERA_AND_WIFI, perms);
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
        Toast.makeText(getContext(), "????????????????????????????????????SD?????????????????????????????????", Toast.LENGTH_SHORT).show();
    }
}



