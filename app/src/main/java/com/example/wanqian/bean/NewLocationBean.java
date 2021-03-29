package com.example.wanqian.bean;

import java.io.Serializable;

public class NewLocationBean implements Serializable {
    private String devId;
    private String devStatus;
    private String deviceId;
    private String eventTime;
    private int groupId;
    private String latitude;
    private String longitude;
    private String onlineStatus;
    private String reportType;
    private String rubbishHeight;
    private String rubbishL;

    public String getDevId() {
        return devId;
    }

    public void setDevId(String devId) {
        this.devId = devId;
    }

    public String getDevStatus() {
        return devStatus;
    }

    public void setDevStatus(String devStatus) {
        this.devStatus = devStatus;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getEventTime() {
        return eventTime;
    }

    public void setEventTime(String eventTime) {
        this.eventTime = eventTime;
    }

    public int getGroupId() {
        return groupId;
    }

    public void setGroupId(int groupId) {
        this.groupId = groupId;
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

    public String getOnlineStatus() {
        return onlineStatus;
    }

    public void setOnlineStatus(String onlineStatus) {
        this.onlineStatus = onlineStatus;
    }

    public String getReportType() {
        return reportType;
    }

    public void setReportType(String reportType) {
        this.reportType = reportType;
    }

    public String getRubbishHeight() {
        return rubbishHeight;
    }

    public void setRubbishHeight(String rubbishHeight) {
        this.rubbishHeight = rubbishHeight;
    }

    public String getRubbishL() {
        return rubbishL;
    }

    public void setRubbishL(String rubbishL) {
        this.rubbishL = rubbishL;
    }
}
