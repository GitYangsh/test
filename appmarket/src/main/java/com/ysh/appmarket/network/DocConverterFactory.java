package com.ysh.appmarket.network;

import com.google.gson.Gson;
import com.google.gson.TypeAdapter;
import com.google.gson.reflect.TypeToken;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

/**
 * 类说明：
 *
 * @author yangsh
 * @version 1.0
 * @time 2017/2/20 18:07
 * Description:
 */

public final class DocConverterFactory extends Converter.Factory {

    private final Gson gson;

    public static DocConverterFactory create() {
        return create(new Gson());
    }

    public static DocConverterFactory create(Gson gson) {
        return new DocConverterFactory(gson);
    }

    private DocConverterFactory(Gson gson) {
        if (gson == null) throw new NullPointerException("gson == null");
        this.gson = gson;
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type, Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = gson.getAdapter(TypeToken.get(type));
        return new DocRequestBodyConverter<>(gson, adapter);
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        return new DocResponseBodyConverter<>();
    }

}
