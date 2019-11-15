package com.example.mmc.bookhouse.newnetwork.utils;

import android.util.Log;

import com.example.mmc.bookhouse.app.BookApplication;
import com.example.mmc.bookhouse.newnetwork.interceptor.HttpCommParamsInterceptor;
import com.example.mmc.bookhouse.newnetwork.interceptor.HttpHeaderInterceptor;
import com.example.mmc.bookhouse.newnetwork.interceptor.ReciveCookieInterceptor;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.ConnectionPool;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by wangjiao on 2019/10/24.
 * description:
 */

public class RetrofitUtils {
    private static final int SECOND_CONNECT_TIMEOUT = 5;
    private static final int SECOND_WRITE_TIMEOUT = 10;
    private static final int SECOND_READ_TIMEOUT = 10;
    private static final int CACHE_SIZE = 10 * 1024 * 1024;
    public static OkHttpClient getOkHttpClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
            @Override
            public void log(String message) {
                try {
                    Log.e("OKHttp-----", URLDecoder.decode(message, "utf-8"));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                    Log.e("OKHttp-----", message);
                }
            }
        });
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        File cacheFile = BookApplication.mInstance.getApplicationContext().getCacheDir();
        Cache cache = new Cache(cacheFile, CACHE_SIZE);

        return new OkHttpClient.Builder()
                .connectionPool(new ConnectionPool(5,45L, TimeUnit.SECONDS))
                .connectTimeout(SECOND_CONNECT_TIMEOUT, TimeUnit.SECONDS)//连接超时(单位:秒)
                .writeTimeout(SECOND_WRITE_TIMEOUT, TimeUnit.SECONDS)//写入超时(单位:秒)
                .readTimeout(SECOND_READ_TIMEOUT, TimeUnit.SECONDS)//读取超时(单位:秒)
                .cache(cache)
                .retryOnConnectionFailure(true)
                .addInterceptor(loggingInterceptor)
                .addInterceptor(new HttpHeaderInterceptor())
                .addInterceptor(new HttpCommParamsInterceptor())
                .addNetworkInterceptor(new ReciveCookieInterceptor())
                .build();

    }
    public static Retrofit.Builder getRetrofitBuilder(String baseUrl){
        Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").serializeNulls().create();
        OkHttpClient okHttpClient = RetrofitUtils.getOkHttpClient();
        return new Retrofit.Builder()
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
//                .addConverterFactory(CustomGsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl(baseUrl);
    }
}
