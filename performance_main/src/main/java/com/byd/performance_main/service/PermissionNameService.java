package com.byd.performance_main.service;


import com.byd.performance_main.model.PermissionNameBean;

import java.util.List;

public interface PermissionNameService {
    int addPermissionName(PermissionNameBean permissionNameBean);

    int delPermissionName(Integer id);

    int updatePermissionName(PermissionNameBean permissionNameBean);

    List<PermissionNameBean> findAllPermissionName();

    String findPermissionNameFromCode(Integer permissionCode);
}
