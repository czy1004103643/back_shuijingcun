package com.byd.performance_main.service;


import com.byd.performance_main.model.RoleBean;
import com.byd.performance_main.model.UserBean;
import com.github.pagehelper.PageInfo;

import java.util.List;

public interface RoleService {
    int addRole(RoleBean role);

    int delRole(Integer id);

    int updateRole(RoleBean role);

    List<RoleBean> findAllRole();

    String findRoleName(Integer roleLevel );
}
