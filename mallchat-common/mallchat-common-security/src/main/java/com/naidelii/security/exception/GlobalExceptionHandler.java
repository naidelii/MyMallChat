package com.naidelii.security.exception;

import cn.hutool.core.util.StrUtil;
import com.naidelii.base.constant.CommonConstants;
import com.naidelii.base.domain.vo.response.Result;
import com.naidelii.base.exception.MallChatException;
import com.naidelii.base.exception.NotLoginException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.yaml.snakeyaml.constructor.DuplicateKeyException;

import java.util.List;

/**
 * @author naidelii
 * 异常处理器
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {


    /**
     * 处理未知异常
     *
     * @param e 异常对象
     * @return 返回结果
     */
    @ExceptionHandler(Exception.class)
    public Result<Object> handleException(Exception e) {
        log.error("system exception！The reason is：{}", e.getMessage(), e);
        return Result.fail();
    }

    /**
     * http请求方式不支持
     *
     * @param e 异常对象
     * @return 返回结果
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public Result<Object> handleException(HttpRequestMethodNotSupportedException e) {
        log.error(e.getMessage(), e);
        return Result.fail(String.format("不支持'%s'请求", e.getMethod()));
    }


    /**
     * 处理自定义异常
     *
     * @param e 自定义异常
     * @return Result
     */
    @ExceptionHandler(MallChatException.class)
    public Result<?> handlerMallChatException(MallChatException e) {
        log.error("MallChat exception！The reason is：{}", e.getMessage());
        return Result.fail(e.getMessage());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    public Result<?> handleDuplicateKeyException(DuplicateKeyException e) {
        log.error(e.getMessage(), e);
        return Result.fail("数据库中已存在该记录");
    }

    /**
     * 处理自定义异常
     *
     * @param e 自定义异常
     * @return Result
     */
    @ExceptionHandler(NotLoginException.class)
    public Result<?> handlerNotLoginException(NotLoginException e) {
        log.error("MallChat exception！The reason is：{}", e.getMessage(), e);
        return Result.unauthorized();
    }

    /**
     * 处理参数校验错误
     *
     * @param e 异常对象
     * @return 返回结果
     */
    @ExceptionHandler(BindException.class)
    public Result<Object> handleBindException(BindException e) {
        log.error("validation parameters error！The reason is：{}", e.getMessage());
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        ObjectError objectError = allErrors.get(0);
        String defaultMessage = objectError.getDefaultMessage();
        String errorMsg = StrUtil.isNotBlank(defaultMessage) ? defaultMessage : CommonConstants.PARAM_VERIFY_ERROR_STR;
        return Result.fail(errorMsg);
    }

    /**
     * 处理参数校验错误（@RequestBody）
     *
     * @param e 异常对象
     * @return 返回结果
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Result<Object> handleMethodArgumentNotValidException(MethodArgumentNotValidException e) {
        log.error("validation parameters error！The reason is：{}", e.getMessage());
        List<ObjectError> allErrors = e.getBindingResult().getAllErrors();
        ObjectError objectError = allErrors.get(0);
        String defaultMessage = objectError.getDefaultMessage();
        String errorMsg = StrUtil.isNotBlank(defaultMessage) ? defaultMessage : CommonConstants.PARAM_VERIFY_ERROR_STR;
        return Result.fail(errorMsg);
    }

}
