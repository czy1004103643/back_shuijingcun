package com.byd.performance_main.dao;


import com.byd.performance_main.model.GroupNameBean;

import java.util.List;

public interface GroupNameDao {
    int insert(GroupNameBean groupNameBean);

    int delete(Integer id);

    int update(GroupNameBean groupNameBean);

    GroupNameBean queryGroupNameBeanFromId(Integer id);

    GroupNameBean queryGroupNameBeanFromName(String groupName);

    List<GroupNameBean> queryAllGroupNameBean();
}
