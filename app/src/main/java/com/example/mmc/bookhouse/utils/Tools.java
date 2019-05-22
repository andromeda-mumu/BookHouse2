package com.example.mmc.bookhouse.utils;

import android.text.TextUtils;
import android.widget.Toast;

import com.example.mmc.bookhouse.app.BookApplication;

import java.util.List;

/**
 * Created by wangjiao on 2019/5/22.
 * 功能描述：
 */

public class Tools {
    public static boolean notNull(Object obj) {
        return obj!=null;
    }

    public static boolean notEmpty(String str) {
        return !TextUtils.isEmpty(str);
    }
    public static boolean isEmpty(String str) {
        return TextUtils.isEmpty(str);
    }
    public static void toast(String msg){
        Toast.makeText(BookApplication.mInstance,msg,Toast.LENGTH_SHORT);
    }
    public static boolean notEmpty(List list){
        return list!=null && list.size()>0;
    }
    public static boolean isEmpty(List list){
        return list==null || list.size()==0;
    }
}
