package com.ysh.appmarket.network;

import com.jy.app.market.idata.data.PageCard;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/20 11:39
 * Description:
 */

public interface ApiService {

    String API_DISCOVERY = "discovery";

    @GET(API_DISCOVERY)
    Call<PageCard> discovery(@Query("pageNo") int pageNo);

}
