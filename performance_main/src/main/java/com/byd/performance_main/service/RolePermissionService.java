package com.byd.performance_main.service;


import com.byd.performance_main.model.RolePermissionBean;

import java.util.List;

public interface RolePermissionService {
    int addRolePermission(RolePermissionBean rolePermissionBean);

    int delRolePermission(Integer id);

    int updateRolePermission(RolePermissionBean rolePermissionBean);

    RolePermissionBean findOwnRolePermissionBeanFromRole(Integer roleLevel);

    List<RolePermissionBean> findAllRolePermission();
}
