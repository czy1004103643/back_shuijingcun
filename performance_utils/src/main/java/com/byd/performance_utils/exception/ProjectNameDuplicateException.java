package com.byd.performance_utils.exception;

public class ProjectNameDuplicateException extends RuntimeException{

public ProjectNameDuplicateException(){
    throw new ProjectNameDuplicateException("相同名字已存在");
}
public  ProjectNameDuplicateException(String message){
    super(message);

}

}
