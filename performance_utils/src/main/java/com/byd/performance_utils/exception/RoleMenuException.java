package com.byd.performance_utils.exception;

public class RoleMenuException extends RuntimeException {
    public RoleMenuException() {
        throw new RoleMenuException("没有权限");
    }

    public RoleMenuException(String message) {
        super(message);
    }
}
