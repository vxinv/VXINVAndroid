package com.example.wanqian.utitls;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.wanqian.BaseApp;

public class SPUtils {

    private static final String GLOBAL_SP_FILE_NAME = "sp_config";

    /**
     * 存储string
     *
     * @param name
     * @param key
     * @param value
     */
    public static void commitValue(String name, String key, String value) {
        SharedPreferences sharedPreferences = BaseApp.mApplicationContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.commit();
    }


    public static void applayValue(String name, String key, String value) {
        SharedPreferences sharedPreferences = BaseApp.mApplicationContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(key, value);
        editor.apply();
    }

    /**
     * 异步写入
     *
     * @param key
     * @param value
     */
    public static void saveValueToDefaultSpByApply(String key, String value) {
        applayValue(GLOBAL_SP_FILE_NAME, key, value);
    }


    public static void saveValueToDefaultSpByCommit(String key, String value) {
        commitValue(GLOBAL_SP_FILE_NAME, key, value);
    }

    public static String getValue(String name, String key) {
        SharedPreferences sharedPreferences = BaseApp.mApplicationContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences.getString(key, "");
    }

    public static int getInt(String name, String key) {
        SharedPreferences sharedPreferences = BaseApp.mApplicationContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        return sharedPreferences.getInt(key, 0);
    }

    //Boolean类型判断用户是否退出登录
    public static void booleanValue(String name, String key, boolean value) {
        SharedPreferences sharedPreferences = BaseApp.mApplicationContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putBoolean(key, value);
        editor.commit();
    }

    //浮点类型经纬度
    public static void floatValue(String name, String key, float value) {
        SharedPreferences sharedPreferences = BaseApp.mApplicationContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putFloat(key, value);
        editor.commit();
    }

    //Boolean类型入参
    public static void saveValueToDefaultSpByBoolean(String key, boolean value) {
        booleanValue(GLOBAL_SP_FILE_NAME, key, value);
    }

    //float类型入参
    public static void saveValueToDefaultSpByFloat(String key, float value) {
        floatValue(GLOBAL_SP_FILE_NAME, key, value);
    }

    //获取Boolean类型的值
    public static boolean getbooleanValue(String key) {
        SharedPreferences sharedPreferences = BaseApp.mApplicationContext.getSharedPreferences(GLOBAL_SP_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getBoolean(key, false);
    }

    //获取float类型的值
    public static float getFloatValue(String key) {
        SharedPreferences sharedPreferences = BaseApp.mApplicationContext.getSharedPreferences(GLOBAL_SP_FILE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences.getFloat(key, 1.0f);
    }

    /**
     * 获取String的值
     *
     * @param key
     * @return
     */
    public static String getValue(String key) {
        return getValue(GLOBAL_SP_FILE_NAME, key);
    }

    public static int getInt(String key) {
        return getInt(GLOBAL_SP_FILE_NAME, key);
    }

    public static void saveValueToDefaultSpByInt(String key, int value) {
        applayInt(GLOBAL_SP_FILE_NAME, key, value);
    }

    public static void applayInt(String name, String key, int value) {
        SharedPreferences sharedPreferences = BaseApp.mApplicationContext.getSharedPreferences(name, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt(key, value);
        editor.apply();
    }
}
