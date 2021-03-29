package com.example.wanqian.newbase.mvp;

import java.io.Serializable;

/**
 *描述：mode
 */
public class BaseModel<T> implements Serializable {
    private String msg;
    private String code;
    private T data;

    public BaseModel(String message, String code) {
        this.msg = message;
        this.code = code;
    }
    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String code() {
        return code;
    }
    public T getData() {
        return data;
    }

    public String getCode() {
        return code;
    }

    public void setData(T result) {
        this.data = result;
    }

    @Override
    public String toString() {
        return "BaseModel{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", data=" + data +
                '}';
    }
}
