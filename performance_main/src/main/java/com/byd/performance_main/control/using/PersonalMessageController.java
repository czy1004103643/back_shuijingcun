package com.byd.performance_main.control.using;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.GroupNameBean;
import com.byd.performance_main.model.ProjectNameBean;
import com.byd.performance_main.model.UserBean;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/personal")
public class PersonalMessageController extends BaseController {

    @GetMapping(value = "/info")
    @ResponseBody
    public Map<String, Object> getPersonalInfo(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/personal/info");
        String userId = checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        ArrayList<Object> userInfoList = new ArrayList<>();
        Map<String, Object> userInfo = new HashMap<>();
        UserBean user = userService.findUserFromUserId(userId);
        userInfo.put("userName", user.getUserName());
        userInfo.put("userId", user.getUserId());
        userInfo.put("employeeLevel", user.getEmployeeLevel());
        List<Integer> groupNames = groupMemberService.findGroupNameFromGroupMember(user.getUserId());
        if (groupNames != null && groupNames.size() > 0) {
            Integer groupId = groupNames.get(0);
            GroupNameBean groupNameBean = groupNameService.findGroupNameFromId(groupId);
            String leader = groupMemberService.findGroupNameLeader(groupId);
            if (groupNameBean != null) {
                userInfo.put("groupName", groupNameBean.getGroupName());
                if (leader != null) {
                    userInfo.put("isLeader", leader.equals(user.getUserId()) ? "组长" : "组员");
                } else {
                    userInfo.put("isLeader", null);
                }
            } else {
                userInfo.put("groupName", null);
                userInfo.put("isLeader", null);
            }
        } else {
            userInfo.put("groupName", null);
            userInfo.put("isLeader", null);
        }
        List<Integer> projectNames = projectMemberService.findProjectNameFromProjectMember(user.getUserId());
        ArrayList<HashMap> projectList = new ArrayList<>();
        for (int i = 0; i < projectNames.size(); i++) {
            HashMap<String, Object> projects = new HashMap<>();
            Integer projectNameId = projectNames.get(i);
            ProjectNameBean projectNameBean = projectNameService.findProjectNameFromId(projectNameId);
            if (projectNameBean != null) {
                projects.put("projectName", projectNameBean.getProjectName());
                Integer projectRole = projectMemberService.findProjectNameRole(projectNameId, user.getUserId());
                projects.put("projectRole", CommonMethod.transformProjectRole(projectRole));
                projectList.add(projects);
            }
        }
        userInfo.put("projects", projectList);
        userInfoList.add(userInfo);
        details.put("userInfo", userInfoList);
        details.put("userAvatar", user.getUserAvatar());
        //todo 员工历史记录还没有写
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }
}
