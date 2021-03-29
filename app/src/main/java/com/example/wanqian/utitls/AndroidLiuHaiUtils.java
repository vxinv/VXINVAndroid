package com.example.wanqian.utitls;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.os.Build;
import android.provider.Settings;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.RequiresApi;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author feihaokui.
 * Date 2018/8/29.
 */

public class AndroidLiuHaiUtils {


    /**
     * 适配刘海屏，包括横竖屏切换
     */
    public static void setTopViewMargin(Activity activity, View view) {
        if (view != null) {
            ViewGroup.LayoutParams layoutParams = view.getLayoutParams();
            ViewGroup.MarginLayoutParams marginParams = null;
            if (layoutParams instanceof ViewGroup.MarginLayoutParams) {
                marginParams = (ViewGroup.MarginLayoutParams) layoutParams;
            } else {
                marginParams = new ViewGroup.MarginLayoutParams(layoutParams);
            }

            if (activity.getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
                marginParams.topMargin = 0;
            } else {
                marginParams.topMargin = getNotchScreenHeight(activity);
            }
            view.setLayoutParams(marginParams);
        }
    }

            /**
             * 判断是否是刘海屏
             * @return
             *
             *  || isAndroidP(activity) != null
             */
    public static boolean hasNotchScreen(Activity activity){
        //TODO 各种品牌
        return getInt("ro.miui.notch", activity) == 1 || hasNotchAtHuawei(activity) || hasNotchAtOPPO(activity)
                || hasNotchAtVivo(activity);
    }

    public static int getNotchScreenHeight(Activity activity){
        int ret = getStatusBarHeight(activity);
        if(hasNotchAtHuawei(activity)){
            ret = getNotchSize(activity)[1];//数组[0]为刘海宽，[1]为刘海高
        }
        return ret;
    }

    /**
     * Android P 刘海屏判断
     * @param activity
     * @return
     */
//    public static DisplayCutout isAndroidP(Activity activity){
//        View decorView = activity.getWindow().getDecorView();
//        if (decorView != null && android.os.Build.VERSION.SDK_INT >= 28){
//            WindowInsets windowInsets = decorView.getRootWindowInsets();
//            if (windowInsets != null)
//                return windowInsets.getDisplayCutout();
//        }
//        return null;
//    }

    /**
     * 小米刘海屏判断.
     * @return 0 if it is not notch ; return 1 means notch
     */
    public static int getInt(String key, Activity activity) {
        int result = 0;
        if (isXiaomi()){
            try {
                ClassLoader classLoader = activity.getClassLoader();
                @SuppressWarnings("rawtypes")
                Class SystemProperties = classLoader.loadClass("android.os.SystemProperties");
                //参数类型
                @SuppressWarnings("rawtypes")
                Class[] paramTypes = new Class[2];
                paramTypes[0] = String.class;
                paramTypes[1] = int.class;
                Method getInt = SystemProperties.getMethod("getInt", paramTypes);
                //参数
                Object[] params = new Object[2];
                params[0] = key;
                params[1] = Integer.valueOf(0);
                result = (Integer) getInt.invoke(SystemProperties, params);

            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        }
        return result;
    }

    /**
     * 华为刘海屏判断
     * @return
     */
    public static boolean hasNotchAtHuawei(Activity activity) {
        boolean ret = false;
        try {
            ClassLoader classLoader = activity.getClassLoader();
            Class HwNotchSizeUtil = classLoader.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("hasNotchInScreen");
            ret = (boolean) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            AppLog.e("hasNotchAtHuawei ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            AppLog.e("hasNotchAtHuawei NoSuchMethodException");
        } catch (Exception e) {
            AppLog.e( "hasNotchAtHuawei Exception");
        } finally {
            return ret;
        }
    }

    /**
     * 华为刘海高度
     * @param context
     * @return
     */
    public static int[] getNotchSize(Context context) {
        int[] ret = new int[]{0, 0};
        try {
            ClassLoader cl = context.getClassLoader();
            Class HwNotchSizeUtil = cl.loadClass("com.huawei.android.util.HwNotchSizeUtil");
            Method get = HwNotchSizeUtil.getMethod("getNotchSize");
            ret = (int[]) get.invoke(HwNotchSizeUtil);
        } catch (ClassNotFoundException e) {
            AppLog.e("test", "getNotchSize ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            AppLog.e("test", "getNotchSize NoSuchMethodException");
        } catch (Exception e) {
            AppLog.e("test", "getNotchSize Exception");
        } finally {
            return ret;
        }

    }

    public static final int VIVO_NOTCH = 0x00000020;//是否有刘海
    public static final int VIVO_FILLET = 0x00000008;//是否有圆角

    /**
     * VIVO刘海屏判断
     * @return
     */
    public static boolean hasNotchAtVivo(Activity activity) {
        boolean ret = false;
        try {
            ClassLoader classLoader = activity.getClassLoader();
            Class FtFeature = classLoader.loadClass("android.util.FtFeature");
            Method method = FtFeature.getMethod("isFeatureSupport", int.class);
            ret = (boolean) method.invoke(FtFeature, VIVO_NOTCH);
        } catch (ClassNotFoundException e) {
            AppLog.e( "hasNotchAtVivo ClassNotFoundException");
        } catch (NoSuchMethodException e) {
            AppLog.e(  "hasNotchAtVivo NoSuchMethodException");
        } catch (Exception e) {
            AppLog.e(  "hasNotchAtVivo Exception");
        } finally {
            return ret;
        }
    }
    /**
     * OPPO刘海屏判断
     * @return
     */
    public static boolean hasNotchAtOPPO(Activity activity) {
        return  activity.getPackageManager().hasSystemFeature("com.oppo.feature.screen.heteromorphism");
    }

    /**
     * oppo刘海高度就是状态栏高度
     * vivo并没有提供刘海高度的获取方式，只能通过获取状态栏高度来当做刘海的高度，但在某些机型可能会有些偏差。
     * @param activity
     * @return
     */
    public static int getStatusBarHeight(Activity activity) {
        int statusBarHeight = 0;
        int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            statusBarHeight = activity.getResources().getDimensionPixelSize(resourceId);
        }
        return statusBarHeight ;
    }


    /**
     *  是否是小米手机
     *  是否使用了全面屏手势
     *  Settings.Global.getInt(context.getContentResolver(), "force_fsg_nav_bar", 0) != 0
     **/
    public static boolean isXiaomi() {
        return "Xiaomi".equals(Build.MANUFACTURER);
    }

    /**
     * 小米手机是否是全屏模式
     * @param activity
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean isFullScreenMode(Activity activity){
        return  Settings.Global.getInt(activity.getContentResolver(), "force_fsg_nav_bar", 0) != 0;
    }
}
