package com.byd.performance_main.service;


import com.byd.performance_main.model.ProjectScoringMemberBean;

import java.util.List;

public interface ProjectScoringMemberService {
    int addProjectScoringMember(ProjectScoringMemberBean projectScoringMemberBean);

    int delProjectScoringMember(Integer id);

    int delManyProjectScoringMember(List<Integer> ids);

    int updateProjectScoringMember(ProjectScoringMemberBean projectScoringMemberBean);

    List<ProjectScoringMemberBean> findProjectScoringMemberBeanFromUserId(String userId);

    List<ProjectScoringMemberBean> findProjectScoringMemberBeanFromUserIdAndScoreTime(String userId, String scoreTime);

    Integer findProjectScoredFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(String userId, String currentProjectName, String ratingUserId, String scoreTime);

    Integer findIdFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(String userId, String currentProjectName, String ratingUserId, String scoreTime);

    ProjectScoringMemberBean findProjectScoringMemberBeanFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(String userId, String currentProjectName, String ratingUserId, String scoreTime);

    ProjectScoringMemberBean findProjectScoringMemberBeanFromUserIdAndProjectNameAndScoreTime(String userId,String currentProjectName,String scoreTime);

    List<ProjectScoringMemberBean> findAllProjectScoringMemberBean();
}
