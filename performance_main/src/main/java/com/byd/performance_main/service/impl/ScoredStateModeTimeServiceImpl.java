package com.byd.performance_main.service.impl;

import com.byd.performance_main.dao.ScoredStateModeTimeDao;
import com.byd.performance_main.model.ScoredStateModeTimeBean;
import com.byd.performance_main.service.ScoredStateModeTimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "scoredStateModeTimeService")
public class ScoredStateModeTimeServiceImpl implements ScoredStateModeTimeService {
    @Autowired
    private ScoredStateModeTimeDao scoredStateModeTimeDao;//这里会报错，但是并不会影响

    @Override
    public int addScoredStateModeTime(ScoredStateModeTimeBean scoredStateModeTimeBean) {
        return scoredStateModeTimeDao.insert(scoredStateModeTimeBean);
    }

    @Override
    public int delScoredStateModeTime(Integer id) {
        return scoredStateModeTimeDao.delete(id);
    }

    @Override
    public int updateScoredStateModeTime(ScoredStateModeTimeBean scoredStateModeTimeBean) {
        return scoredStateModeTimeDao.update(scoredStateModeTimeBean);
    }

    @Override
    public ScoredStateModeTimeBean findScoredStateModeTimeBeanFromScoredTime(String scoreTime) {
        return scoredStateModeTimeDao.queryScoredStateModeTimeBeanFromScoredTime(scoreTime);
    }

    @Override
    public List<ScoredStateModeTimeBean> findScoredStateModeTimeBeanFromIsScored(Integer isScored) {
        return scoredStateModeTimeDao.queryScoredStateModeTimeBeanFromIsScored(isScored);
    }

    @Override
    public List<ScoredStateModeTimeBean> findAllScoredStateModeTimeBean() {
        return scoredStateModeTimeDao.queryAllScoredStateModeTimeBean();
    }
}
