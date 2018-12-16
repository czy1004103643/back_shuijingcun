package com.byd.performance_main.service;


import com.byd.performance_main.model.ProjectMemberBean;

import java.util.List;

public interface ProjectMemberService {
    int addProjectMember(ProjectMemberBean projectMemberBean);

    int addProjectBoss(String projectMember);

    int delProjectMember(Integer id);

    int deleteProjectMemberFromProjectMember(String projectMember);

    int updateProjectMember(ProjectMemberBean projectMemberBean);

    ProjectMemberBean findProjectMemberBeanFromProjectNameAndProjectMember(Integer projectName, String projectMember);

    List<String> findProjectMemberFromProjectName(Integer projectName);

    List<ProjectMemberBean> findBossFromProjectMemberTable(Integer projectName);

    List<Integer> findProjectNameFromProjectMember(String projectMember);

    List<String> findProjectMemberFromProjectIdAndRole(Integer projectName, Integer roleName);

    Integer findProjectNameRole(Integer projectName, String projectMember);

    List<ProjectMemberBean> findAllProjectMemberBean();
}
