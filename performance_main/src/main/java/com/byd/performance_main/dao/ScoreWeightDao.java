package com.byd.performance_main.dao;


import com.byd.performance_main.model.ScoreWeightBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreWeightDao {
    int insert(ScoreWeightBean scoreWeightBean);

    int delete(Integer id);

    int update(ScoreWeightBean scoreWeightBean);

    int updateWeightNameFromValue(@Param("weightName") Integer weightName, @Param("scoreValue") Integer scoreValue);

    Integer queryScoreValueFromWeightName(Integer weightName);

    List<ScoreWeightBean> queryScoreWeightBean();
}
