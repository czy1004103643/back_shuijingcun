package com.byd.performance_utils.exception;

public class ParamInvalidException extends RuntimeException {
    //无参构造方法
    public ParamInvalidException() {
        throw new ParamInvalidException("Param invalid or null");
    }

    //有参的构造方法
    public ParamInvalidException(String message) {
        super(message);
    }
}
