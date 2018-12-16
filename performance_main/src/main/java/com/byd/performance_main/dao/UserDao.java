package com.byd.performance_main.dao;


import com.byd.performance_main.model.UserBean;
import org.apache.catalina.User;

import java.util.List;

public interface UserDao {
    int insert(UserBean userBean);

    int updateUserRole(UserBean userBean);

    int update(UserBean userBean);

    int delete(UserBean userBean);

    int updatePassword(UserBean userBean);

    List<UserBean> adminUser(UserBean userBean);

    String queryPasswordFromUserId(String userId);

    String queryUserIdFromCookie(String userCookie);

    UserBean queryUserFromUserId(String userId);

    List<UserBean> queryUserFromUserName(String userName);

    List<UserBean> queryUserBean();
}
