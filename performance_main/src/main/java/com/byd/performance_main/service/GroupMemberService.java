package com.byd.performance_main.service;


import com.byd.performance_main.model.GroupMemberBean;

import java.util.List;

public interface GroupMemberService {
    int addGroupMember(GroupMemberBean groupMemberBean);

    int addGroupBoss(String groupMember);

    int delGroupMember(Integer id);

    int deleteGroupMemberFromGroupMember(String groupMember);

    int updateGroupMember(GroupMemberBean groupMemberBean);

    Integer findIdFromGroupMember(String groupMember);

    Integer findIdFromGroupNameAndGroupMember(Integer groupName, String groupMember);

    Integer checkoutIsLeaderFromGroupMember(String groupMember);

    List<String> findGroupMemberFromGroupNameAndRole(Integer groupName, Integer isLeader);

    List<String> findGroupMemberFromGroupName(Integer groupName);

    List<Integer> findGroupNameFromGroupMember(String groupMember);

    String findGroupNameLeader(Integer groupName);

    Integer findGroupNameRole(String groupMember);

    List<GroupMemberBean> findAllGroupMemberBean();

    GroupMemberBean findGroupMemberBeanFromGrouName(Integer groupName);

    GroupMemberBean findGroupMemberBeanFromGroupMember(String groupMember);


}
