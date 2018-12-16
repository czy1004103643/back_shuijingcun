package com.byd.performance_main.service.impl;


import com.byd.performance_main.dao.ScoreWeightDao;
import com.byd.performance_main.model.ScoreWeightBean;
import com.byd.performance_main.service.ScoreWeightService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "scoreWeightService")
public class ScoreWeightServiceImpl implements ScoreWeightService {
    @Autowired
    private ScoreWeightDao scoreWeightDao;//这里会报错，但是并不会影响

    @Override
    public int addScoreWeight(ScoreWeightBean scoreWeightBean) {
        return scoreWeightDao.insert(scoreWeightBean);
    }

    @Override
    public int delScoreWeight(Integer id) {
        return scoreWeightDao.delete(id);
    }

    @Override
    public int updateScoreWeight(ScoreWeightBean scoreWeightBean) {
        return scoreWeightDao.update(scoreWeightBean);
    }

    @Override
    public int updateWeightNameFromValue(Integer weightName, Integer scoreValue) {
        return scoreWeightDao.updateWeightNameFromValue(weightName, scoreValue);
    }

    @Override
    public Integer findScoreValueFromWeightName(Integer weightName) {
        return scoreWeightDao.queryScoreValueFromWeightName(weightName);
    }

    @Override
    public List<ScoreWeightBean> findAllScoreWeight() {
        return scoreWeightDao.queryScoreWeightBean();
    }
}
