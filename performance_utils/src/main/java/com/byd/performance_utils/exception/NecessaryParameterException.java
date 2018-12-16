package com.byd.performance_utils.exception;

public class NecessaryParameterException extends RuntimeException {
    //无参构造方法
    public NecessaryParameterException() {

        throw new NecessaryParameterException("Necessary Parameter Error");
    }

    //有参的构造方法
    private NecessaryParameterException(String message) {
        super(message);

    }
}
