package com.example.wanqian.main.location.demo;

import com.amap.api.maps.model.LatLng;
import com.example.wanqian.bean.NewLocationBean;
import com.example.wanqian.main.location.ClusterItem;
import com.example.wanqian.main.location.bean.LocationBean;

import java.util.List;

public class RegionItem implements ClusterItem {
    private LatLng mLatLng;
    private List<NewLocationBean> data;

    public RegionItem(LatLng latLng, List<NewLocationBean> title) {
        mLatLng = latLng;
        data = title;
    }

    @Override
    public LatLng getPosition() {
        // TODO Auto-generated method stub
        return mLatLng;
    }

    public List<NewLocationBean> getTitle() {
        return data;
    }

}