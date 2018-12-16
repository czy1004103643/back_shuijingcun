package com.byd.performance_main.control;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.UserPermissionBean;
import com.byd.performance_utils.code.PermissionCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/permission")
public class UserPermissionController extends BaseController {
    @ResponseBody
    @PostMapping("/add-user-permission")
    public Object addUserPermission(@RequestParam(value = "cookie") String cookie,
                                    UserPermissionBean userPermissionBean) {
        urlLogInfo("/permission/add-user-permission");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_USER_PERMISSION);
        checkUserIdIsDuplicateFromUserPermissionBean(userPermissionBean.getUserId());

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        userPermissionBean.setId(null);
        int add_result = 0;
        if (userPermissionBean.getUserId() != null && !userPermissionBean.getUserId().isEmpty()) {
            add_result = userPermissionService.addUserPermission(userPermissionBean);
        }

        if (add_result == 1) {
            message = "用户新权限添加成功";
            result = true;
        } else {
            message = "用户新权限添加失败";
        }

        details.put("add_new_user_permission", result);
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @ResponseBody
    @PostMapping("/delete-user-permission")
    public Object deleteUserPermission(@RequestParam(value = "cookie") String cookie,
                                       @RequestParam(value = "id") Integer id) {
        urlLogInfo("/permission/delete-user-permission");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_USER_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = userPermissionService.delUserPermission(id);
        if (del_result == 1) {
            message = "特定用户权限删除成功";
            result = true;
        } else {
            message = "特定用户权限删除失败";
        }

        details.put("delete_user_permission", result);
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @ResponseBody
    @PostMapping("/update-user-permission")
    public Object updateUserPermission(@RequestParam(value = "cookie") String cookie,
                                       UserPermissionBean userPermissionBean) {
        urlLogInfo("/permission/update-user-permission");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_USER_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = 0;
        if (userPermissionBean.getUserId() != null && !userPermissionBean.getUserId().isEmpty()) {
            update_result = userPermissionService.updateUserPermission(userPermissionBean);
        }

        if (update_result == 1) {
            message = "特定用户权限更新成功";
            result = true;
        } else {
            message = "特定用户权限更新失败";
        }

        details.put("update_user_permission", result);
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @ResponseBody
    @GetMapping("/all-user-permission")
    public Map<String, Object> findAllUserPermission(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/permission/all-user-permission");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_USER_PERMISSION);

        List<UserPermissionBean> allUserPermission = userPermissionService.findAllUserPermission();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("allUserPermission", allUserPermission);

        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @ResponseBody
    @GetMapping("/query-user-permission")
    public Map<String, Object> findOwnUserPermissionBeanFromUserId(@RequestParam(value = "cookie") String cookie,
                                                                   @RequestParam(value = "userId") String userId) {
        urlLogInfo("/permission/query-user-permission");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_USER_PERMISSION);

        UserPermissionBean userPermissionBean = userPermissionService.findOwnUserPermissionBeanFromUserId(userId);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("userPermissionBean", userPermissionBean);
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }
}
