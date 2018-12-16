package com.byd.performance_main.dao;

import com.byd.performance_main.model.SysRoleMenuBean;

public interface SysRoleMenuDao {
    SysRoleMenuBean querySysRoleMenuFromRoleId(Integer roleId);
    int updateSysRoleMenu(SysRoleMenuBean sysRoleMenuBean);
}
