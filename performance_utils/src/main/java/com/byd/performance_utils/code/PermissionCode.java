package com.byd.performance_utils.code;

/**
 * 权限类型的分类码
 */

public interface PermissionCode {
    /**
     * 位运算只能进行到30位，所以权限数量最多只能为30
     */

    //允许查看用户数据库权限
    int ALLOW_VIEW_USER = 0;

    //允许修改用户数据库权限
    int ALLOW_MODIFY_USER = 1;

    //允许查看角色类型数据库权限
    int ALLOW_VIEW_ROLE = 2;

    //允许修改角色类型数据库权限
    int ALLOW_MODIFY_ROLE = 3;

    //允许查看权限名称和内容数据库的权限
    int ALLOW_VIEW_PERMISSION_NAME = 4;

    //允许修改权限名称和内容数据库的权限
    int ALLOW_MODIFY_PERMISSION_NAME = 5;

    //允许查看角色权限数据库的权限
    int ALLOW_VIEW_ROLE_PERMISSION = 6;

    //允许修改角色权限数据库的权限
    int ALLOW_MODIFY_ROLE_PERMISSION = 7;

    //允许查看特定用户权限数据库的权限
    int ALLOW_VIEW_USER_PERMISSION = 8;

    //允许修改特定用户权限数据库的权限
    int ALLOW_MODIFY_USER_PERMISSION = 9;

    //允许查看用户组数据库的权限
    int ALLOW_VIEW_GROUP_NAME_PERMISSION = 10;

    //允许修改用户组数据库的权限
    int ALLOW_MODIFY_GROUP_NAME_PERMISSION = 11;

    //允许查看用户组成员数据库的权限
    int ALLOW_VIEW_GROUP_MEMBER_PERMISSION = 12;

    //允许修改用户组成员数据库的权限
    int ALLOW_MODIFY_GROUP_MEMBER_PERMISSION = 13;

    //允许查看项目组成员数据库的权限
    int ALLOW_VIEW_PROJECT_MEMBER_PERMISSION = 16;

    //允许修改项目组成员数据库的权限
    int ALLOW_MODIFY_PROJECT_MEMBER_PERMISSION = 17;

}
