package com.example.wanqian.main.location;

import android.graphics.drawable.Drawable;

import androidx.fragment.app.Fragment;

import com.amap.api.maps.AMap;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * 设备定位
 */

public class Location_Fragment extends Fragment implements ClusterRender, AMap.OnMapLoadedListener, ClusterClickListener {
    @Override
    public void onMapLoaded() {

    }

    @Override
    public void onClick(Marker marker, List<ClusterItem> clusterItems) {

    }

    @Override
    public Drawable getDrawAble(int clusterNum, LatLng centerLatLng) {
        return null;
    }

//    private static final String TAG = "Location_Fragment";
//    private MapView mMapView;
//    private AMap mAMap;
//    private int clusterRadius = 100;
//    private Map<Integer, Drawable> mBackDrawAbles = new HashMap<Integer, Drawable>();
//    private ClusterOverlay mClusterOverlay;
//    public static List<List<LocationBean.DataBean>> data;
//    private String onlineStatus;
//    private String devStatus;
//    private TextView text;
//    private String[] reportType;
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//        // Inflate the layout for this fragment
//        View view = inflater.inflate(R.layout.fragment_location_, container, false);
//        mMapView = (MapView) view.findViewById(R.id.map);
//        text = (TextView) view.findViewById(R.id.text);
//        mMapView.onCreate(savedInstanceState);
//        init();
//        return view;
//    }
//
//    private void init() {
//        if (mAMap == null) {
//            // 初始化地图
//            mAMap = mMapView.getMap();
//            mAMap.moveCamera(CameraUpdateFactory.zoomTo(10));
//            mAMap.setOnMapLoadedListener(this);
//        }
//    }
//
//    private void initData() {
//        Retrofit retrofit = new Retrofit.Builder()
//                .baseUrl(Myapi.url)
//                .addConverterFactory(GsonConverterFactory.create())
//                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
//                .build();
//        LocationService service = retrofit.create(LocationService.class);
//        Observable<LocationBean> beanObservable = service.locationpostFiled(SPUtils.getInt("id")+"");
//        beanObservable.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<LocationBean>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//                        Log.d(TAG, "onSubscribe: " + d.toString());
//                    }
//
//                    @Override
//                    public void onNext(LocationBean locationBean) {
//                        data = locationBean.getData();
//                        List<ClusterItem> items = new ArrayList<ClusterItem>();
//                        if (data != null) {
//                            for (int i = 0; i < data.size(); i++) {
//                                String latitude = data.get(i).get(0).getLatitude();
//                                String longitude = data.get(i).get(0).getLongitude();
//                                LatLng latLng = new LatLng(Double.parseDouble(latitude), Double.parseDouble(longitude));
//                                RegionItem regionItem = new RegionItem(latLng, data.get(i));
//                                items.add(regionItem);
//                            }
//                            mClusterOverlay = new ClusterOverlay(mAMap, items, dp2px(getContext(), clusterRadius), getContext());
//                            mClusterOverlay.setClusterRenderer(Location_Fragment.this);
//                            mClusterOverlay.setOnClusterClickListener(Location_Fragment.this);
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        Log.d(TAG, "onError: " + e.getMessage());
//                    }
//
//                    @Override
//                    public void onComplete() {
//                        Log.d(TAG, "onComplete: ");
//                    }
//                });
//    }
//
//    @Override
//    public void onMapLoaded() {
//        //添加测试数据
//        new Thread() {
//            public void run() {
//                initData();
//            }
//        }.start();
//
//    }
//
//    @Override
//    public void onClick(Marker marker, List<ClusterItem> clusterItems) {
//
//        LatLngBounds.Builder builder = new LatLngBounds.Builder();
//        for (ClusterItem clusterItem : clusterItems) {
//            builder.include(clusterItem.getPosition());
//            if (clusterItems.size() == 1) {
//                List<LocationBean.DataBean> data = clusterItem.getTitle();
//                LocationPopWindow popWindow=new LocationPopWindow(getContext(),data.get(0));
//                popWindow.showAtLocation(mMapView, Gravity.TOP,0, ScreenUtils.dp2px(getContext(),115));
//                //makePopupWindow(text, data.getDevId(), data.getLatitude() + "---" + data.getLongitude());
//            }
//        }
//        LatLngBounds latLngBounds = builder.build();
//        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(latLngBounds, 0));
//    }
//
//    /**
//     * 创建一个PopupWindow
//     * @param parent
//     * @param text1
//     * @param text2
//     */
//    private void makePopupWindow(TextView parent, String text1, String text2) {
//        LayoutInflater inflater = (LayoutInflater)
//                getActivity().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View vPopWindow = inflater.inflate(R.layout.custom_pup_layout, null, false);
//        PopupWindow popWindow = new PopupWindow(vPopWindow, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT, true);
//        popWindow.setFocusable(true); //设置PopupWindow可获得焦点
//        popWindow.setTouchable(true); //设置PopupWindow可触摸
//        popWindow.setOutsideTouchable(true); //设置非PopupWindow区域可触摸
//
//        popWindow.showAtLocation(parent,Gravity.CENTER, 0, 0);
//    }
//
//    private int mType32, mType4, mType16, mType8, mType1, mType;
//
//    @Override
//    public Drawable getDrawAble(int clusterNum, LatLng centerLatLng) {
//        mType32 = mType4 = mType16 = mType8 = mType1 = mType = 0;
//        if (clusterNum == 1) {
//            Drawable bitmapDrawable = mBackDrawAbles.get(1);
//            for (int i = 0; i < data.size(); i++) {
//                onlineStatus = data.get(i).get(0).getOnlineStatus();
//                devStatus = data.get(i).get(0).getDevStatus();
//                reportType = data.get(i).get(0).getReportType().split(",");
//                if (Double.valueOf(data.get(i).get(0).getLatitude()).equals(centerLatLng.latitude) && Double.valueOf(data.get(i).get(0).getLongitude()).equals(centerLatLng.longitude)) {
//                    if (onlineStatus.equals("1")) {
//                        if (devStatus.equals("0")) {
//                            for (int j = 0; j < reportType.length; j++) {
//                                if (reportType[j].equals("32")) {
//                                    mType32++;
//                                } else if (reportType[j].equals("4")) {
//                                    mType4++;
//                                } else if (reportType[j].equals("16")) {
//                                    mType16++;
//                                } else if (reportType[j].equals("8")) {
//                                    mType8++;
//                                } else if (reportType[j].equals("1")) {
//                                    mType1++;
//                                } else {
//                                    mType++;
//                                }
//                            }
//                        } else {
//                            bitmapDrawable = getResources().getDrawable(R.mipmap.icon_guzhang);
//                        }
//                    } else if (onlineStatus.equals("3")) {
//                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_lixian);
//                    }
//                    if (mType32 != 0) {
//                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_guzhang);
//                    } else if (mType4 != 0) {
//                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_chaowen);
//                    } else if (mType16 != 0) {
//                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_qianya);
//                    } else if (mType8 != 0) {
//                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_qingxie);
//                    } else if (mType1 != 0) {
//                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_lajiyiman);
//                    } else if (mType != 0) {
//                        bitmapDrawable = getResources().getDrawable(R.mipmap.icon_zhengchang);
//                    }
//                    mBackDrawAbles.put(1, bitmapDrawable);
//                    return bitmapDrawable;
//                }
//            }
//            return bitmapDrawable;
//        } else if (clusterNum < 5) {
//            Drawable bitmapDrawable = mBackDrawAbles.get(2);
//            if (bitmapDrawable == null) {
//                bitmapDrawable = getResources().getDrawable(R.mipmap.m2);
//                mBackDrawAbles.put(2, bitmapDrawable);
//            }
//            return bitmapDrawable;
//        } else if (clusterNum < 10) {
//            Drawable bitmapDrawable = mBackDrawAbles.get(3);
//            if (bitmapDrawable == null) {
//                bitmapDrawable = getResources().getDrawable(R.mipmap.m3);
//                mBackDrawAbles.put(3, bitmapDrawable);
//            }
//            return bitmapDrawable;
//        } else {
//            Drawable bitmapDrawable = mBackDrawAbles.get(4);
//            if (bitmapDrawable == null) {
//                bitmapDrawable = getResources().getDrawable(R.mipmap.m4);
//                mBackDrawAbles.put(4, bitmapDrawable);
//            }
//            return bitmapDrawable;
//        }
//    }
//
//    /**
//     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
//     */
//    public int dp2px(Context context, float dpValue) {
//        final float scale = context.getResources().getDisplayMetrics().density;
//        return (int) (dpValue * scale + 0.5f);
//    }
//
//    public void onResume() {
//        super.onResume();
//        mMapView.onResume();
//    }
//
//    public void onPause() {
//        super.onPause();
//        mMapView.onPause();
//    }
//
//    public void onDestroy() {
//        super.onDestroy();
//        //销毁资源
//        mClusterOverlay.onDestroy();
//        mMapView.onDestroy();
//    }
//
//    public static List<List<LocationBean.DataBean>> getData() {
//        return data;
//    }
}
