package com.example.mmc.bookhouse.newnetwork.exception;

/**
 * Created by wangjiao on 2019/10/24.
 * description:
 */

public class ServerResponseException extends RuntimeException {
    private int code;
    private String info ;
    public ServerResponseException(int code, String info) {
      super(info);
      this.code= code;
      this.info=info;
    }

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
}
