package com.example.wanqian.bean;

import java.io.Serializable;

public class AllMessageDeviceBean implements Serializable {
    /**
     "deviceId": "3830719d-067a-4295-bba3-2ed550c92675",
     "devId": "WQDSN3AA000A9997",
     "devName": "测试1",
     "latitude": "39.99557016276476",
     "longitude": "116.39295030204283",
     "batV": "3147",
     "temp": "2712",
     "time": "2020-02-19 11:56:00",
     "rubbishL": "560",
     "rubbishR": "0",
     "angle": "0",
     "reportType": "1,16,64",
     "imei": "000000000000001",
     "devStatus": "0",
     "onlineStatus": "1",
     "eventTime": "2020-02-19 11:56:00",
     "rubbishHeight": "120",
     "maintenanceMan": "李四",
     "connectType": "3",
     "combination": "口罩回收机构",
     "sumDeliveryTimes": "0",
     "productModel": "SWMCDB100N_G0"
     */
    String deviceId;
    String devId;
    String devName;
    String latitude;
    String longitude;
    String batV;
    String tmp;
    String time;
    String rubbishL;
    String rubbishR;
    String angle;
    String reportType;
    String imei;
    String devStatus;
    String onlineStatus;
    String eventTime;
    String rubbishHeight;
    String maintenanceMan;
    String connectType;
    String combination;
    String sumDeliveryTimes;
    String productModel;
    String signalQ;

    public String getSignalQ() {
        return signalQ;
    }

    public void setSignalQ(String signalQ) {
        this.signalQ = signalQ;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getDevName() {
        return devName;
    }

    public void setDevName(String devName) {
        this.devName = devName;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getBatV() {
        return batV;
    }

    public void setBatV(String batV) {
        this.batV = batV;
    }

    public String getTmp() {
        return tmp;
    }

    public void setTmp(String tmp) {
        this.tmp = tmp;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getRubbishL() {
        return rubbishL;
    }

    public void setRubbishL(String rubbishL) {
        this.rubbishL = rubbishL;
    }

    public String getRubbishR() {
        return rubbishR;
    }

    public void setRubbishR(String rubbishR) {
        this.rubbishR = rubbishR;
    }

    public String getAngle() {
        return angle;
    }

    public void setAngle(String angle) {
        this.angle = angle;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getImei() {
        return imei;
    }

    public void setImei(String imei) {
        this.imei = imei;
    }

    public String getDevStatus() {
        return devStatus;
    }

    public void setDevStatus(String devStatus) {
        this.devStatus = devStatus;
    }

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public String getRubbishHeight() {
        return rubbishHeight;
    }

    public void setRubbishHeight(String rubbishHeight) {
        this.rubbishHeight = rubbishHeight;
    }

    public String getMaintenanceMan() {
        return maintenanceMan;
    }

    public void setMaintenanceMan(String maintenanceMan) {
        this.maintenanceMan = maintenanceMan;
    }

    public String getConnectType() {
        return connectType;
    }

    public void setConnectType(String connectType) {
        this.connectType = connectType;
    }

    public String getCombination() {
        return combination;
    }

    public void setCombination(String combination) {
        this.combination = combination;
    }

    public String getSumDeliveryTimes() {
        return sumDeliveryTimes;
    }

    public void setSumDeliveryTimes(String sumDeliveryTimes) {
        this.sumDeliveryTimes = sumDeliveryTimes;
    }

    public String getProductModel() {
        return productModel;
    }

    public void setProductModel(String productModel) {
        this.productModel = productModel;
    }
}
