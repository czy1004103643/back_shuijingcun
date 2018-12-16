package com.byd.performance_utils.exception;

public class CookieInvalidException extends RuntimeException {
    //无参构造方法
    public CookieInvalidException() {

        throw new CookieInvalidException("cookie invalid");
    }

    //有参的构造方法
    private CookieInvalidException(String message) {
        super(message);

    }
}
