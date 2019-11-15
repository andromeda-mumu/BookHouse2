package com.example.mmc.bookhouse.newnetwork.interceptor;

import android.text.TextUtils;

import java.io.IOException;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by zhangbl on 2016/12/29.
 */

public class ReciveCookieInterceptor implements Interceptor {


    @Override
    public Response intercept(Chain chain) throws IOException {
        Request request = chain.request();
        Request newRequest;
        newRequest = request.newBuilder().addHeader("Connection", "Close")
                .build();
        Response originalResponse = chain.proceed(newRequest);
        List<String> cookieList = originalResponse.headers("Set-Cookie");
        if (cookieList != null && !cookieList.isEmpty()) {
            StringBuilder coolies = new StringBuilder();
            for (String s : cookieList) {
                if (!TextUtils.isEmpty(s)) {
                    String cookie = s.split(";")[0];
                    coolies.append(cookie);
                    coolies.append(";");
                }
            }
            coolies.deleteCharAt(coolies.length()-1);
//            BookApplication.mInstance.setCookie(coolies.toString());
//            PreferencesUtils.putString(Preference.HTTP_COOKIE, coolies.toString());
        }
        return originalResponse;
    }
}
