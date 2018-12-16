package com.byd.performance_utils.exception;

public class GroupMemberDuplicateException extends RuntimeException {
    //无参构造方法
    public GroupMemberDuplicateException() {

        throw new GroupMemberDuplicateException("Group Member is duplicate in same group.");
    }

    //有参的构造方法
    private GroupMemberDuplicateException(String message) {
        super(message);

    }
}
