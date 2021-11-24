package com.sdut.community.exception;

/**
 * @author 24699
 */
public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(IfCustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
