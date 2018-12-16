package com.byd.performance_main.base;

import com.byd.performance_utils.utils.CommonMethod;

public class BasePermissionBean {

    protected Integer permissionAllCode;

    public Integer getPermissionAllCode() {
        return permissionAllCode;
    }

    public void setPermissionAllCode(Integer permissionAllCode) {
        this.permissionAllCode = permissionAllCode;
    }

    /**
     * 将权限码转换为对应权限二进制值显示
     */
    public String transformCode() {
        return CommonMethod.fromDecimalToBinaryString(permissionAllCode);
    }
}
