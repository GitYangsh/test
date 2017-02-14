package com.example.retrofit.network;

import java.io.IOException;

import okhttp3.HttpUrl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/14 16:48
 * Description:
 */

public class BaseParamsInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        HttpUrl originalHttpUrl = originalRequest.url();

//        originalHttpUrl.newBuilder().
        HttpUrl httpUrl = originalHttpUrl.newBuilder()
                .addQueryParameter("key1", "value1").build();
        Request request = originalRequest.newBuilder()
                .url(httpUrl).build();

        return chain.proceed(request);
    }

}
