package com.byd.performance_main.service.impl;


import com.byd.performance_main.dao.RolePermissionDao;
import com.byd.performance_main.model.RolePermissionBean;
import com.byd.performance_main.service.RolePermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "rolePermissionService")
public class RolePermissionServiceImpl implements RolePermissionService {
    @Autowired
    private RolePermissionDao rolePermissionDao;//这里会报错，但是并不会影响


    @Override
    public int addRolePermission(RolePermissionBean rolePermissionBean) {
        return rolePermissionDao.insert(rolePermissionBean);
    }

    @Override
    public int delRolePermission(Integer id) {
        return rolePermissionDao.delete(id);
    }

    @Override
    public int updateRolePermission(RolePermissionBean rolePermissionBean) {
        return rolePermissionDao.update(rolePermissionBean);
    }

    @Override
    public RolePermissionBean findOwnRolePermissionBeanFromRole(Integer roleLevel) {
        return rolePermissionDao.queryOwnRolePermissionBeanFromRole(roleLevel);
    }

    @Override
    public List<RolePermissionBean> findAllRolePermission() {
        return rolePermissionDao.queryAllRolePermissionBean();
    }
}
