package com.byd.performance_main.service.impl;


import com.byd.performance_main.dao.GroupScoringMemberDao;
import com.byd.performance_main.model.GroupScoringMemberBean;
import com.byd.performance_main.service.GroupScoringMemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "groupScoringMemberService")
public class GroupScoringMemberServiceImpl implements GroupScoringMemberService {
    @Autowired
    private GroupScoringMemberDao groupScoringMemberDao;//这里会报错，但是并不会影响

    @Override
    public int addGroupScoringMember(GroupScoringMemberBean groupScoringMemberBean) {
        return groupScoringMemberDao.insert(groupScoringMemberBean);
    }

    @Override
    public int delGroupScoringMember(Integer id) {
        return groupScoringMemberDao.delete(id);
    }

    @Override
    public int delManyGroupScoringMember(List<Integer> ids) {
        return groupScoringMemberDao.deleteMany(ids);
    }

    @Override
    public int updateGroupScoringMember(GroupScoringMemberBean groupScoringMemberBean) {
        return groupScoringMemberDao.update(groupScoringMemberBean);
    }

    @Override
    public List<GroupScoringMemberBean> findGroupScoringMemberBeanFromUserId(String userId) {
        return groupScoringMemberDao.queryGroupScoringMemberBeanFromUserId(userId);
    }

    @Override
    public GroupScoringMemberBean findGroupScoringMemberBeanFromUserIdAndTime(String userId, String scoreTime) {
        return groupScoringMemberDao.queryGroupScoringMemberBeanFromUserIdAndTime(userId, scoreTime);
    }

    @Override
    public List<GroupScoringMemberBean> findGroupScoringMemberBeansFromUserIdAndTime(String userId, String scoreTime) {
        return groupScoringMemberDao.queryGroupScoringMemberBeansFromUserIdAndTime(userId, scoreTime);
    }

    @Override
    public GroupScoringMemberBean findGroupScoringMemberBean(String userId, String currentGroupName, String ratingUserId, String scoreTime) {
        return groupScoringMemberDao.queryGroupScoringMemberId(userId, currentGroupName, ratingUserId, scoreTime);
    }

    @Override
    public List<GroupScoringMemberBean> findAllGroupScoringMemberBean() {
        return groupScoringMemberDao.queryAllGroupScoringMemberBean();
    }
}
