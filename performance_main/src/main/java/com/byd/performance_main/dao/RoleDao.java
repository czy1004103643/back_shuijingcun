package com.byd.performance_main.dao;


import com.byd.performance_main.model.RoleBean;
import com.byd.performance_main.model.UserBean;

import java.util.List;

public interface RoleDao {
    int insert(RoleBean roleBean);

    int delete(Integer id);

    int update(RoleBean roleBean);

    List<RoleBean> queryRoleBean();

    String queryRoleName(Integer roleLevel );
}
