package com.byd.performance_utils.exception;

public class AccountOrPasswordInvalidException extends RuntimeException {
    //无参构造方法
    public AccountOrPasswordInvalidException() {

        throw new AccountOrPasswordInvalidException("Account Or Password invalid");
    }

    //有参的构造方法
    private AccountOrPasswordInvalidException(String message) {
        super(message);

    }
}
