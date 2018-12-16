package com.byd.performance_main.service.impl;


import com.byd.performance_main.dao.ProjectScoringMemberDao;
import com.byd.performance_main.model.ProjectScoringMemberBean;
import com.byd.performance_main.service.ProjectScoringMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "projectScoringMemberService")
public class ProjectScoringMemberServiceImpl implements ProjectScoringMemberService {
    @Autowired
    private ProjectScoringMemberDao projectScoringMemberDao;//这里会报错，但是并不会影响


    @Override
    public int addProjectScoringMember(ProjectScoringMemberBean projectScoringMemberBean) {
        return projectScoringMemberDao.insert(projectScoringMemberBean);
    }

    @Override
    public int delProjectScoringMember(Integer id) {
        return projectScoringMemberDao.delete(id);
    }

    @Override
    public int delManyProjectScoringMember(List<Integer> ids) {
        return projectScoringMemberDao.deleteMany(ids);
    }

    @Override
    public int updateProjectScoringMember(ProjectScoringMemberBean projectScoringMemberBean) {
        return projectScoringMemberDao.update(projectScoringMemberBean);
    }

    @Override
    public List<ProjectScoringMemberBean> findProjectScoringMemberBeanFromUserId(String userId) {
        return projectScoringMemberDao.queryProjectScoringMemberBeanFromUserId(userId);
    }

    @Override
    public List<ProjectScoringMemberBean> findProjectScoringMemberBeanFromUserIdAndScoreTime(String userId, String scoreTime) {
        return projectScoringMemberDao.queryProjectScoringMemberBeanFromUserIdAndScoreTime(userId, scoreTime);
    }

    @Override
    public Integer findProjectScoredFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(String userId, String currentProjectName, String ratingUserId, String scoreTime) {
        return projectScoringMemberDao.queryProjectScoredFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(userId, currentProjectName, ratingUserId, scoreTime);
    }

    @Override
    public Integer findIdFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(String userId, String currentProjectName, String ratingUserId, String scoreTime) {
        return projectScoringMemberDao.queryIdFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(userId, currentProjectName, ratingUserId, scoreTime);
    }

    @Override
    public ProjectScoringMemberBean findProjectScoringMemberBeanFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(String userId, String currentProjectName, String ratingUserId, String scoreTime) {
        return projectScoringMemberDao.queryProjectScoringMemberBeanFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(userId, currentProjectName, ratingUserId, scoreTime);
    }

    @Override
    public List<ProjectScoringMemberBean> findAllProjectScoringMemberBean() {
        return projectScoringMemberDao.queryAllProjectScoringMemberBean();
    }

    @Override
    public ProjectScoringMemberBean findProjectScoringMemberBeanFromUserIdAndProjectNameAndScoreTime(String userId, String currentProjectName, String scoreTime) {
        return projectScoringMemberDao.findProjectScoringMemberBeanFromUserIdAndProjectNameAndScoreTime(userId,currentProjectName,scoreTime);
    }
}
