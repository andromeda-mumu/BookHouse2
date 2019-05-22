package com.example.mmc.bookhouse.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：activity基类
 */

public abstract  class BaseActivity extends Activity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        initData();
        initListener();
    }

    protected abstract int getResId();
    protected void initData(){};
    protected void initListener(){};

    public void startActivity(Class<Activity> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }

}
