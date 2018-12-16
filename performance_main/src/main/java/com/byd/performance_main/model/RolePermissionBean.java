package com.byd.performance_main.model;

import com.byd.performance_main.base.BasePermissionBean;

public class RolePermissionBean extends BasePermissionBean {
    private Integer id;

    private Integer roleLevel;

    public RolePermissionBean() {

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

    @Override
    public String toString() {
        return "RolePermissionBean{" +
                "id=" + id +
                ", roleLevel=" + roleLevel +
                ", permissionAllCode=" + permissionAllCode +
                '}';
    }
}
