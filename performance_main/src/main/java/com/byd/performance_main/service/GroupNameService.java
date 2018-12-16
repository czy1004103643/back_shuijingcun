package com.byd.performance_main.service;


import com.byd.performance_main.model.GroupNameBean;

import java.util.List;

public interface GroupNameService {
    int addGroupName(GroupNameBean groupNameBean);

    int delGroupName(Integer id);

    int updateGroupName(GroupNameBean groupNameBean);

    GroupNameBean findGroupNameFromId(Integer id);

    GroupNameBean findGroupNameFromName(String groupName);

    List<GroupNameBean> findAllGroupName();
}
