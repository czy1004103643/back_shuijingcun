package com.byd.performance_main.control;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.RolePermissionBean;
import com.byd.performance_utils.code.PermissionCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/permission")
public class RolePermissionController extends BaseController {
    @ResponseBody
    @PostMapping("/add-role-permission")
    public Object addRolePermission(@RequestParam(value = "cookie") String cookie,
                                    RolePermissionBean rolePermissionBean) {
        urlLogInfo("/permission/add-role-permission");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_ROLE_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        rolePermissionBean.setId(null);
        int add_result = rolePermissionService.addRolePermission(rolePermissionBean);
        if (add_result == 1) {
            message = "角色新权限添加成功";
            result = true;
        } else {
            message = "角色新权限添加失败";
        }
        details.put("add_new_role_permission", result);
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @ResponseBody
    @PostMapping("/delete-role-permission")
    public Object deleteRolePermission(@RequestParam(value = "cookie") String cookie,
                                       @RequestParam(value = "id") Integer id) {
        urlLogInfo("/permission/delete-role-permission");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_ROLE_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = rolePermissionService.delRolePermission(id);
        if (del_result == 1) {
            message = "角色权限删除成功";
            result = true;
        } else {
            message = "角色权限删除失败";
        }

        details.put("delete_role_permission", result);
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @ResponseBody
    @PostMapping("/update-role-permission")
    public Object updateRolePermission(@RequestParam(value = "cookie") String cookie,
                                       RolePermissionBean rolePermissionBean) {
        urlLogInfo("/permission/update-role-permission");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_ROLE_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = 0;
        if (rolePermissionBean.getRoleLevel() != null && rolePermissionBean.getRoleLevel() != 0) {
            update_result = rolePermissionService.updateRolePermission(rolePermissionBean);
        }

        if (update_result == 1) {
            message = "角色权限更新成功";
            result = true;
        } else {
            message = "角色权限更新失败";
        }

        details.put("update_role_permission", result);
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @ResponseBody
    @GetMapping("/all-role-permission")
    public Map<String, Object> findAllRolePermission(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/permission/all-role-permission");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_ROLE_PERMISSION);

        List<RolePermissionBean> allRolePermission = rolePermissionService.findAllRolePermission();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("allPermissionName", allRolePermission);

        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @ResponseBody
    @GetMapping("/query-role-permission")
    public Map<String, Object> findOwnRolePermissionBeanFromRole(@RequestParam(value = "cookie") String cookie,
                                                                 @RequestParam(value = "roleLevel") Integer roleLevel) {
        urlLogInfo("/permission/query-role-permission");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_PERMISSION_NAME);

        RolePermissionBean rolePermissionBean = rolePermissionService.findOwnRolePermissionBeanFromRole(roleLevel);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("rolePermission", rolePermissionBean);
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }
}
