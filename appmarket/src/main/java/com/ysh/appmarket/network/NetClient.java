package com.ysh.appmarket.network;

import com.ysh.appmarket.util.BuildConfig;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/20 14:28
 * Description:
 */

public class NetClient {

    private static ApiService sApiService;

    public static ApiService apiService() {
        if (sApiService == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();

            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(BuildConfig.getBaseUrl())
                    .addConverterFactory(DocConverterFactory.create())
                    .addConverterFactory(ScalarsConverterFactory.create())
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build();

            sApiService = retrofit.create(ApiService.class);
        }

        return sApiService;
    }

}
