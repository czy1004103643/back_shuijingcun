package com.byd.performance_main.model;

public class SysUserRoleBean {
    private int id;
    private int roleId;
    private String moduleCode;
    private String backupsModuleCode;;
    private int isModified;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRoleId() {
        return roleId;
    }

    public void setRoleId(int roleId) {
        this.roleId = roleId;
    }

    public int getIsModified() {
        return isModified;
    }

    public void setIsModified(int isModified) {
        this.isModified = isModified;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getBackupsModuleCode() {
        return backupsModuleCode;
    }

    public void setBackupsModuleCode(String backupsModuleCode) {
        this.backupsModuleCode = backupsModuleCode;
    }
}
