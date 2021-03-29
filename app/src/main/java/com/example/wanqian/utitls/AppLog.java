/**
 * Copyright (c) 2004-2017 http://www.ibczy.com/
 * All rights reserved.
 * <p>
 * Crated by ant,  date:  2017/4/26   17:15
 * <p>
 * Permission is hereby granted, free  of charge, to any person obtaining
 * a  copy  of this  software  and  associated  documentation files  (the
 * "Software"), to  deal in  the Software without  restriction, including
 * without limitation  the rights to  use, copy, modify,  merge, publish,
 * distribute,  sublicense, and/or sell  copies of  the Software,  and to
 * permit persons to whom the Software  is furnished to do so, subject to
 * the following conditions:
 * <p>
 * The  above  copyright  notice  and  this permission  notice  shall  be
 * included in all copies or substantial portions of the Software.
 * <p>
 * THE  SOFTWARE IS  PROVIDED  "AS  IS", WITHOUT  WARRANTY  OF ANY  KIND,
 * EXPRESS OR  IMPLIED, INCLUDING  BUT NOT LIMITED  TO THE  WARRANTIES OF
 * MERCHANTABILITY,    FITNESS    FOR    A   PARTICULAR    PURPOSE    AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE
 * LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION
 * OF CONTRACT, TORT OR OTHERWISE,  ARISING FROM, OUT OF OR IN CONNECTION
 * WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.example.wanqian.utitls;

import android.util.Log;

import com.example.wanqian.BuildConfig;

/**
 * Created by baichuan on 2016/7/22.
 * 自己定义的log
 */
public class AppLog {
    private static final String TAG = "app_rubbish";

    public static void d(String tag, String message) {
        if (BuildConfig.DEBUG && message != null)
            Log.d(tag, message);
    }

    public static void d(String message) {
        d(TAG, message);
    }


    public static void i(String tag, String message) {
        if (BuildConfig.DEBUG && message != null)
            Log.i(tag, message);
    }

    public static void i(String message) {
        i(TAG, message);
    }

    public static void w(String tag, String message) {
        if (BuildConfig.DEBUG && message != null)
            Log.w(tag, message);
    }

    public static void w(String message) {
        if (BuildConfig.DEBUG)
            w(TAG, message);
    }

    public static void e(String tag, String message) {
        if (BuildConfig.DEBUG && message != null)
            Log.e(tag, message);
    }

    public static void e(String tag, Throwable e) {
        if (BuildConfig.DEBUG && e != null) {
            Log.e(tag, e.getMessage(), e);
        }
    }

    public static void e(String tag, String message, Throwable e) {
        if (BuildConfig.DEBUG && e != null) {
            Log.e(tag, message, e);
            e.printStackTrace();
        }
    }

    public static void e(String message) {
        e(TAG, message);
    }

}