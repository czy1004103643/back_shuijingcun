package com.byd.performance_utils.exception;

public class UserIdInvalid extends RuntimeException {
    //无参
    public UserIdInvalid(){
        throw new UserIdInvalid("工号不存在！");
    }
    //有参
    public UserIdInvalid(String message){
        super(message);
    }
}
