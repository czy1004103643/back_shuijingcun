package com.byd.performance_main.service;


import com.byd.performance_main.model.ProjectOwnScoringBean;

import java.util.List;

public interface ProjectOwnScoringService {
    int addProjectOwnScoring(ProjectOwnScoringBean projectOwnScoringBean);

    int delProjectOwnScoring(Integer id);

    int delManyProjectOwnScoring(List<Integer> ids);

    int updateProjectOwnScoring(ProjectOwnScoringBean projectOwnScoringBean);

    List<ProjectOwnScoringBean> findProjectOwnScoringBeanFromUserId(String userId);

    List<ProjectOwnScoringBean> findProjectOwnScoringBeanFromUserIdAndScoreTime(String userId, String scoreTime);

    List<ProjectOwnScoringBean> findProjectOwnScoringBeanFromCurrentProjectNameAndScoreTime(String currentProjectName, String scoreTime);

    ProjectOwnScoringBean findProjectOwnScoringBeanFromUserIdAndCurrentProjectAndScoreTime(String userId, String currentProjectName, String scoreTime);

    List<ProjectOwnScoringBean> findProjectOwnScoringBeansFromUserIdAndCurrentProjectAndScoreTime(String userId, String currentProjectName, String scoreTime);

    List<ProjectOwnScoringBean> findAllProjectOwnScoringBean();
}
