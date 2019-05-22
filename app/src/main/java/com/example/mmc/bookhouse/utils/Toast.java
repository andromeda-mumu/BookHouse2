package com.example.mmc.bookhouse.utils;

import com.example.mmc.bookhouse.app.BookApplication;

/**
 * Created by wangjiao on 2019/5/22.
 */

public class Toast {
    public static void show(String msg){
        android.widget.Toast.makeText(BookApplication.mInstance,msg, android.widget.Toast.LENGTH_SHORT).show();
    }

}
