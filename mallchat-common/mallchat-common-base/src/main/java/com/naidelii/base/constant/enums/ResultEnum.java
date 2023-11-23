package com.naidelii.base.constant.enums;

import lombok.Getter;

/**
 * @author naidelii
 */

@Getter
public enum ResultEnum {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     */
    ERROR(500, "操作失败"),
    /**
     * 未登录
     */
    UNAUTHORIZED(401, "登录已过期，请重新登录"),
    /**
     * 暂无权限
     */
    FORBIDDEN(403, "暂无权限");

    /**
     * 响应code
     */
    Integer code;

    /**
     * 响应消息
     */
    String message;

    ResultEnum(Integer code, String message) {
        this.message = message;
        this.code = code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
