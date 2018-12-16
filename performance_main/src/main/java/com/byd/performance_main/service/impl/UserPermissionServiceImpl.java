package com.byd.performance_main.service.impl;


import com.byd.performance_main.dao.UserPermissionDao;
import com.byd.performance_main.model.UserPermissionBean;
import com.byd.performance_main.service.UserPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "userPermissionService")
public class UserPermissionServiceImpl implements UserPermissionService {
    @Autowired
    private UserPermissionDao userPermissionDao;//这里会报错，但是并不会影响


    @Override
    public int addUserPermission(UserPermissionBean userPermissionBean) {
        return userPermissionDao.insert(userPermissionBean);
    }

    @Override
    public int delUserPermission(Integer id) {
        return userPermissionDao.delete(id);
    }

    @Override
    public int updateUserPermission(UserPermissionBean userPermissionBean) {
        return userPermissionDao.update(userPermissionBean);
    }

    @Override
    public UserPermissionBean findOwnUserPermissionBeanFromUserId(String userId) {
        return userPermissionDao.queryOwnUserPermissionBeanFromUserId(userId);
    }

    @Override
    public List<UserPermissionBean> findAllUserPermission() {
        return userPermissionDao.queryAllUserPermissionBean();
    }
}
