package com.example.wanqian.main.current.bean;

import java.io.Serializable;
import java.util.List;

public class CurrentBean implements Serializable {

    /**
     * msg : 接受成功
     * code : 200
     * data : [{"area":0,"garbageId":0,"groupId":0,"latitude":40.007187,"full":0,"longitude":116.397209},{"area":28,"garbageId":-1,"groupId":0,"latitude":40.004955,"full":0,"longitude":116.396798},{"area":28,"garbageId":218,"groupId":218,"latitude":40.004646834285225,"full":1,"longitude":116.39685303064961},{"area":28,"garbageId":217,"groupId":217,"latitude":40.00377374958248,"full":1,"longitude":116.39676086718497},{"area":28,"garbageId":216,"groupId":216,"latitude":40.00326369678728,"full":1,"longitude":116.39670076794259},{"area":28,"garbageId":215,"groupId":215,"latitude":40.00264768509938,"full":1,"longitude":116.39672970555259},{"area":28,"garbageId":-1,"groupId":0,"latitude":40.002495,"full":0,"longitude":116.396615}]
     */

    private String msg;
    private int code;
    private List<DataBean> data;

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * area : 0
         * garbageId : 0
         * groupId : 0
         * latitude : 40.007187
         * full : 0
         * longitude : 116.397209
         */

        private int area;
        private int garbageId;
        private int groupId;
        private double latitude;
        private int full;
        private double longitude;

        public int getArea() {
            return area;
        }

        public void setArea(int area) {
            this.area = area;
        }

        public int getGarbageId() {
            return garbageId;
        }

        public void setGarbageId(int garbageId) {
            this.garbageId = garbageId;
        }

        public int getGroupId() {
            return groupId;
        }

        public void setGroupId(int groupId) {
            this.groupId = groupId;
        }

        public double getLatitude() {
            return latitude;
        }

        public void setLatitude(double latitude) {
            this.latitude = latitude;
        }

        public int getFull() {
            return full;
        }

        public void setFull(int full) {
            this.full = full;
        }

        public double getLongitude() {
            return longitude;
        }

        public void setLongitude(double longitude) {
            this.longitude = longitude;
        }
    }
}
