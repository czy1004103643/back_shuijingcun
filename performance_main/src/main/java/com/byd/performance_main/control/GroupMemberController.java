package com.byd.performance_main.control;


import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.GroupMemberBean;
import com.byd.performance_utils.code.PermissionCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.exception.GroupMemberDuplicateException;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/group-member")
public class GroupMemberController extends BaseController {

    @ResponseBody
    @PostMapping("/add")
    public Object addUser(@RequestParam(value = "cookie") String cookie,
                          GroupMemberBean groupMemberBean) {
        urlLogInfo("/group-member/add");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        Integer groupName = groupMemberBean.getGroupName();
        String groupMember = groupMemberBean.getGroupMember();
        Integer id = groupMemberService.findIdFromGroupNameAndGroupMember(groupName, groupMember);

        if (id != null) {
            throw new GroupMemberDuplicateException();
        }

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        int add_result = groupMemberService.addGroupMember(groupMemberBean);
        if (add_result == 1) {
            message = "小组成员添加成功";
            result = true;
        } else {
            message = "小组成员添加失败";
        }
        details.put("add_group_member", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;

    }

    @ResponseBody
    @PostMapping("/delete")
    public Object deleteRole(@RequestParam(value = "cookie") String cookie,
                             @RequestParam(value = "id") Integer id) {
        urlLogInfo("/group-member/delete");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = groupMemberService.delGroupMember(id);
        if (del_result == 1) {
            message = "小组成员删除成功";
            result = true;
        } else {
            message = "小组成员删除失败";
        }

        details.put("delete_group_member", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @PostMapping("/update")
    public Object updateRole(@RequestParam(value = "cookie") String cookie,
                             GroupMemberBean groupMemberBean) {
        urlLogInfo("/group-member/update");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = groupMemberService.updateGroupMember(groupMemberBean);
        if (update_result == 1) {
            message = "小组成员更新成功";
            result = true;
        } else {
            message = "小组成员更新失败";
        }

        details.put("update_group_member", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/find-group-member-id")
    public Map<String, Object> findIdFromGroupNameAndGroupMember(@RequestParam(value = "cookie") String cookie,
                                                                 @RequestParam(value = "groupName") Integer groupName,
                                                                 @RequestParam(value = "groupMember") String groupMember) {
        urlLogInfo("/group-member/find-group-member-id");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_GROUP_MEMBER_PERMISSION);

        Integer id = groupMemberService.findIdFromGroupNameAndGroupMember(groupName, groupMember);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("find-group-member-id", id);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/find-group-member")
    public Map<String, Object> findGroupMemberFromGroupName(@RequestParam(value = "cookie") String cookie,
                                                            @RequestParam(value = "groupName") Integer groupName) {
        urlLogInfo("/group-member/find-group-member");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_GROUP_MEMBER_PERMISSION);

        List<String> groupMembers = groupMemberService.findGroupMemberFromGroupName(groupName);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("find-group-member", groupMembers);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/find-group-name")
    public Map<String, Object> findGroupNameFromGroupMember(@RequestParam(value = "cookie") String cookie,
                                                            @RequestParam(value = "groupMember") String groupMember) {
        urlLogInfo("/group-member/find-group-name");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_GROUP_MEMBER_PERMISSION);

        List<Integer> groupNames = groupMemberService.findGroupNameFromGroupMember(groupMember);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("find-group-name", groupNames);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/find-group-leader")
    public Map<String, Object> findGroupNameLeader(@RequestParam(value = "cookie") String cookie,
                                                   @RequestParam(value = "groupName") Integer groupName) {
        urlLogInfo("/group-member/find-group-leader");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_GROUP_MEMBER_PERMISSION);

        String groupNameLeader = groupMemberService.findGroupNameLeader(groupName);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("find-group-leader", groupNameLeader);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/all")
    public Map<String, Object> findAllGroupMembers(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/group-member/all");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_GROUP_MEMBER_PERMISSION);

        List<GroupMemberBean> allGroupMemberBean = groupMemberService.findAllGroupMemberBean();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("all_group_members", allGroupMemberBean);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

}
