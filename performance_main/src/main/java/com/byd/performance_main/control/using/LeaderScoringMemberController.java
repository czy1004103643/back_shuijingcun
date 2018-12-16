package com.byd.performance_main.control.using;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.*;
import com.byd.performance_utils.code.*;
import com.byd.performance_utils.exception.DatabaseOperationFailedException;
import com.byd.performance_utils.exception.ParamInvalidException;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/leader-scoring")
public class LeaderScoringMemberController extends BaseController {

    @GetMapping(value = "/info")
    @ResponseBody
    public Map<String, Object> getLeaderScoringMyselfInfo(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/leader-scoring/info");
        String userId = checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        ArrayList<Object> myGroupData = new ArrayList<>();
        ArrayList<Object> myProjectData = new ArrayList<>();
        String currentTime = CommonMethod.getLastMonth();
        List<ScoredStateModeTimeBean> scoredStateModeTimeBeans = scoredStateModeTimeService.findScoredStateModeTimeBeanFromIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
        List<ProjectOwnScoringBean> projectOwnScoringBean = projectOwnScoringService.findProjectOwnScoringBeanFromUserId(userId);
        String scoreTime;
        if (scoredStateModeTimeBeans != null && scoredStateModeTimeBeans.size() == 1) {
            scoreTime = scoredStateModeTimeBeans.get(0).getScoreTime();
        } else {
            details.put("groupData", myGroupData);
            details.put("projectData", myProjectData);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
            return map;
        }
        if (!currentTime.equals(scoreTime)) {
            GroupScoringMemberBean groupScoringMemberBeanIsValid = groupScoringMemberService.findGroupScoringMemberBeanFromUserIdAndTime(userId, scoreTime);
            if (groupScoringMemberBeanIsValid != null) {
                Map<String, Object> groupData = new HashMap<>();
                groupData.put("time", scoreTime);
                groupData.put("userId", groupScoringMemberBeanIsValid.getUserId());
                groupData.put("userName", userService.findUserFromUserId(groupScoringMemberBeanIsValid.getUserId()).getUserName());
                groupData.put("groupName", groupScoringMemberBeanIsValid.getCurrentGroupName());
                groupData.put("isLeader", GroupRoleCode.FO_NAME);
                groupData.put("myBoss", userService.findUserFromUserId(groupScoringMemberBeanIsValid.getRatingUserId()).getUserName());
                groupData.put("myBossUserId", userService.findUserFromUserId(groupScoringMemberBeanIsValid.getRatingUserId()).getUserName());
                Integer score = groupScoringMemberBeanIsValid.getScore();
                groupData.put("scoreResult", score);
                myGroupData.add(groupData);
            }
        } else {
            List<Integer> groupNameIds = groupMemberService.findGroupNameFromGroupMember(userId);
            if (groupNameIds != null && groupNameIds.size() > 0) {
                Integer groupId = groupNameIds.get(0);
                if (groupId == SpecialRecordCode.BOSS_GROUP_ID) {
                    //Boss组
                    List<GroupNameBean> allGroupName = groupNameService.findAllGroupName();
                    for (int i = 0; i < allGroupName.size(); i++) {
                        GroupNameBean groupNameBean = allGroupName.get(i);
                        Map<String, Object> groupData = new HashMap<>();
                        ArrayList groupDataList = new ArrayList();
                        String groupNameLeaderId = groupMemberService.findGroupNameLeader(groupNameBean.getId());
                        groupData.put("time", scoreTime);
                        if (groupNameLeaderId != null && !groupNameLeaderId.isEmpty()) {
                            groupData.put("userId", groupNameLeaderId);
                            groupData.put("userName", userService.findUserFromUserId(groupNameLeaderId).getUserName());
                        } else {
                            groupData.put("userId", null);
                            groupData.put("userName", null);
                        }
                        groupData.put("groupName", groupNameBean.getGroupName());
                        groupData.put("isLeader", GroupRoleCode.RTL_NAME);
                        groupData.put("myBoss", userService.findUserFromUserId(userId).getUserName());
                        groupData.put("myBossUserId", userId);
                        groupData.put("scoreWeight", scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.BOSS_SCORING_RTL));
                        List<ProjectOwnScoringBean> projectOwnScoringBean1 = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndScoreTime(groupNameLeaderId, scoreTime);
                        if (projectOwnScoringBean1.size() > 0) {
                            groupDataList.add(projectOwnScoringBean1.get(0).getWorkContent());
                        } else {
                            groupDataList.add(null);
                        }
                        groupData.put("workContent", groupDataList);
                        GroupScoringMemberBean groupScoringMemberBean = groupScoringMemberService.findGroupScoringMemberBeanFromUserIdAndTime(groupNameLeaderId, scoreTime);
                        setGroupScoreResultAndWeight(groupData, groupNameLeaderId, groupScoringMemberBean);
                        myGroupData.add(groupData);
                    }
                } else {
                    GroupNameBean groupNameBean = groupNameService.findGroupNameFromId(groupId);
                    String groupNameLeader = groupMemberService.findGroupNameLeader(groupId);
                    if (groupNameLeader != null) {
                        String groupLeaderId = groupMemberService.findGroupNameLeader(groupId);
                        boolean isLeader = groupLeaderId.equals(userId);
                        if (isLeader) {
                            List<String> groupMemberIds = groupMemberService.findGroupMemberFromGroupName(groupId);
                            for (int i = 0; i < groupMemberIds.size(); i++) {
                                String groupMemberId = groupMemberIds.get(i);
                                if(userService.findUserFromUserId(groupMemberId).getUserRole()==SysRoleCode.GuestCode){
                                    continue;
                                }
                                Map<String, Object> groupData = new HashMap<>();
                                ArrayList workContentList = new ArrayList();
                                if (groupMemberId.equals(userId)) {
                                    continue;
                                }
                                UserBean userBean = userService.findUserFromUserId(groupMemberId);
                                if (userBean.getUserRole() == SysRoleCode.FGLCode) {
                                    groupData.put("scoreWeight", scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.RTL_SCORING_FGL));
                                } else if (userBean.getUserRole() == SysRoleCode.FOCode) {
                                    groupData.put("scoreWeight", scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.RTL_SCORING_FO));
                                } else if (userBean.getUserRole() == SysRoleCode.SPDMCode || userBean.getUserRole() == SysRoleCode.ARCCode) {
                                    groupData.put("scoreWeight", scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.RTL_SCORING_SPDM_OR_ARC));
                                } else {
                                    groupData.put("scoreWeight", null);
                                }
                                groupData.put("time", scoreTime);
                                groupData.put("userId", groupMemberId);
                                groupData.put("userName", userService.findUserFromUserId(groupMemberId).getUserName());
                                groupData.put("groupName", groupNameBean.getGroupName());
                                if (userService.findUserFromUserId(groupMemberId).getUserRole() == SysRoleCode.FOCode) {
                                    groupData.put("isLeader", ProjectRoleCode.FO_NAME);
                                } else if (userService.findUserFromUserId(groupMemberId).getUserRole() == SysRoleCode.FGLCode) {
                                    groupData.put("isLeader", ProjectRoleCode.FGL_NAME);
                                } else if (userService.findUserFromUserId(groupMemberId).getUserRole() == SysRoleCode.SPDMCode) {
                                    groupData.put("isLeader", ProjectRoleCode.SPDM_NAME);
                                } else if (userService.findUserFromUserId(groupMemberId).getUserRole() == SysRoleCode.ARCCode) {
                                    groupData.put("isLeader", ProjectRoleCode.ARC_NAME);
                                } else {
                                    groupData.put("isLeader", SysRoleCode.GuestName);
                                }
                                groupData.put("myBoss", userService.findUserFromUserId(userId).getUserName());
                                groupData.put("myBossUserId", userId);
                                List<ProjectOwnScoringBean> projectOwnScoringBean1 = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndScoreTime(groupMemberId, scoreTime);
                                if (projectOwnScoringBean1 != null) {
                                    for (int k = 0; k < projectOwnScoringBean1.size(); k++) {
                                        workContentList.add(projectOwnScoringBean1.get(k).getWorkContent());
                                    }
                                }
                                groupData.put("workContent", workContentList);
                                GroupScoringMemberBean groupScoringMemberBean = groupScoringMemberService.findGroupScoringMemberBeanFromUserIdAndTime(groupMemberId, scoreTime);
                                setGroupScoreResultAndWeight(groupData, groupMemberId, groupScoringMemberBean);
                                myGroupData.add(groupData);
                            }
                        }
                    }
                }
            }
        }
        details.put("groupData", myGroupData);
        if (userService.findUserFromUserId(userId).getUserRole() != SysRoleCode.BossCode) {
            if (!currentTime.equals(scoreTime)) {
                List<ProjectScoringMemberBean> projectScoringMemberBeanIsValid = projectScoringMemberService.findProjectScoringMemberBeanFromUserIdAndScoreTime(userId, scoreTime);
                if (projectScoringMemberBeanIsValid != null && projectScoringMemberBeanIsValid.size() > 0) {
                    for (int i = 0; i < projectScoringMemberBeanIsValid.size(); i++) {
                        ProjectScoringMemberBean projectScoringMemberBean = projectScoringMemberBeanIsValid.get(i);
                        Map<String, Object> projectData = new HashMap<>();
                        projectData.put("projectName", projectScoringMemberBean.getCurrentProjectName());
                        projectData.put("projectRole", CommonMethod.transformProjectRole(projectScoringMemberBean.getCurrentRole()));
                        projectData.put("myBossUserId", projectScoringMemberBean.getRatingUserId());
                        projectData.put("myBoss", projectScoringMemberBean.getRatingUserId());
                        projectData.put("userId", projectScoringMemberBean.getUserId());
                        projectData.put("userName", userService.findUserFromUserId(projectScoringMemberBean.getUserId()).getUserName());
                        projectData.put("scoreResult", projectScoringMemberBean.getScore());
                        projectData.put("time", scoreTime);
                        myProjectData.add(projectData);
                    }
                }
            } else {
                List<Integer> projectNames = projectMemberService.findProjectNameFromProjectMember(userId);
                if (projectNames != null && projectNames.size() > 0) {
                    Integer projectNameId = projectNames.get(0);
                    if (projectNameId == SpecialRecordCode.BOSS_PROJECT_ID) {
                        List<ProjectNameBean> allProjectName = projectNameService.findAllProjectName();
                        for (int i = 0; i < allProjectName.size(); i++) {
                            ProjectNameBean projectNameBean = allProjectName.get(i);
                            Integer projectId = projectNameBean.getId();
                            List<String> projectSPDMIds = projectMemberService.findProjectMemberFromProjectIdAndRole(projectId, ProjectRoleCode.SPDM);
                            List<String> projectARCIds = projectMemberService.findProjectMemberFromProjectIdAndRole(projectId, ProjectRoleCode.ARC);
                            ArrayList<String> projectSPDMandARCIds = new ArrayList<>();
                            if (projectSPDMIds != null)
                                projectSPDMandARCIds.addAll(projectSPDMIds);
                            if (projectARCIds != null)
                                projectSPDMandARCIds.addAll(projectARCIds);
                            if (projectSPDMandARCIds.size() > 0) {
                                for (int j = 0; j < projectSPDMandARCIds.size(); j++) {
                                    Map<String, Object> projectData = setProjectMemberScoreJson(userId, scoreTime, projectId, projectSPDMandARCIds.get(j), ScoreWeightNameCode.RTL_SCORING_SPDM_OR_ARC);
                                    myProjectData.add(projectData);
                                }
                            }
                        }
                    } else {
                        for (int i = 0; i < projectNames.size(); i++) {
                            Integer projectId = projectNames.get(i);
                            Integer projectNameRole = projectMemberService.findProjectNameRole(projectId, userId);
                            if (projectNameRole == ProjectRoleCode.FGL) {
                                List<String> projectFOIds = projectMemberService.findProjectMemberFromProjectIdAndRole(projectId, ProjectRoleCode.FO);
                                if (projectFOIds != null && projectFOIds.size() > 0) {
                                    for (int j = 0; j < projectFOIds.size(); j++) {
                                        Map<String, Object> projectData = setProjectMemberScoreJson(userId, scoreTime, projectId, projectFOIds.get(j), ScoreWeightNameCode.FGL_SCORING_FO);
                                        myProjectData.add(projectData);
                                    }
                                }
                            } else if (projectNameRole == ProjectRoleCode.SPDM) {
                                List<String> projectFGLIds = projectMemberService.findProjectMemberFromProjectIdAndRole(projectId, ProjectRoleCode.FGL);
                                if (projectFGLIds != null && projectFGLIds.size() > 0) {
                                    for (int j = 0; j < projectFGLIds.size(); j++) {
                                        Map<String, Object> projectData = setProjectMemberScoreJson(userId, scoreTime, projectId, projectFGLIds.get(j), ScoreWeightNameCode.SPDM_SCORING_FGL);
                                        myProjectData.add(projectData);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            details.put("projectData", myProjectData);
        } else {
            details.put("projectData", null);
        }
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    private void setGroupScoreResultAndWeight(Map<String, Object> groupData, String groupNameLeaderId, GroupScoringMemberBean groupScoringMemberBean) {
        if (groupScoringMemberBean != null) {
            Integer score = groupScoringMemberBean.getScore();
            groupData.put("scoreResult", score);
        } else {
            groupData.put("scoreResult", null);
        }
    }

    private Map<String, Object> setProjectMemberScoreJson(String userId, String currentTime, Integer projectId, String ratingUserId, int leaderScoring) {
        Map<String, Object> projectData = new HashMap<>();
        ProjectNameBean projectNameBean = projectNameService.findProjectNameFromId(projectId);
        ProjectOwnScoringBean projectOwnScoringBean = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndCurrentProjectAndScoreTime(ratingUserId, projectNameBean.getProjectName(), currentTime);
        if (projectOwnScoringBean != null) {
            projectData.put("workContent", projectOwnScoringBean.getWorkContent());
        } else {
            projectData.put("workContent", null);
        }
        projectData.put("projectName", projectNameBean.getProjectName());
        Integer projectNameRoleId = projectMemberService.findProjectNameRole(projectId, ratingUserId);
        String projectRole = CommonMethod.transformProjectRole(projectNameRoleId);
        projectData.put("projectRole", projectRole);
        projectData.put("myBossUserId", userId);
        projectData.put("myBoss", userService.findUserFromUserId(userId).getUserName());
        projectData.put("userId", ratingUserId);
        projectData.put("userName", userService.findUserFromUserId(ratingUserId).getUserName());
        Integer scored = projectScoringMemberService.findProjectScoredFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(ratingUserId, projectNameBean.getProjectName(), userId, currentTime);
        if (scored != null) {
            projectData.put("scoreResult", scored);
        } else {
            projectData.put("scoreResult", null);
        }
        Integer weightValue = scoreWeightService.findScoreValueFromWeightName(leaderScoring);
        if (weightValue != null) {
            projectData.put("scoreWeight", weightValue);
        } else {
            projectData.put("scoreWeight", 0);
        }
        projectData.put("time", currentTime);
        return projectData;
    }

    @Transactional
    @ResponseBody
    @PostMapping("/group-score-submit")
    public Map<String, Object> submitGroupScore(@RequestBody JSONObject jsonParam) {
        urlLogInfo("/leader-scoring/group-score-submit");
        logger.info(jsonParam.toJSONString());
        String cookie;
        String loginUserId;
        JSONArray groupData;
        HashMap<String, Object> details = new HashMap<>();
        try {
            cookie = jsonParam.getString("cookie");
            loginUserId = checkCookieValid(cookie);

            groupData = jsonParam.getJSONArray("groupData");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamInvalidException("groupData " + e.getMessage());
        }
        UserBean userBean = userService.findUserFromUserId(loginUserId);
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.GroupAssessmentCode);
        for (int i = 0; i < groupData.size(); i++) {
            JSONObject jsonObject = (JSONObject) groupData.get(i);
            String myBossUserId = jsonObject.getString("myBossUserId");
            if (myBossUserId == null || userService.findUserFromUserId(myBossUserId.trim()) == null) {
                throw new ParamInvalidException("myBossUserId");
            }
            if (!myBossUserId.equals(loginUserId)) {
                throw new ParamInvalidException("myBossUserId");
            }
            myBossUserId = myBossUserId.trim();
            String scoreResult = jsonObject.getString("scoreResult");
            if (CommonMethod.judgeStringIsNull(scoreResult).equals("")) {
                throw new ParamInvalidException("scoreResult");
            }
            scoreResult = scoreResult.trim();
            String userId = jsonObject.getString("userId");
            if (userService.findUserFromUserId(userId) == null) {
                throw new ParamInvalidException("userId");
            }
            String groupName = jsonObject.getString("groupName");
            if (CommonMethod.judgeStringIsNull(groupName).equals("")) {
                throw new ParamInvalidException("groupName");
            }
            groupName.trim();
            String scoreWeight = jsonObject.getString("scoreWeight");
            if (CommonMethod.judgeStringIsNull(scoreWeight).equals("")) {
                throw new ParamInvalidException("scoreWeight");
            }
            String time = jsonObject.getString("time");
            if (CommonMethod.judgeStringIsNull(time).equals("") || !CommonMethod.isValidDate(time, CommonMethod.TIME_FORMAT_yyyy_MM)) {
                throw new ParamInvalidException("time");
            }
            time.trim();
            List<ScoredStateModeTimeBean> scoredStateModeTimeBeans = scoredStateModeTimeService.findScoredStateModeTimeBeanFromIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
            checkScoreTimeValid(time, scoredStateModeTimeBeans);
            GroupScoringMemberBean groupScoringMemberBean = new GroupScoringMemberBean();
            groupScoringMemberBean.setUserId(userId);
            if (time.equals(CommonMethod.getLastMonth())) {
                Integer groupId = groupNameService.findGroupNameFromName(groupName).getId();
                if (groupId == null) {
                    throw new ParamInvalidException("groupId");
                }
            }
            groupScoringMemberBean.setCurrentGroupName(groupName);
            groupScoringMemberBean.setCurrentRole(groupMemberService.findGroupNameRole(userId));
            groupScoringMemberBean.setRatingUserId(myBossUserId);
            groupScoringMemberBean.setRatingUserRole(groupMemberService.findGroupNameRole(myBossUserId));
            groupScoringMemberBean.setScore(CommonMethod.checkScoreValueValid(scoreResult));
            groupScoringMemberBean.setScoreTime(time);
            groupScoringMemberBean.setWeight(Integer.parseInt(scoreWeight));
            int result = SpecialRecordCode.SQL_RESULT_FAIL;
            if (!time.equals(CommonMethod.getLastMonth())) {
                GroupScoringMemberBean groupScoringMember = groupScoringMemberService.findGroupScoringMemberBean(userId, groupName, myBossUserId, time);
                if (groupScoringMember != null) {
                    groupScoringMemberBean.setId(groupScoringMember.getId());
                    result = groupScoringMemberService.updateGroupScoringMember(groupScoringMemberBean);
                }
            } else {
                GroupScoringMemberBean groupScoringMemberBeanOld = groupScoringMemberService.findGroupScoringMemberBeanFromUserIdAndTime(userId, time);
                if (groupScoringMemberBeanOld != null) {
                    groupScoringMemberService.delGroupScoringMember(groupScoringMemberBeanOld.getId());
                }
                GroupScoringMemberBean groupScoringMember = groupScoringMemberService.findGroupScoringMemberBean(userId, groupName, myBossUserId, time);
                if (groupScoringMember != null) {
                    groupScoringMemberBean.setId(groupScoringMember.getId());
                    result = groupScoringMemberService.updateGroupScoringMember(groupScoringMemberBean);
                } else {
                    List<Integer> projectNames = projectMemberService.findProjectNameFromProjectMember(userId);
                    if (projectNames != null && projectNames.size() > 0) {
                        result = groupScoringMemberService.addGroupScoringMember(groupScoringMemberBean);
                    } else {
                        result = groupScoringMemberService.addGroupScoringMember(groupScoringMemberBean);
                    }
                }
            }
            if (result == SpecialRecordCode.SQL_RESULT_FAIL) {
                throw new DatabaseOperationFailedException();
            }
        }
        boolean result = true;
        String message = "人员评分成功";
        details.put("groupData_result", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @Transactional
    @ResponseBody
    @PostMapping("/project-score-submit")
    public Map<String, Object> submitProjectScore(@RequestBody JSONObject jsonParam) {
        urlLogInfo("/leader-scoring/project-score-submit");
        logger.info(jsonParam.toJSONString());
        String cookie;
        String loginUserId;
        JSONArray projectData;
        String subMessage = "";
        HashMap<String, Object> details = new HashMap<>();
        try {
            cookie = jsonParam.getString("cookie");
            loginUserId = checkCookieValid(cookie);

            projectData = jsonParam.getJSONArray("projectData");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamInvalidException("projectData " + e.getMessage());
        }
        //检查是否有提交权限
        UserBean userBean = userService.findUserFromUserId(loginUserId);
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.ProjectAssessmentCode);
        for (int i = 0; i < projectData.size(); i++) {
            JSONObject jsonObject = (JSONObject) projectData.get(i);
            String myBossUserId = jsonObject.getString("myBossUserId");
            if (myBossUserId == null || userService.findUserFromUserId(myBossUserId.trim()) == null) {
                throw new ParamInvalidException("myBossUserId");
            }
            if (!myBossUserId.equals(loginUserId)) {
                throw new ParamInvalidException("myBossUserId");
            }
            myBossUserId = myBossUserId.trim();
            String scoreResult = jsonObject.getString("scoreResult");
            if (CommonMethod.judgeStringIsNull(scoreResult).equals("")) {
                throw new ParamInvalidException("scoreResult");
            }
            scoreResult = scoreResult.trim();
            String projectRole = jsonObject.getString("projectRole");
            if (CommonMethod.judgeStringIsNull(projectRole).equals("")) {
                throw new ParamInvalidException("projectRole");
            }
            projectRole.trim();
            String time = jsonObject.getString("time");
            if (CommonMethod.judgeStringIsNull(time).equals("") || !CommonMethod.isValidDate(time, CommonMethod.TIME_FORMAT_yyyy_MM)) {
                throw new ParamInvalidException("time");
            }
            time.trim();
            String workContent = jsonObject.getString("workContent");
            if (workContent == null) {
                subMessage += jsonObject.getString("userId") + ":" + jsonObject.getString("projectName") + "工作内容为空" + " ";
                ;

            }
            List<ScoredStateModeTimeBean> scoredStateModeTimeBeans = scoredStateModeTimeService.findScoredStateModeTimeBeanFromIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
            checkScoreTimeValid(time, scoredStateModeTimeBeans);
            String projectName = jsonObject.getString("projectName");
            if (CommonMethod.judgeStringIsNull(projectName).equals("")) {
                throw new ParamInvalidException("projectName");
            }
            projectName.trim();
            String userId = jsonObject.getString("userId");
            if (userId == null || userService.findUserFromUserId(userId.trim()) == null) {
                throw new ParamInvalidException("userId");
            }
            userId.trim();
            ProjectScoringMemberBean projectScoringMemberBean = new ProjectScoringMemberBean();
            projectScoringMemberBean.setUserId(userId);
            if (time.equals(CommonMethod.getLastMonth())) {
                Integer currentProjectId = projectNameService.findProjectNameFromName(projectName).getId();
                if (currentProjectId == null) {
                    throw new ParamInvalidException("currentProjectId");
                }
            }
            projectScoringMemberBean.setCurrentProjectName(projectName);
            projectScoringMemberBean.setCurrentRole(CommonMethod.transformProjectRoleCode(projectRole));
            projectScoringMemberBean.setRatingUserId(myBossUserId);
            Integer bossRole = projectMemberService.findProjectNameRole(0, myBossUserId);
            Integer projectNameRole;
            if (bossRole != null && bossRole.equals(ProjectRoleCode.BOSS)) {
                projectNameRole = ProjectRoleCode.BOSS;
            } else {
                projectNameRole = projectMemberService.findProjectNameRole(projectNameService.findProjectNameFromName(projectName).getId(), myBossUserId);
                if (projectNameRole == null) {
                    throw new ParamInvalidException("projectNameRole");
                }
            }
            projectScoringMemberBean.setRatingUserRole(projectNameRole);
            projectScoringMemberBean.setScore(CommonMethod.checkScoreValueValid(scoreResult));
            projectScoringMemberBean.setScoreTime(time);
            int result = SpecialRecordCode.SQL_RESULT_FAIL;
            if (!time.equals(CommonMethod.getLastMonth())) {
                ProjectScoringMemberBean projectScoringMemberBeanIsValid = projectScoringMemberService.findProjectScoringMemberBeanFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(userId, projectName, myBossUserId, time);
                if (projectScoringMemberBeanIsValid != null) {
                    projectScoringMemberBean.setId(projectScoringMemberBeanIsValid.getId());
                    projectScoringMemberBean.setWeight(projectScoringMemberBeanIsValid.getWeight());
                    result = projectScoringMemberService.updateProjectScoringMember(projectScoringMemberBean);
                }
            } else {
                ProjectScoringMemberBean projectScoringMemberBeanIsValid = projectScoringMemberService.findProjectScoringMemberBeanFromUserIdAndProjectNameAndRatingUserIdAndScoreTime(userId, projectName, myBossUserId, time);
                if (projectScoringMemberBeanIsValid == null) {
                    Integer role = projectMemberService.findProjectNameRole(projectNameService.findProjectNameFromName(projectName).getId(), userId);
                    if (role.equals(ProjectRoleCode.FO)) {
                        projectScoringMemberBean.setWeight(scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.FGL_SCORING_FO));
                    } else if (role.equals(ProjectRoleCode.FGL)) {
                        projectScoringMemberBean.setWeight(scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.SPDM_SCORING_FGL));
                    } else if (role.equals(ProjectRoleCode.SPDM) || role.equals(ProjectRoleCode.ARC)) {
                        projectScoringMemberBean.setWeight(scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.RTL_SCORING_SPDM_OR_ARC));
                    } else {
                        projectScoringMemberBean.setWeight(0);
                    }
                    result = projectScoringMemberService.addProjectScoringMember(projectScoringMemberBean);
                } else {
                    projectScoringMemberBean.setId(projectScoringMemberBeanIsValid.getId());
                    projectScoringMemberBean.setWeight(projectScoringMemberBeanIsValid.getWeight());
                    result = projectScoringMemberService.updateProjectScoringMember(projectScoringMemberBean);
                }
            }
            if (result == 0) {
                throw new DatabaseOperationFailedException();
            }
        }
        boolean result = true;
        String message = "人员评分成功!";
        details.put("projectData_result", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message + subMessage, details);
        return map;
    }
}
