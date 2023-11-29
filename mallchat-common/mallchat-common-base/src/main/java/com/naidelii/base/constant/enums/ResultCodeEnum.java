package com.naidelii.base.constant.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author naidelii
 */

@Getter
@AllArgsConstructor
public enum ResultCodeEnum {
    /**
     * 操作成功
     */
    SUCCESS(200, "操作成功"),
    /**
     * 操作失败
     */
    FAIL(500, "操作失败"),
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
    private final Integer code;

    /**
     * 响应消息
     */
    private final String message;

}
