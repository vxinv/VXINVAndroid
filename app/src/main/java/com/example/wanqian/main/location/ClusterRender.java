package com.example.wanqian.main.location;

import android.graphics.drawable.Drawable;

import com.amap.api.maps.model.LatLng;

public interface ClusterRender {
    /**
     * 根据聚合点的元素数目返回渲染背景样式
     *
     * @param clusterNum
     * @return
     */
    Drawable getDrawAble(int clusterNum, LatLng centerLatLng);
}