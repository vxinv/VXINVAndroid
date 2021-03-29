package com.example.wanqian.utitls;

import android.content.Context;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;

import androidx.annotation.RequiresApi;

import com.amap.api.maps.model.LatLng;
import com.amap.api.maps.model.LatLngBounds;
import com.amap.api.navi.model.AMapNaviPath;
import com.amap.api.navi.model.AMapNaviStep;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.DecimalFormat;
import java.util.List;

public class Utils {
    public static final int AVOID_CONGESTION = 4;  // 躲避拥堵
    public static final int AVOID_COST = 5;  // 避免收费
    public static final int AVOID_HIGHSPEED = 6; //不走高速
    public static final int PRIORITY_HIGHSPEED = 7; //高速优先
    public static final int START_ACTIVITY_REQUEST_CODE = 1;
    public static final int ACTIVITY_RESULT_CODE = 2;
    public static final String INTENT_NAME_AVOID_CONGESTION = "AVOID_CONGESTION";
    public static final String INTENT_NAME_AVOID_COST = "AVOID_COST";
    public static final String INTENT_NAME_AVOID_HIGHSPEED = "AVOID_HIGHSPEED";
    public static final String INTENT_NAME_PRIORITY_HIGHSPEED = "PRIORITY_HIGHSPEED";
    private static DecimalFormat fnum = new DecimalFormat("##0.0");

    public static String getFriendlyTime(int s) {
        String timeDes = "";
        int h = s / 3600;
        if (h > 0) {
            timeDes += h + "小时";
        }
        int min = (int) (s % 3600) / 60;
        if (min > 0) {
            timeDes += min + "分";
        }
        return timeDes;
    }

    public static String getFriendlyDistance(int m) {
        if (m < 1000) {
            return m + "米";
        }
        float dis = m / 1000f;
        String disDes = fnum.format(dis) + "公里";
        return disDes;
    }

    public static Spanned getRouteOverView(AMapNaviPath path) {
        String routeOverView = "";
        if (path == null) {
            Html.fromHtml(routeOverView);
        }

        int cost = path.getTollCost();
        if (cost > 0) {
            routeOverView += "过路费约<font color=\"red\" >" + cost + "</font>元";
        }
        int trafficLightNumber = getTrafficNumber(path);
        if (trafficLightNumber > 0) {
            routeOverView += "红绿灯" + trafficLightNumber + "个";
        }
        return Html.fromHtml(routeOverView);
    }

    public static int getTrafficNumber(AMapNaviPath path) {
        int trafficLightNumber = 0;
        if (path == null) {
            return trafficLightNumber;
        }
        List<AMapNaviStep> steps = path.getSteps();
        for (AMapNaviStep step : steps) {
            trafficLightNumber += step.getTrafficLightNumber();
        }
        return trafficLightNumber;
    }

    /**
     * 获取IMEI号
     */
    @RequiresApi(api = Build.VERSION_CODES.M)
    public static String getIMei(Context context) {
       /* TelephonyManager tm = (TelephonyManager) context.getSystemService(Service.TELEPHONY_SERVICE);
        if (context.checkSelfPermission(Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            return "";
        }*/
        return SPUtils.getValue("CarNumberMs") + "";
        //return "1004";
    }

    public static boolean isBlank(String var0) {
        int var1;
        if (var0 != null && (var1 = var0.length()) != 0) {
            for (int var2 = 0; var2 < var1; ++var2) {
                if (!Character.isWhitespace(var0.charAt(var2))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

    /**
     * 返回为m，适合短距离测量
     *
     * @param lon1
     * @param lat1
     * @param lon2
     * @param lat2
     * @return
     */


    public static Double getShortDistance(double lon1, double lat1, double lon2, double lat2) {
        double DEF_PI = 3.14159265359; // PI
        double DEF_2PI = 6.28318530712; // 2*PI
        double DEF_PI180 = 0.01745329252; // PI/180.0
        double DEF_R = 6370693.5; // radius of earth


        double ew1, ns1, ew2, ns2;
        double dx, dy, dew;
        double distance;
        // 角度转换为弧度
        ew1 = lon1 * DEF_PI180;
        ns1 = lat1 * DEF_PI180;
        ew2 = lon2 * DEF_PI180;
        ns2 = lat2 * DEF_PI180;
        // 经度差
        dew = ew1 - ew2;
        // 若跨东经和西经180 度，进行调整
        if (dew > DEF_PI)
            dew = DEF_2PI - dew;
        else if (dew < -DEF_PI)
            dew = DEF_2PI + dew;
        dx = DEF_R * Math.cos(ns1) * dew; // 东西方向长度(在纬度圈上的投影长度)
        dy = DEF_R * (ns1 - ns2); // 南北方向长度(在经度圈上的投影长度)
        // 勾股定理求斜边长
        distance = Math.sqrt(dx * dx + dy * dy);
        return distance;
    }

    /**
     * 读取assets本地json
     *
     * @param fileName
     * @param context
     * @return
     */
    public static String getAssetJson(String fileName, Context context) {
        //将json数据变成字符串
        StringBuilder stringBuilder = new StringBuilder();
        try {
            //获取assets资源管理器
            AssetManager assetManager = context.getAssets();
            //通过管理器打开文件并读取
            BufferedReader bf = new BufferedReader(new InputStreamReader(
                    assetManager.open(fileName)));
            String line;
            while ((line = bf.readLine()) != null) {
                stringBuilder.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }


    /**
     * 旋转bitmap
     */
    public static Bitmap adjustPhotoRotation2(Bitmap bm, final int orientationDegree) {

        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        float targetX, targetY;
        if (orientationDegree == 90) {
            targetX = bm.getHeight();
            targetY = 0;
        } else {
            targetX = bm.getHeight();
            targetY = 0;
        }

        final float[] values = new float[9];
        m.getValues(values);

        float x1 = values[Matrix.MTRANS_X];
        float y1 = values[Matrix.MTRANS_Y];

        m.postTranslate(targetX - x1, targetY - y1);

        Bitmap bm1 = Bitmap.createBitmap(bm.getHeight(), bm.getWidth(), Bitmap.Config.ARGB_8888);

        Paint paint = new Paint();
        Canvas canvas = new Canvas(bm1);
        canvas.drawBitmap(bm, m, paint);


        return bm1;
    }

    public static Bitmap adjustPhotoRotation(Bitmap bm, final int orientationDegree, float zoomout) {

        Matrix m = new Matrix();
        m.setRotate(orientationDegree, (float) bm.getWidth() / 2, (float) bm.getHeight() / 2);
        m.postScale(zoomout, zoomout);
        try {
            Bitmap bm1 = Bitmap.createBitmap(bm, 0, 0, bm.getWidth(), bm.getHeight(), m, true);
            return bm1;

        } catch (OutOfMemoryError ex) {
        }
        return null;
    }

    /**
     * 判断当前设备是手机还是平板，代码来自 Google I/O App for Android（官方方法）
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    public static boolean isPad(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    //整数相除 保留一位小数
    public static String division(double a, double b) {
        String result = "";
        double num = a / b;

        DecimalFormat df = new DecimalFormat("0.00");

        result = df.format(num);

        return result;

    }

    //设置高德地图缩放比例
    public static int scale2Zoom(Double scale) {
        if (scale <= 10) return 19;
        else if (scale <= 25) return 18;
        else if (scale <= 50) return 18;
        else if (scale <= 100) return 18;
        else if (scale <= 200) return 17;
        else if (scale <= 500) return 14;
        else if (scale <= 1000) return 13;
        else if (scale <= 2000) return 12;
        else if (scale <= 5000) return 11;
        else if (scale <= 10000) return 10;
        else if (scale <= 20000) return 9;
        else if (scale <= 30000) return 8;
        else if (scale <= 50000) return 7;
        else if (scale <= 100000) return 6;
        else if (scale <= 200000) return 5;
        else if (scale <= 500000) return 4;
        else if (scale <= 1000000) return 3;
        else if (scale > 1000000) return 2;
        return 20;
    }

    /**
     * @param angle
     * @param abs
     * @return
     */
    public static double[] absoluteAngle(double angle, double abs) {
        double bigAngle = angle + abs > 360D ? angle + abs - 360D : angle + abs;
        double smallAngle = angle - abs > 0D ? angle - abs : angle + 360 - abs;
        double[] doubles;
        if (bigAngle > smallAngle) {
            doubles = new double[]{smallAngle, bigAngle};
        } else {
            doubles = new double[]{smallAngle, bigAngle};
        }
        return doubles;
    }


    /**
     * 当前角度在判断角度的右边还是左边
     *
     * @param currAngle  当前陀螺仪的角度
     * @param checkAngle 当前路线的角度
     * @param bias       偏差角度
     * @return
     */
    public static boolean isRight(double currAngle, double checkAngle, double bias) {
        if (currAngle >= 0D && checkAngle < 180D) {
            if (checkAngle > currAngle && checkAngle < 180D + currAngle - bias) {
                return true;
            }
        }
        if (currAngle >= 180D && currAngle <= 360D) {
            return checkAngle > currAngle - 180D - bias && checkAngle < currAngle;
        }
        return false;
    }

    /**
     * 获取相反的方向
     *
     * @param angle 当前的方向
     * @return
     */
    public static double oppositeDirection(double angle) {
        return angle + 180D > 360D ? angle - 180D : angle + 180D;
    }


    /*
     * 获取两个经纬度坐标点的角度 计算方位角,正北向为0度，以顺时针方向递增
     * @param LatLng
     * @param LatLng
     */
    public static double coordinatePointAngle(LatLng la1, LatLng la2) {
        double lat1 = la1.latitude, lon1 = la1.longitude, lat2 = la2.latitude,
                lon2 = la2.longitude;
        double result = 0.0;

        int ilat1 = (int) (0.50 + lat1 * 360000.0);
        int ilat2 = (int) (0.50 + lat2 * 360000.0);
        int ilon1 = (int) (0.50 + lon1 * 360000.0);
        int ilon2 = (int) (0.50 + lon2 * 360000.0);

        lat1 = Math.toRadians(lat1);
        lon1 = Math.toRadians(lon1);
        lat2 = Math.toRadians(lat2);
        lon2 = Math.toRadians(lon2);

        if ((ilat1 == ilat2) && (ilon1 == ilon2)) {
            return result;
        } else if (ilon1 == ilon2) {
            if (ilat1 > ilat2)
                result = 180.0;
        } else {
            double c = Math
                    .acos(Math.sin(lat2) * Math.sin(lat1) + Math.cos(lat2)
                            * Math.cos(lat1) * Math.cos((lon2 - lon1)));
            double A = Math.asin(Math.cos(lat2) * Math.sin((lon2 - lon1))
                    / Math.sin(c));
            result = Math.toDegrees(A);
            if ((ilat2 > ilat1) && (ilon2 > ilon1)) {
            } else if ((ilat2 < ilat1) && (ilon2 < ilon1)) {
                result = 180.0 - result;
            } else if ((ilat2 < ilat1) && (ilon2 > ilon1)) {
                result = 180.0 - result;
            } else if ((ilat2 > ilat1) && (ilon2 < ilon1)) {
                result += 360.0;
            }
        }
        return result;
    }

    /**
     * 判断当前设备是手机还是平板（非官方方法）
     *
     * @param context
     * @return 平板返回 True，手机返回 False
     */
    private boolean isPadbyDimens(Context context) {
        WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics dm = new DisplayMetrics();
        display.getMetrics(dm);
        double x = Math.pow(dm.widthPixels / dm.xdpi, 2);
        double y = Math.pow(dm.heightPixels / dm.ydpi, 2);
        double screenInches = Math.sqrt(x + y);
        return screenInches >= 6.0;
    }

    /**
     * 根据2个坐标返回一个矩形Bounds
     * 以此来智能缩放地图显示
     */
    public static LatLngBounds createBounds(Double latA, Double lngA, Double latB, Double lngB) {
        LatLng northeastLatLng;
        LatLng southwestLatLng;

        Double topLat, topLng;
        Double bottomLat, bottomLng;
        if (latA >= latB) {
            topLat = latA;
            bottomLat = latB;
        } else {
            topLat = latB;
            bottomLat = latA;
        }
        if (lngA >= lngB) {
            topLng = lngA;
            bottomLng = lngB;
        } else {
            topLng = lngB;
            bottomLng = lngA;
        }
        northeastLatLng = new LatLng(topLat, topLng);
        southwestLatLng = new LatLng(bottomLat, bottomLng);
        return new LatLngBounds(southwestLatLng, northeastLatLng);
    }

}