package com.example.retrofit;

import com.example.retrofit.model.ResponseInfo;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.QueryMap;


/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/1/18 13:53
 * Description:
 */

public interface DemoService {
    @GET("api/{name}")
    Call<ResponseInfo> testHttpGet(@Path("name") String apiAction, @QueryMap Map<String, String> params);
}
