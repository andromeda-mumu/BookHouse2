package com.example.mmc.bookhouse.newnetwork.convert;

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
 * Created by wangjiao on 2019/10/25.
 * description:
 */

public class CustomGsonConverterFactory extends Converter.Factory {
    private final Gson mGson;
    private CustomGsonConverterFactory(Gson gson){
        if(gson==null) throw new NullPointerException("gson==null");
        this.mGson = gson;
    }
    public static CustomGsonConverterFactory create(){
        return create(new Gson());
    }

    public static CustomGsonConverterFactory create(Gson gson) {
        return new CustomGsonConverterFactory(gson);
    }
    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations,
                                                            Retrofit retrofit) {
        TypeAdapter<?> adapter = mGson.getAdapter(TypeToken.get(type));
        return new CustomGsonResponseBodyConverter<>(mGson, adapter);
    }

    @Override
    public Converter<?, RequestBody> requestBodyConverter(Type type,
                                                          Annotation[] parameterAnnotations, Annotation[] methodAnnotations, Retrofit retrofit) {
        TypeAdapter<?> adapter = mGson.getAdapter(TypeToken.get(type));
        return new CustomGsonRequestBodyConverter<>(mGson, adapter);
    }
}
