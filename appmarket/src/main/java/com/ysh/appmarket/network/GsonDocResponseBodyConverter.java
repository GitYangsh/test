package com.ysh.appmarket.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.jy.app.market.idata.Doc;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

import okhttp3.ResponseBody;
import retrofit2.Converter;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/20 18:18
 * Description:
 */

final class GsonDocResponseBodyConverter<T> implements Converter<ResponseBody, T> {
    private final Gson gson;
    private final TypeAdapter<T> adapter;

    GsonDocResponseBodyConverter(Gson gson, TypeAdapter<T> adapter) {
        this.gson = gson;
        this.adapter = adapter;
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            BufferedReader in = new BufferedReader(value.charStream());
            StringBuffer buffer = new StringBuffer();
            String line;
            while ((line = in.readLine()) != null) {
                buffer.append(line);
            }
            Doc doc = Doc.fromJson(buffer.toString());
            if (doc.isOk()) {
                return doc.getData();
            }
            return null;
        } finally {
            value.close();
        }
    }
}

