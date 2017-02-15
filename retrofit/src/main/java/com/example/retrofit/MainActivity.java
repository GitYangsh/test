package com.example.retrofit;

import android.content.Context;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.view.View;

import com.example.retrofit.model.ResponseInfo;
import com.example.retrofit.network.BaseParamsInterceptor;
import com.example.retrofit.network.NetTool;
import com.example.retrofit.util.LogUtil;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;

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
                .baseUrl(NetTool.getServerUrl())
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

        // step2
        DemoService demoService = retrofit.create(DemoService.class);

        // step3
        TreeMap<String, String> params = new TreeMap<>();
        appendCommonParams(MainActivity.this, params);
        params.put("packageName", "com.market2345");
        String checkSign = createChecksignString(params, null, DemoService.API_APP);
        String sign = md5(checkSign);
        params.put("_cs", sign);

        Call<String> call = demoService.app(params);
        // step4
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                LogUtil.i(TAG, "onResponse");
//                ResponseBody body = response.body();
//                Source source = body.source();
//                BufferedSource bs = Okio.buffer(source);
//                try {
//                    String s = bs.readString(Charset.forName("utf-8"));
//                    LogUtil.i(TAG, "response body str:" + s);
//                    bs.close();
//                    source.close();
//                } catch (IOException e) {
//                    e.printStackTrace();
//                }

                System.out.println("response body:" + response.body());
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                LogUtil.i(TAG, "onFailure:" + t.getMessage());
            }
        });

    }

    private void appendCommonParams(Context context, Map<String, String> params) {
        String android_id = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
        params.put("_aid", android_id);// AndroidID
        TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String device_id = tm.getDeviceId();
        // if(tm.getPhoneType() == TelephonyManager.PHONE_TYPE_CDMA){
        // params.put(key, value)
        // }else{
        // }
        // IMEI (_aid, _mid 至少要传一个)
        params.put("_mid", device_id);
        try {
            params.put("_sop", tm.getSimOperator());
        } catch (Exception e) {
            // ignore
        }
        // 版本名，必填.格式为：n.m.x, 比如：1.3.2
        params.put("_vn", NetTool.getVersionName(context));
        // 版本号，必填。比如：11
        params.put("_vc", String.valueOf(NetTool.getVersionCode(context)));
        params.put("_ch", NetTool.getChannelNumber(context));
        params.put("_pn", context.getPackageName());
        // IMSI
        params.put("_sid", tm.getSubscriberId());
        // magicNum
        params.put("_mnum", NetTool.getMagicNum(context));

    }

    private String createChecksignString(final TreeMap<String, String> params,
                                         final TreeMap<String, String> postParams, String m) {
        if (params == null) {
            return "";
        }

        TreeMap<String, String> dest = params;

        if (postParams != null) {
            dest = new TreeMap<>(params);
            dest.putAll(postParams);
        }

        StringBuilder sb = new StringBuilder();
        for (String key : dest.keySet()) {
            String value = dest.get(key);
            if (value != null) {
                sb.append(value);
            }
            sb.append("&");
        }
        sb.append(NetTool.getSecrectKey()).append('&').append(m);
        return sb.toString();
    }

    protected final String md5(String str) {
        if (str == null) {
            return null;
        }
        return md5(str, "utf-8");
    }

    private String md5(String str, String encodingType) {
        MessageDigest md5;
        try {
            md5 = MessageDigest.getInstance("MD5");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

        try {
            md5.update(str.getBytes(encodingType));
        } catch (UnsupportedEncodingException e) {
            md5.update(str.getBytes());
        }

        byte[] md5Bytes = md5.digest();

        StringBuilder hexValue = new StringBuilder();
        for (byte md5Byte : md5Bytes) {
            int val = ((int) md5Byte) & 0xff;
            if (val < 16) {
                hexValue.append("0");
            }
            hexValue.append(Integer.toHexString(val));
        }
        return hexValue.toString();
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
            }

            @Override
            public void onFailure(Call<ResponseInfo> call, Throwable t) {
                Log.d(TAG, t.getMessage());
            }
        });

    }
}
