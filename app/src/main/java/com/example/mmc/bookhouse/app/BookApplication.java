package com.example.mmc.bookhouse.app;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.raizlabs.android.dbflow.config.FlowConfig;
import com.raizlabs.android.dbflow.config.FlowLog;
import com.raizlabs.android.dbflow.config.FlowManager;

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

        //初始化DBFlow
        FlowManager.init(new FlowConfig.Builder(this).build());
        //设置日志显示
        FlowLog.setMinimumLoggingLevel(FlowLog.Level.V);

        Stetho.initializeWithDefaults(this);
    }

}
