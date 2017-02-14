package com.example.retrofit.network;

import com.example.retrofit.util.Config;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/14 15:51
 * Description:
 */

public class NetworkTool {

    public static String getServerUrl() {
        if (Config.DEBUG) {
            return "http://192.168.20.16:8080"; // 测试环境
        }

        return "http://market.aijiaoyan.com/api/v1/"; // 正式环境
    }

    public static String getSecrectKey() {
        if (Config.DEBUG) {
            return "www.520jy.com"; // 测试环境
        }

        return "vx8ea3uz"; // 正式环境
    }

}
