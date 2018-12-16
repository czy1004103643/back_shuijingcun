package com.byd.performance_utils.exception;

public class ProjectMemberDuplicateException extends RuntimeException {
    public ProjectMemberDuplicateException(){
        throw  new ProjectMemberDuplicateException("相同名字已存在");
    }
    public ProjectMemberDuplicateException(String message){
        super(message);

    }
}
