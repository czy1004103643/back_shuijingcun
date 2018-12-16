package com.byd.performance_utils.code;

public interface ScoreWeightNameCode {
    //各个部分的分值权重

    //给FO打分的权重部分
    //FO自己部分的分数，即个人项目完成度
    int FO_SCORING_MYSELF = 0;
    //RTL给FO打分
    int RTL_SCORING_FO = 1;
    //FGL给FO打分
    int FGL_SCORING_FO = 2;


    //给FGL打分的权重部分
    //FGL自己部分的分数，即个人项目完成度
    int FGL_SCORING_MYSELF = 3;
    //RTL给FGL打分
    int RTL_SCORING_FGL = 4;
    //SPDM给FGL打分
    int SPDM_SCORING_FGL = 5;

    //RTL打分权重
    //RTL组员自评平均分
    int GROUP_MEMBER_AVG = 6;
    //BOSS给RTL打分
    int BOSS_SCORING_RTL = 10;

    //给SPDM和ARC打分的权重部分
    //SPDM或ARC自己部分的分数，即个人项目完成度
    int SPDM_OR_ARC_SCORING_MYSELF = 7;
    //BOSS级别人物给SPDM或ARC打分
    int RTL_SCORING_SPDM_OR_ARC = 8;
}
