package com.byd.performance_utils.code;

/**
 * 返回给前端判断的状态码
 */

public interface StateCode {
    //未知错误
    int UNKNOWN_ERROR = -1;
    String UNKNOWN_ERROR_MESSAGE = "未知错误";

    //正常处理所需内容
    int SUCCESS_PROCESS = 0;
    String SUCCESS_PROCESS_MESSAGE = "后台已处理";

    //处理所需内容失败
    int FAIL_PROCESSED = 1;
    String FAIL_PROCESSED_MESSAGE = "后台处理失败";

    //数据库处理异常
    int DATABASE_ERROR = 2;
    String DATABASE_ERROR_MESSAGE = "数据处理异常";

    //网络请求缺失必要的参数
    int MISS_PARAMETER = 3;
    String MISS_PARAMETER_MESSAGE = "网络请求缺失必要参数";

    //网络请求cookie无效
    int COOKIE_INVALID = 4;
    String COOKIE_INVALID_MESSAGE = "请求中的cookie参数验证无效";

    //当前用户权限不够，不能执行相应操作
    int PERMISSION_DENIED = 5;
    String PERMISSION_DENIED_MESSAGE = "权限不够，拒绝操作";

    //账户名已存在
    int USERID_DUPLCATE = 6;
    String USERID_DUPLCATE_MESSAGE = "账户名已存在";

    //小组中成员已存在
    int GROUP_MEMBER_DUPLCATE = 7;
    String GROUP_MEMBER_DUPLCATE_MESSAGE = "小组中成员已存在";

    //参数无效或为空
    int PARAM_INVALID = 8;
    String PARAM_INVALID_MESSAGE = "参数无效或为空";

    //数据库操作失败
    int DATABASE_OPERATION_FAILED = 9;
    String DATABASE_OPERATION_FAILED_MESSAGE = "数据库操作失败";

    //数据库操作失败
    int ACCOUNT_OR_PASSWORD_FAILED = 10;
    String ACCOUNT_OR_PASSWORD_FAILED_MESSAGE = "账号或密码错误";

    //工号不存在
    int SUBMIT_FAILURE = 11;
    String SUBMIT_FAILURE_MESSAGE = "提交失败";
}
