package com.ysh.appmarket.util;

import android.util.Log;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/14 15:55
 * Description:
 */

public class LogUtil {

    public static void i(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.i(tag, msg);
        }
    }

    public static void d(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.d(tag, msg);
        }
    }

    public static void w(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.w(tag, msg);
        }
    }

    public static void e(String tag, String msg) {
        if (BuildConfig.DEBUG) {
            Log.e(tag, msg);
        }
    }

}
