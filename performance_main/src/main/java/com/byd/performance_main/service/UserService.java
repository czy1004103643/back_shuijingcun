package com.byd.performance_main.service;


import com.byd.performance_main.model.UserBean;
import com.github.pagehelper.PageInfo;

import java.util.List;
import java.util.Map;

public interface UserService {
    int addUser(UserBean user);

    int updateUser(UserBean userBean);

    int updateUserCookie(String userCookie, String userId);

    int updateUserPassword(String userId, String userPassword);

    int updatePassword(UserBean userBean);

    int updateUserRole(UserBean userBean);

    int deleteUser(String userId);

    List<UserBean> adminUser(UserBean userBean);

    Map<String, Boolean> deleteUser(List<String> userIds);

    String findPasswordFromUserId(String userId);

    String findUserIdFromCookie(String cookie);

    UserBean findUserFromUserId(String userId);

    List<UserBean> findUserFromUserName(String userName);

    PageInfo<UserBean> findAllUser(int pageNum, int pageSize);

    List<UserBean> findAllUser();
}
