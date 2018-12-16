package com.byd.performance_utils.code;

public interface GroupRoleCode {
    int FO = 0; //小组成员
    String FO_NAME = "组员";

    int RTL = 1; //小组组长
    String RTL_NAME = "组长"; //小组组长

    int BOSS = 2;//大老板
    String BOSS_NAME = "BOSS";//大老板

    int REAL_BOSS = 3;//超级权限
    String REAL_BOSS_NAME = "超级权限";//超级权限

    int UNKNOWN = 4;//未知
    String UNKNOWN_NAME = "未知人员";//未知人员
}
