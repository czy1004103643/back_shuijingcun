package com.byd.performance_utils.exception;

public class DatabaseOperationFailedException extends RuntimeException {
    //无参构造方法
    public DatabaseOperationFailedException() {

        throw new DatabaseOperationFailedException("Database operation failed");
    }

    //有参的构造方法
    private DatabaseOperationFailedException(String message) {
        super(message);

    }
}
