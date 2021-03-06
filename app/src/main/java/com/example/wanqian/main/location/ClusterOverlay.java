package com.example.wanqian.main.location;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.HandlerThread;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.widget.TextView;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.Marker;
import com.example.wanqian.R;

import java.util.ArrayList;
import java.util.List;

import android.os.Handler;
import android.util.LruCache;

import com.amap.api.maps.AMap;
import com.amap.api.maps.AMapUtils;
import com.amap.api.maps.model.BitmapDescriptor;
import com.amap.api.maps.model.BitmapDescriptorFactory;
import com.amap.api.maps.model.CameraPosition;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.model.MarkerOptions;
import com.amap.api.maps.model.animation.AlphaAnimation;
import com.amap.api.maps.model.animation.Animation;
import com.example.wanqian.bean.NewLocationBean;
import com.example.wanqian.fragment.LocationFragment;
import com.example.wanqian.main.location.bean.LocationBean;

public class ClusterOverlay implements AMap.OnCameraChangeListener,
        AMap.OnMarkerClickListener {
    private static final String TAG = "ClusterOverlay";
    private AMap mAMap;
    private Context mContext;
    private List<ClusterItem> mClusterItems;
    private List<Cluster> mClusters;
    private int mClusterSize;
    private ClusterClickListener mClusterClickListener;
    private ClusterRender mClusterRender;
    private List<Marker> mAddMarkers = new ArrayList<Marker>();
    private double mClusterDistance;
    private LruCache<LatLng, BitmapDescriptor> mLruCache;
    private LruCache<Integer, BitmapDescriptor> mLruCaches;
    private HandlerThread mMarkerHandlerThread = new HandlerThread("addMarker");
    private HandlerThread mSignClusterThread = new HandlerThread("calculateCluster");
    private Handler mMarkerhandler;
    private Handler mSignClusterHandler;
    private float mPXInMeters;
    private boolean mIsCanceled = false;
    private String onlineStatus;
    private String devStatus;
    private String reportType;

    /**
     * ????????????
     *
     * @param amap
     * @param clusterSize ???????????????????????????????????????????????????????????????????????????????????????
     * @param context
     */
    public ClusterOverlay(AMap amap, int clusterSize, Context context) {
        this(amap, null, clusterSize, context);


    }

    /**
     * ????????????,???????????????????????????,?????????????????????
     *
     * @param amap
     * @param clusterItems ????????????
     * @param clusterSize
     * @param context
     */
    public ClusterOverlay(AMap amap, List<ClusterItem> clusterItems,
                          int clusterSize, Context context) {
//?????????????????????80???????????????????????????????????????,???????????????????????????app??????????????????,??????????????????
        mLruCache = new LruCache<LatLng, BitmapDescriptor>(80) {
            protected void entryRemoved(boolean evicted, Integer key, BitmapDescriptor oldValue, BitmapDescriptor newValue) {
                oldValue.getBitmap().recycle();
            }
        };
        mLruCaches = new LruCache<Integer, BitmapDescriptor>(80) {
            protected void entryRemoved(boolean evicted, Integer key, BitmapDescriptor oldValue, BitmapDescriptor newValue) {
                oldValue.getBitmap().recycle();
            }
        };
        if (clusterItems != null) {
            mClusterItems = clusterItems;
        } else {
            mClusterItems = new ArrayList<ClusterItem>();
        }
        mContext = context;
        mClusters = new ArrayList<Cluster>();
        this.mAMap = amap;
        mClusterSize = clusterSize;
        mPXInMeters = mAMap.getScalePerPixel();
        mClusterDistance = mPXInMeters * mClusterSize;
        amap.setOnCameraChangeListener(this);
        amap.setOnMarkerClickListener(this);
        initThreadHandler();
        assignClusters();
    }

    /**
     * ??????????????????????????????
     *
     * @param clusterClickListener
     */
    public void setOnClusterClickListener(
            ClusterClickListener clusterClickListener) {
        mClusterClickListener = clusterClickListener;
    }

    /**
     * ?????????????????????
     *
     * @param item
     */
    public void addClusterItem(ClusterItem item) {
        Message message = Message.obtain();
        message.what = SignClusterHandler.CALCULATE_SINGLE_CLUSTER;
        message.obj = item;
        mSignClusterHandler.sendMessage(message);
    }

    /**
     * ??????????????????????????????????????????????????????????????????????????????????????????
     *
     * @param render
     */
    public void setClusterRenderer(ClusterRender render) {
        mClusterRender = render;
    }

    public void onDestroy() {
        mIsCanceled = true;
        mSignClusterHandler.removeCallbacksAndMessages(null);
        mMarkerhandler.removeCallbacksAndMessages(null);
        mSignClusterThread.quit();
        mMarkerHandlerThread.quit();
        for (Marker marker : mAddMarkers) {
            marker.remove();
        }
        mAddMarkers.clear();
        mLruCache.evictAll();
        mLruCaches.evictAll();
    }

    //?????????Handler
    private void initThreadHandler() {
        mMarkerHandlerThread.start();
        mSignClusterThread.start();
        mMarkerhandler = new MarkerHandler(mMarkerHandlerThread.getLooper());
        mSignClusterHandler = new SignClusterHandler(mSignClusterThread.getLooper());
    }

    @Override
    public void onCameraChange(CameraPosition arg0) {

    }

    @Override
    public void onCameraChangeFinish(CameraPosition arg0) {
        mPXInMeters = mAMap.getScalePerPixel();
        mClusterDistance = mPXInMeters * mClusterSize;
        assignClusters();
    }

    //????????????
    @Override
    public boolean onMarkerClick(Marker arg0) {
        if (mClusterClickListener == null) {
            return true;
        }
        Cluster cluster = (Cluster) arg0.getObject();
        if (cluster != null) {
            mClusterClickListener.onClick(arg0, cluster.getClusterItems());
            return true;
        }
        return false;
    }


    /**
     * ?????????????????????????????????
     */
    private void addClusterToMap(List<Cluster> clusters) {

        ArrayList<Marker> removeMarkers = new ArrayList<>();
        removeMarkers.addAll(mAddMarkers);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1, 0);
        MyAnimationListener myAnimationListener = new MyAnimationListener(removeMarkers);
        for (Marker marker : removeMarkers) {
            marker.setAnimation(alphaAnimation);
            marker.setAnimationListener(myAnimationListener);
            marker.startAnimation();
        }

        for (Cluster cluster : clusters) {
            addSingleClusterToMap(cluster);
        }
    }

    private AlphaAnimation mADDAnimation = new AlphaAnimation(0, 1);

    /**
     * ??????????????????????????????????????????
     *
     * @param cluster
     */
    private void addSingleClusterToMap(Cluster cluster) {
        LatLng latlng = cluster.getCenterLatLng();
        MarkerOptions markerOptions = new MarkerOptions();
        markerOptions.anchor(0.5f, 0.5f)
                .icon(getBitmapDes(cluster.getClusterCount(), latlng)).position(latlng);
        Marker marker = mAMap.addMarker(markerOptions);
        marker.setAnimation(mADDAnimation);
        marker.setObject(cluster);
        marker.startAnimation();
        cluster.setMarker(marker);
        mAddMarkers.add(marker);
    }

    private void calculateClusters() {
        mIsCanceled = false;
        mClusters.clear();
        LatLngBounds visibleBounds = mAMap.getProjection().getVisibleRegion().latLngBounds;
        for (ClusterItem clusterItem : mClusterItems) {
            if (mIsCanceled) {
                return;
            }
            LatLng latlng = clusterItem.getPosition();
            if (visibleBounds.contains(latlng)) {
                Cluster cluster = getCluster(latlng, mClusters);
                if (cluster != null) {
                    cluster.addClusterItem(clusterItem);
                } else {
                    cluster = new Cluster(latlng);
                    mClusters.add(cluster);
                    cluster.addClusterItem(clusterItem);
                }
            }
        }

        //?????????????????????????????????
        List<Cluster> clusters = new ArrayList<Cluster>();
        clusters.addAll(mClusters);
        Message message = Message.obtain();
        message.what = MarkerHandler.ADD_CLUSTER_LIST;
        message.obj = clusters;
        if (mIsCanceled) {
            return;
        }
        mMarkerhandler.sendMessage(message);
    }

    /**
     * ??????????????????
     */
    private void assignClusters() {
        mIsCanceled = true;
        mSignClusterHandler.removeMessages(SignClusterHandler.CALCULATE_CLUSTER);
        mSignClusterHandler.sendEmptyMessage(SignClusterHandler.CALCULATE_CLUSTER);
    }

    /**
     * ??????????????????????????????????????????????????????????????????
     *
     * @param clusterItem
     */
    private void calculateSingleCluster(ClusterItem clusterItem) {
        LatLngBounds visibleBounds = mAMap.getProjection().getVisibleRegion().latLngBounds;
        LatLng latlng = clusterItem.getPosition();
        if (!visibleBounds.contains(latlng)) {
            return;
        }
        Cluster cluster = getCluster(latlng, mClusters);
        if (cluster != null) {
            cluster.addClusterItem(clusterItem);
            Message message = Message.obtain();
            message.what = MarkerHandler.UPDATE_SINGLE_CLUSTER;
            message.obj = cluster;
            mMarkerhandler.removeMessages(MarkerHandler.UPDATE_SINGLE_CLUSTER);
            mMarkerhandler.sendMessageDelayed(message, 5);
        } else {
            cluster = new Cluster(latlng);
            mClusters.add(cluster);
            cluster.addClusterItem(clusterItem);
            Message message = Message.obtain();
            message.what = MarkerHandler.ADD_SINGLE_CLUSTER;
            message.obj = cluster;
            mMarkerhandler.sendMessage(message);
        }
    }

    /**
     * ?????????????????????????????????????????????????????????????????????null
     *
     * @param latLng
     * @return
     */
    private Cluster getCluster(LatLng latLng, List<Cluster> clusters) {
        for (Cluster cluster : clusters) {
            LatLng clusterCenterPoint = cluster.getCenterLatLng();
            double distance = AMapUtils.calculateLineDistance(latLng, clusterCenterPoint);
            if (distance < mClusterDistance && mAMap.getCameraPosition().zoom < 19) {
                return cluster;
            }
        }
        return null;
    }

    /**
     * ????????????????????????????????????
     */
    private BitmapDescriptor getBitmapDes(int num, LatLng centerLatLng) {
        if (num == 1) {
            BitmapDescriptor bitmapDescriptor = mLruCache.get(centerLatLng);
            if (bitmapDescriptor == null) {
                TextView textView = new TextView(mContext);
                List<List<NewLocationBean>> data = LocationFragment.getData();
                int size = data.size();
                if (num > 1) {
                    String tile = String.valueOf(num);
                    textView.setText(tile + "/" + size);
                }
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                Drawable drawAble = mClusterRender.getDrawAble(num, centerLatLng);
                if (mClusterRender != null && drawAble != null) {
                    textView.setBackgroundDrawable(drawAble);
                } else {
                    textView.setBackgroundResource(R.mipmap.m1);
                }
                bitmapDescriptor = BitmapDescriptorFactory.fromView(textView);
                if (bitmapDescriptor != null)
                    mLruCache.put(centerLatLng, bitmapDescriptor);
            }
            return bitmapDescriptor;
        } else {
            BitmapDescriptor bitmapDescriptor = mLruCaches.get(num);
            if (bitmapDescriptor == null) {
                TextView textView = new TextView(mContext);
                List<List<NewLocationBean>> data = LocationFragment.getData();
                int size = data.size();
                if (num > 1) {
                    String tile = String.valueOf(num);
                    textView.setText(tile + "/" + size);
                }
                textView.setGravity(Gravity.CENTER);
                textView.setTextColor(Color.WHITE);
                textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
                Drawable drawAble = mClusterRender.getDrawAble(num, centerLatLng);
                if (mClusterRender != null && drawAble != null) {
                    textView.setBackgroundDrawable(drawAble);
                } else {
                    textView.setBackgroundResource(R.mipmap.m1);
                }
                bitmapDescriptor = BitmapDescriptorFactory.fromView(textView);
                if (bitmapDescriptor != null)
                    mLruCaches.put(num, bitmapDescriptor);
            }
            return bitmapDescriptor;
        }
    }

    /**
     * ???????????????????????????????????????
     */
    private void updateCluster(Cluster cluster) {
        Marker marker = cluster.getMarker();
        marker.setIcon(getBitmapDes(cluster.getClusterCount(), cluster.getCenterLatLng()));
    }


//-----------------------??????????????????---------------------------------------------

    /**
     * marker?????????????????????????????????Marker??????
     */
    class MyAnimationListener implements Animation.AnimationListener {
        private List<Marker> mRemoveMarkers;

        MyAnimationListener(List<Marker> removeMarkers) {
            mRemoveMarkers = removeMarkers;
        }

        @Override
        public void onAnimationStart() {

        }

        @Override
        public void onAnimationEnd() {
            for (Marker marker : mRemoveMarkers) {
                marker.remove();
            }
            mRemoveMarkers.clear();
        }
    }

    /**
     * ??????market????????????????????????
     */
    class MarkerHandler extends Handler {

        static final int ADD_CLUSTER_LIST = 0;

        static final int ADD_SINGLE_CLUSTER = 1;

        static final int UPDATE_SINGLE_CLUSTER = 2;

        MarkerHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {

            switch (message.what) {
                case ADD_CLUSTER_LIST:
                    List<Cluster> clusters = (List<Cluster>) message.obj;
                    addClusterToMap(clusters);
                    break;
                case ADD_SINGLE_CLUSTER:
                    Cluster cluster = (Cluster) message.obj;
                    addSingleClusterToMap(cluster);
                    break;
                case UPDATE_SINGLE_CLUSTER:
                    Cluster updateCluster = (Cluster) message.obj;
                    updateCluster(updateCluster);
                    break;
            }
        }
    }

    /**
     * ???????????????????????????
     */
    class SignClusterHandler extends Handler {
        static final int CALCULATE_CLUSTER = 0;
        static final int CALCULATE_SINGLE_CLUSTER = 1;

        SignClusterHandler(Looper looper) {
            super(looper);
        }

        public void handleMessage(Message message) {
            switch (message.what) {
                case CALCULATE_CLUSTER:
                    calculateClusters();
                    break;
                case CALCULATE_SINGLE_CLUSTER:
                    ClusterItem item = (ClusterItem) message.obj;
                    mClusterItems.add(item);
                    Log.i("yiyi.qi", "calculate single cluster");
                    calculateSingleCluster(item);
                    break;
            }
        }
    }
}


//public class ClusterOverlay implements AMap.OnCameraChangeListener, AMap.OnMarkerClickListener {
//    private AMap mAMap;
//    private Context mContext;
//    private List<ClusterItem> mClusterItems;
//    private List<Cluster> mClusters;
//    private int mClusterSize;
//    private ClusterClickListener mClusterClickListener;
//    private ClusterRender mClusterRender;
//    private List<Marker> mAddMarkers = new ArrayList<Marker>();
//    private double mClusterDistance;
//    private LruCache<Integer, BitmapDescriptor> mLruCache;
//    private HandlerThread mMarkerHandlerThread = new HandlerThread("addMarker");
//    private HandlerThread mSignClusterThread = new HandlerThread("calculateCluster");
//    private Handler mMarkerhandler;
//    private Handler mSignClusterHandler;
//    private float mPXInMeters;
//    private boolean mIsCanceled = false;
//
//    /**
//     * ????????????
//     *
//     * @param amap
//     * @param clusterSize ???????????????????????????????????????????????????????????????????????????????????????
//     * @param context
//     */
//    public ClusterOverlay(AMap amap, int clusterSize, Context context) {
//        this(amap, null, clusterSize, context);
//
//
//    }
//
//    /**
//     * ????????????,???????????????????????????,?????????????????????
//     *
//     * @param amap
//     * @param clusterItems ????????????
//     * @param clusterSize
//     * @param context
//     */
//    public ClusterOverlay(AMap amap, List<ClusterItem> clusterItems,
//                          int clusterSize, Context context) {
////?????????????????????80???????????????????????????????????????,???????????????????????????app??????????????????,??????????????????
//        mLruCache = new LruCache<Integer, BitmapDescriptor>(80) {
//            protected void entryRemoved(boolean evicted, Integer key, BitmapDescriptor oldValue, BitmapDescriptor newValue) {
//                oldValue.getBitmap().recycle();
//            }
//        };
//        if (clusterItems != null) {
//            mClusterItems = clusterItems;
//        } else {
//            mClusterItems = new ArrayList<ClusterItem>();
//        }
//        mContext = context;
//        mClusters = new ArrayList<Cluster>();
//        this.mAMap = amap;
//        mClusterSize = clusterSize;
//        mPXInMeters = mAMap.getScalePerPixel();
//        mClusterDistance = mPXInMeters * mClusterSize;
//        amap.setOnCameraChangeListener(this);
//        amap.setOnMarkerClickListener(this);
//        initThreadHandler();
//        assignClusters();
//    }
//
//    /**
//     * ??????????????????????????????
//     *
//     * @param clusterClickListener
//     */
//    public void setOnClusterClickListener(
//            ClusterClickListener clusterClickListener) {
//        mClusterClickListener = clusterClickListener;
//    }
//
//    /**
//     * ?????????????????????
//     *
//     * @param item
//     */
//    public void addClusterItem(ClusterItem item) {
//        Message message = Message.obtain();
//        message.what = SignClusterHandler.CALCULATE_SINGLE_CLUSTER;
//        message.obj = item;
//        mSignClusterHandler.sendMessage(message);
//    }
//
//    /**
//     * ??????????????????????????????????????????????????????????????????????????????????????????
//     *
//     * @param render
//     */
//    public void setClusterRenderer(ClusterRender render) {
//        mClusterRender = render;
//    }
//
//    public void onDestroy() {
//        mIsCanceled = true;
//        mSignClusterHandler.removeCallbacksAndMessages(null);
//        mMarkerhandler.removeCallbacksAndMessages(null);
//        mSignClusterThread.quit();
//        mMarkerHandlerThread.quit();
//        for (Marker marker : mAddMarkers) {
//            marker.remove();
//
//        }
//        mAddMarkers.clear();
//        mLruCache.evictAll();
//    }
//
//    //?????????Handler
//    private void initThreadHandler() {
//        mMarkerHandlerThread.start();
//        mSignClusterThread.start();
//        mMarkerhandler = new MarkerHandler(mMarkerHandlerThread.getLooper());
//        mSignClusterHandler = new SignClusterHandler(mSignClusterThread.getLooper());
//    }
//
//    @Override
//    public void onCameraChange(CameraPosition arg0) {
//
//    }
//
//    @Override
//    public void onCameraChangeFinish(CameraPosition arg0) {
//        mPXInMeters = mAMap.getScalePerPixel();
//        mClusterDistance = mPXInMeters * mClusterSize;
//        assignClusters();
//    }
//
//    //????????????
//    @Override
//    public boolean onMarkerClick(Marker arg0) {
//        if (mClusterClickListener == null) {
//            return true;
//        }
//       Cluster cluster= (Cluster) arg0.getObject();
//        if(cluster!=null){
//            mClusterClickListener.onClick(arg0,cluster.getClusterItems());
//            return true;
//        }
//        return false;
//    }
//
//    /**
//     * ?????????????????????????????????
//     */
//    private void addClusterToMap(List<Cluster> clusters) {
//
//        ArrayList<Marker> removeMarkers = new ArrayList<>();
//        removeMarkers.addAll(mAddMarkers);
//        AlphaAnimation alphaAnimation=new AlphaAnimation(1, 0);
//        MyAnimationListener myAnimationListener=new MyAnimationListener(removeMarkers);
//        for (Marker marker : removeMarkers) {
//            marker.setAnimation(alphaAnimation);
//            marker.setAnimationListener(myAnimationListener);
//            marker.startAnimation();
//        }
//
//        for (Cluster cluster : clusters) {
//            addSingleClusterToMap(cluster);
//        }
//    }
//
//    private AlphaAnimation mADDAnimation=new AlphaAnimation(0, 1);
//    /**
//     * ??????????????????????????????????????????
//     *
//     * @param cluster
//     */
//    private void addSingleClusterToMap(Cluster cluster) {
//        LatLng latlng = cluster.getCenterLatLng();
//        MarkerOptions markerOptions = new MarkerOptions();
//        markerOptions.anchor(0.5f, 0.5f)
//                .icon(getBitmapDes(cluster.getClusterCount())).position(latlng);
//        Marker marker = mAMap.addMarker(markerOptions);
//        marker.setAnimation(mADDAnimation);
//        marker.setObject(cluster);
//
//        marker.startAnimation();
//        cluster.setMarker(marker);
//        mAddMarkers.add(marker);
//
//    }
//
//
//
//    private void calculateClusters() {
//        mIsCanceled = false;
//        mClusters.clear();
//        LatLngBounds visibleBounds = mAMap.getProjection().getVisibleRegion().latLngBounds;
//        for (ClusterItem clusterItem : mClusterItems) {
//            if (mIsCanceled) {
//                return;
//            }
//            LatLng latlng = clusterItem.getPosition();
//            if (visibleBounds.contains(latlng)) {
//                Cluster cluster = getCluster(latlng,mClusters);
//                if (cluster != null) {
//                    cluster.addClusterItem(clusterItem);
//                } else {
//                    cluster = new Cluster(latlng);
//                    mClusters.add(cluster);
//                    cluster.addClusterItem(clusterItem);
//                }
//
//            }
//        }
//
//        //?????????????????????????????????
//        List<Cluster> clusters = new ArrayList<Cluster>();
//        clusters.addAll(mClusters);
//        Message message = Message.obtain();
//        message.what = MarkerHandler.ADD_CLUSTER_LIST;
//        message.obj = clusters;
//        if (mIsCanceled) {
//            return;
//        }
//        mMarkerhandler.sendMessage(message);
//    }
//
//    /**
//     * ??????????????????
//     */
//    private void assignClusters() {
//        mIsCanceled = true;
//        mSignClusterHandler.removeMessages(SignClusterHandler.CALCULATE_CLUSTER);
//        mSignClusterHandler.sendEmptyMessage(SignClusterHandler.CALCULATE_CLUSTER);
//    }
//
//    /**
//     * ??????????????????????????????????????????????????????????????????
//     *
//     * @param clusterItem
//     */
//    private void calculateSingleCluster(ClusterItem clusterItem) {
//        LatLngBounds visibleBounds = mAMap.getProjection().getVisibleRegion().latLngBounds;
//        LatLng latlng = clusterItem.getPosition();
//        if (!visibleBounds.contains(latlng)) {
//            return;
//        }
//        Cluster cluster = getCluster(latlng,mClusters);
//        if (cluster != null) {
//            cluster.addClusterItem(clusterItem);
//            Message message = Message.obtain();
//            message.what = MarkerHandler.UPDATE_SINGLE_CLUSTER;
//
//            message.obj = cluster;
//            mMarkerhandler.removeMessages(MarkerHandler.UPDATE_SINGLE_CLUSTER);
//            mMarkerhandler.sendMessageDelayed(message, 5);
//
//        } else {
//
//            cluster = new Cluster(latlng);
//            mClusters.add(cluster);
//            cluster.addClusterItem(clusterItem);
//            Message message = Message.obtain();
//            message.what = MarkerHandler.ADD_SINGLE_CLUSTER;
//            message.obj = cluster;
//            mMarkerhandler.sendMessage(message);
//
//        }
//    }
//
//    /**
//     * ?????????????????????????????????????????????????????????????????????null
//     *
//     * @param latLng
//     * @return
//     */
//    private Cluster getCluster(LatLng latLng,List<Cluster>clusters) {
//        for (Cluster cluster : clusters) {
//            LatLng clusterCenterPoint = cluster.getCenterLatLng();
//            double distance = AMapUtils.calculateLineDistance(latLng, clusterCenterPoint);
//            if (distance < mClusterDistance && mAMap.getCameraPosition().zoom < 19) {
//                return cluster;
//            }
//        }
//
//        return null;
//    }
//
//
//    /**
//     * ????????????????????????????????????
//     */
//    private BitmapDescriptor getBitmapDes(int num) {
//        BitmapDescriptor bitmapDescriptor = mLruCache.get(num);
//        if (bitmapDescriptor == null) {
//            TextView textView = new TextView(mContext);
////            View view = View.inflate(mContext, R.layout.fenshu, null);
////            TextView textView = view.findViewById(R.id.tv1);
//            if (num > 1) {
//                String tile = String.valueOf(num);
//                textView.setText(tile+"/"+1000);
////                textView.getPaint().setFlags(Paint. UNDERLINE_TEXT_FLAG );
//            }
//            textView.setGravity(Gravity.CENTER);
////            textView.setGravity(Gravity.CENTER_HORIZONTAL);
//            textView.setTextColor(Color.BLACK);
//            textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 15);
//            if (mClusterRender != null && mClusterRender.getDrawAble(num) != null) {
//                textView.setBackgroundDrawable(mClusterRender.getDrawAble(num));
//            } else {
//                textView.setBackgroundResource(R.drawable.ic_launcher_background);
//            }
//            bitmapDescriptor = BitmapDescriptorFactory.fromView(textView);
//            mLruCache.put(num, bitmapDescriptor);
//        }
//        return bitmapDescriptor;
//    }
//
//    /**
//     * ???????????????????????????????????????
//     */
//    private void updateCluster(Cluster cluster) {
//
//            Marker marker = cluster.getMarker();
//            marker.setIcon(getBitmapDes(cluster.getClusterCount()));
//
//    }
//
////-----------------------??????????????????---------------------------------------------
//
//    /**
//     * marker?????????????????????????????????Marker??????
//     */
//    class MyAnimationListener implements Animation.AnimationListener {
//        private  List<Marker> mRemoveMarkers ;
//
//        MyAnimationListener(List<Marker> removeMarkers) {
//            mRemoveMarkers = removeMarkers;
//        }
//
//        @Override
//        public void onAnimationStart() {
//
//        }
//
//        @Override
//        public void onAnimationEnd() {
//            for(Marker marker:mRemoveMarkers){
//                marker.remove();
//            }
//            mRemoveMarkers.clear();
//        }
//    }
//
//    /**
//     * ??????market????????????????????????
//     */
//    class MarkerHandler extends Handler {
//
//        static final int ADD_CLUSTER_LIST = 0;
//
//        static final int ADD_SINGLE_CLUSTER = 1;
//
//        static final int UPDATE_SINGLE_CLUSTER = 2;
//
//        MarkerHandler(Looper looper) {
//            super(looper);
//        }
//
//        public void handleMessage(Message message) {
//
//            switch (message.what) {
//                case ADD_CLUSTER_LIST:
//                    List<Cluster> clusters = (List<Cluster>) message.obj;
//                    addClusterToMap(clusters);
//                    break;
//                case ADD_SINGLE_CLUSTER:
//                    Cluster cluster = (Cluster) message.obj;
//                    addSingleClusterToMap(cluster);
//                    break;
//                case UPDATE_SINGLE_CLUSTER:
//                    Cluster updateCluster = (Cluster) message.obj;
//                    updateCluster(updateCluster);
//                    break;
//            }
//        }
//    }
//
//    /**
//     * ???????????????????????????
//     */
//    class SignClusterHandler extends Handler {
//        static final int CALCULATE_CLUSTER = 0;
//        static final int CALCULATE_SINGLE_CLUSTER = 1;
//
//        SignClusterHandler(Looper looper) {
//            super(looper);
//        }
//
//        public void handleMessage(Message message) {
//            switch (message.what) {
//                case CALCULATE_CLUSTER:
//                    calculateClusters();
//                    break;
//                case CALCULATE_SINGLE_CLUSTER:
//                    ClusterItem item = (ClusterItem) message.obj;
//                    mClusterItems.add(item);
//                    Log.i("yiyi.qi","calculate single cluster");
//                    calculateSingleCluster(item);
//                    break;
//            }
//        }
//    }
//}
