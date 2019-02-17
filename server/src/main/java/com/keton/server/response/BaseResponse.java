package com.keton.server.response;

import com.keton.server.enums.StatusCode;

public class BaseResponse<T> {
    private Integer code;
    private String msg;
    private T data;


    public BaseResponse(StatusCode statusCode,T data) {
        this.data = data;
        this.code=statusCode.getCode();
        this.msg=statusCode.getMsg();
    }

    public BaseResponse(StatusCode statusCode,String msg) {
        this.msg = msg;
        this.code=statusCode.getCode();
    }

    public BaseResponse(StatusCode statusCode) {
        this.code=statusCode.getCode();
        this.msg=statusCode.getMsg();
    }


    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
