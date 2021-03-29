package com.example.wanqian.main.current.map;

/**
 * @author hq
 * @description: 线路点 自己继承可序列化接口
 * @date :2019/12/21 16:02
 */
public class LinePoint {
    private int area;
    private int garbageId;
    private int groupId;
    private double latitude;
    private double longitude;
    private int full;

    public LinePoint(double longitude, double latitude) {
        this.latitude = latitude;
        this.longitude = longitude;
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

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public int getFull() {
        return full;
    }

    public void setFull(int full) {
        this.full = full;
    }

    public static Point2D line2Point2D(LinePoint point) {
        return new Point2D(point.getLongitude(), point.getLatitude());
    }
}
