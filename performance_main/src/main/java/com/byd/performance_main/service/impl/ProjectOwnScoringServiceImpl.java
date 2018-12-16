package com.byd.performance_main.service.impl;

import com.byd.performance_main.dao.ProjectOwnScoringDao;
import com.byd.performance_main.model.ProjectOwnScoringBean;
import com.byd.performance_main.service.ProjectOwnScoringService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "projectOwnScoringService")
public class ProjectOwnScoringServiceImpl implements ProjectOwnScoringService {
    @Autowired
    private ProjectOwnScoringDao projectOwnScoringDao;//这里会报错，但是并不会影响

    @Override
    public int addProjectOwnScoring(ProjectOwnScoringBean projectOwnScoringBean) {
        return projectOwnScoringDao.insert(projectOwnScoringBean);
    }

    @Override
    public int delProjectOwnScoring(Integer id) {
        return projectOwnScoringDao.delete(id);
    }

    @Override
    public int delManyProjectOwnScoring(List<Integer> ids) {
        return projectOwnScoringDao.deleteMany(ids);
    }

    @Override
    public int updateProjectOwnScoring(ProjectOwnScoringBean projectOwnScoringBean) {
        return projectOwnScoringDao.update(projectOwnScoringBean);
    }

    @Override
    public List<ProjectOwnScoringBean> findProjectOwnScoringBeanFromUserId(String userId) {
        return projectOwnScoringDao.queryProjectOwnScoringBeanFromUserId(userId);
    }

    @Override
    public List<ProjectOwnScoringBean> findProjectOwnScoringBeanFromUserIdAndScoreTime(String userId, String scoreTime) {
        return projectOwnScoringDao.queryProjectOwnScoringBeanFromUserIdAndScoreTime(userId, scoreTime);
    }

    @Override
    public List<ProjectOwnScoringBean> findProjectOwnScoringBeanFromCurrentProjectNameAndScoreTime(String currentProjectName, String scoreTime) {
        return projectOwnScoringDao.queryProjectOwnScoringBeanFromCurrentProjectNameAndScoreTime(currentProjectName, scoreTime);
    }

    @Override
    public ProjectOwnScoringBean findProjectOwnScoringBeanFromUserIdAndCurrentProjectAndScoreTime(String userId, String currentProjectName, String scoreTime) {
        return projectOwnScoringDao.queryProjectOwnScoringBeanFromUserIdAndCurrentProjectNameAndScoreTime(userId, scoreTime, currentProjectName);
    }

    @Override
    public List<ProjectOwnScoringBean> findProjectOwnScoringBeansFromUserIdAndCurrentProjectAndScoreTime(String userId, String currentProjectName, String scoreTime) {
        return projectOwnScoringDao.queryProjectOwnScoringBeansFromUserIdAndCurrentProjectNameAndScoreTime(userId, scoreTime, currentProjectName);
    }

    @Override
    public List<ProjectOwnScoringBean> findAllProjectOwnScoringBean() {
        return projectOwnScoringDao.queryAllProjectOwnScoringBean();
    }

}
