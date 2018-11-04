package com.example.excel.domain.dto;

import java.io.Serializable;

/**
 *
 * @param <T>
 */
public class BaseResult<T extends Object> implements Serializable {
    /**
     * 成功
     */
    private static final String SUCCESS = "0";
    /**
     * 失败
     */
    private static final String ERROR = "1";

    private String code;

    private String msg;

    private String slt;


    private T data;


    public BaseResult() {

    }

    public BaseResult(T data) {
        this(data, SUCCESS);
    }


    public BaseResult(T data, String code) {
        this.data = data;
        this.code = code;
    }

    public BaseResult(T data, String code, String msg, String slt) {
        this.data = data;
        this.code = code;
        this.msg = msg;
        this.slt = slt;
    }

    public BaseResult(String code) {
        this.code = code;
    }

    public BaseResult(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public BaseResult(String code, String msg, String slt) {
        this(null, code, msg, slt);
    }

    public static <T> BaseResult<T> success(T data) {
        return new BaseResult<T>(data, SUCCESS);
    }

    public static <T> BaseResult<T> success(T data, String msg) {
        return new BaseResult<T>(data, SUCCESS, msg, null);
    }

    public static <T> BaseResult<T> error(String errorMessage) {
        return new BaseResult<T>(ERROR, errorMessage, null);
    }

    public static <T> BaseResult<T> error(T data, String errorMessage) {
        return new BaseResult<T>(data, ERROR, errorMessage, null);
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getSlt() {
        return slt;
    }

    public void setSlt(String slt) {
        this.slt = slt;
    }

    public T getData() {
        return data;
    }


    public void setData(T data) {
        this.data = data;
    }

}
