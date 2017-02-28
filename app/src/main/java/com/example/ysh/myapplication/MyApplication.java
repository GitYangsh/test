package com.example.ysh.myapplication;

import android.app.Application;
import android.content.Context;

import com.example.ysh.myapplication.util.AppUtils;
import com.example.ysh.myapplication.util.SharedPreferencesUtil;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 *          CreateTime:  2017/2/28 19:01
 *          Description:
 */

public class MyApplication extends Application {
    private static MyApplication sInstance;

    @Override
    public void onCreate() {
        super.onCreate();
        sInstance = this;
        AppUtils.init(this);
        SharedPreferencesUtil.init(getApplicationContext(), getPackageName() + "_preference", Context.MODE_MULTI_PROCESS);
    }

    public static MyApplication getsInstance() {
        return sInstance;
    }
}
