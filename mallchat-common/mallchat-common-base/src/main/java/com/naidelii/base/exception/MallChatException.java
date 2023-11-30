package com.naidelii.base.exception;

/**
 * @author naidelii
 * 自定义异常
 */
public class MallChatException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    protected Integer errorCode;

    protected String errorMessage;


    public MallChatException(String message) {
        super(message);
        this.errorMessage = message;
    }

    public MallChatException(Integer errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

}
