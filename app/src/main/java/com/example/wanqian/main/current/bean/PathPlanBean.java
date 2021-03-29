package com.example.wanqian.main.current.bean;

import java.io.Serializable;
import java.util.List;

public class PathPlanBean  {

    /**
     * av : 1.2
     * egc : 1.5
     * carInfos : [{"carNum":"京CAA123","carLon":116.385795,"carLat":39.995713}]
     */

    private double av;
    private double egc;
    private List<CarInfo> carInfos;

    public double getAv() {
        return av;
    }

    public void setAv(double av) {
        this.av = av;
    }

    public double getEgc() {
        return egc;
    }

    public void setEgc(double egc) {
        this.egc = egc;
    }

    public List<CarInfo> getCarInfos() {
        return carInfos;
    }

    public void setCarInfos(List<CarInfo> carInfos) {
        this.carInfos = carInfos;
    }

    public static class CarInfo implements Serializable {
        /**
         * carNum : 京CAA123
         * carLon : 116.385795
         * carLat : 39.995713
         */

        private String carNum;
        private double carLon;
        private double carLat;

        public String getCarNum() {
            return carNum;
        }

        public void setCarNum(String carNum) {
            this.carNum = carNum;
        }

        public double getCarLon() {
            return carLon;
        }

        public void setCarLon(double carLon) {
            this.carLon = carLon;
        }

        public double getCarLat() {
            return carLat;
        }

        public void setCarLat(double carLat) {
            this.carLat = carLat;
        }
    }
}
