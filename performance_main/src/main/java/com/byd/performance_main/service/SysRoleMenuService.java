package com.byd.performance_main.service;

import com.byd.performance_main.model.SysRoleMenuBean;

public interface SysRoleMenuService {
    SysRoleMenuBean querySysRoleMenuFromRoleId(Integer roleId);
    int updateSysRoleMenu(SysRoleMenuBean sysRoleMenuBean);

}
