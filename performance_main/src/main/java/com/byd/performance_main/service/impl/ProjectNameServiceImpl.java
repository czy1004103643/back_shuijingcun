package com.byd.performance_main.service.impl;

import com.byd.performance_main.dao.ProjectNameDao;
import com.byd.performance_main.model.ProjectNameBean;
import com.byd.performance_main.service.ProjectNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "projectNameService")
public class ProjectNameServiceImpl implements ProjectNameService {
    @Autowired
    private ProjectNameDao projectNameDao;//这里会报错，但是并不会影响


    @Override
    public int addProjectName(ProjectNameBean projectNameBean) {
        return projectNameDao.insert(projectNameBean);
    }

    @Override
    public int delProjectName(Integer id) {
        return projectNameDao.delete(id);
    }

    @Override
    public int updateProjectName(ProjectNameBean projectNameBean) {
        return projectNameDao.update(projectNameBean);
    }

    @Override
    public ProjectNameBean findProjectNameFromId(Integer id) {
        return projectNameDao.queryProjectNameBeanFromId(id);
    }

    @Override
    public ProjectNameBean findProjectNameFromName(String projectName) {
        return projectNameDao.queryProjectNameBeanFromName(projectName);
    }

    @Override
    public List<ProjectNameBean> findAllProjectName() {
        return projectNameDao.queryAllProjectNameBean();
    }
}
