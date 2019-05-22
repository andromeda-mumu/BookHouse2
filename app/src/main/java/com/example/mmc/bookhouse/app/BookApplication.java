package com.example.mmc.bookhouse.app;

import android.app.Application;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */

public class BookApplication extends Application {
    public static BookApplication mInstance;
    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
