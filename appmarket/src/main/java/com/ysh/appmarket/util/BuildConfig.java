package com.ysh.appmarket.util;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/14 16:19
 * Description:
 */

public class BuildConfig {

    public static final boolean DEBUG = true;

    public static String getBaseUrl() {
        if (DEBUG) {
            return "http://192.168.20.16:8080"; // 测试环境
            //return "http://market.aijiaoyan.com/api/v1/"; // 正式环境

        }
        return "http://market.aijiaoyan.com/api/v1/"; // 正式环境
    }

}
