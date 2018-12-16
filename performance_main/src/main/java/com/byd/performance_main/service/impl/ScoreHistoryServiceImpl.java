package com.byd.performance_main.service.impl;


import com.byd.performance_main.dao.ScoreHistoryDao;
import com.byd.performance_main.model.ScoreHistoryBean;
import com.byd.performance_main.service.ScoreHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "scoreHistoryService")
public class ScoreHistoryServiceImpl implements ScoreHistoryService {
    @Autowired
    private ScoreHistoryDao scoreHistoryDao;//这里会报错，但是并不会影响

    @Override
    public int addScoreHistory(ScoreHistoryBean scoreHistoryBean) {
        return scoreHistoryDao.insert(scoreHistoryBean);
    }

    @Override
    public int addManyScoreHistory(List<ScoreHistoryBean> scoreHistoryBeans) {
        return scoreHistoryDao.insertMany(scoreHistoryBeans);
    }

    @Override
    public int delScoreHistory(Integer id) {
        return scoreHistoryDao.delete(id);
    }

    @Override
    public int delManyScoreHistory(List<Integer> ids) {
        return scoreHistoryDao.deleteMany(ids);
    }

    @Override
    public int updateScoreHistory(ScoreHistoryBean scoreHistoryBean) {
        return scoreHistoryDao.update(scoreHistoryBean);
    }

    @Override
    public List<ScoreHistoryBean> findScoreHistoryBeanFromUserId(String userId) {
        return scoreHistoryDao.queryScoreHistoryBeanFromUserId(userId);
    }

    @Override
    public ScoreHistoryBean findScoreHistoryBeanFromUserIdAndScoreTime(String userId, String scoreTime) {
        return scoreHistoryDao.queryScoreHistoryBeanFromUserIdAndScoreTime(userId, scoreTime);
    }

    @Override
    public List<ScoreHistoryBean> findAllScoreHistoryBean() {
        return scoreHistoryDao.queryAllScoreHistoryBean();
    }
}
