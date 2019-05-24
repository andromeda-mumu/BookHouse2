package com.example.mmc.bookhouse.utils;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mmc.bookhouse.app.BookApplication;

/**
 * Created by wangjiao on 2019/5/24.
 * 功能描述：
 */

public class SharePreferentUtils {
   private static SharedPreferences sp = BookApplication.mInstance.getSharedPreferences("book_house",Context.MODE_PRIVATE);
    public static void putBoolean(String name,boolean value){
        sp.edit().putBoolean(name,value).apply();
    }
    public static boolean getBoolean(String name,boolean def){
       return sp.getBoolean(name,def);
    }

}
