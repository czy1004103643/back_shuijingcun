package com.byd.performance_main.service.impl;

import com.byd.performance_main.dao.ProjectMemberDao;
import com.byd.performance_main.model.ProjectMemberBean;
import com.byd.performance_main.service.ProjectMemberService;
import com.byd.performance_utils.code.ProjectRoleCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "projectMemberService")
public class ProjectMemberServiceImpl implements ProjectMemberService {
    @Autowired
    private ProjectMemberDao projectMemberDao;//这里会报错，但是并不会影响

    @Override
    public int addProjectMember(ProjectMemberBean projectMemberBean) {
        return projectMemberDao.insert(projectMemberBean);
    }

    @Override
    public int addProjectBoss(String projectMember) {
        ProjectMemberBean projectMemberBean = new ProjectMemberBean();
        projectMemberBean.setProjectName(0);
        projectMemberBean.setProjectMember(projectMember);
        projectMemberBean.setRoleName(ProjectRoleCode.BOSS);
        return projectMemberDao.insert(projectMemberBean);
    }

    @Override
    public int delProjectMember(Integer id) {
        return projectMemberDao.delete(id);
    }

    @Override
    public int deleteProjectMemberFromProjectMember(String projectMember) {
        return projectMemberDao.deleteProjectMemberFromProjectMember(projectMember);
    }

    @Override
    public int updateProjectMember(ProjectMemberBean projectMemberBean) {
        return projectMemberDao.update(projectMemberBean);
    }

    @Override
    public ProjectMemberBean findProjectMemberBeanFromProjectNameAndProjectMember(Integer projectName, String projectMember) {
        return projectMemberDao.queryProjectMemberBeanFromProjectNameAndProjectMember(projectName, projectMember);
    }

    @Override
    public List<String> findProjectMemberFromProjectName(Integer projectName) {
        return projectMemberDao.queryProjectMemberFromProjectName(projectName);
    }

    @Override
    public List<Integer> findProjectNameFromProjectMember(String projectMember) {
        return projectMemberDao.queryProjectNameFromProjectMember(projectMember);
    }

    @Override
    public List<String> findProjectMemberFromProjectIdAndRole(Integer projectName, Integer roleName) {
        return projectMemberDao.queryProjectMemberFromProjectIdAndRole(projectName, roleName);
    }

    @Override
    public Integer findProjectNameRole(Integer projectName, String projectMember) {
        return projectMemberDao.queryProjectNameRole(projectName, projectMember);
    }

    @Override
    public List<ProjectMemberBean> findBossFromProjectMemberTable(Integer projectName) {
        return projectMemberDao.queryBossFromProjectMemberTable(projectName);
    }

    @Override
    public List<ProjectMemberBean> findAllProjectMemberBean() {
        return projectMemberDao.queryAllProjectMemberBean();
    }
}
