package com.example.wanqian.main.location;

import java.util.List;
import com.amap.api.maps.model.Marker;

public interface ClusterClickListener{
        /**
         * 点击聚合点的回调处理函数
         *
         * @param marker
         *            点击的聚合点
         * @param clusterItems
         *            聚合点所包含的元素
         */
        void onClick(Marker marker, List<ClusterItem> clusterItems);
}