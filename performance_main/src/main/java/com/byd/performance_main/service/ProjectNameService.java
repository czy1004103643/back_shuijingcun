package com.byd.performance_main.service;


import com.byd.performance_main.model.ProjectNameBean;

import java.util.List;

public interface ProjectNameService {
    int addProjectName(ProjectNameBean projectNameBean);

    int delProjectName(Integer id);

    int updateProjectName(ProjectNameBean projectNameBean);

    ProjectNameBean findProjectNameFromId(Integer id);

    ProjectNameBean findProjectNameFromName(String projectName);

    List<ProjectNameBean> findAllProjectName();
}
