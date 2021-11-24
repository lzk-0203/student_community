package com.sdut.community.exception;

import org.jetbrains.annotations.NotNull;

/**
 * @author 24699
 */
public class CustomizeException extends RuntimeException{
    private String message;

    public CustomizeException(@NotNull IfCustomizeErrorCode errorCode) {
        this.message = errorCode.getMessage();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
