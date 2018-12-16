package com.byd.performance_main.control;


import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.RoleBean;
import com.byd.performance_utils.utils.CommonMethod;
import com.byd.performance_utils.code.PermissionCode;
import com.byd.performance_utils.code.StateCode;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/role")
public class RoleController extends BaseController {

    @ResponseBody
    @PostMapping("/add")
    public Object addUser(@RequestParam(value = "cookie") String cookie,
                          RoleBean roleBean) {
        urlLogInfo("/role/add");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_ROLE);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        roleBean.setId(null);
        int add_result = roleService.addRole(roleBean);
        if (add_result == 1) {
            message = "角色添加成功";
            result = true;
        } else {
            message = "角色添加失败";
        }
        details.put("add_role", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;

    }

    @ResponseBody
    @PostMapping("/delete")
    public Object deleteRole(@RequestParam(value = "cookie") String cookie,
                             @RequestParam(value = "id") Integer id) {
        urlLogInfo("/role/delete");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_ROLE);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = roleService.delRole(id);
        if (del_result == 1) {
            message = "角色删除成功";
            result = true;
        } else {
            message = "角色删除失败";
        }

        details.put("delete_role", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @PostMapping("/update")
    public Object updateRole(@RequestParam(value = "cookie") String cookie,
                             RoleBean roleBean) {
        urlLogInfo("/role/update");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_ROLE);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = roleService.updateRole(roleBean);
        if (update_result == 1) {
            message = "角色更新成功";
            result = true;
        } else {
            message = "角色更新失败";
        }

        details.put("update_role", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/all")
    public Map<String, Object> findAllRoles(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/role/all");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_ROLE);

        List<RoleBean> allRole = roleService.findAllRole();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("allRoles", allRole);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

}
