package com.byd.performance_main.dao;


import com.byd.performance_main.model.ProjectOwnScoringBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectOwnScoringDao {
    int insert(ProjectOwnScoringBean projectOwnScoringBean);

    int delete(Integer id);

    int deleteMany(List<Integer> ids);

    int update(ProjectOwnScoringBean projectOwnScoringBean);

    List<ProjectOwnScoringBean> queryProjectOwnScoringBeanFromUserId(String userId);

    List<ProjectOwnScoringBean> queryProjectOwnScoringBeanFromUserIdAndScoreTime(@Param("userId") String userId, @Param("scoreTime") String scoreTime);

    List<ProjectOwnScoringBean> queryProjectOwnScoringBeanFromCurrentProjectNameAndScoreTime(@Param("currentProjectName") String currentProjectName, @Param("scoreTime") String scoreTime);

    ProjectOwnScoringBean queryProjectOwnScoringBeanFromUserIdAndCurrentProjectNameAndScoreTime(@Param("userId") String userId,
                                                                                                @Param("scoreTime") String scoreTime,
                                                                                                @Param("currentProjectName") String currentProjectName);

    List<ProjectOwnScoringBean> queryProjectOwnScoringBeansFromUserIdAndCurrentProjectNameAndScoreTime(@Param("userId") String userId,
                                                                                                       @Param("scoreTime") String scoreTime,
                                                                                                       @Param("currentProjectName") String currentProjectName);

    List<ProjectOwnScoringBean> queryAllProjectOwnScoringBean();
}
