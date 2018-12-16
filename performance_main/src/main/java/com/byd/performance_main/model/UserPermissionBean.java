package com.byd.performance_main.model;

import com.byd.performance_main.base.BasePermissionBean;

public class UserPermissionBean extends BasePermissionBean {
    private Integer id;

    private String userId;


    public UserPermissionBean() {

    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String toString() {
        return "UserPermissionBean{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", permissionAllCode=" + permissionAllCode +
                '}';
    }
}
