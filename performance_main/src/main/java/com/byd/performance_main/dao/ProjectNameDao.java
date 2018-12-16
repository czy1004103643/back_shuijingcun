package com.byd.performance_main.dao;


import com.byd.performance_main.model.ProjectNameBean;

import java.util.List;

public interface ProjectNameDao {
    int insert(ProjectNameBean projectNameBean);

    int delete(Integer id);

    int update(ProjectNameBean projectNameBean);

    ProjectNameBean queryProjectNameBeanFromId(Integer id);

    ProjectNameBean queryProjectNameBeanFromName(String projectName);

    List<ProjectNameBean> queryAllProjectNameBean();
}
