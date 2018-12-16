package com.byd.performance_main.service;


import com.byd.performance_main.model.UserPermissionBean;

import java.util.List;

public interface UserPermissionService {
    int addUserPermission(UserPermissionBean userPermissionBean);

    int delUserPermission(Integer id);

    int updateUserPermission(UserPermissionBean userPermissionBean);

    UserPermissionBean findOwnUserPermissionBeanFromUserId(String userId);

    List<UserPermissionBean> findAllUserPermission();
}
