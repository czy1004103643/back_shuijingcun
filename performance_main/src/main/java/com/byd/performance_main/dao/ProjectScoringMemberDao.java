package com.byd.performance_main.dao;


import com.byd.performance_main.model.ProjectScoringMemberBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectScoringMemberDao {
    int insert(ProjectScoringMemberBean projectScoringMemberBean);

    int delete(Integer id);

    int deleteMany(List<Integer> ids);

    int update(ProjectScoringMemberBean projectScoringMemberBean);

    List<ProjectScoringMemberBean> queryProjectScoringMemberBeanFromUserId(String userId);

    List<ProjectScoringMemberBean> queryProjectScoringMemberBeanFromUserIdAndScoreTime(@Param("userId") String userId, @Param("scoreTime") String scoreTime);

    Integer queryProjectScoredFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(@Param("userId") String userId, @Param("currentProjectName") String currentProjectName, @Param("ratingUserId") String ratingUserId, @Param("scoreTime") String scoreTime);

    Integer queryIdFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(@Param("userId") String userId, @Param("currentProjectName") String currentProjectName, @Param("ratingUserId") String ratingUserId, @Param("scoreTime") String scoreTime);

    ProjectScoringMemberBean queryProjectScoringMemberBeanFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(@Param("userId") String userId, @Param("currentProjectName") String currentProjectName, @Param("ratingUserId") String ratingUserId, @Param("scoreTime") String scoreTime);

    ProjectScoringMemberBean findProjectScoringMemberBeanFromUserIdAndProjectNameAndScoreTime(@Param("userId") String userId, @Param("currentProjectName") String currentProjectName,@Param("scoreTime") String scoreTime);

    List<ProjectScoringMemberBean> queryAllProjectScoringMemberBean();
}
