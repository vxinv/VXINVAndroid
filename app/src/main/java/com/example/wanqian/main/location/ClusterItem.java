package com.example.wanqian.main.location;

import com.amap.api.maps.model.LatLng;
import com.example.wanqian.bean.NewLocationBean;
import com.example.wanqian.main.location.bean.LocationBean;

import java.util.List;

public interface ClusterItem {
    /**
     * 返回聚合元素的地理位置
     *
     * @return
     */
    LatLng getPosition();

    List<NewLocationBean> getTitle();
}