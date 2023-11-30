package com.naidelii.base.exception;

/**
 * @author naidelii
 */
public class NotLoginException extends MallChatException{
    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(Throwable cause) {
        super(cause);
    }

    public NotLoginException(String message, Throwable cause) {
        super(message, cause);
    }
}
