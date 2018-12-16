package com.byd.performance_utils.exception;

public class ExcelParseException extends RuntimeException {
    public ExcelParseException() {
        throw new ExcelParseException("excel格式错误");
    }
    public ExcelParseException(String message) {
        super(message);
    }
}
