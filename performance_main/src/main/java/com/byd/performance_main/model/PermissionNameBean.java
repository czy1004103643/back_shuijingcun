package com.byd.performance_main.model;

public class PermissionNameBean {
    private Integer id;

    private Integer permissionCode;

    private String permissionName;

    public PermissionNameBean() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getPermissionCode() {
        return permissionCode;
    }

    public void setPermissionCode(Integer permissionCode) {
        this.permissionCode = permissionCode;
    }

    public String getPermissionName() {
        return permissionName;
    }

    public void setPermissionName(String permissionName) {
        this.permissionName = permissionName;
    }

    @Override
    public String toString() {
        return "PermissionNameBean{" +
                "id=" + id +
                ", permissionCode=" + permissionCode +
                ", permissionName='" + permissionName + '\'' +
                '}';
    }
}
