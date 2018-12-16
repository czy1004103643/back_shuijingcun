package com.byd.performance_main.service.impl;

import com.byd.performance_main.dao.GroupNameDao;
import com.byd.performance_main.model.GroupNameBean;
import com.byd.performance_main.service.GroupNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "groupNameService")
public class GroupNameServiceImpl implements GroupNameService {
    @Autowired
    private GroupNameDao groupNameDao;//这里会报错，但是并不会影响


    @Override
    public int addGroupName(GroupNameBean groupNameBean) {
        return groupNameDao.insert(groupNameBean);
    }

    @Override
    public int delGroupName(Integer id) {
        return groupNameDao.delete(id);
    }

    @Override
    public int updateGroupName(GroupNameBean groupNameBean) {
        return groupNameDao.update(groupNameBean);
    }

    @Override
    public GroupNameBean findGroupNameFromId(Integer id) {
        return groupNameDao.queryGroupNameBeanFromId(id);
    }

    @Override
    public GroupNameBean findGroupNameFromName(String groupName) {
        return groupNameDao.queryGroupNameBeanFromName(groupName);
    }

    @Override
    public List<GroupNameBean> findAllGroupName() {
        return groupNameDao.queryAllGroupNameBean();
    }
}
