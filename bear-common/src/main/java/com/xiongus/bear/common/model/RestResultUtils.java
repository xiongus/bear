package com.xiongus.bear.common.model;

/**
 * Rest result utils.
 */
public class RestResultUtils {
    
    public static <T> RestResult<T> success() {
        return RestResult.<T>builder().withCode(200).build();
    }
    
    public static <T> RestResult<T> success(T data) {
        return RestResult.<T>builder().withCode(200).withData(data).build();
    }
    
    public static <T> RestResult<T> success(int code, T data) {
        return RestResult.<T>builder().withCode(code).withData(data).build();
    }
    
    public static <T> RestResult<T> failed() {
        return RestResult.<T>builder().withCode(500).build();
    }
    
    public static <T> RestResult<T> failed(String errMsg) {
        return RestResult.<T>builder().withCode(500).withMsg(errMsg).build();
    }
    
    public static <T> RestResult<T> failed(int code, T data) {
        return RestResult.<T>builder().withCode(code).withData(data).build();
    }
    
    public static <T> RestResult<T> failed(int code, T data, String errMsg) {
        return RestResult.<T>builder().withCode(code).withData(data).withMsg(errMsg).build();
    }
    
    public static <T> RestResult<T> failedWithMsg(int code, String errMsg) {
        return RestResult.<T>builder().withCode(code).withMsg(errMsg).build();
    }
    
}
