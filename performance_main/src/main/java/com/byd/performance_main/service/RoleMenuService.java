package com.byd.performance_main.service;

import com.byd.performance_main.model.RoleMenuBean;

public interface RoleMenuService {

    RoleMenuBean queryRoleMenuFromUserId(String userId);
}
