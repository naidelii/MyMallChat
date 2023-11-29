package com.naidelii.base.domain.vo.response;

import com.naidelii.base.constant.enums.ResultCodeEnum;
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
     * @see com.naidelii.base.constant.enums.ResultCodeEnum
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

    private Result(Integer code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    /**
     * 默认的成功消息
     *
     * @return Result
     */
    public static <T> Result<T> success() {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), null);
    }


    public static <T> Result<T> success(T data) {
        return new Result<>(ResultCodeEnum.SUCCESS.getCode(), ResultCodeEnum.SUCCESS.getMessage(), data);
    }

    /**
     * 默认的错误信息
     *
     * @return Result
     */

    public static <T> Result<T> failed() {
        return new Result<>(ResultCodeEnum.FAIL.getCode(), ResultCodeEnum.FAIL.getMessage(), null);
    }

    public static <T> Result<T> failed(String msg) {
        return new Result<>(ResultCodeEnum.FAIL.getCode(), msg, null);
    }

    public static <T> Result<T> failed(Integer code, String msg) {
        return new Result<>(code, msg, null);
    }
}
