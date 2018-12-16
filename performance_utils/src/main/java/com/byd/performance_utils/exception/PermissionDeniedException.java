package com.byd.performance_utils.exception;

public class PermissionDeniedException extends RuntimeException {
    //无参构造方法
    public PermissionDeniedException() {
        throw new PermissionDeniedException("Permission denied");
    }

    //有参的构造方法
    public PermissionDeniedException(String message) {
        super(message);
    }
}
