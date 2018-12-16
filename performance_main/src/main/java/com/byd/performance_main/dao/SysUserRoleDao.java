package com.byd.performance_main.dao;

import com.byd.performance_main.model.SysUserRoleBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface SysUserRoleDao {
    //int queSysUserRoleIdFromUserIdAndModuleCode(Integer roleId,String moduleCode);
    //@Param("weightName") Integer weightName, @Param("scoreValue") Integer scoreValue
    String queryModuleCodeFromRoleIdAndBackupsModuleCode(@Param("roleId") Integer roleId, @Param("backupsModuleCode") String backupsModuleCode);
    int queryIdFromRoleIdAndBackupsModuleCode(@Param("roleId") Integer roleId, @Param("backupsModuleCode") String backupsModuleCode);
    int queryIsModifiedFromRoleIdAndBackupsModuleCode(@Param("roleId") Integer roleId, @Param("backupsModuleCode") String backupsModuleCode);
    int updateSysUserRole(SysUserRoleBean sysUserRoleBean);
    int updateModuleCodeFromId(SysUserRoleBean sysUserRoleBean);
    List<SysUserRoleBean> listSysUserRoleFromUserId(String userId);
    List<SysUserRoleBean> allSysUserRole();
    //int updateSysUserRole()
}
