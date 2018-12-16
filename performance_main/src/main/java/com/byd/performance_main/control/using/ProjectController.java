package com.byd.performance_main.control.using;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.GroupMemberBean;
import com.byd.performance_main.model.ProjectMemberBean;
import com.byd.performance_main.model.ProjectNameBean;
import com.byd.performance_main.model.UserBean;
import com.byd.performance_utils.code.*;
import com.byd.performance_utils.exception.ParamInvalidException;
import com.byd.performance_utils.exception.UserIdInvalid;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/project")
public class ProjectController extends BaseController {

    static String REAL_BOSS_MEMBERS = "realBossMembers";
    static String BOSS_MEMBERS = "bossMembers";
    static String SPDM_MEMBERS = "spdmMembers";
    static String ARC_MEMBERS = "arcMembers";
    static String LEADER_MEMBERS = "leaderMembers";
    static String FO_MEMBERS = "foMembers";
    static String MEMBERS_ID = "userId";
    static String MEMBERS_NAME = "userName";

    @GetMapping(value = "/info")
    @ResponseBody
    public Map<String, Object> getMemberScoredGroupInfo(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/project/info");
        String userId = checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        ArrayList<Object> projectData = new ArrayList<>();
        List<ProjectNameBean> allProjectNames = projectNameService.findAllProjectName();
        if (allProjectNames != null && allProjectNames.size() > 0) {
            for (int i = 0; i < allProjectNames.size(); i++) {
                ProjectNameBean projectNameBean = allProjectNames.get(i);
                if (projectNameBean.getId() == SpecialRecordCode.BOSS_PROJECT_ID) {
                    continue;
                }
                HashMap<String, Object> projectDataMessage = new HashMap<>();
                Integer projectId = projectNameBean.getId();
                String projectName = projectNameBean.getProjectName();
                projectDataMessage.put("featureGroupName", projectName);
                projectDataMessage.put("featureGroupSPDM", getMemberMessage(projectId, ProjectRoleCode.SPDM));
                projectDataMessage.put("featureGroupARC", getMemberMessage(projectId, ProjectRoleCode.ARC));
                projectDataMessage.put("featureGroupLeader", getMemberMessage(projectId, ProjectRoleCode.FGL));
                projectDataMessage.put("featureOwner", getMemberMessages(projectId, ProjectRoleCode.FO));
                projectData.add(projectDataMessage);
            }
        }
        details.put("projectData", projectData);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    private HashMap<String, Object> getMemberMessage(Integer projectId, Integer projectRoleCode) {
        HashMap<String, Object> memberMessage = new HashMap<>();
        List<String> projectMemberIds = projectMemberService.findProjectMemberFromProjectIdAndRole(projectId, projectRoleCode);
        if (projectMemberIds != null && projectMemberIds.size() > 0) {
            String projectMemberId = projectMemberIds.get(0);
            UserBean userBean = userService.findUserFromUserId(projectMemberId);
            memberMessage.put(MEMBERS_ID, userBean.getUserId());
            memberMessage.put(MEMBERS_NAME, userBean.getUserName());
        }
        return memberMessage;
    }

    private ArrayList<Object> getMemberMessages(Integer projectId, Integer projectRoleCode) {
        ArrayList<Object> memberList = new ArrayList<>();
        List<String> projectMemberIds = projectMemberService.findProjectMemberFromProjectIdAndRole(projectId, projectRoleCode);
        if (projectMemberIds != null && projectMemberIds.size() > 0) {
            for (String projectMemberId : projectMemberIds) {
                HashMap<String, Object> memberMessage = new HashMap<>();
                UserBean userBean = userService.findUserFromUserId(projectMemberId);
                memberMessage.put(MEMBERS_ID, userBean.getUserId());
                memberMessage.put(MEMBERS_NAME, userBean.getUserName());
                memberList.add(memberMessage);
            }
        }
        return memberList;
    }

    @GetMapping(value = "/optional-user")
    @ResponseBody
    public Map<String, Object> getProjectOptionalUser(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/project/optional-user");
        String userId = checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        ArrayList<Object> realBossMembers = new ArrayList<>();
        ArrayList<Object> bossMembers = new ArrayList<>();
        ArrayList<Object> spdmMembers = new ArrayList<>();
        ArrayList<Object> arcMembers = new ArrayList<>();
        ArrayList<Object> leaderMembers = new ArrayList<>();
        ArrayList<Object> foMembers = new ArrayList<>();
        List<GroupMemberBean> allUser = groupMemberService.findAllGroupMemberBean();
        if (allUser != null && allUser.size() > 0) {
            for (int i = 0; i < allUser.size(); i++) {
                GroupMemberBean groupMemberBean = allUser.get(i);
                UserBean userBean = userService.findUserFromUserId(groupMemberBean.getGroupMember());
                Integer roleId = userBean.getUserRole();
                if (roleId == SysRoleCode.AdminCode || roleId == SysRoleCode.BossCode) {
                    continue;
                }
                HashMap<String, Object> userMessage = new HashMap<>();
                userMessage.put(MEMBERS_ID, userBean.getUserId());
                userMessage.put(MEMBERS_NAME, userBean.getUserName());
                List<Integer> projectNames = projectMemberService.findProjectNameFromProjectMember(userBean.getUserId());
                if (projectNames != null && projectNames.size() > 0) {
                    for (int j = 0; j < projectNames.size(); j++) {
                        Integer projectId = projectNames.get(j);
                        Integer projectRole = projectMemberService.findProjectNameRole(projectId, userBean.getUserId());
                        if (projectRole != null && projectRole == ProjectRoleCode.BOSS) {
                            bossMembers.add(userMessage);
                        } else if (projectRole != null && projectRole == ProjectRoleCode.SPDM) {
                            spdmMembers.add(userMessage);
                        } else if (projectRole != null && projectRole == ProjectRoleCode.ARC) {
                            arcMembers.add(userMessage);
                        } else if (projectRole != null && projectRole == ProjectRoleCode.FGL) {
                            leaderMembers.add(userMessage);
                        } else if (projectRole != null && projectRole == ProjectRoleCode.FO) {
                            foMembers.add(userMessage);
                        }
                        break;
                    }
                    realBossMembers.add(userMessage);
                } else {
                    realBossMembers.add(userMessage);
                    bossMembers.add(userMessage);
                    spdmMembers.add(userMessage);
                    arcMembers.add(userMessage);
                    leaderMembers.add(userMessage);
                    foMembers.add(userMessage);
                }
            }
        }
        details.put(REAL_BOSS_MEMBERS, realBossMembers);
        details.put(BOSS_MEMBERS, bossMembers);
        details.put(SPDM_MEMBERS, spdmMembers);
        details.put(ARC_MEMBERS, arcMembers);
        details.put(LEADER_MEMBERS, leaderMembers);
        details.put(FO_MEMBERS, foMembers);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    @Transactional
    @ResponseBody
    @PostMapping("/create")
    public Object setScoredState(@RequestParam(value = "cookie") String cookie,
                                 @RequestParam(value = "projectName") String projectName,
                                 @RequestParam(value = "spdmId") String spdmId,
                                 @RequestParam(value = "leaderId") String leaderId) {
        urlLogInfo("/project/create");
        checkCookieValid(cookie);
        UserBean userBean = userService.findUserFromUserId(checkCookieValid(cookie));
        Map<String, Object> details = new HashMap<>();
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.ProjectManagementCode);
        boolean result = true;
        String message = "项目组创建成功";
        if (spdmId == null || spdmId.isEmpty()) {
            throw new ParamInvalidException("spdmId is null");
        } else if (leaderId == null || leaderId.isEmpty()) {
            throw new ParamInvalidException("leaderId is null");
        } else if (spdmId.equals(leaderId)) {
            throw new ParamInvalidException("spdmId == leaderId");
        } else if (spdmId.equals(SpecialRecordCode.ADMIN_USERID)) {
            throw new ParamInvalidException("userId is " + SpecialRecordCode.ADMIN_USERID);
        } else if (leaderId.equals(SpecialRecordCode.ADMIN_USERID)) {
            throw new ParamInvalidException("userId is " + SpecialRecordCode.ADMIN_USERID);
        }
        List<Integer> projectNames = projectMemberService.findProjectNameFromProjectMember(spdmId);
        if (projectNames != null && projectNames.size() > 0) {
            for (Integer projectNameId : projectNames) {
                Integer projectNameRole = projectMemberService.findProjectNameRole(projectNameId, spdmId);
                if (projectNameRole != ProjectRoleCode.SPDM) {
                    throw new ParamInvalidException("spdmId is other role.");
                }
            }
        }
        projectNames = projectMemberService.findProjectNameFromProjectMember(leaderId);
        if (projectNames != null && projectNames.size() > 0) {
            for (Integer projectNameId : projectNames) {
                Integer projectNameRole = projectMemberService.findProjectNameRole(projectNameId, leaderId);
                if (projectNameRole != ProjectRoleCode.FGL) {
                    throw new ParamInvalidException("leaderId is other role.");
                }
            }
        }
        ProjectNameBean projectNameBean = projectNameService.findProjectNameFromName(projectName);
        if (projectNameBean != null) {
            throw new ParamInvalidException("projectName has exist.");
        }
        projectNameBean = new ProjectNameBean();
        projectNameBean.setProjectName(projectName);
        int addProjectName_result = projectNameService.addProjectName(projectNameBean);
        if (addProjectName_result == SpecialRecordCode.SQL_RESULT_FAIL) {
            throw new ParamInvalidException("projectName");
        }
        Integer projectId = projectNameService.findProjectNameFromName(projectName).getId();
        UserBean spdmBean = userService.findUserFromUserId(spdmId);
        if (spdmBean != null && userService.findUserFromUserId(spdmId).getUserRole() != SysRoleCode.RTLCode) {
            ProjectMemberBean projectSPDMBean = new ProjectMemberBean();
            projectSPDMBean.setProjectName(projectId);
            projectSPDMBean.setProjectMember(spdmId);
            projectSPDMBean.setRoleName(ProjectRoleCode.SPDM);
            projectMemberService.addProjectMember(projectSPDMBean);
            //更新用户表里的userRole码
            userBean.setUserId(spdmId);
            userBean.setUserRole(SysRoleCode.SPDMCode);
            userService.updateUserRole(userBean);
        } else {
            throw new UserIdInvalid("组长不能担任SPDM！");
        }
        UserBean fglBean = userService.findUserFromUserId(leaderId);
        if (fglBean != null && userService.findUserFromUserId(leaderId).getUserRole() != SysRoleCode.RTLCode) {
            ProjectMemberBean projectFGLBean = new ProjectMemberBean();
            projectFGLBean.setProjectName(projectId);
            projectFGLBean.setProjectMember(leaderId);
            projectFGLBean.setRoleName(ProjectRoleCode.FGL);
            projectMemberService.addProjectMember(projectFGLBean);
            //更新用户表里的userRole码
            userBean.setUserId(leaderId);
            userBean.setUserRole(SysRoleCode.FGLCode);
            userService.updateUserRole(userBean);
        } else {
            throw new UserIdInvalid("组长不能担任FGL");
        }
        details.put("project_create", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @Transactional
    @PostMapping(value = "/delete-project")
    @ResponseBody
    public Map<String, Object> deleteProject(@RequestParam(value = "cookie") String cookie, @RequestParam(value = "projectName") String projectName) {
        urlLogInfo("/project/delete-project");
        String userId = checkCookieValid(cookie);
        UserBean userBean = userService.findUserFromUserId(checkCookieValid(cookie));
        Integer loginUserRole = userBean.getUserRole();
        Map<String, Object> details = new HashMap<>();
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.ProjectManagementCode);
        ProjectNameBean projectNameBean = projectNameService.findProjectNameFromName(projectName);
        if (projectNameBean == null || projectNameBean.getId() == SpecialRecordCode.BOSS_PROJECT_ID) {
            throw new ParamInvalidException(projectName + " is invalid");
        }
        List<ProjectMemberBean> allProjectMemberBeans = projectMemberService.findAllProjectMemberBean();
        if (allProjectMemberBeans != null && allProjectMemberBeans.size() > 0) {
            for (ProjectMemberBean projectMemberBean : allProjectMemberBeans) {
                if (projectMemberBean.getProjectName().equals(projectNameBean.getId())) {
                    int result = projectMemberService.delProjectMember(projectMemberBean.getId());
                    if (result == 0) {
                        throw new ParamInvalidException("Delete member: " + projectMemberBean.getProjectMember() + " is error");
                    }
                    String projectMemberId = projectMemberBean.getProjectMember();
                    List<Integer> allId = projectMemberService.findProjectNameFromProjectMember(projectMemberId);
                    if (allId.size() > 0) {

                    } else {
                        UserBean userBean1 = new UserBean();
                        userBean1.setUserId(projectMemberBean.getProjectMember());
                        userBean1.setUserRole(SysRoleCode.GuestCode);
                        userService.updateUserRole(userBean1);

                    }
                }
            }
        }
        int result = projectNameService.delProjectName(projectNameBean.getId());
        if (result == SpecialRecordCode.SQL_RESULT_FAIL) {
            throw new ParamInvalidException("Delete project`s name: " + projectNameBean.getProjectName() + " is error");
        }
        details.put("delete-project", "删除成功");
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    @Transactional
    @ResponseBody
    @PostMapping("/project-update-member")
    public Map<String, Object> projectUpdateMember(@RequestBody JSONObject jsonParam) {
        urlLogInfo("/project/project-update-member");
        logger.info(jsonParam.toJSONString());
        String cookie;
        String loginUserId;
        String projectName;
        String projectRole;
        JSONArray memberData;
        try {
            cookie = jsonParam.getString("cookie");
            loginUserId = checkCookieValid(cookie);
            projectName = jsonParam.getString("projectName");
            projectRole = jsonParam.getString("projectRole");
            memberData = jsonParam.getJSONArray("memberData");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamInvalidException("memberData " + e.getMessage());
        }
        //检查是否有项目创建权限
        UserBean userBean = userService.findUserFromUserId(checkCookieValid(cookie));
        Map<String, Object> details = new HashMap<>();
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.ProjectManagementCode);
        if (projectName == null || projectName.equals("")) {
            throw new ParamInvalidException("projectName " + projectName);
        }
        ProjectNameBean projectNameBean = projectNameService.findProjectNameFromName(projectName);
        if (projectNameBean == null) {
            throw new ParamInvalidException("projectName " + projectName);
        }
        if (projectRole == null || projectRole.equals("")) {
            throw new ParamInvalidException("projectRole");
        }
        if (transformRoleMessage(projectRole) == ProjectRoleCode.UNKNOWN) {
            throw new ParamInvalidException("The projectRole is invalid.");
        }
        if (transformRoleMessage(projectRole) != ProjectRoleCode.FO && memberData.size() > 1) {
            throw new ParamInvalidException("The users` number not match requirements.");
        }
        List<String> projectMemberIds = projectMemberService.findProjectMemberFromProjectIdAndRole(projectNameBean.getId(), transformRoleMessage(projectRole));
        for (String projectMemberId : projectMemberIds) {
            if(projectMemberService.findProjectNameFromProjectMember(projectMemberId).size()<=1) {
                if (userService.findUserFromUserId(projectMemberId).getUserRole() == SysRoleCode.RTLCode) {

                } else {
                    userBean.setUserId(projectMemberId);
                    userBean.setUserRole(SysRoleCode.GuestCode);
                    userService.updateUserRole(userBean);
                }
            }
            Integer projectMemberServiceId = projectMemberService.findProjectMemberBeanFromProjectNameAndProjectMember(projectNameBean.getId(), projectMemberId).getId();
            int result = projectMemberService.delProjectMember(projectMemberServiceId);
            if (result == SpecialRecordCode.SQL_RESULT_FAIL) {
                throw new ParamInvalidException("Delete is error. " + "projectName: " + projectName + ", projectMemberId: " + projectMemberId);
            }
        }
        for (int i = 0; i < memberData.size(); i++) {
            JSONObject jsonObject = (JSONObject) memberData.get(i);
            String userId = jsonObject.getString(MEMBERS_ID);
            if (userId == null || userService.findUserFromUserId(userId.trim()) == null) {
                throw new UserIdInvalid(userId + " 工号不存在！");
            }
            if (userId.equals(SpecialRecordCode.ADMIN_USERID)) {
                throw new ParamInvalidException("groupMemberId is " + SpecialRecordCode.ADMIN_USERID);
            }
            userId = userId.trim();
            List<Integer> projectNames = projectMemberService.findProjectNameFromProjectMember(userId);
            if (projectNames != null && projectNames.size() > 0) {
                for (Integer projectNameId : projectNames) {
                    Integer userProjectRole = projectMemberService.findProjectNameRole(projectNameId, userId);
                    if (transformRoleMessage(projectRole) != userProjectRole) {
                        throw new ParamInvalidException("The userId is not same project role.");
                    }
                }
            }
            //更新用户表里的userRole码
            int sysRole = userService.findUserFromUserId(userId).getUserRole();
            userBean.setUserId(userId);
            if (projectRole.equals("foMembers")) {
                if (sysRole != SysRoleCode.RTLCode) {
                    userBean.setUserRole(SysRoleCode.FOCode);
                    userService.updateUserRole(userBean);
                } else {
                    throw new UserIdInvalid("组长不能担任FO！");
                }
            } else if (projectRole.equals("arcMembers")) {
                if(sysRole!=SysRoleCode.RTLCode) {
                    userBean.setUserRole(SysRoleCode.ARCCode);
                    userService.updateUserRole(userBean);
                }
            } else if (projectRole.equals("spdmMembers")) {
                if (sysRole != SysRoleCode.RTLCode) {
                    userBean.setUserRole(SysRoleCode.SPDMCode);
                    userService.updateUserRole(userBean);
                } else {
                    throw new UserIdInvalid("组长不能担任SPDM");
                }
            } else if (projectRole.equals("leaderMembers")) {
                if (sysRole != SysRoleCode.RTLCode) {
                    userBean.setUserRole(SysRoleCode.FGLCode);
                    userService.updateUserRole(userBean);
                } else {
                    throw new UserIdInvalid("组长不能担任FGL");
                }
            }
            ProjectMemberBean projectMemberBean = new ProjectMemberBean();
            projectMemberBean.setProjectName(projectNameBean.getId());
            projectMemberBean.setRoleName(transformRoleMessage(projectRole));
            projectMemberBean.setProjectMember(userId);
            int result = projectMemberService.addProjectMember(projectMemberBean);
            if (result == SpecialRecordCode.SQL_RESULT_FAIL) {
                throw new ParamInvalidException("Add is error. " + "userId" + userId);
            }
        }
        boolean result = true;
        String message = "人员更新成功";
        details.put("project_member_update_result", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    private int transformRoleMessage(String projectRole) {
        int projectRoleCode = ProjectRoleCode.UNKNOWN;
        if (projectRole.equals(REAL_BOSS_MEMBERS)) {
            projectRoleCode = ProjectRoleCode.REAL_BOSS;
        } else if (projectRole.equals(BOSS_MEMBERS)) {
            projectRoleCode = ProjectRoleCode.BOSS;
        } else if (projectRole.equals(SPDM_MEMBERS)) {
            projectRoleCode = ProjectRoleCode.SPDM;
        } else if (projectRole.equals(ARC_MEMBERS)) {
            projectRoleCode = ProjectRoleCode.ARC;
        } else if (projectRole.equals(LEADER_MEMBERS)) {
            projectRoleCode = ProjectRoleCode.FGL;
        } else if (projectRole.equals(FO_MEMBERS)) {
            projectRoleCode = ProjectRoleCode.FO;
        }
        return projectRoleCode;
    }
}
