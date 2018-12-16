package com.byd.performance_main.service;


import com.byd.performance_main.model.ScoreWeightBean;

import java.util.List;

public interface ScoreWeightService {
    int addScoreWeight(ScoreWeightBean scoreWeightBean);

    int delScoreWeight(Integer id);

    int updateScoreWeight(ScoreWeightBean scoreWeightBean);

    int updateWeightNameFromValue(Integer weightName, Integer scoreValue);

    Integer findScoreValueFromWeightName(Integer weightName);

    List<ScoreWeightBean> findAllScoreWeight();
}
