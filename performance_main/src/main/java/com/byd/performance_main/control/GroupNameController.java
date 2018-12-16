package com.byd.performance_main.control;


import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.GroupNameBean;
import com.byd.performance_utils.code.PermissionCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/group-name")
public class GroupNameController extends BaseController {

    @ResponseBody
    @PostMapping("/add")
    public Object addGroupName(@RequestParam(value = "cookie") String cookie,
                          GroupNameBean groupNameBean) {
        urlLogInfo("/group-name/add");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_NAME_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        int add_result = groupNameService.addGroupName(groupNameBean);
        if (add_result == 1) {
            message = "小组添加成功";
            result = true;
        } else {
            message = "小组添加失败";
        }
        details.put("add_group_name", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;

    }

    @ResponseBody
    @PostMapping("/delete")
    public Object deleteGroupName(@RequestParam(value = "cookie") String cookie,
                             @RequestParam(value = "id") Integer id) {
        urlLogInfo("/group-name/delete");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_NAME_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = groupNameService.delGroupName(id);
        if (del_result == 1) {
            message = "小组删除成功";
            result = true;
        } else {
            message = "小组删除失败";
        }

        details.put("delete_group_name", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @PostMapping("/update")
    public Object updateGroupName(@RequestParam(value = "cookie") String cookie,
                             GroupNameBean groupNameBean) {
        urlLogInfo("/group-name/update");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_NAME_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = groupNameService.updateGroupName(groupNameBean);
        if (update_result == 1) {
            message = "小组更新成功";
            result = true;
        } else {
            message = "小组更新失败";
        }

        details.put("update_group_name", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/find-group-name-id")
    public Map<String, Object> findGroupNameFromId(@RequestParam(value = "cookie") String cookie,
                                                   @RequestParam(value = "id") Integer id) {
        urlLogInfo("/group-name/find-group-name-id");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_GROUP_NAME_PERMISSION);

        GroupNameBean groupName = groupNameService.findGroupNameFromId(id);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("find-group-name-id", groupName);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/find-group-name")
    public Map<String, Object> findGroupNameBeanFromName(@RequestParam(value = "cookie") String cookie,
                                                         @RequestParam(value = "groupName") String groupName) {
        urlLogInfo("/group-name/find-group-name");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_GROUP_NAME_PERMISSION);

        GroupNameBean groupNameBean = groupNameService.findGroupNameFromName(groupName);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("find-group-name", groupNameBean);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/all")
    public Map<String, Object> findAllGroupNames(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/group-name/all");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_GROUP_NAME_PERMISSION);

        List<GroupNameBean> allGroupName = groupNameService.findAllGroupName();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("all_group_names", allGroupName);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

}
