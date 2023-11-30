package com.naidelii.base.exception;

/**
 * @author naidelii
 */
public class NotLoginException extends MallChatException{

    public NotLoginException(String message) {
        super(message);
    }

    public NotLoginException(Integer errorCode, String errorMessage) {
        super(errorCode, errorMessage);
    }
}
