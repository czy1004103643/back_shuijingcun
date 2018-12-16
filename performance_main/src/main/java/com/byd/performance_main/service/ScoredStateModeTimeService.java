package com.byd.performance_main.service;


import com.byd.performance_main.model.ScoredStateModeTimeBean;

import java.util.List;

public interface ScoredStateModeTimeService {
    int addScoredStateModeTime(ScoredStateModeTimeBean scoredStateModeTimeBean);

    int delScoredStateModeTime(Integer id);

    int updateScoredStateModeTime(ScoredStateModeTimeBean scoredStateModeTimeBean);

    ScoredStateModeTimeBean findScoredStateModeTimeBeanFromScoredTime(String scoreTime);

    List<ScoredStateModeTimeBean> findScoredStateModeTimeBeanFromIsScored(Integer isScored);

    List<ScoredStateModeTimeBean> findAllScoredStateModeTimeBean();
}
