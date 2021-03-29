package com.example.wanqian.main.current;

import android.app.Activity;
import android.graphics.Point;
import android.graphics.Rect;

public class DisplayMetrics {
    //实际显示区域指定包含系统装饰的内容的显示部分

    //getDisplay
    public static Rect getRect(Activity context){
        Rect rect = new Rect();
        context.getWindowManager().getDefaultDisplay().getRectSize(rect);
        return rect;
        }

    // getReal
    public static Point getPoint(Activity activity){
        Point point = new Point();
        activity.getWindowManager().getDefaultDisplay().getRealSize(point);
        return point;
    }
}