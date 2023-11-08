package com.naidelii.exception;

/**
 * @author naidelii
 * 自定义异常
 */
public class MallChatException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    public MallChatException(String message) {
        super(message);
    }

    public MallChatException(Throwable cause) {
        super(cause);
    }

    public MallChatException(String message, Throwable cause) {
        super(message, cause);
    }
}
