package com.byd.performance_main.service;


import com.byd.performance_main.model.GroupScoringMemberBean;

import java.util.List;

public interface GroupScoringMemberService {
    int addGroupScoringMember(GroupScoringMemberBean groupScoringMemberBean);

    int delGroupScoringMember(Integer id);

    int delManyGroupScoringMember(List<Integer> ids);

    int updateGroupScoringMember(GroupScoringMemberBean groupScoringMemberBean);

    List<GroupScoringMemberBean> findGroupScoringMemberBeanFromUserId(String userId);

    GroupScoringMemberBean findGroupScoringMemberBeanFromUserIdAndTime(String userId, String scoreTime);

    List<GroupScoringMemberBean> findGroupScoringMemberBeansFromUserIdAndTime(String userId, String scoreTime);

    GroupScoringMemberBean findGroupScoringMemberBean(String userId, String currentGroupName, String ratingUserId, String scoreTime);

    List<GroupScoringMemberBean> findAllGroupScoringMemberBean();
}
