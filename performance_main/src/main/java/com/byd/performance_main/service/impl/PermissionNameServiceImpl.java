package com.byd.performance_main.service.impl;


import com.byd.performance_main.dao.PermissionNameDao;
import com.byd.performance_main.model.PermissionNameBean;
import com.byd.performance_main.service.PermissionNameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "permissionNameService")
public class PermissionNameServiceImpl implements PermissionNameService {
    @Autowired
    private PermissionNameDao permissionNameDao;//这里会报错，但是并不会影响


    @Override
    public int addPermissionName(PermissionNameBean permissionNameBean) {
        return permissionNameDao.insert(permissionNameBean);
    }

    @Override
    public int delPermissionName(Integer id) {
        return permissionNameDao.delete(id);
    }

    @Override
    public int updatePermissionName(PermissionNameBean permissionNameBean) {
        return permissionNameDao.update(permissionNameBean);
    }

    @Override
    public List<PermissionNameBean> findAllPermissionName() {
        return permissionNameDao.queryAllPermissionName();
    }

    @Override
    public String findPermissionNameFromCode(Integer permissionCode) {
        return permissionNameDao.queryPermissionNameFromCode(permissionCode);
    }
}
