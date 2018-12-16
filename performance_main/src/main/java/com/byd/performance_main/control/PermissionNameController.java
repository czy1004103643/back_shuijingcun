package com.byd.performance_main.control;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.PermissionNameBean;
import com.byd.performance_utils.code.PermissionCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/permission")
public class PermissionNameController extends BaseController {
    @ResponseBody
    @PostMapping("/add-permission-name")
    public Object addPermissionName(@RequestParam(value = "cookie") String cookie,
                                    PermissionNameBean permissionNameBean) {
        urlLogInfo("/permission/add-permission-name");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_PERMISSION_NAME);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        permissionNameBean.setId(null);
        int add_result = permissionNameService.addPermissionName(permissionNameBean);
        if (add_result == 1) {
            message = "新权限名称添加成功";
            result = true;
        } else {
            message = "新权限名称添加失败";
        }
        details.put("add_new_permission_name", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @PostMapping("/delete-permission-name")
    public Object deletePermissionName(@RequestParam(value = "cookie") String cookie,
                                       @RequestParam(value = "id") Integer id) {
        urlLogInfo("/permission/delete-permission-name");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_PERMISSION_NAME);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = permissionNameService.delPermissionName(id);
        if (del_result == 1) {
            message = "权限名称删除成功";
            result = true;
        } else {
            message = "权限名称删除失败";
        }

        details.put("delete_permission_name", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @PostMapping("/update-permission-name")
    public Object updatePermissionName(@RequestParam(value = "cookie") String cookie,
                                       PermissionNameBean permissionNameBean) {
        urlLogInfo("/permission/update-permission-name");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_PERMISSION_NAME);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = permissionNameService.updatePermissionName(permissionNameBean);
        if (update_result == 1) {
            message = "权限名称更新成功";
            result = true;
        } else {
            message = "权限名称更新失败";
        }

        details.put("update_permission_name", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/all-permission-name")
    public Map<String, Object> findAllPermissionNames(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/permission/all-permission-name");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_PERMISSION_NAME);

        List<PermissionNameBean> allPermissionName = permissionNameService.findAllPermissionName();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("allPermissionName", allPermissionName);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/query-permission-name")
    public Map<String, Object> findPermissionNameFormCode(@RequestParam(value = "cookie") String cookie,
                                                          @RequestParam(value = "permissionCode") Integer permissionCode) {
        urlLogInfo("/permission/query-permission-name");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_PERMISSION_NAME);

        String permissionName = permissionNameService.findPermissionNameFromCode(permissionCode);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("permissionName", permissionName);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }
}
