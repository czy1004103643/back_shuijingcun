package com.byd.performance_main.service.impl;


import com.byd.performance_main.dao.SysUserRoleDao;
import com.byd.performance_main.model.SysUserRoleBean;
import com.byd.performance_main.service.SysUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "sysUserRoleService")
public class SysUserRoleServiceImpl implements SysUserRoleService {
    @Autowired
    SysUserRoleDao sysUserRoleDao;

    @Override
    public String queryModuleCodeFromRoleIdAndBackupsModuleCode(Integer roleId, String backupsModuleCode) {
        return sysUserRoleDao.queryModuleCodeFromRoleIdAndBackupsModuleCode(roleId,backupsModuleCode);
    }

    @Override
    public int queryIdFromRoleIdAndBackupsModuleCode(Integer roleId, String backupsModuleCode) {
        return sysUserRoleDao.queryIdFromRoleIdAndBackupsModuleCode(roleId,backupsModuleCode);
    }

    @Override
    public int queryIsModifiedFromRoleIdAndBackupsModuleCode(Integer roleId, String backupsModuleCode) {
        return sysUserRoleDao.queryIsModifiedFromRoleIdAndBackupsModuleCode(roleId,backupsModuleCode);
    }

    @Override
    public int updateSysUserRole(SysUserRoleBean sysUserRoleBean) {
        return sysUserRoleDao.updateSysUserRole(sysUserRoleBean);
    }

    @Override
    public int updateModuleCodeFromId(SysUserRoleBean sysUserRoleBean) {
        return sysUserRoleDao.updateModuleCodeFromId(sysUserRoleBean);
    }

    @Override
    public List<SysUserRoleBean> listSysUserRoleFromUserId(String userId) {
        return sysUserRoleDao.listSysUserRoleFromUserId(userId);
    }

    @Override
    public List<SysUserRoleBean> allSysUserRole() {
        return sysUserRoleDao.allSysUserRole();
    }
}
