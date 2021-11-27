package com.sdut.community.exception;

/**
 * @author 24699
 */
public interface IfCustomizeErrorCode {
    /**
     * error信息
     * @return
     */
    String getMessage();

    /**
     * error代码
     * @return
     */
    Integer getCode();
}
