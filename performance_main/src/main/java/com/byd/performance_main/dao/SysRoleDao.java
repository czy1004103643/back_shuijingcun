package com.byd.performance_main.dao;

import com.byd.performance_main.model.SysRoleBean;

public interface SysRoleDao {
    SysRoleBean querySysRoleFromRoleId(Integer roleId);
}
