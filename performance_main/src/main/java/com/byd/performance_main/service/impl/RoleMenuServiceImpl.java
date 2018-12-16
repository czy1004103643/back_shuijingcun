package com.byd.performance_main.service.impl;

import com.byd.performance_main.dao.RoleMenuDao;
import com.byd.performance_main.model.RoleMenuBean;
import com.byd.performance_main.service.RoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service(value = "roleMenuService")
public class RoleMenuServiceImpl implements RoleMenuService {
    @Autowired
    RoleMenuDao roleMenuDao;
    @Override
    public RoleMenuBean queryRoleMenuFromUserId(String userId) {
        return roleMenuDao.queryRoleMenuFromUserId(userId);
    }
}
