package com.sdut.community.exception;

/**
 * @author 24699
 */

public enum CustomizeErrorCode implements IfCustomizeErrorCode{
    QUESTION_NOT_FOUND(2001,"你找的问题不在了，换个试试吧!"),
    TARGET_PARAM_NOT_FOUND(2002,"请选择相应的评论或问题~~"),
    NO_LOGIN(2003,"当前未登录！请登录后重试~~"),
    SYSTEM_ERROR(2004,"好像找到bug了..."),
    TYPE_PARAM_WRONG(2005,"评论类型错误或不存在~~"),
    COMMENT_NOT_FOUND(2006,"你回复的评论不在了，换个试试吧!"),
    CONTENT_IS_EMPTY(2007,"回复内容不能为空!!");

    private Integer code;
    private String message = "好像出错了";;

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public Integer getCode() {
        return code;
    }

    CustomizeErrorCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
