package com.byd.performance_main.dao;


import com.byd.performance_main.model.PermissionNameBean;

import java.util.List;

public interface PermissionNameDao {
    //todo
    int insert(PermissionNameBean permissionNameBean);

    int delete(Integer id);

    int update(PermissionNameBean permissionNameBean);

    String queryPermissionNameFromCode(Integer permissionCode);

    List<PermissionNameBean> queryAllPermissionName();
}
