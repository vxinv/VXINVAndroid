package com.example.wanqian.fragment;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.BitmapFactory;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Pair;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

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
import com.example.wanqian.bean.TaskListBean;
import com.example.wanqian.main.current.EndClearBean;
import com.example.wanqian.main.zhu.utils.MapBdUtils;
import com.example.wanqian.newbase.NewBaseFragment;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.pView.MapFunctionFrView;
import com.example.wanqian.presenter.MapFunctionFrPresenter;
import com.example.wanqian.utitls.AlertDialog;
import com.example.wanqian.utitls.SPUtils;
import com.example.wanqian.utitls.SpeechManager;
import com.example.wanqian.utitls.TimeUpdateLocationUtils;
import com.example.wanqian.utitls.ToastUtils;
import com.example.wanqian.utitls.Utils;
import com.example.wanqian.widget.MFTaskClearPopWindow;
import com.google.gson.Gson;
import com.xiasuhuei321.loadingdialog.view.LoadingDialog;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import okhttp3.MediaType;
import okhttp3.RequestBody;


public class MapFunctionFragment extends NewBaseFragment<MapFunctionFrPresenter>
        implements MapFunctionFrView, View.OnClickListener, AMapLocationListener, LocationSource, SensorEventListener {

    private static final String TAG = "MapFunctionFragment";
    @BindView(R.id.new_map)
    MapView mapView;
    @BindView(R.id.new_btn_job)
    TextView b_job;
    // 方向传感器
    private SensorManager sensorManager;
    private Sensor sensor;

    // 清收显示的maker
    private List<Marker> trueMarker = new ArrayList<>();
    private AMap aMap;

    //声明AMapLocationClient类对象，定位发起端
    private AMapLocationClient mLocationClient;
    //声明mLocationOption对象，定位参数
    private AMapLocationClientOption mLocationOption;

    private SmoothMoveMarker smoothMarker;
    //定位需要的声明
    private LocationSource.OnLocationChangedListener mListener;//定位监听器
    private double latitudeLocaTion;
    private double longitudeLocaTion;
    // 所有的点位
    private List<TaskListBean> taskList = new ArrayList<>();
    // 真实的点位
    private List<TaskListBean> taskListReal = new ArrayList<>();
    // 本次未清收的点位
    private List<Integer> mNotClear = new ArrayList<>();
    // 要经过的路径线路点集合 仅供划线
    private List<LatLng> roadLatLngLists = new ArrayList<>();
    private MFTaskClearPopWindow mfTaskClearPopWindow;
    private float speed;
    // 需要平滑移动的点位
    private List<LatLng> twoLatlng = new ArrayList<>();
    private boolean nearFirstPlace = true;
    // 下一个要清收的位置
    private int clear_index = 0;
    // 上一个清收的位置
    private int last_clear_index = 0;
    // 上次方法调用时间 控制某些方法的调用频率 路线偏移
    private long lastExeTime;
    // 控制车辆居中的速率
    private long carCenterTime;
    private volatile boolean moving = false;
    // 当前是否接受服务器的辅助控制变量
    private volatile boolean testing = true;

    /**
     * marker点击事件的监听
     */
    AMap.OnMarkerClickListener markerClickListener = marker -> {
        alertDialog(Integer.parseInt(marker.getSnippet()));
        return true;
    };
    // 即将行驶的线段
    private Polyline polyline;

    @Override
    protected MapFunctionFrPresenter createPresenter() {
        return new MapFunctionFrPresenter(this);
    }

    @Override
    protected void initListener() {
        // b_job.setOnClickListener(this::otherViewClick);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_mapfunction;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mapView.onCreate(savedInstanceState);
    }


    /**
     * view完成 初始化数据
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void initData() {

        nearFirstPlace = true;
        if (SpeechManager.getInstance().getSynthesizer() == null) {
            SpeechManager.getInstance().initialTts(mContext);
        }
        smoothMarker = new SmoothMoveMarker(aMap);
        //获取SensorManager实例
        sensorManager = (SensorManager) getActivity().getSystemService(Context.SENSOR_SERVICE);
        //获取Sensor实例
        sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ORIENTATION);
        sensorManager.registerListener(this, sensor, SensorManager.SENSOR_DELAY_UI);
        initMap();
    }

    /**
     * 显示marker
     * 开始清收显示
     */
    private void startClearTask() {
        mapClear();
        speak("任务已经获取成功");
        for (int i = 0; i < taskList.size(); i++) {
            TaskListBean taskListBean = taskList.get(i);
            roadLatLngLists.add(new LatLng(taskListBean.getLatitude(), taskListBean.getLongitude()));
            if (taskListBean.getGarbageId() != -1) {
                taskListBean.setRoadIndex(i);
                taskListReal.add(taskListBean);
                Marker marker = aMap.addMarker(customMarker(R.mipmap.icon_lajiyiman, taskList.get(i).getGarbageId(), null)
                        .position(new LatLng(taskList.get(i).getLatitude(), taskList.get(i).getLongitude()))
                        .snippet(String.valueOf(taskListReal.size() - 1)));
                // 将marker 加入
                trueMarker.add(marker);
            }
        }
        aMap.setOnMarkerClickListener(markerClickListener);
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(roadLatLngLists)
                .useGradient(false)
                .width(20)
                .setCustomTexture(BitmapDescriptorFactory.fromResource(R.mipmap.icon_road));
        aMap.addPolyline(polylineOptions);
        clearEndPaintGreenRoad();
        blingNextMarker();
        smoothMarker = new SmoothMoveMarker(aMap);
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.icon_che_one));
        List<LatLng> firstList = new ArrayList<>();
        //进行初始化不需要改动只是确定车位置 移动到第一个规划的初始点
        firstList.add(roadLatLngLists.get(0));
        firstList.add(roadLatLngLists.get(0));
        CarMove(firstList, 2);
        twoLatlng.add(roadLatLngLists.get(0));
        twoLatlng.add(roadLatLngLists.get(0));
    }

    /**
     * 地图的清理 当出现异常时 清理之前的路线和清收信息
     */
    public void mapClear() {
        aMap.clear(true);
        taskListReal.clear();
        roadLatLngLists.clear();
        twoLatlng.clear();
        mNotClear.clear();
        trueMarker.clear();
        clear_index = 0;
        last_clear_index = 0;
        testing = true;
        if (mfTaskClearPopWindow != null && mfTaskClearPopWindow.isShowing()) {
            mfTaskClearPopWindow.dismiss();
        }
    }

    public void CarMove(List<LatLng> latLngs, int mTime) {
        if (latLngs.size() < 2) {
            return;
        }
        moving = true;
        twoLatlng = latLngs.subList(latLngs.size() - 2, latLngs.size());
        smoothMarker.setPoints(twoLatlng);
        smoothMarker.setTotalDuration(1);
        smoothMarker.startSmoothMove();
        smoothMarker.setMoveListener(d -> {
            if (d == 0) {
                moving = false;
            }
        });
    }

    /**
     * view 初始化完毕
     * 开始初始化地图定位
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    private void initMap() {
        if (aMap == null) {
            aMap = mapView.getMap();
        }
        //设置显示定位按钮 并且可以点击
        UiSettings settings = aMap.getUiSettings();
        // 是否显示定位按钮
        settings.setMyLocationButtonEnabled(true);
        //设置了定位的监听
        aMap.setLocationSource(this);
        aMap.setMyLocationEnabled(true);//显示定位层并且可以触发定位,默认是flase
        //设置是否显示定位小蓝点，用于满足只想使用定位，不想使用定位小蓝点的场景，设置false以后图面上不再有定位蓝点的概念，但是会持续回调位置信息。
        aMap.setMyLocationStyle(new MyLocationStyle()
                .myLocationType(MyLocationStyle.LOCATION_TYPE_FOLLOW)
                .showMyLocation(false));
        //设置缩放级别
        aMap.moveCamera(CameraUpdateFactory.zoomTo(17));
        changeBJob(true, false, true, "查询任务中");
        smoothMarker = new SmoothMoveMarker(aMap);
        smoothMarker.setDescriptor(BitmapDescriptorFactory.fromResource(R.mipmap.icon_che_one));
        // 初始化上报位置 定时器
        TimeUpdateLocationUtils.getInstance().startPolling();
        TimeUpdateLocationUtils.getInstance().setOnTimeClickListener(() -> {
            if (latitudeLocaTion != 0 && longitudeLocaTion != 0) {
                //更新当前位置 初始地图 启动定时器
                if (Utils.getIMei(getActivity()).equals("")) {
                    speak("当前车辆未绑定");
                    changeBJob(true, false, false, "当前车辆未绑定");
                }
                mPresenter.UpPositionMessage(Utils.getIMei(mContext), latitudeLocaTion, longitudeLocaTion);
            }
        });
    }


    /**
     * 自定义marker的图标
     */
    public MarkerOptions customMarker(int pictureid, int groupId, AMapLocation aMapLocation) {
        MarkerOptions markerOption = new MarkerOptions();
        if (groupId != -1) {
            markerOption.icon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), pictureid)));
            if (aMapLocation != null)
                markerOption.position(new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude()));
            return markerOption;
        }
        return markerOption;
    }

    /**
     * 位置变化的监听
     *
     * @param aMapLocation
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onLocationChanged(AMapLocation aMapLocation) {
        if (aMapLocation == null || aMapLocation.getErrorCode() != 0) {
            speak("定位失败,建议持设备到相对开阔的露天场所再次尝试");
            Toast.makeText(getActivity(), "定位失败：建议持设备到相对开阔的露天场所再次尝试。" + aMapLocation.getErrorCode(), Toast.LENGTH_LONG).show();
            return;
        }
        latitudeLocaTion = aMapLocation.getLatitude();
        longitudeLocaTion = aMapLocation.getLongitude();
        speed = aMapLocation.getSpeed();
        //Log.i(TAG, "onLocationChanged: " + latitudeLocaTion + " == > " + longitudeLocaTion + " == >" + speed);
        //ToastUtils.showToast("onLocationChanged: " + latitudeLocaTion + " == > " + longitudeLocaTion + " == >" + speed);
        carCenterMoving();
        mListener.onLocationChanged(aMapLocation);
        if (b_job.getVisibility() != View.VISIBLE || !testing) {
            return;
        }
        LatLng latLng = new LatLng(aMapLocation.getLatitude(), aMapLocation.getLongitude());
        double minDistance = MapBdUtils.calculateLineDistance(latLng, new LatLng(taskListReal.get(clear_index).getLatitude(), taskListReal.get(clear_index).getLongitude()));
        if (minDistance <= 20D && nearFirstPlace) {
            nearFirstPlace = false;
            pullDownAnim(null);
        }
        lineOffset(latitudeLocaTion, longitudeLocaTion);
    }

    /**
     * 到第一个垃圾桶执行操作
     * 先播放声音 10s后弹框
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void pullDownAnim(Integer index) {
        //  停止检测
        testing = false;
        final int currIndex = index == null ? clear_index : index;
        mfTaskClearPopWindow = new MFTaskClearPopWindow(mContext, taskListReal.get(currIndex).getGroupId());
        mfTaskClearPopWindow.setDoWhatCallBack(() -> {
            mPresenter.endClearTask(Utils.getIMei(mContext), taskListReal.get(currIndex).getGroupId() + "", taskListReal.get(currIndex).getType(),
                    SPUtils.getInt("id"), latitudeLocaTion, longitudeLocaTion, currIndex);
            mfTaskClearPopWindow.dismiss();
            nearFirstPlace = true;
        });
        mfTaskClearPopWindow.showAtLocation(mapView, Gravity.CENTER, 0, 0);
        speak("您已到达" + taskListReal.get(currIndex).getGarbageId() + "号垃圾桶附近,请及时下车清收");
    }

    @Override
    public void activate(OnLocationChangedListener onLocationChangedListener) {
        mListener = onLocationChangedListener;
//        //Log.i(TAG, "activate: 初始化定位");
        if (mLocationClient == null) {
            mLocationClient = new AMapLocationClient(getActivity());
            mLocationClient.setLocationListener(this);
            mLocationOption = new AMapLocationClientOption();
            mLocationOption.setLocationMode(AMapLocationClientOption.AMapLocationMode.Hight_Accuracy);
            mLocationOption.setInterval(4000);
            mLocationClient.setLocationOption(mLocationOption);
            mLocationClient.startLocation();
        }
    }

    /**
     * 得到任务列表返回的接口
     */
    @Override
    public void getTaskListSuccess(List<TaskListBean> listBaseModel) {
        changeBJob(true, false, true, "任务进行中");
        taskList.clear();
        if (listBaseModel == null || listBaseModel.isEmpty()) {
            return;
        }
        taskList.addAll(listBaseModel);
        startClearTask();
    }

    /**
     * 每个清理完毕 网络上报成功后 调用
     */
    @Override
    public void clearComplete(int index) {
        taskListReal.get(index).setComplete(true);
        trueMarker.get(index).setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.map_p_normal)));
        if (!allIsCleared()) {
            if (clear_index == getNextClearIndex()) {
                testing = true;
                nearFirstPlace = true;
                return;
            }
            last_clear_index = clear_index;
            clear_index = getNextClearIndex();
            List<LatLng> latLngs = clearEndPaintGreenRoad();
            blingNextMarker();
            mPresenter.speakLocation(latLngs);
            nearFirstPlace = true;
            testing = true;
            return;
        }
        speak("本次派发任务全部清收完成,请等待下次任务");
        changeBJob(true, false, false, "请等待下次任务");
        testing = true;
        mapClear();
        Toast.makeText(getActivity(), "已清收！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 检测是否全部清理完成
     */
    boolean allIsCleared() {
        for (TaskListBean taskListBean : taskListReal) {
            if (!taskListBean.isComplete()) {
                return false;
            }
        }
        return true;
    }

    /**
     * 清收完毕 缩放动画 绘制线路
     */
    public List<LatLng> clearEndPaintGreenRoad() {
        if (polyline != null) {
            polyline.remove();
        }
        int start = clear_index == 0 ? clear_index : taskListReal.get(last_clear_index).getRoadIndex();
        ////Log.i(TAG, "clearEndPaintGreenRoad: " + roadLatLngLists.size());
        ////Log.i(TAG, "clearEndPaintGreenRoad: " + last_clear_index + "curr==>" + clear_index);
        int end = Math.min(taskListReal.get(clear_index).getRoadIndex() + 1, roadLatLngLists.size());
        List<LatLng> latLngs = roadLatLngLists.subList(start, end);
        ////Log.i(TAG, "clearEndPaintGreenRoad: " + latLngs.size());
        PolylineOptions polylineOptions = new PolylineOptions()
                .addAll(latLngs)
                .useGradient(true)
                .width(20)
                .zIndex(100)
                .setCustomTexture(BitmapDescriptorFactory.fromResource(R.mipmap.icon_green));
        polyline = aMap.addPolyline(polylineOptions);
        // 地图移动到下个清收点  且缩放
        carCenterTime = System.currentTimeMillis();
        TaskListBean taskListBean = taskListReal.get(clear_index);
        aMap.animateCamera(CameraUpdateFactory.newLatLngBounds(Utils.createBounds(
                latitudeLocaTion, longitudeLocaTion, taskListBean.getLatitude(), taskListBean.getLongitude()
        ), 200));
        return latLngs;
    }

    /**
     * 闪烁下一个图标
     */
    void blingNextMarker() {
        Marker marker = trueMarker.get(clear_index);
        marker.setIcon(BitmapDescriptorFactory.fromBitmap(BitmapFactory.decodeResource(getResources(), R.mipmap.icon_next)));
    }

    /**
     * 清理任務請求接口失敗
     */
    @Override
    public void clearTaskFail(String msg) {
        ToastUtils.showSingleToast(msg);
        //handler.sendEmptyMessageDelayed(0, 1000);
    }

    /**
     * 改变b_job的状态
     *
     * @param canClick
     * @param canClear
     */
    private void changeBJob(boolean canSee, boolean canClick, boolean canClear, String msg) {
        b_job.setVisibility(canSee ? View.VISIBLE : View.GONE);
        b_job.setBackgroundResource(canClear ? R.drawable.btn_down_bg : R.drawable.bg_not_work);
        b_job.setClickable(canClick);
        b_job.setText(msg);
    }

    @Override
    public void processServiceEndOrCarNotTurnOn(String code) {
        b_job.setBackgroundResource(R.drawable.bg_not_work);
        b_job.setVisibility(View.VISIBLE);
        b_job.setClickable(false);
        testing = false;
        // 车辆未开启
        if (code.equals("301")) {
            speak("当前车辆未开启");
            b_job.setText(R.string.theCurrentVehicleIsNotTurnedOn);
        }
        if (code.equals("302")) {
            speak("清运服务已关闭");
            b_job.setText(R.string.clearanceServiceClosed);
        }
        if (code.equals("303")) {
            speak("当前任务全部完成");
            b_job.setText(R.string.allTheCurrentTasksAreCompleted);
        }
        mapClear();
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public String getImei() {
        return Utils.getIMei(mContext);
    }

    /**
     * 計算偏移量判断某一个点是否在当前的线段上
     *
     * @param mLocationLat 当前的纬度
     * @param mLocationLon 当前的经度
     */
    public void lineOffset(double mLocationLat, double mLocationLon) {
        if (speed == 0) {
            return;
        }
        // Find the nearest point of the path that the current point is from
        Pair<Integer, LatLng> closePoint = SpatialRelationUtil.calShortestDistancePoint(roadLatLngLists, new LatLng(mLocationLat, mLocationLon));
        // Calculate the offset distance
        double shortDistanceNear = MapBdUtils.calculateLineDistance(new LatLng(closePoint.second.latitude, closePoint.second.longitude), new LatLng(mLocationLat, mLocationLon));
        if (shortDistanceNear >= 30D) {
            twoLatlng.add(new LatLng(mLocationLat, mLocationLon));
            CarMove(twoLatlng, 2);
            if (lastExeTime == 0L) {
                lastExeTime = System.currentTimeMillis();
                return;
            }
            if (System.currentTimeMillis() - lastExeTime > 10 * 1000) {
                lastExeTime = System.currentTimeMillis();
                speak("路线偏移 请按照路线行驶");
                Toast.makeText(getActivity(), "路线偏移", Toast.LENGTH_LONG).show();
            }
            return;
        }

        twoLatlng.add(new LatLng(closePoint.second.latitude, closePoint.second.longitude));
        CarMove(twoLatlng, 2);
    }

    public void carCenterMoving() {
        if (carCenterTime == 0L) {
            carCenterTime = System.currentTimeMillis();
            return;
        }
        //  控制方法的调用速率
        if (System.currentTimeMillis() - carCenterTime > 15 * 1000) {
            carCenterTime = System.currentTimeMillis();
            //aMap.animateCamera(CameraUpdateFactory.zoomTo(18));
            aMap.moveCamera(CameraUpdateFactory.changeLatLng(new LatLng(latitudeLocaTion, longitudeLocaTion)));
        }

    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void otherViewClick(View view) {
        switch (view.getId()) {
            case R.id.new_btn_job:
                if (b_job.getText().toString().equals("接受任务")) {
                    mPresenter.StartClearTask(Utils.getIMei(mContext));
                } else {
                    doEndClear();
                }
                break;
        }
    }

    @Nullable
    @Override
    public Context getContext() {
        return this.getActivity().getApplicationContext();
    }

    @Override
    public void speak(String msg) {
        SpeechManager.getInstance().speak(msg);
    }


    @Override
    public void onSensorChanged(SensorEvent event) {
        if (moving) {
            return;
        }
        float direction = (float) (Math.round(event.values[0] * 100)) / 100;
        float curr = direction + 90F > 360F ? direction + 90F - 360F : direction + 90F;
        smoothMarker.setRotate(curr);
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }

    /**
     * 提示对话框
     */
    @SuppressLint("NewApi")
    public void alertDialog(int sni) {
        new AlertDialog(this.getActivity())
                .init()
                .setTitle("是否清收" + taskListReal.get(sni).getGarbageId() + "号垃圾桶")
                .setPositiveButton("清收", v -> checkLocation(sni))
                .setNegativeButton("取消", v -> speak("您已取消清收该位置垃圾桶")).show();
    }

    /**
     * 开始检测当前的位置是否符合点击位置的清收
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void checkLocation(int sni) {
        TaskListBean taskListBean = taskListReal.get(sni);
        if (taskListBean.isComplete()) {
            speak("已经清收完成,无需清收");
        }
        LoadingDialog ld = new LoadingDialog(this.getActivity());
        //speak("正在检测" + taskListBean.getGarbageId() + "号垃圾桶是否在指定范围");
        ld.setLoadingText("检测位置中")
                .setSuccessText("加载成功")
                .setShowTime(3000)
                .show();
        double minDistance = MapBdUtils.calculateLineDistance(new LatLng(taskListBean.getLatitude(), taskListBean.getLongitude()),
                new LatLng(latitudeLocaTion, longitudeLocaTion));
        //  当用户检测时 尽量缩小精确度
        if (minDistance > 30D) {
            speak("未在指定范围 清收失败");
            ld.setFailedText("未在指定范围");
            ld.close();
            return;
        }
        ld.close();
        pullDownAnim(sni);
    }

    /**
     * 获取下一个要清收的索引
     */
    public int getNextClearIndex() {
        for (int i = 0; i < taskListReal.size(); i++) {
            if (!taskListReal.get(i).isComplete()) {
                return i;
            }
        }
        return taskListReal.size();
    }


    /**
     * end
     */
    //中途点击结束 把未清理的垃圾桶上报
    @RequiresApi(api = Build.VERSION_CODES.M)
    public void doEndClear() {
        mNotClear.clear();
        for (int j = 0; j < taskListReal.size(); j++) {
            if (!taskListReal.get(j).isComplete()) {
                mNotClear.add(taskListReal.get(j).getGroupId());
            }
        }
        EndClearBean endClearBean = new EndClearBean();
        endClearBean.setImei(Utils.getIMei(mContext));
        endClearBean.setNotClear(mNotClear);
        endClearBean.setUserAccount(SPUtils.getValue("yhm"));
        String mNoClearJson = new Gson().toJson(endClearBean);
        RequestBody requestBody = RequestBody.create(MediaType.parse("application/json"), mNoClearJson);
        mPresenter.finishTask(requestBody);
    }


    /**
     * 开始任务返回成功结果
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void startTaskSuccess(BaseModel baseModel) {
        mPresenter.getJobList(Utils.getIMei(mContext));
    }

    @Override
    public void startTaskFail(String msg) {
        ToastUtils.showSingleToast(msg);
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
        //Log.i(TAG, "onResume: mf");
        if (SpeechManager.getInstance().getSynthesizer() == null) {
            SpeechManager.getInstance().initialTts(mContext);
        }
        mLocationClient.startLocation();
        if (!TimeUpdateLocationUtils.IsOn) {
            TimeUpdateLocationUtils.getInstance().startPolling();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //Log.i(TAG, "onPause: mf");
        if (mLocationClient != null) {
            mLocationClient.stopLocation();
        }
        if (TimeUpdateLocationUtils.IsOn) {
            TimeUpdateLocationUtils.getInstance().stopPolling();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //Log.i(TAG, "onDestroy:  MapFun 销毁");
        if (mapView != null) {
            mapView.onDestroy();
        }
        if (null != mLocationClient) {
            mLocationClient.onDestroy();
        }
        smoothMarker.removeMarker();
        SpeechManager.getInstance().relese();
        TimeUpdateLocationUtils.getInstance().destroy();
    }

    /**
     * 结束任务成功
     */
    @Override
    public void endTaskSuccess(BaseModel baseModel) {
        //延时10秒重置状态
        //handler.sendEmptyMessageDelayed(1, 10000);
        b_job.setText("接受任务");
        b_job.setVisibility(View.GONE);
        aMap.clear(true);
        smoothMarker.destroy();
        Toast.makeText(getActivity(), "任务已结束,位置回到当前定位！", Toast.LENGTH_SHORT).show();
    }

    /**
     * 结束任务失败
     */
    @Override
    public void endTaskFail(String msg) {
        //延时10秒重置状态
        //handler.sendEmptyMessageDelayed(1, 10000);
        b_job.setText("查询任务中");
        b_job.setVisibility(View.GONE);
        aMap.clear(true);
        smoothMarker.destroy();
        Toast.makeText(getActivity(), "任务结束失败,位置回到当前定位！", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void getTaskListFail(String msg) {
        b_job.setVisibility(View.GONE);
    }


}








