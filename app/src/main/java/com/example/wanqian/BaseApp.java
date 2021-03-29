package com.example.wanqian;

import android.app.Activity;
import android.app.Application;

import androidx.multidex.MultiDex;

import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;

import java.util.LinkedList;
import java.util.List;

/**
 * 创建日期：2019/11/9.
 * 类说明：
 */
public class BaseApp extends Application {
    public static BaseApp mApplicationContext;

    //static 代码段可以防止内存泄露
    static {
        // 设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator((context, layout) -> {
            layout.setPrimaryColorsId(R.color.white, R.color.smartRefreshColor);//全局设置主题颜色
            return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
        });
        // 设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator((context, layout) -> {
            //指定为经典 Footer，默认是 BallPulseFooter
            layout.setPrimaryColorsId(R.color.white, R.color.smartRefreshColor);//全局设置主题颜色
            return new ClassicsFooter(context).setDrawableSize(20);
        });
    }

    List<Activity> activityList = new LinkedList<>();

    public static BaseApp getInstance() {
        return mApplicationContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        UMConfigure.init(this, "5fc750a6094d637f3131dadf", "Umeng_test", UMConfigure.DEVICE_TYPE_PHONE, "");
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        // 主要是添加下面这句代码
        MultiDex.install(this);
        mApplicationContext = this;
    }

    public void addActivity(Activity activity) {
        activityList.add(activity);
    }

    //遍历所有的activity并finish
    public void exitApp() {
        for (Activity activity : activityList) {
            if (activity != null)
                activity.finish();
        }
    }


    //清空缓存
    @Override
    public void onLowMemory() {
        super.onLowMemory();
        System.gc();
    }

}
