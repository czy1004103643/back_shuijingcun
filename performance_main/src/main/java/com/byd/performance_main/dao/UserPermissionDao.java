package com.byd.performance_main.dao;


import com.byd.performance_main.model.UserPermissionBean;

import java.util.List;

public interface UserPermissionDao {
    //todo
    int insert(UserPermissionBean userPermissionBean);

    int delete(Integer id);

    int update(UserPermissionBean userPermissionBean);

    UserPermissionBean queryOwnUserPermissionBeanFromUserId(String userId);

    List<UserPermissionBean> queryAllUserPermissionBean();
}
