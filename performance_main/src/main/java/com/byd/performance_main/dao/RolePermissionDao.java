package com.byd.performance_main.dao;


import com.byd.performance_main.model.RolePermissionBean;

import java.util.List;

public interface RolePermissionDao {
    //todo
    int insert(RolePermissionBean rolePermissionBean);

    int delete(Integer id);

    int update(RolePermissionBean rolePermissionBean);

    RolePermissionBean queryOwnRolePermissionBeanFromRole(Integer roleLevel);

    List<RolePermissionBean> queryAllRolePermissionBean();
}
