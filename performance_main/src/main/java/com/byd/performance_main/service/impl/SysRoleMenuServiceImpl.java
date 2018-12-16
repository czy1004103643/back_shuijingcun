package com.byd.performance_main.service.impl;

import com.byd.performance_main.dao.SysRoleMenuDao;
import com.byd.performance_main.model.SysRoleMenuBean;
import com.byd.performance_main.service.SysRoleMenuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service(value="sysRoleMenuService")
public class SysRoleMenuServiceImpl implements SysRoleMenuService {
    @Autowired
    SysRoleMenuDao sysRoleMenuDao;
    @Override
    public SysRoleMenuBean querySysRoleMenuFromRoleId(Integer roleId) {
        return sysRoleMenuDao.querySysRoleMenuFromRoleId(roleId);
    }



    @Override
    public int updateSysRoleMenu(SysRoleMenuBean sysRoleMenuBean) {
        return sysRoleMenuDao.updateSysRoleMenu(sysRoleMenuBean);
    }
}
