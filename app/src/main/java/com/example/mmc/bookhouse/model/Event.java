package com.example.mmc.bookhouse.model;

/**
 * Created by wangjiao on 2019/5/23.
 * 功能描述：$end$
 */

public class Event {
    public String eventType;
    public Object object;
    public Event(String type){
        this.eventType=type;
    }
    public Event(String type,Object obj){
        this.eventType = type;
        this.object =obj;
    }

}
