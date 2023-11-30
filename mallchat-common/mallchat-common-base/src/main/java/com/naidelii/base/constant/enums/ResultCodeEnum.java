package com.naidelii.base.constant.enums;

import cn.hutool.json.JSONUtil;
import com.naidelii.base.constant.CommonConstants;
import com.naidelii.base.domain.vo.response.Result;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;

/**
 * @author naidelii
 */

@Getter
@AllArgsConstructor
@Slf4j
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

    /**
     * 错误响应
     *
     * @param response HttpServletResponse
     */
    public void sendHttpError(HttpServletResponse response) {
        response.setContentType(CommonConstants.JSON_UTF8_CONTENT_TYPE);
        response.setStatus(code);
        PrintWriter out;
        try {
            out = response.getWriter();
            Result<Object> result = Result.failed(code, message);
            out.write(JSONUtil.toJsonStr(result));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

}
