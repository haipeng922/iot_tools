package com.fencer.utils;

/**
 * @Date:           2020/10/28
 * @Author:         dengpeng
 * @Description:    restful风格数据响应
 */
public class ResponseResult<T> {

    private int code;
    private boolean success;
    private String message;
    private int count;
    private T data;


    public ResponseResult() {
    }

    public ResponseResult(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ResponseResult(boolean success, int code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }

    private ResponseResult(boolean success, int code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    private ResponseResult(boolean success, int code, String message, T data,int count) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
        this.count = count;
    }

    public static <T> ResponseResult<T> success() {
        return new ResponseResult(true, 0,"[]");
    }

    public static <T> ResponseResult<T> success(String message) {
        return new ResponseResult(true, 0, message);
    }

    public static <T> ResponseResult<T> success(T data) {
        return new ResponseResult(true, 0, "[]",data);
    }

    public static <T> ResponseResult<T> success(int successCode, String message) {
        return new ResponseResult(true,successCode, message);
    }

    public static <T> ResponseResult<T> success(T data,int count) {
        return new ResponseResult(true, 0, "[]", data,count);
    }

    public static <T> ResponseResult<T> success(String message, T data,int count) {
        return new ResponseResult(true, 0, message, data,count);
    }

    public static <T> ResponseResult<T> success(int successCode, String message, T data) {
        return new ResponseResult(true, successCode, message, data);
    }

    public static <T> ResponseResult<T> error(int errorCode, String errormessage) {
        return new ResponseResult(false, errorCode, errormessage);
    }

    public static <T> ResponseResult<T> error(String errormessage) {
        return new ResponseResult(false, -1,errormessage);
    }

    public boolean getSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getmessage() {
        return this.message;
    }

    public void setmessage(String message) {
        this.message = message;
    }

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
}
