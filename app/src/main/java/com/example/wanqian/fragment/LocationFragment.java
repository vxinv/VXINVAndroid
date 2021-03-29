package com.example.wanqian.fragment;

import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdate;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.MapView;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.Marker;
import com.example.wanqian.R;
import com.example.wanqian.bean.AllMessageDeviceBean;
import com.example.wanqian.bean.NewLocationBean;
import com.example.wanqian.main.location.ClusterClickListener;
import com.example.wanqian.main.location.ClusterItem;
import com.example.wanqian.main.location.ClusterOverlay;
import com.example.wanqian.main.location.ClusterRender;
import com.example.wanqian.main.location.demo.RegionItem;
import com.example.wanqian.newbase.NewBaseFragment;
import com.example.wanqian.newbase.mvp.BaseModel;
import com.example.wanqian.pView.LocationBackDataView;
import com.example.wanqian.presenter.LocationPresenter;
import com.example.wanqian.ui.locationMessage.LocationDialogFragment;
import com.example.wanqian.utitls.AppLog;
import com.example.wanqian.utitls.SPUtils;
import com.example.wanqian.utitls.ScreenUtils;
import com.example.wanqian.utitls.ToastUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;

public class LocationFragment extends NewBaseFragment<LocationPresenter> implements LocationBackDataView, ClusterRender, AMap.OnMapLoadedListener, ClusterClickListener {

    @BindView(R.id.map)
    MapView mMapView;

    public static List<List<NewLocationBean>> listData;
    private AMap mAMap;
    private int clusterRadius = 100;
    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<Integer, Drawable>();
    private ClusterOverlay mClusterOverlay;
    private String onlineStatus;
    private String devStatus;
    private TextView text;
    private String[] reportType;

    @Override
    protected LocationPresenter createPresenter() {
        return new LocationPresenter(this);
    }


    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        mMapView.onCreate(savedInstanceState);
        init();
    }

    @Override
    protected void initListener() {

    }

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_location_;
    }

    @Override
    protected void initData() {


    }

    public void init(){
        if (mAMap == null) {
            // 初始化地图
            mAMap = mMapView.getMap();
            mAMap.moveCamera(CameraUpdateFactory.zoomTo(10));
            mAMap.setOnMapLoadedListener(this);
        }
    }

    @Override
    protected void otherViewClick(View view) {

    }

    @Override
    public void getBaseData(BaseModel baseModel) {


        listData= (List<List<NewLocationBean>>) baseModel.getData();
        List<ClusterItem> items = new ArrayList<ClusterItem>();
        if (listData != null) {
            for (int i = 0; i < listData.size(); i++) {
                String latitude = listData.get(i).get(0).getLatitude();
                String longitude = listData.get(i).get(0).getLongitude();
                if (!TextUtils.isEmpty(latitude)&&!TextUtils.isEmpty(longitude)){
                    LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
                    RegionItem regionItem = new RegionItem(latLng, listData.get(i));
                    items.add(regionItem);
                }
            }
            mClusterOverlay = new ClusterOverlay(mAMap, items, ScreenUtils.dp2px(getContext(), clusterRadius), getContext());
            mClusterOverlay.setClusterRenderer(this);
            mClusterOverlay.setOnClusterClickListener(this);
            Handler mainHandler = new Handler(Looper.getMainLooper());
            mainHandler.post(() -> {
                if (listData!=null&&listData.get(0)!=null&&listData.get(0).get(0)!=null&&listData.get(0).get(0).getLongitude()!=null&&listData.get(0).get(0).getLatitude()!=null){
                    CameraPosition cameraPosition = new CameraPosition(new LatLng(Double.valueOf(listData.get(0).get(0).getLatitude()), Double.valueOf(listData.get(0).get(0).getLongitude())), 15, 0, 30);
                    CameraUpdate cameraUpdate = CameraUpdateFactory.newCameraPosition(cameraPosition);
                    mAMap.moveCamera(cameraUpdate);
                }
            });
        }
    }

    @Override
    public void getErrorMsg(String msg) {
        ToastUtils.showSingleToast(msg);
    }

    @Override
    public void getSingleData(List<AllMessageDeviceBean> allMessageDeviceBeans) {
        if (allMessageDeviceBeans!=null){
            LocationDialogFragment.newInstance(allMessageDeviceBeans).setOutCancel(true).show(getChildFragmentManager());
        }

    }

    @Override
    public void onMapLoaded() {

        mPresenter.getMapList(SPUtils.getInt("id")+"");
    }

    @Override
    public void onClick(Marker marker, List<ClusterItem> clusterItems) {
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        for (ClusterItem clusterItem : clusterItems) {
            builder.include(clusterItem.getPosition());
            if (clusterItems.size() == 1) {
                List<NewLocationBean> data = clusterItem.getTitle();
                AppLog.d(data.get(0).getDevId());
                List<String> devIds=new ArrayList<>();
                if (data.size()==1){
                    devIds.add(data.get(0).getDevId());
                }else{
                    devIds.add(data.get(0).getDevId());
                    devIds.add(data.get(1).getDevId());
                }
                mPresenter.getDetailData(devIds);
            }
        }
        LatLngBounds latLngBounds = builder.build();
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0));
    }


    private int mType32, mType4, mType16, mType8, mType1, mType;
    @Override
    public Drawable getDrawAble(int clusterNum, LatLng centerLatLng) {
        mType32 = mType4 = mType16 = mType8 = mType1 = mType = 0;
        if (clusterNum == 1) {
            Drawable bitmapDrawable = mBackDrawAbles.get(1);
            for (int i = 0; i < listData.size(); i++) {
                onlineStatus = listData.get(i).get(0).getOnlineStatus();
                devStatus = listData.get(i).get(0).getDevStatus();
                reportType = listData.get(i).get(0).getReportType().split(",");
                if (Double.valueOf(listData.get(i).get(0).getLatitude()).equals(centerLatLng.latitude) && Double.valueOf(listData.get(i).get(0).getLongitude()).equals(centerLatLng.longitude)) {
                    if (onlineStatus.equals("1")) {
                        if (devStatus.equals("0")) {
                            for (int j = 0; j < reportType.length; j++) {
                                if (reportType[j].equals("32")) {
                                    mType32++;
                                } else if (reportType[j].equals("4")) {
                                    mType4++;
                                } else if (reportType[j].equals("16")) {
                                    mType16++;
                                } else if (reportType[j].equals("8")) {
                                    mType8++;
                                } else if (reportType[j].equals("1")) {
                                    mType1++;
                                } else {
                                    mType++;
                                }
                            }
                        } else {
                            bitmapDrawable = getResources().getDrawable(R.mipmap.icon_guzhang);
                        }
                    } else if (onlineStatus.equals("3")) {
                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_lixian);
                    }
                    if (mType32 != 0) {
                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_guzhang);
                    } else if (mType4 != 0) {
                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_chaowen);
                    } else if (mType16 != 0) {
                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_qianya);
                    } else if (mType8 != 0) {
                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_qingxie);
                    } else if (mType1 != 0) {
                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_lajiyiman);
                    } else if (mType != 0) {
                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_zhengchang);
                    }
                    mBackDrawAbles.put(1, bitmapDrawable);
                    return bitmapDrawable;
                }
            }
            return bitmapDrawable;
        } else if (clusterNum < 5) {
            Drawable bitmapDrawable = mBackDrawAbles.get(2);
            if (bitmapDrawable == null) {
                bitmapDrawable = getResources().getDrawable(R.mipmap.m2);
                mBackDrawAbles.put(2, bitmapDrawable);
            }
            return bitmapDrawable;
        } else if (clusterNum < 10) {
            Drawable bitmapDrawable = mBackDrawAbles.get(3);
            if (bitmapDrawable == null) {
                bitmapDrawable = getResources().getDrawable(R.mipmap.m3);
                mBackDrawAbles.put(3, bitmapDrawable);
            }
            return bitmapDrawable;
        } else {
            Drawable bitmapDrawable = mBackDrawAbles.get(4);
            if (bitmapDrawable == null) {
                bitmapDrawable = getResources().getDrawable(R.mipmap.m4);
                mBackDrawAbles.put(4, bitmapDrawable);
            }
            return bitmapDrawable;
        }
    }

    public void onResume() {
        super.onResume();
        mMapView.onResume();
    }

    public void onPause() {
        super.onPause();
        mMapView.onPause();
    }

    public void onDestroy() {
        super.onDestroy();
        //销毁资源
        if (mClusterOverlay!=null){
            mClusterOverlay.onDestroy();
        }

        if (mMapView!=null){
            mMapView.onDestroy();
        }
    }

    public static List<List<NewLocationBean>> getData() {
        return listData;
    }
}
