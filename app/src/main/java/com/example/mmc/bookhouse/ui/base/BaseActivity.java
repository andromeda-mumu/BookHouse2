package com.example.mmc.bookhouse.ui.base;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import butterknife.ButterKnife;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：activity基类
 */

public abstract  class BaseActivity extends AppCompatActivity{
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(getResId());
        ButterKnife.bind(this);
        initView();
        initData();
        initListener();
    }

    protected abstract int getResId();
    protected void initData(){};
    protected void initListener(){};
    protected void initView(){}

    public void startActivity(Class<Activity> clazz){
        Intent intent = new Intent(this,clazz);
        startActivity(intent);
    }


}
