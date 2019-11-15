package com.example.mmc.bookhouse.newnetwork.response;

/**
 * Created by wangjiao on 2019/10/24.
 * description:
 */

public class BasicResponse<T> {
    private int code;
    private String info;
    private T data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
