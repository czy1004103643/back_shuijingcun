package com.byd.performance_main.control.using;


import com.alibaba.fastjson.JSONObject;
import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.GroupMemberBean;

import com.byd.performance_main.model.ProjectMemberBean;
import com.byd.performance_main.model.UserBean;
import com.byd.performance_utils.code.*;
import com.byd.performance_utils.exception.ParamInvalidException;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/boss")
public class LeaderController extends BaseController {

    /**
     * 显示boss的信息
     */
    @GetMapping(value = "/info")
    @ResponseBody
    public Map<String, Object> getBossInfo(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/boss/info");
        checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        //获取各个group信息
        List<String> groupMembers = groupMemberService.findGroupMemberFromGroupName(SpecialRecordCode.BOSS_GROUP_ID);
        HashMap<String, Object> bossData = new HashMap();
        if (groupMembers.size() > 0) {
            bossData.put("bossId", groupMembers.get(0));
            bossData.put("bossName", userService.findUserFromUserId(groupMembers.get(0)).getUserName());
        } else {
            bossData.put("bossId", null);
            bossData.put("bossName", null);
        }

        details.put("bossData", bossData);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    @Transactional
    @ResponseBody
    @PostMapping("/update")
    public Map<String, Object> projectUpdateMember(@RequestBody JSONObject jsonParam) {
        urlLogInfo("/boss/update");
        logger.info(jsonParam.toJSONString());
        String cookie;
        String loginUserId;
        String bossId;
        try {
            cookie = jsonParam.getString("cookie");
            checkCookieValid(cookie);
            bossId = jsonParam.getString("bossId");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamInvalidException("groupMembers " + e.getMessage());
        }
        if (bossId == null || bossId.equals("") || bossId.equals(SpecialRecordCode.ADMIN_USERID)) {
            throw new ParamInvalidException("bossId " + bossId);
        }
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.UserManagementCode);
        UserBean adminUser = new UserBean();
        //Boss角色码
        adminUser.setUserRole(SysRoleCode.BossCode);
        List<UserBean> allAdminUserBean = userService.adminUser(adminUser);
        UserBean userBean = userService.findUserFromUserId(bossId);
        for (int t = 0; allAdminUserBean.size() > t; t++) {
            String userId = allAdminUserBean.get(t).getUserId();
            if (!userId.equals(bossId)) {
                userBean.setUserId(userId);
                userBean.setUserRole(SysRoleCode.GuestCode);
                userService.updateUserRole(userBean);
                continue;
            }
            List<Integer> groupNames = groupMemberService.findGroupNameFromGroupMember(bossId);
            if (groupNames.size() > 0) {
                throw new ParamInvalidException("The bossId has a group.");
            }
            int del_result = SpecialRecordCode.SQL_RESULT_FAIL;
            List<String> groupMembers = groupMemberService.findGroupMemberFromGroupName(SpecialRecordCode.BOSS_GROUP_ID);
            for (String groupMember : groupMembers) {
                Integer groupMemberId = groupMemberService.findIdFromGroupNameAndGroupMember(SpecialRecordCode.BOSS_GROUP_ID, groupMember);
                del_result = groupMemberService.delGroupMember(groupMemberId);
                if (del_result == SpecialRecordCode.SQL_RESULT_FAIL) {
                    throw new ParamInvalidException("Delete boss is error from group.");
                }
                ProjectMemberBean projectMemberBean = projectMemberService.findProjectMemberBeanFromProjectNameAndProjectMember(SpecialRecordCode.BOSS_PROJECT_ID, groupMember);
                del_result = projectMemberService.delProjectMember(projectMemberBean.getId());
                if (del_result == SpecialRecordCode.SQL_RESULT_FAIL) {
                    throw new ParamInvalidException("Delete boss is error from project.");
                }
            }
        }


        int add_result = SpecialRecordCode.SQL_RESULT_FAIL;
        GroupMemberBean groupMemberBean = new GroupMemberBean();
        groupMemberBean.setIsLeader(GroupRoleCode.BOSS);
        groupMemberBean.setGroupName(SpecialRecordCode.BOSS_GROUP_ID);
        groupMemberBean.setGroupMember(bossId);
        add_result = groupMemberService.addGroupMember(groupMemberBean);
        if (add_result == SpecialRecordCode.SQL_RESULT_FAIL) {
            throw new ParamInvalidException("Add boss is error from group.");
        }

        ProjectMemberBean projectMemberBean = new ProjectMemberBean();
        projectMemberBean.setProjectName(SpecialRecordCode.BOSS_PROJECT_ID);
        projectMemberBean.setRoleName(ProjectRoleCode.BOSS);
        projectMemberBean.setProjectMember(bossId);
        add_result = projectMemberService.addProjectMember(projectMemberBean);
        if (add_result == SpecialRecordCode.SQL_RESULT_FAIL) {
            throw new ParamInvalidException("Add boss is error from project.");
        }

        List<String> allGroupBossId = groupMemberService.findGroupMemberFromGroupName(SpecialRecordCode.BOSS_GROUP_ID);
        if (allGroupBossId.size() > 0) {
            for (int q = 0; q < allGroupBossId.size(); q++) {
                if (!allGroupBossId.get(q).equals(bossId)) {
                    groupMemberService.delGroupMember(groupMemberService.findGroupMemberBeanFromGroupMember(allGroupBossId.get(q)).getId());
                }
            }
        }
        List<ProjectMemberBean> allProjectBossId = projectMemberService.findBossFromProjectMemberTable(SpecialRecordCode.BOSS_PROJECT_ID);
        if (allProjectBossId != null) {
            for (int q = 0; q < allProjectBossId.size(); q++) {
                if (!allProjectBossId.get(q).getProjectMember().equals(bossId)) {
                    projectMemberService.delProjectMember(allProjectBossId.get(q).getId());
                }
            }
        }
        userBean.setUserRole(SysRoleCode.BossCode);
        userBean.setUserId(bossId);
        userService.updateUserRole(userBean);
        HashMap<String, Object> details = new HashMap<>();
        boolean result = true;
        String message = "人员更新成功";

        details.put("group_member_update_result", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);

        return map;
    }


    @PostMapping(value = "/delete")
    @ResponseBody
    public Object deleteProjectName(@RequestParam(value = "cookie") String cookie,
                                    @RequestParam(value = "bossId") String bossId) {
        urlLogInfo("/boss/delete");
        checkCookieValid(cookie);
        UserBean userBean = userService.findUserFromUserId(bossId);
        if (userBean == null) {
            throw new ParamInvalidException("The bossId is invalid.");
        }
        int del_result = SpecialRecordCode.SQL_RESULT_FAIL;
        Integer groupMemberId = groupMemberService.findIdFromGroupNameAndGroupMember(SpecialRecordCode.BOSS_GROUP_ID, bossId);
        if (groupMemberId != null) {
            del_result = groupMemberService.delGroupMember(groupMemberId);
            if (del_result == SpecialRecordCode.SQL_RESULT_FAIL) {
                throw new ParamInvalidException("Delete boss is error from group.");
            }
            ProjectMemberBean projectMemberBean = projectMemberService.findProjectMemberBeanFromProjectNameAndProjectMember(SpecialRecordCode.BOSS_PROJECT_ID, bossId);
            del_result = projectMemberService.delProjectMember(projectMemberBean.getId());
            if (del_result == SpecialRecordCode.SQL_RESULT_FAIL) {
                throw new ParamInvalidException("Delete boss is error from project.");
            }
        } else {
            throw new ParamInvalidException("The bossId is not boss.");
        }
        HashMap<String, Object> details = new HashMap<>();
        details.put("deleteBossId", true);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }
}
