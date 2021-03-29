package com.example.wanqian.main.current.map;

/**
 * Created by hq on 2018/1/14.
 */

public class Point2D {
    public String PointName;

    public final String getPointName() {
        return PointName;
    }

    public final void setPointName(String value) {
        PointName = value;
    }

    public double X;

    public final double getX() {
        return X;
    }

    public final void setX(double value) {
        X = value;
    }

    public double Y;

    public final double getY() {
        return Y;
    }

    public final void setY(double value) {
        Y = value;
    }

    public Point2D() {

    }

    public Point2D(double x, double y) {
        X = x;
        Y = y;
    }

    public Point2D(String pointName, double x, double y) {
        this.setPointName(pointName);
        this.setX(x);
        this.setY(y);
    }
}
