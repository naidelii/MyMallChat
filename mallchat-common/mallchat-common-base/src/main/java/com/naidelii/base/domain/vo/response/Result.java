package com.naidelii.base.domain.vo.response;

import com.naidelii.base.constant.enums.ResultEnum;
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
    private String msg;

    /**
     * 响应Code
     *
     * @see com.naidelii.base.constant.enums.ResultEnum
     */
    private Integer code;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 私有化构造器
     */
    private Result() {

    }

    /**
     * 默认的成功消息
     *
     * @return Result
     */
    public static Result<Object> success() {
        return successData(null);
    }

    public static Result<Object> success(String msg) {
        return successData(msg, null);
    }

    public static <T> Result<T> successData(T data) {
        return successData(ResultEnum.SUCCESS.getMessage(), data);
    }

    public static <T> Result<T> successData(String msg, T data) {
        Result<T> result = new Result<>();
        result.setData(data);
        result.setMsg(msg);
        result.setCode(ResultEnum.SUCCESS.getCode());
        return result;
    }

    /**
     * 默认的错误信息
     *
     * @return Result
     */
    public static Result<Object> error() {
        return error(ResultEnum.ERROR.getCode(), ResultEnum.ERROR.getMessage());
    }

    public static Result<Object> error(String msg) {
        return error(ResultEnum.ERROR.getCode(), msg);
    }

    public static Result<Object> error(int code, String msg) {
        Result<Object> result = new Result<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }

}
