package com.example.mmc.bookhouse.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

/**
 * Created by wangjiao on 2019/5/24.
 * 功能描述：
 */

public class TextviewUtils {
    public static void setLeftDrawable(Context context, TextView  tv,int resc){
        Drawable drawable = context.getResources().getDrawable(resc);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tv.setCompoundDrawables(drawable,null,null,null);
    }
    public static void setRightDrawable(Context context, TextView  tv,int resc){
        Drawable drawable = context.getResources().getDrawable(resc);
        drawable.setBounds(0,0,drawable.getMinimumWidth(),drawable.getMinimumHeight());
        tv.setCompoundDrawables(null,null,drawable,null);
    }


}
