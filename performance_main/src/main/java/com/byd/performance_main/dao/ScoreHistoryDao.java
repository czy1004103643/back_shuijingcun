package com.byd.performance_main.dao;


import com.byd.performance_main.model.ScoreHistoryBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScoreHistoryDao {
    int insert(ScoreHistoryBean scoreHistoryBean);

    int insertMany(List<ScoreHistoryBean> scoreHistoryBeans);

    int delete(Integer id);

    int deleteMany(List<Integer> ids);

    int update(ScoreHistoryBean scoreHistoryBean);

    List<ScoreHistoryBean> queryScoreHistoryBeanFromUserId(String userId);

    ScoreHistoryBean queryScoreHistoryBeanFromUserIdAndScoreTime(@Param("userId") String userId, @Param("scoreTime") String scoreTime);

    List<ScoreHistoryBean> queryAllScoreHistoryBean();
}
