package com.example.mmc.bookhouse.newnetwork;


import com.example.mmc.bookhouse.newnetwork.api.WzApi;
import com.example.mmc.bookhouse.newnetwork.api.WzApiService;

/**
 * Created by wangjiao on 2019/10/24.
 * description:
 */

public class RetrofitHelper {
    private static WzApi mWzApi;
    public static WzApi getWzApi(){
        return mWzApi;
    }
    static {
        mWzApi = WzApiService.getApiService(WzApi.class, "htpt://www.bookhouse.com/");
    }

}
