package com.example.mmc.bookhouse.utils;

import com.example.mmc.bookhouse.model.Event;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by wangjiao on 2019/5/23.
 * 功能描述：
 */

public class EventBusUtils {
    public static void register(Object subsciber){
        EventBus.getDefault().register(subsciber);
    }
    public static void unregister(Object subsciber){
        EventBus.getDefault().unregister(subsciber);
    }
    public static void post(String eventType){
        post(new Event(eventType));
    }

    public static void post(String eventType,Object obj){
        post(new Event(eventType,obj));
    }

    private static void post(Event event){
        EventBus.getDefault().post(event);
    }


}
