package com.spring.cache.util;



import java.io.Serializable;


public class JsonResponse<T> implements Serializable {
    private String errcode;
    private String errmsg;
    private T data;

    public JsonResponse() {
    }

    public JsonResponse(String errcode, String errmsg, T data) {
        this.errcode = errcode;
        this.errmsg = errmsg;
        this.data = data;
    }

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }


    public static JsonResponse success() {
        return new JsonResponse("0", "ok", null);
    }

    public static JsonResponse success(Object data) {
        return new JsonResponse("0", "ok", data);
    }

    public static JsonResponse error(String errcode, String errmsg) {
        return new JsonResponse(errcode, errmsg, null);
    }

    public void setSuccess(T data) {
        this.errcode = "0";
        this.errmsg = "ok";
        this.data = data;
    }

}
