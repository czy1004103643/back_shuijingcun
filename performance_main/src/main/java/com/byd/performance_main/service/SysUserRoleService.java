package com.byd.performance_main.service;

import com.byd.performance_main.model.SysUserRoleBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleService{
    //int queSysUserRoleIdFromUserIdAndModuleCode(Integer roleId,String moduleCode);
    String queryModuleCodeFromRoleIdAndBackupsModuleCode(Integer roleId, String backupsModuleCode);
    int queryIdFromRoleIdAndBackupsModuleCode(Integer roleId, String backupsModuleCode);
    int queryIsModifiedFromRoleIdAndBackupsModuleCode(Integer roleId, String backupsModuleCode);
    int updateSysUserRole(SysUserRoleBean sysUserRoleBean);
    int updateModuleCodeFromId(SysUserRoleBean sysUserRoleBean);
    List<SysUserRoleBean> listSysUserRoleFromUserId(String userId);
    List<SysUserRoleBean> allSysUserRole();
    //int updateSysUserRole()
}
