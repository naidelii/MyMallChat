package com.naidelii.api;

import com.naidelii.constant.enums.ResultEnum;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author naidelii
 */
@Getter
@Setter
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 返回消息
     */
    private String message;

    /**
     * 响应Code
     */
    private int code;

    /**
     * 响应数据
     */
    private T data;



    private Result() {
    }

    private Result(int code, String message) {
        this.message = message;
        this.code = code;
    }

    public static <T> Result<T> ok() {
        return ok(ResultEnum.SUCCESS.getMessage());
    }

    public static <T> Result<T> ok(String msg) {
        return new Result<>(ResultEnum.SUCCESS.getCode(), msg);
    }

    public static <T> Result<T> okData(T data) {
        return okData(ResultEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> okData(String msg, T data) {
        Result<T> result = new Result<>(ResultEnum.SUCCESS.getCode(), msg);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> error() {
        return error(ResultEnum.ERROR.getMessage());
    }

    public static <T> Result<T> error(String msg) {
        return error(ResultEnum.ERROR.getCode(), msg);
    }

    public static <T> Result<T> error(ResultEnum resultEnum) {
        return error(resultEnum.getCode(), resultEnum.getMessage());
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg);
    }


    public static <T> Result<T> errorData(T data) {
        return errorData(ResultEnum.ERROR.getMessage(), data);
    }

    public static <T> Result<T> errorData(String msg, T data) {
        Result<T> result = new Result<>(ResultEnum.ERROR.getCode(), msg);
        result.setData(data);
        return result;
    }

}
