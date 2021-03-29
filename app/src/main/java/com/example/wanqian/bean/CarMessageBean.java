package com.example.wanqian.bean;

import java.io.Serializable;

public class CarMessageBean implements Serializable {
    private String carName;
    private int clearCount;
    private String name;
    private String carNum;
    private String startTime;
    private String endTime;
    private int cri;
    private int status;

    public String getCarName() {
        return carName;
    }

    public void setCarName(String carName) {
        this.carName = carName;
    }

    public int getClearCount() {
        return clearCount;
    }

    public void setClearCount(int clearCount) {
        this.clearCount = clearCount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCarNum() {
        return carNum;
    }

    public void setCarNum(String carNum) {
        this.carNum = carNum;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public int getCri() {
        return cri;
    }

    public void setCri(int cri) {
        this.cri = cri;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CarMessageBean{" +
                "carName='" + carName + '\'' +
                ", clearCount=" + clearCount +
                ", name='" + name + '\'' +
                ", carNum='" + carNum + '\'' +
                ", startTime='" + startTime + '\'' +
                ", endTime='" + endTime + '\'' +
                ", cri=" + cri +
                ", status=" + status +
                '}';
    }
}
