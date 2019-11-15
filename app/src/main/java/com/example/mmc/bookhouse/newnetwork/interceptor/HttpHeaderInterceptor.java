package com.example.mmc.bookhouse.newnetwork.interceptor;


import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by wangjiao on 2019/10/24.
 * description:
 */

public class HttpHeaderInterceptor implements Interceptor {
    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        Request.Builder builder = originalRequest.newBuilder();
        builder.header("User-Agent","getUa");
        builder.header("Referer", "referUrl");
        builder.header("Cookie", "cookie");
        Request.Builder requestBuilder = builder.method(originalRequest.method(),originalRequest.body());
        Request request = requestBuilder.build();
        return chain.proceed(request);
    }
}
