package com.byd.performance_main.service;


import com.byd.performance_main.model.ScoreHistoryBean;

import java.util.List;

public interface ScoreHistoryService {
    int addScoreHistory(ScoreHistoryBean scoreHistoryBean);

    int addManyScoreHistory(List<ScoreHistoryBean> scoreHistoryBeans);

    int delScoreHistory(Integer id);

    int delManyScoreHistory(List<Integer> ids);

    int updateScoreHistory(ScoreHistoryBean scoreHistoryBean);

    List<ScoreHistoryBean> findScoreHistoryBeanFromUserId(String userId);

    ScoreHistoryBean findScoreHistoryBeanFromUserIdAndScoreTime(String userId, String scoreTime);

    List<ScoreHistoryBean> findAllScoreHistoryBean();
}
