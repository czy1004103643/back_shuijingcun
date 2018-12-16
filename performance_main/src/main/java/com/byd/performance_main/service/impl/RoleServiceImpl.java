package com.byd.performance_main.service.impl;


import com.byd.performance_main.dao.RoleDao;
import com.byd.performance_main.dao.UserDao;
import com.byd.performance_main.model.RoleBean;
import com.byd.performance_main.model.UserBean;
import com.byd.performance_main.service.RoleService;
import com.byd.performance_main.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "roleService")
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;//这里会报错，但是并不会影响

    @Override
    public int addRole(RoleBean role) {
        return roleDao.insert(role);
    }

    @Override
    public int delRole(Integer id) {
        return roleDao.delete(id);
    }

    @Override
    public int updateRole(RoleBean role) {
        return roleDao.update(role);
    }

    @Override
    public List<RoleBean> findAllRole() {
        List<RoleBean> roleBeans = roleDao.queryRoleBean();
        return roleBeans;
    }

    @Override
    public String findRoleName(Integer roleLevel) {
        return roleDao.queryRoleName(roleLevel);
    }
}
