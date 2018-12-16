package com.byd.performance_main.dao;


import com.byd.performance_main.model.GroupScoringMemberBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface GroupScoringMemberDao {
    int insert(GroupScoringMemberBean groupScoringMemberBean);

    int delete(Integer id);

    int deleteMany(List<Integer> ids);

    int update(GroupScoringMemberBean groupScoringMemberBean);

    List<GroupScoringMemberBean> queryGroupScoringMemberBeanFromUserId(String userId);

    GroupScoringMemberBean queryGroupScoringMemberBeanFromUserIdAndTime(@Param("userId") String userId, @Param("scoreTime") String scoreTime);

    List<GroupScoringMemberBean> queryGroupScoringMemberBeansFromUserIdAndTime(@Param("userId") String userId, @Param("scoreTime") String scoreTime);

    GroupScoringMemberBean queryGroupScoringMemberId(@Param("userId") String userId, @Param("currentGroupName") String currentGroupName, @Param("ratingUserId") String ratingUserId, @Param("scoreTime") String scoreTime);

    List<GroupScoringMemberBean> queryAllGroupScoringMemberBean();
}
