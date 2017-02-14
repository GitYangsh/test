package com.example.retrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.example.retrofit.model.ResponseInfo;
import com.example.retrofit.network.BaseParamsInterceptor;
import com.example.retrofit.network.NetworkTool;

import java.util.HashMap;
import java.util.Map;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_doget).setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_doget :
                //testRetrofitHttpGet();
                testMarketApi();
                break;
        }
    }

    private void testMarketApi() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new BaseParamsInterceptor()).build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(NetworkTool.getServerUrl())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        // step2
        DemoService demoService = retrofit.create(DemoService.class);

        // step3
        Map<String, String> params = new HashMap<>();
        params.put("param1", "中文值");
        params.put("param2", "value2");

        Call<ResponseInfo> call = demoService.testHttpGet("retrofitTesting", params);

        // step4
        call.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                Log.d(TAG, "onResponse");
                Log.d(TAG, response.body().describe);
                ResponseInfo responseInfo = response.body();
            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

    }

    private void testRetrofitHttpGet() {
        // step1
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://192.168.20.16:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        // step2
        DemoService demoService = retrofit.create(DemoService.class);

        // step3
        Map<String, String> params = new HashMap<>();
        params.put("param1", "中文值");
        params.put("param2", "value2");

        Call<ResponseInfo> call = demoService.testHttpGet("retrofitTesting", params);

        // step4
        call.enqueue(new Callback<ResponseInfo>() {
            @Override
            public void onResponse(Call<ResponseInfo> call, Response<ResponseInfo> response) {
                Log.d(TAG, "onResponse");
                Log.d(TAG, response.body().describe);
                ResponseInfo responseInfo = response.body();
            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

    }

}
