package com.byd.performance_main.model;

public class RoleMenuBean {
    private int id;
    private String userId;
    private String roleMenuCode;
    private char idModified;

    public RoleMenuBean() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getRoleMenuCode() {
        return roleMenuCode;
    }

    public void setRoleMenuCode(String roleMenuCode) {
        this.roleMenuCode = roleMenuCode;
    }

    public char getIdModified() {
        return idModified;
    }

    public void setIdModified(char idModified) {
        this.idModified = idModified;
    }

    @Override
    public String toString() {
        return "RoleMenuBean{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", roleMenuCode='" + roleMenuCode + '\'' +
                ", idModified=" + idModified +
                '}';
    }
}
