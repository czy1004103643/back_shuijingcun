package com.byd.performance_main.dao;


import com.byd.performance_main.model.GroupMemberBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupMemberDao {
    int insert(GroupMemberBean groupMemberBean);

    int delete(Integer id);

    int deleteGroupMemberFromGroupMember(String groupMember);

    int update(GroupMemberBean groupMemberBean);

    Integer queryIdFromGroupNameAndGroupMember(@Param("groupName") Integer groupName, @Param("groupMember") String groupMember);

    Integer queryIdFromGroupMember(@Param("groupMember") String groupMember);

    List<String> queryGroupMemberFromGroupName(Integer groupName);

    List<Integer> queryGroupNameFromGroupMember(String groupMember);

    Integer checkoutIsLeaderFromGroupMember(String groupMember);

    List<String> queryGroupMemberFromGroupNameAndRole(@Param("groupName") Integer groupName, @Param("isLeader") Integer isLeader);

    String queryGroupNameLeader(Integer groupName);

    Integer queryGroupMemberRole(String groupMember);

    List<GroupMemberBean> queryAllGroupMemberBean();

    GroupMemberBean queryGroupMemberBeanFromGroupName(Integer groupName);

    GroupMemberBean findGroupMemberBeanFromGroupMember(String groupMember);

}
