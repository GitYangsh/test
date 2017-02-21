package com.ysh.appmarket.network;

import com.jy.app.market.idata.Doc;

import java.io.IOException;

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

final class DocResponseBodyConverter<T> implements Converter<ResponseBody, T> {

    DocResponseBodyConverter() {
    }

    @Override
    public T convert(ResponseBody value) throws IOException {
        try {
            Doc doc = Doc.fromJson(value.charStream());
            if (doc.isOk()) {
                return doc.getData();
            }
            return null;
        } finally {
            value.close();
        }
    }
}

