package com.example.wanqian.main.zhu.utils;

import android.util.Pair;

import com.amap.api.maps.AMap;
import com.amap.api.maps.CameraUpdateFactory;
import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.maps.utils.SpatialRelationUtil;
import com.amap.api.maps.utils.overlay.SmoothMoveMarker;
import java.util.List;

public class MapBdUtils {


    //計算地圖偏移位置工具類
    public static void calShortestDistancePoint(AMap mAMap, SmoothMoveMarker smoothMarker, List<LatLng> latLngs) {
        LatLngBounds.Builder b = LatLngBounds.builder();
        for (int i = 0; i < latLngs.size(); i++) {
            b.include(latLngs.get(i));
        }
        LatLngBounds bounds = b.build();
        mAMap.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds, 100));

        // 取轨迹点的第一个点 作为 平滑移动的启动
        LatLng drivePoint = latLngs.get(0);
        Pair<Integer, LatLng> pair = SpatialRelationUtil.calShortestDistancePoint(latLngs, drivePoint);
        latLngs.set(pair.first, drivePoint);
        final List<LatLng> subList = latLngs.subList(pair.first, latLngs.size());

        // 设置轨迹点
        smoothMarker.setPoints(subList);
        // 设置平滑移动的总时间  单位  秒
        smoothMarker.setTotalDuration(100);
        // 开始移动
        smoothMarker.startSmoothMove();
        // 设置移动的监听事件  返回 距终点的距离  单位 米
        smoothMarker.setMoveListener(new SmoothMoveMarker.MoveListener() {
            @Override
            public void move(final double distance) {


            }
        });
    }

    /**
     * 根据用户的起点和终点经纬度计算两点间距离，此距离为相对较短的距离，单位米。
     * @param start 起点的坐标
     * @param end   终点的坐标
     * @return
     */
    public static double calculateLineDistance(LatLng start, LatLng end) {
        if ((start == null) || (end == null)) {
            throw new IllegalArgumentException("非法坐标值，不能为null");
        }
        double d1 = 0.01745329251994329D;
        double d2 = start.longitude;
        double d3 = start.latitude;
        double d4 = end.longitude;
        double d5 = end.latitude;
        d2 *= d1;
        d3 *= d1;
        d4 *= d1;
        d5 *= d1;
        double d6 = Math.sin(d2);
        double d7 = Math.sin(d3);
        double d8 = Math.cos(d2);
        double d9 = Math.cos(d3);
        double d10 = Math.sin(d4);
        double d11 = Math.sin(d5);
        double d12 = Math.cos(d4);
        double d13 = Math.cos(d5);
        double[] arrayOfDouble1 = new double[3];
        double[] arrayOfDouble2 = new double[3];
        arrayOfDouble1[0] = (d9 * d8);
        arrayOfDouble1[1] = (d9 * d6);
        arrayOfDouble1[2] = d7;
        arrayOfDouble2[0] = (d13 * d12);
        arrayOfDouble2[1] = (d13 * d10);
        arrayOfDouble2[2] = d11;
        double d14 = Math.sqrt((arrayOfDouble1[0] - arrayOfDouble2[0]) * (arrayOfDouble1[0] - arrayOfDouble2[0])
                + (arrayOfDouble1[1] - arrayOfDouble2[1]) * (arrayOfDouble1[1] - arrayOfDouble2[1])
                + (arrayOfDouble1[2] - arrayOfDouble2[2]) * (arrayOfDouble1[2] - arrayOfDouble2[2]));

        return (Math.asin(d14 / 2.0D) * 12742001.579854401D);
    }

}
