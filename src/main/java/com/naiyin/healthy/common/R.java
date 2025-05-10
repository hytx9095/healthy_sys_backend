package com.naiyin.healthy.common;

import lombok.Data;

@Data
public class R<T> {
    private int code;
    private String msg;
    private T data;

    public R(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public static <T> R<T> success(){
        return new R<>(0, "success", null);
    }
    public static <T> R<T> success(T data){
        return new R<>(0, "success", data);
    }
    public static <T> R<T> success(String msg, T data){
        return new R<>(0, msg, data);
    }

    public static <T> R<T> error(String msg){
        return new R<>(40000, msg, null);
    }


    public static <T> R<T> error(int code, String msg){
        return new R<>(code, msg, null);
    }

}
