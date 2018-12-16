package com.byd.performance_utils.code;

public interface SpecialRecordCode {
    //Boss特定一个小组分组
    int BOSS_GROUP_ID = 0;

//    //ADMIN特定的一个小组分组
//    int ADMIN_GROUP_ID = 1;

    //Boss特定的一个项目分组
    int BOSS_PROJECT_ID = 0;

//    //ADMIN特定的一个项目分组
//    int ADMIN_PROJECT_ID = 1;

    //ADMIN的userId
    String ADMIN_USERID = "admin";

    //SQL语句增加，删除指令，判断是否执行成功，成功为1，失败为0
    int SQL_RESULT_FAIL = 0;

    int SQL_RESULT_SUCCESS = 1;
}
