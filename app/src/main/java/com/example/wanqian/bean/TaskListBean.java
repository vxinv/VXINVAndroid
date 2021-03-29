package com.example.wanqian.bean;

import java.io.Serializable;

public class TaskListBean implements Serializable {
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
    private boolean complete = false;
    private int type;
    private int roadIndex;

    public boolean isComplete() {
        return complete;
    }

    public int getRoadIndex() {
        return roadIndex;
    }

    public void setRoadIndex(int roadIndex) {
        this.roadIndex = roadIndex;
    }

    public void setComplete(boolean complete) {
        this.complete = complete;
    }

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

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "TaskListBean{" +
                "area=" + area +
                ", garbageId=" + garbageId +
                ", groupId=" + groupId +
                ", latitude=" + latitude +
                ", full=" + full +
                ", longitude=" + longitude +
                ", complete=" + complete +
                ", type=" + type +
                '}';
    }
}
