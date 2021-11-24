package com.sdut.community.exception;

/**
 * @author 24699
 */

public enum CustomizeErrorCode implements IfCustomizeErrorCode{
    QUESTION_NOT_FOUND("你找的问题不在了，换个试试吧!");

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    CustomizeErrorCode(String message) {
        this.message = message;
    }
}
