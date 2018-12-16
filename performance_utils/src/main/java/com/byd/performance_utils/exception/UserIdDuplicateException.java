package com.byd.performance_utils.exception;

public class UserIdDuplicateException extends RuntimeException {
    //无参构造方法
    public UserIdDuplicateException() {

        throw new UserIdDuplicateException("UserId is duplicate");
    }

    //有参的构造方法
    private UserIdDuplicateException(String message) {
        super(message);

    }
}
