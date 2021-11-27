package com.sdut.community.exception;

/**
 * @author 24699
 */

public class CustomizeException extends RuntimeException{
    private String message = "好像出错了";
    private Integer code;

    public CustomizeException(IfCustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
        this.code = errorCode.getCode();
    }

    @Override
    public String getMessage() {
        return message;
    }

    public Integer getCode() {
        return code;
    }
}
