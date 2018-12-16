package com.byd.performance_utils.code;

public interface ProjectRoleCode {
    int UNKNOWN = 0; //未知
    String UNKNOWN_NAME = "UNKNOWN";

    int FO = 1; //小组成员
    String FO_NAME = "FO";

    int FGL = 2; //功能组组长
    String FGL_NAME = "FGL"; //功能组组长

    int SPDM = 3;//项目管理人员
    String SPDM_NAME = "SPDM";//项目管理人员

    int ARC = 4;//架构师
    String ARC_NAME = "ARC";//架构师

    int BOSS = 5;//大老板
    String BOSS_NAME = "BOSS";//大老板

    int REAL_BOSS = 6;//超级权限
    String REAL_BOSS_NAME = "超级权限";//超级权限

    int RTL=7;
    String RTL_NAME="小组组长";
}
