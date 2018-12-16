package com.byd.performance_main.control.using;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.GroupMemberBean;
import com.byd.performance_main.model.GroupNameBean;
import com.byd.performance_main.model.UserBean;
import com.byd.performance_utils.code.*;
import com.byd.performance_utils.exception.ParamInvalidException;
import com.byd.performance_utils.exception.UserIdInvalid;
import com.byd.performance_utils.utils.CommonMethod;
import com.github.pagehelper.PageException;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


@RestController
@RequestMapping(value = "/group")
public class GroupController extends BaseController {


    /**
     * 显示全部group
     */
    @GetMapping(value = "/info")
    @ResponseBody
    public Map<String, Object> getGroupInfo(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/group/info");
        checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        //获取各个group信息
        List<GroupNameBean> groupNamesBean = groupNameService.findAllGroupName();
        ArrayList<Object> myGroupData = new ArrayList<>();
        if (groupNamesBean != null && groupNamesBean.size() > 0) {
            for (int i = 0; i < groupNamesBean.size(); i++) {
                if (groupNamesBean.get(i).getId().equals(SpecialRecordCode.BOSS_GROUP_ID)
                ) {
                    continue;
                }
                Map<String, Object> myGroup = new HashMap<>();
                myGroup.put("groupName", groupNamesBean.get(i).getGroupName());
                Integer groupNameId = groupNamesBean.get(i).getId();
                String groupNameLeader = groupMemberService.findGroupNameLeader(groupNameId);
                if (groupNameLeader != null) {
                    HashMap<String, Object> groupLeader = new HashMap<>();

                    UserBean groupLeaderNameBean = userService.findUserFromUserId(groupNameLeader);
                    groupLeader.put("idNumber", groupNameLeader);
                    groupLeader.put("leaderName", groupLeaderNameBean.getUserName());

                    myGroup.put("groupLeader", groupLeader);
                }
                List<String> groupMemberIds = groupMemberService.findGroupMemberFromGroupName(groupNameId);
                ArrayList<Object> groupMemberData = new ArrayList<>();
                if (groupMemberIds != null && groupMemberIds.size() > 0) {
                    for (int j = 0; j < groupMemberIds.size(); j++) {
                        String groupMemberId = groupMemberIds.get(j);

                        if (!groupMemberId.equals(groupNameLeader)) {

                            HashMap<String, Object> members = new HashMap<>();
                            UserBean userBean = userService.findUserFromUserId(groupMemberId);
                            members.put("idNumber", groupMemberId);
                            members.put("memberName", userBean.getUserName());

                            groupMemberData.add(members);
                        }
                    }
                    myGroup.put("groupMembers", groupMemberData);
                }
                myGroupData.add(myGroup);
            }
        }
        details.put("groupData", myGroupData);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    @GetMapping(value = "/optional-user")
    @ResponseBody
    public Map<String, Object> getGroupOptionalUser(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/group/optional-user");
        String userId = checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        //获取可以作为组长和组员的人员
        ArrayList<Object> isSelectedLeaders = new ArrayList<>();
        ArrayList<Object> isSelectedMembers = new ArrayList<>();
        List<UserBean> allUser = userService.findAllUser();
        for (int i = 0; i < allUser.size(); i++) {
            UserBean userBean = allUser.get(i);
            if (userBean.getUserRole() == 1 || userBean.getUserRole() == 2) {
                continue;
            }
            Map<String, Object> isValidLeader = new HashMap<>();
            Map<String, Object> isValidMember = new HashMap<>();
            List<Integer> groupNames = groupMemberService.findGroupNameFromGroupMember(userBean.getUserId());
            if (groupNames == null || groupNames.size() == 0) {

                isValidLeader.put("leaderId", userBean.getUserId());
                isValidLeader.put("leader", userBean.getUserName());
                isSelectedLeaders.add(isValidLeader);

                isValidMember.put("memberId", userBean.getUserId());
                isValidMember.put("member", userBean.getUserName());
                isSelectedMembers.add(isValidMember);
            }
        }
        details.put("leaderSelectData", isSelectedLeaders);
        details.put("MemberSelectData", isSelectedMembers);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    /**
     * 新增加小组group
     */
    @Transactional
    @PostMapping(value = "/create")
    @ResponseBody
    public Map<String, Object> addNewGroup(@RequestParam(value = "cookie") String cookie,
                                           @RequestParam(value = "leaderId") String leaderId,
                                           @RequestParam(value = "groupName") String groupName) {
        urlLogInfo("/group/create");
        checkCookieValid(cookie);
        //检查是否有小组创建权限
        UserBean userBean = userService.findUserFromUserId(checkCookieValid(cookie));
        Map<String, Object> details = new HashMap<>();
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.GroupManagementCode);
        if (leaderId.equals(SpecialRecordCode.ADMIN_USERID) || leaderId == null || userService.findUserFromUserId(leaderId) == null) {
            throw new UserIdInvalid(leaderId + " 工号不存在！");
        }
        GroupNameBean groupNameBean = groupNameService.findGroupNameFromName(groupName);
        if (groupNameBean != null) {
            throw new ParamInvalidException("The groupName is invalid.");
        }
        List<Integer> groupNames = groupMemberService.findGroupNameFromGroupMember(leaderId);
        if (groupNames != null && groupNames.size() > 0) {
            throw new ParamInvalidException("The leaderId is invalid.");
        }
        groupNameBean = new GroupNameBean();
        groupNameBean.setGroupName(groupName);
        int add_Group_Result = groupNameService.addGroupName(groupNameBean);
        if (add_Group_Result == SpecialRecordCode.SQL_RESULT_FAIL) {
            throw new ParamInvalidException("Create groupName error.");
        }
        GroupMemberBean groupMemberBean = new GroupMemberBean();
        groupMemberBean.setIsLeader(GroupRoleCode.RTL);
        groupMemberBean.setGroupName(groupNameService.findGroupNameFromName(groupName).getId());
        groupMemberBean.setGroupMember(leaderId);
        int add_Group_Member_Result = groupMemberService.addGroupMember(groupMemberBean);
        if (add_Group_Member_Result == SpecialRecordCode.SQL_RESULT_FAIL) {
            throw new ParamInvalidException("Add group member error.");
        }
        userBean.setUserId(leaderId);
        userBean.setUserRole(SysRoleCode.RTLCode);
        userService.updateUserRole(userBean);
        details.put("group-create", "小组创建成功");
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    @Transactional
    @ResponseBody
    @PostMapping("/group-update-member")
    public Map<String, Object> projectUpdateMember(@RequestBody JSONObject jsonParam) {
        urlLogInfo("/group/group-update-member");
        logger.info(jsonParam.toJSONString());
        String cookie;
        String loginUserId;
        String groupName;
        String groupLeader;
        JSONArray groupMembers;
        try {
            cookie = jsonParam.getString("cookie");
            checkCookieValid(cookie);
            groupName = jsonParam.getString("groupName");
            groupLeader = jsonParam.getString("groupLeader");
            groupMembers = jsonParam.getJSONArray("groupMembers");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamInvalidException("groupMembers " + e.getMessage());
        }
        //检查是否有项目创建权限
        UserBean userBean = userService.findUserFromUserId(checkCookieValid(cookie));
        Map<String, Object> details = new HashMap<>();
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.GroupManagementCode);
        if (groupName == null || groupName.equals("")) {
            throw new ParamInvalidException("groupName " + groupName);
        }
        GroupNameBean groupNameBean = groupNameService.findGroupNameFromName(groupName);
        if (groupNameBean == null) {
            throw new ParamInvalidException("groupName " + groupName);
        }
        if (groupNameBean.getId().equals(SpecialRecordCode.BOSS_GROUP_ID)
        ) {
            throw new ParamInvalidException("ADMIN_GROUP_ID and BOSS_GROUP_ID don`t updated.");
        }
        if (groupLeader != null && !groupLeader.trim().equals("")) {
            groupLeader = groupLeader.trim();
            UserBean userLeaderBean = userService.findUserFromUserId(groupLeader.trim());
            if (userLeaderBean == null) {
                throw new UserIdInvalid(groupLeader + " 工号不存在！");
            }
            List<Integer> groupNames = groupMemberService.findGroupNameFromGroupMember(groupLeader);
            if (groupNames != null && groupNames.size() > 0) {
                throw new ParamInvalidException("The groupLeader already joined on a group.");
            }
            List<String> groupMemberLeaderIds = groupMemberService.findGroupMemberFromGroupNameAndRole(groupNameBean.getId(), GroupRoleCode.RTL);
            for (String groupMemberLeaderId : groupMemberLeaderIds) {
                Integer groupMemberServiceId = groupMemberService.findIdFromGroupNameAndGroupMember(groupNameBean.getId(), groupMemberLeaderId);
                //将原组长设置为guest角色
                userBean.setUserRole(SysRoleCode.GuestCode);
                userBean.setUserId(groupMemberLeaderId);
                userService.updateUserRole(userBean);
                int result = groupMemberService.delGroupMember(groupMemberServiceId);
                if (result == SpecialRecordCode.SQL_RESULT_FAIL) {
                    throw new ParamInvalidException("Delete old groupLeader is error. " + "groupName: " + groupName + ", old groupLeader: " + groupMemberLeaderId);
                }
            }
            //设置用户表的组长角色码，组内前组长userRole不变
            userBean.setUserId(groupLeader);
            userBean.setUserRole(SysRoleCode.RTLCode);
            userService.updateUserRole(userBean);
            GroupMemberBean groupMemberBean = new GroupMemberBean();
            groupMemberBean.setGroupName(groupNameBean.getId());
            groupMemberBean.setGroupMember(groupLeader);
            groupMemberBean.setIsLeader(GroupRoleCode.RTL);
            int result = groupMemberService.addGroupMember(groupMemberBean);
            if (result == SpecialRecordCode.SQL_RESULT_FAIL) {
                throw new ParamInvalidException("Add groupLeader is error. " + "groupLeader" + groupLeader);
            }
        }
        List<String> groupMemberIds = groupMemberService.findGroupMemberFromGroupNameAndRole(groupNameBean.getId(), GroupRoleCode.FO);
        for (String groupMemberId : groupMemberIds) {
            Integer groupMemberServiceId = groupMemberService.findIdFromGroupNameAndGroupMember(groupNameBean.getId(), groupMemberId);
            int result = groupMemberService.delGroupMember(groupMemberServiceId);
            if (result == SpecialRecordCode.SQL_RESULT_FAIL) {
                throw new ParamInvalidException("Delete is error. " + "groupName: " + groupName + ", groupMemberId: " + groupMemberId);
            }
        }
        for (int i = 0; i < groupMembers.size(); i++) {
            String groupMemberId = (String) groupMembers.get(i);
            if (groupMemberId == null || userService.findUserFromUserId(groupMemberId.trim()) == null) {
                throw new UserIdInvalid(groupMemberId + " 工号不存在！");
            }
            if (groupMemberId.equals(SpecialRecordCode.ADMIN_USERID)) {
                throw new ParamInvalidException("groupMemberId is " + SpecialRecordCode.ADMIN_USERID);
            }
            groupMemberId = groupMemberId.trim();
            UserBean userMemberBean = userService.findUserFromUserId(groupMemberId);
            if (userMemberBean == null) {
                throw new ParamInvalidException("The groupMemberId is invalid." + " groupMemberId: " + groupMemberId);
            }
            List<Integer> groupNames = groupMemberService.findGroupNameFromGroupMember(groupMemberId);
            if (groupNames != null && groupNames.size() > 0) {
                throw new ParamInvalidException("The groupMemberId: " + groupMemberId + " is already join in a group.");
            }
            GroupMemberBean groupMemberBean = new GroupMemberBean();
            groupMemberBean.setGroupName(groupNameBean.getId());
            groupMemberBean.setGroupMember(groupMemberId);
            groupMemberBean.setIsLeader(GroupRoleCode.FO);
            int result = groupMemberService.addGroupMember(groupMemberBean);
            if (result == SpecialRecordCode.SQL_RESULT_FAIL) {
                throw new ParamInvalidException("Add groupMember is error. " + "groupMemberId" + groupMemberId);
            }
        }
        boolean result = true;
        String message = "人员更新成功";
        details.put("group_member_update_result", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    /**
     * 删除小组group
     */
    @Transactional
    @ResponseBody
    @PostMapping("/delete-group")
    public Object deleteGroup(@RequestParam(value = "cookie") String cookie,
                              @RequestParam(value = "groupName") String groupName) {
        urlLogInfo("/group/delete-group");
        checkCookieValid(cookie);
        //检查是否有项目创建权限
        UserBean userBean = userService.findUserFromUserId(checkCookieValid(cookie));
        Map<String, Object> details = new HashMap<>();
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.GroupManagementCode);
        GroupNameBean groupNameBean = groupNameService.findGroupNameFromName(groupName);
        if (groupNameBean == null || groupNameBean.getId() == SpecialRecordCode.BOSS_GROUP_ID) {
            throw new PageException("The groupName is invalid.");
        }
        //先删除小组所有成员
        int id = groupNameBean.getId();
        List<String> groupMembers = groupMemberService.findGroupMemberFromGroupName(id);
        if (groupMembers != null && groupMembers.size() > 0) {
            for (int i = 0; i < groupMembers.size(); i++) {
                Integer isLeader = groupMemberService.checkoutIsLeaderFromGroupMember(groupMembers.get(i));
                if (isLeader == 1) {
                    userBean.setUserId(groupMembers.get(i));
                    userBean.setUserRole(SysRoleCode.GuestCode);
                    userService.updateUserRole(userBean);
                }
                int groupMemberId = groupMemberService.findIdFromGroupMember(groupMembers.get(i));
                int member_result = groupMemberService.delGroupMember(groupMemberId);
                if (member_result == SpecialRecordCode.SQL_RESULT_FAIL) {
                    throw new PageException("Delete members is error.");
                }
            }
        }
        //小组成员全部删除后，删除小组
        int group_result = groupNameService.delGroupName(id);
        if (group_result == SpecialRecordCode.SQL_RESULT_FAIL) {
            throw new PageException("Delete group is error.");
        }
        details.put("delete_group", "小组删除成功");
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }
}






