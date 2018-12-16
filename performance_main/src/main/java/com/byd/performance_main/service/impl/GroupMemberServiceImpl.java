package com.byd.performance_main.service.impl;

import com.byd.performance_main.dao.GroupMemberDao;
import com.byd.performance_main.model.GroupMemberBean;
import com.byd.performance_main.service.GroupMemberService;
import com.byd.performance_utils.code.GroupRoleCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "groupMemberService")
public class GroupMemberServiceImpl implements GroupMemberService {
    @Autowired
    private GroupMemberDao groupMemberDao;//这里会报错，但是并不会影响

    @Override
    public int addGroupMember(GroupMemberBean groupMemberBean) {
        return groupMemberDao.insert(groupMemberBean);
    }

    @Override
    public int addGroupBoss(String groupMember) {
        GroupMemberBean groupMemberBean = new GroupMemberBean();
        groupMemberBean.setGroupName(0);
        groupMemberBean.setGroupMember(groupMember);
        groupMemberBean.setIsLeader(GroupRoleCode.BOSS);
        return groupMemberDao.insert(groupMemberBean);
    }

    @Override
    public int delGroupMember(Integer id) {
        return groupMemberDao.delete(id);
    }

    @Override
    public int deleteGroupMemberFromGroupMember(String groupMember) {
        return groupMemberDao.deleteGroupMemberFromGroupMember(groupMember);
    }

    @Override
    public GroupMemberBean findGroupMemberBeanFromGroupMember(String groupMember) {
        return groupMemberDao.findGroupMemberBeanFromGroupMember(groupMember);
    }

    @Override
    public int updateGroupMember(GroupMemberBean groupMemberBean) {
        return groupMemberDao.update(groupMemberBean);
    }

    @Override
    public Integer findIdFromGroupNameAndGroupMember(Integer groupName, String groupMember) {
        return groupMemberDao.queryIdFromGroupNameAndGroupMember(groupName, groupMember);
    }

    @Override
    public Integer findIdFromGroupMember(String groupMember) {
        return groupMemberDao.queryIdFromGroupMember(groupMember);
    }

    @Override
    public List<String> findGroupMemberFromGroupName(Integer groupName) {
        return groupMemberDao.queryGroupMemberFromGroupName(groupName);
    }

    @Override
    public Integer checkoutIsLeaderFromGroupMember(String groupMember) {
        return groupMemberDao.checkoutIsLeaderFromGroupMember(groupMember);
    }

    @Override
    public List<String> findGroupMemberFromGroupNameAndRole(Integer groupName, Integer isLeader) {
        return groupMemberDao.queryGroupMemberFromGroupNameAndRole(groupName, isLeader);
    }

    @Override
    public List<Integer> findGroupNameFromGroupMember(String groupMember) {
        return groupMemberDao.queryGroupNameFromGroupMember(groupMember);
    }

    @Override
    public String findGroupNameLeader(Integer groupName) {
        return groupMemberDao.queryGroupNameLeader(groupName);
    }

    @Override
    public Integer findGroupNameRole(String groupMember) {
        return groupMemberDao.queryGroupMemberRole(groupMember);
    }

    @Override
    public List<GroupMemberBean> findAllGroupMemberBean() {
        return groupMemberDao.queryAllGroupMemberBean();
    }

    @Override
    public GroupMemberBean findGroupMemberBeanFromGrouName(Integer groupName) {
        return groupMemberDao.queryGroupMemberBeanFromGroupName(groupName);
    }


}
