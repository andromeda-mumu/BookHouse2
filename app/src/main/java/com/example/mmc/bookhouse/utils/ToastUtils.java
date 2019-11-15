package com.example.mmc.bookhouse.utils;

import android.widget.Toast;

import com.example.mmc.bookhouse.app.BookApplication;

/**
 * Created by wangjiao on 2019/11/15.
 * description:
 */

public class ToastUtils {
    public static void show(String msg){
        android.widget.Toast.makeText(BookApplication.mInstance,msg, Toast.LENGTH_SHORT).show();
    }
}
