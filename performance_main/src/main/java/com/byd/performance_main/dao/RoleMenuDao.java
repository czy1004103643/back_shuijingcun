package com.byd.performance_main.dao;

import com.byd.performance_main.model.RoleMenuBean;

public interface RoleMenuDao {
    RoleMenuBean queryRoleMenuFromUserId(String userId);
}
