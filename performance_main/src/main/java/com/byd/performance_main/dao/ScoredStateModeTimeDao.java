package com.byd.performance_main.dao;


import com.byd.performance_main.model.ScoredStateModeTimeBean;

import java.util.List;

public interface ScoredStateModeTimeDao {
    int insert(ScoredStateModeTimeBean scoredStateModeTimeBean);

    int delete(Integer id);

    int update(ScoredStateModeTimeBean scoredStateModeTimeBean);

    ScoredStateModeTimeBean queryScoredStateModeTimeBeanFromScoredTime(String scoreTime);

    List<ScoredStateModeTimeBean> queryScoredStateModeTimeBeanFromIsScored(Integer isScored);

    List<ScoredStateModeTimeBean> queryAllScoredStateModeTimeBean();
}
