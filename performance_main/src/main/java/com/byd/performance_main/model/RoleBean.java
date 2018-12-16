package com.byd.performance_main.model;

import com.byd.performance_utils.code.PermissionRoleCode;

public class RoleBean {
    private Integer id;

    private Integer roleLevel;

    private String roleName;

    public RoleBean() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getRoleLevel() {
        return roleLevel;
    }

    public void setRoleLevel(Integer roleLevel) {
        this.roleLevel = roleLevel;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "RoleBean{" +
                "id=" + id +
                ", roleLevel=" + roleLevel +
                ", roleName='" + roleName + '\'' +
                '}';
    }

    public Integer clearBadCode(Integer roleLevel) {
        if (roleLevel < PermissionRoleCode.ROLE_ADMIN_LEVEL || roleLevel > PermissionRoleCode.ROLE_GUEST_LEVEL) {
            roleLevel = PermissionRoleCode.ROLE_GUEST_LEVEL;
        }
        return roleLevel;
    }
}
