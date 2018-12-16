package com.byd.performance_main.dao;


import com.byd.performance_main.model.ProjectMemberBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ProjectMemberDao {
    int insert(ProjectMemberBean projectMemberBean);

    int delete(Integer id);

    int deleteProjectMemberFromProjectMember(String projectMember);

    int update(ProjectMemberBean projectMemberBean);

    ProjectMemberBean queryProjectMemberBeanFromProjectNameAndProjectMember(@Param("projectName") Integer projectName, @Param("projectMember") String projectMember);

    List<String> queryProjectMemberFromProjectName(Integer projectName);

    List<Integer> queryProjectNameFromProjectMember(String projectMember);

    List<ProjectMemberBean> queryBossFromProjectMemberTable(Integer projectName);

    List<String> queryProjectMemberFromProjectIdAndRole(@Param("projectName") Integer projectName, @Param("roleName") Integer roleName);

    Integer queryProjectNameRole(@Param("projectName") Integer projectName, @Param("projectMember") String projectMember);

    List<ProjectMemberBean> queryAllProjectMemberBean();
}
