package com.example.mmc.bookhouse.newnetwork.api;


import com.example.mmc.bookhouse.newnetwork.utils.RetrofitUtils;

import retrofit2.Retrofit;

/**
 * Created by wangjiao on 2019/10/24.
 * description:
 */

public class WzApiService {
    public  static <T>T getApiService(Class<T> cls, String baseurl){
        Retrofit retrofit = RetrofitUtils.getRetrofitBuilder(baseurl).build();
        return retrofit.create(cls);
    }
}
