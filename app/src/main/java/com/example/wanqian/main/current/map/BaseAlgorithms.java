package com.example.wanqian.main.current.map;

import android.util.Log;

/**
 * @author jason.wang
 * @ClassName: BaseAlgorithms
 * @Description: 基础算法类
 * @date 2019/3/12
 */

public final class BaseAlgorithms {

    /**
     * 判断圆弧的计算方法顺时针还是逆时针方向 判断规则为:按ABC的次序在圆弧上顺时针移动。如果ABC是逆时针，移动的距离超过一圈.
     * 1.分别求出AO的斜率及A和O的位置关系，求出A角度。这个结果应该是一个[0, 360)的 值（或者说弧度表示0到2PI）； 2.同理求出B和C的角度，它们也应该是一个[0,
     * 360)的值。A\B\C的角度应该各不相同， 否则有两点重合； 3.判断B的角度是否小于A的角度，若小于就给B加360，直到B的角度大于A。 即 0 < B角 - A角 < 360
     * 4.同上，保证C角大于B角。 即 0 < C角 - B角 < 360 5.判断 C角-A角的值。 如果小于360， ABC顺时针；否则ABC逆时针；
     *
     * @param azimuthA 圆心与起点方位角；
     * @param azimuthB 圆心与第二点方位角；
     * @param azimuthC 圆心与第三点方位角； @ return 如果是顺时针，则返回值为1，否则返回值为0；
     */
    public static double getArcDirectionByThreeAzimuth(double azimuthA, double azimuthB, double azimuthC) {

        double result = 0;
        if (azimuthA > azimuthB) {
            azimuthB = azimuthB + 2.0 * Math.PI;
        }
        if (azimuthC < azimuthB) {
            azimuthC = azimuthC + 2.0 * Math.PI;
        }
        if (azimuthC < azimuthB) {
            azimuthC = azimuthC + 2.0 * Math.PI;
        }
        if ((azimuthC - azimuthA) < 2.0 * Math.PI) {
            result = 1;
        }
        return result;
    }

    /**
     * 两点间距离
     *
     * @param fPT
     * @param sPT
     * @return
     */
    public static double getTwoPointDistance(Point2D fPT, Point2D sPT) {
        double distance = Math.sqrt(Math.pow(fPT.getX() - sPT.getX(), 2.0) + Math.pow(fPT.getY() - sPT.getY(), 2.0));
        return distance;
    }

    /**
     * 计算两个点之间的方位角. 方位角只指以X轴为起点，
     *
     * @param startPoint 计算起点,类型为BMaP.Point;
     * @param endPoint   计算终点;类型为BMaP.Point;
     * @return 返回两个点之间的方位角;
     */
    public static double getAzimuthByTwoPoint(Point2D startPoint, Point2D endPoint) {

        double azimuth = 0.0;

        double startX = startPoint.getX();
        double startY = startPoint.getY();
        double endX = endPoint.getX();
        double endY = endPoint.getY();
        double deltX = endX - startX;
        double deltY = endY - startY;
        if (Math.abs(deltX) < 0.00000001) {
            if (deltY > 0.0) {
                azimuth = Math.PI / 2.0;
            } else {
                azimuth = 3.0 * Math.PI / 2.0;
            }
        } else {
            azimuth = Math.atan2(endY - startY, endX - startX);
            if (azimuth < 0.0) {
                azimuth = azimuth + Math.PI * 2.0;
            }
        }
        return azimuth;
    }


    /**
     * @MethodName: getPointToLineDis
     * @Author: jason.wang
     * @Description: 获取点到直线的距离
     * @Date: 22:43 2019/3/27
     * @Param: p3线路外点
     * @return:
     **/
    public static double getPointToLineDis(Point2D p1, Point2D p2, Point2D p3) {

        double A = p2.getY() - p1.getY();
        double B = p1.getX() - p2.getX();
        double C = p2.getX() * p1.getY() - p1.getX() * p2.getY();

        double dis = Math.abs(A * p3.getX() + B * p3.getY() + C) / Math.sqrt(A * A + B * B);
        return dis;
    }


    /**
     * @MethodName: getFoot
     * @Author: jason.wang
     * @Description: 获取点外一点到直线的垂足点
     * @Date: 22:41 2019/3/27
     * @Param: p3线外点
     * @return:
     **/
    public static Point2D getFoot(Point2D p1, Point2D p2, Point2D p3) {
        Point2D foot = new Point2D();

        double dx = p1.X - p2.X;
        double dy = p1.Y - p2.Y;

        double u = (p3.X - p1.X) * dx + (p3.Y - p1.Y) * dy;
        u /= dx * dx + dy * dy;

        foot.X = p1.X + u * dx;
        foot.Y = p1.Y + u * dy;

        return foot;
    }

    /**
     * 求解p点关于p1，p2点构成方程的对称点
     *
     * @param p1
     * @param p2
     * @param p  要对称的点
     * @return
     */
    public static Point2D getSymmetryPoint(Point2D p1, Point2D p2, Point2D p) {
        double A = p2.getY() - p1.getY();
        double B = p1.getX() - p2.getX();
        double C = p2.getX() * p1.getY() - p1.getX() * p2.getY();

        double x = ((B * B - A * A) * p.X - 2 * A * B * p.Y - 2 * A * C) / (A * A + B * B);
        double y = (-2 * A * B * p.X + (A * A - B * B) * p.Y - 2 * B * C) / (A * A + B * B);
        return new Point2D(x, y);
    }

    /**
     * 求在直线上的点坐标
     *
     * @param p1     已知点1
     * @param p2     已知点2
     * @param dis2P1 未知点距点1距离
     * @return
     */
    public static Point2D getOnlinePoint(Point2D p1, Point2D p2, double dis2P1) {
        double k = getAzimuthByTwoPoint(p1, p2);
        if (k == 0) {
            k = 0.000000000001;
        }
        k = Math.tan(k);
        double scale = dis2P1 / getTwoPointDistance(p1, p2);

        double x = scale * (p2.Y - p1.Y) * k + p1.X;
        double y = p1.Y + k * x - k * p1.X;
        return new Point2D(x, y);
    }

    /**
     * 求在直线上的点坐标
     *
     * @param p1    已知点1
     * @param p2    已知点2
     * @param scale 未知点到已知点1的比例
     * @return
     */
    public static Point2D getOnlinePointByScale(Point2D p1, Point2D p2, double scale) {
        double dis2FirstPoint = getTwoPointDistance(p1, p2);
        return getOnlinePoint(p1, p2, dis2FirstPoint * scale);
    }

    public static void main(String[] args) {
        Point2D point2D = getOnlinePoint(new Point2D(0.0, 0.), new Point2D(2, 2), Math.sqrt(2.0));
        Log.d("", point2D.getX() + "");
    }

    /**
     * 垂足是否在线段上
     *
     * @param pd1
     * @param pd2
     * @param foot
     * @return
     */
    public static Point2D getNearstPoint(Point2D pd1, Point2D pd2, Point2D foot, Point2D outp) {
        double disP1 = getTwoPointDistance(pd1, outp);
        double disP2 = getTwoPointDistance(pd2, outp);
        double disfoot = getTwoPointDistance(foot, outp);
        if (disP1 < disP2) {
            if (disfoot < disP1) {
                return foot;
            } else {
                return pd1;
            }
        } else {
            if (disfoot < disP2) {
                return foot;
            } else {
                return pd2;
            }
        }
    }
}

