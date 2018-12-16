package com.byd.performance_main.control.using;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.*;
import com.byd.performance_utils.code.*;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/member-scored")
public class MemberScoredByLeaderShowController extends BaseController {

    @GetMapping(value = "/group-info")
    @ResponseBody
    public Map<String, Object> getMemberScoredGroupInfo(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/member-scored/group-info");
        String userId = checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        ArrayList<Object> myGroupData = new ArrayList<>();
        ArrayList projectContent = new ArrayList();
        List<ScoredStateModeTimeBean> scoredStateModeTimeBeans = scoredStateModeTimeService.findScoredStateModeTimeBeanFromIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
        String scoreTime;
        if (scoredStateModeTimeBeans != null && scoredStateModeTimeBeans.size() == 1) {
            scoreTime = scoredStateModeTimeBeans.get(0).getScoreTime();
        } else {
            details.put("groupData", myGroupData);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
            return map;
        }
        if (!scoreTime.equals(CommonMethod.getLastMonth())) {
            GroupScoringMemberBean groupScoringMemberBean = groupScoringMemberService.findGroupScoringMemberBeanFromUserIdAndTime(userId, scoreTime);
            if (groupScoringMemberBean != null) {
                Map<String, Object> groupData = new HashMap<>();
                groupData.put("time", scoreTime);
                groupData.put("userId", userId);
                groupData.put("userName", userService.findUserFromUserId(userId).getUserName());
                groupData.put("groupName", groupScoringMemberBean.getCurrentGroupName());
                if (groupScoringMemberBean.getCurrentRole() == GroupRoleCode.RTL) {
                    groupData.put("isLeader", GroupRoleCode.FO_NAME);
                } else {
                    groupData.put("isLeader", GroupRoleCode.FO_NAME);
                }
                groupData.put("myBoss", userService.findUserFromUserId(groupScoringMemberBean.getRatingUserId()).getUserName());
                groupData.put("myBossUserId", groupScoringMemberBean.getRatingUserId());
                groupData.put("scoreResult", groupScoringMemberBean.getScore());
                groupData.put("scoreWeight", groupScoringMemberBean.getWeight());
                List<ProjectOwnScoringBean> projectOwnScoringBean = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndScoreTime(userId, scoreTime);
                if (projectOwnScoringBean != null) {
                    for (int i = 0; i < projectOwnScoringBean.size(); i++) {
                        projectContent.add(projectOwnScoringBean.get(i).getWorkContent());
                        groupData.put("workContent", projectContent);
                    }
                } else {
                    groupData.put("workContent", null);
                }
                myGroupData.add(groupData);
            }
        } else {
            List<Integer> groupNameIds = groupMemberService.findGroupNameFromGroupMember(userId);
            if (groupNameIds != null && groupNameIds.size() > 0) {
                for (int i = 0; i < groupNameIds.size(); i++) {
                    Integer groupId = groupNameIds.get(i);
                    if (groupId != SpecialRecordCode.BOSS_GROUP_ID) {
                        GroupNameBean groupNameBean = groupNameService.findGroupNameFromId(groupId);
                        String groupNameLeader = groupMemberService.findGroupNameLeader(groupId);
                        if (groupNameLeader != null) {
                            String groupLeaderId = groupMemberService.findGroupNameLeader(groupId);
                            boolean isLeader = groupLeaderId.equals(userId);
                            Map<String, Object> groupData = new HashMap<>();
                            if (!isLeader) {
                                groupData.put("time", scoreTime);
                                groupData.put("userId", userId);
                                groupData.put("userName", userService.findUserFromUserId(userId).getUserName());
                                groupData.put("groupName", groupNameBean.getGroupName());
                                groupData.put("isLeader", GroupRoleCode.FO_NAME);
                                groupData.put("myBoss", userService.findUserFromUserId(groupLeaderId).getUserName());
                                groupData.put("myBossUserId", groupLeaderId);
                                GroupScoringMemberBean groupScoringMemberBean = groupScoringMemberService.findGroupScoringMemberBeanFromUserIdAndTime(userId, scoreTime);
                                if (groupScoringMemberBean != null) {
                                    Integer score = groupScoringMemberBean.getScore();
                                    groupData.put("scoreResult", score);
                                } else {
                                    groupData.put("scoreResult", null);
                                }
                                List<Integer> allProject = projectMemberService.findProjectNameFromProjectMember(userId);
                                if (allProject.size() > 0) {
                                    for (int h = 0; h < allProject.size(); h++) {
                                        ProjectNameBean projectNameBean = projectNameService.findProjectNameFromId(allProject.get(h));
                                        ProjectOwnScoringBean projectOwnScoringBean = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndCurrentProjectAndScoreTime(userId, projectNameBean.getProjectName(), scoreTime);
                                        if (projectOwnScoringBean != null) {
                                            String workContent = projectOwnScoringBean.getWorkContent();
                                            projectContent.add(workContent);
                                        } else {
                                            groupData.put("workContent", null);
                                        }
                                    }
                                } else {
                                    groupData.put("workContent", null);
                                }
                                groupData.put("workContent", projectContent);
                                UserBean groupUser = userService.findUserFromUserId(userId);
                                Integer weights = null;
                                if (groupUser.getUserRole() == SysRoleCode.FOCode) {
                                    weights = scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.RTL_SCORING_FO);
                                } else if (groupUser.getUserRole() == SysRoleCode.FGLCode) {
                                    weights = scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.RTL_SCORING_FGL);
                                } else if (groupUser.getUserRole() == SysRoleCode.ARCCode || groupUser.getUserRole() == SysRoleCode.SPDMCode) {
                                    weights = scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.RTL_SCORING_SPDM_OR_ARC);
                                }
                                if (weights != null) {
                                    groupData.put("scoreWeight", weights);
                                }
                            } else {
                                groupData.put("time", scoreTime);
                                groupData.put("userId", userId);
                                groupData.put("userName", userService.findUserFromUserId(userId).getUserName());
                                groupData.put("groupName", groupNameBean.getGroupName());
                                groupData.put("isLeader", GroupRoleCode.RTL_NAME);
                                List<String> groupMembers = groupMemberService.findGroupMemberFromGroupName(SpecialRecordCode.BOSS_GROUP_ID);
                                groupData.put("myBoss", userService.findUserFromUserId(groupMembers.get(0)).getUserName());
                                groupData.put("myBossUserId", groupMembers.get(0));
                                groupData.put("scoreWeight", scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.BOSS_SCORING_RTL));
                                List<ProjectOwnScoringBean> projectOwnScoringBean = projectOwnScoringService.findProjectOwnScoringBeansFromUserIdAndCurrentProjectAndScoreTime(groupLeaderId, "组内自评均值", scoreTime);
                                ArrayList projectOwnScoringBeanList = new ArrayList();
                                if (projectOwnScoringBean.size() > 0) {
                                    for (int r = 0; i < projectOwnScoringBean.size(); i++) {
                                        ProjectOwnScoringBean projectOwnScoringBean1 = projectOwnScoringBean.get(i);
                                        projectOwnScoringBeanList.add(projectOwnScoringBean1.getWorkContent());
                                        groupData.put("workContent", projectOwnScoringBeanList);
                                    }
                                } else {
                                    groupData.put("workContent", null);
                                }
                                GroupScoringMemberBean groupScoringMemberBean = groupScoringMemberService.findGroupScoringMemberBeanFromUserIdAndTime(userId, scoreTime);
                                if (groupScoringMemberBean != null) {
                                    Integer score = groupScoringMemberBean.getScore();
                                    groupData.put("scoreResult", score);
                                } else {
                                    groupData.put("scoreResult", null);
                                }
                            }
                            myGroupData.add(groupData);
                        }
                    }
                }
            }
        }
        details.put("groupData", myGroupData);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    @GetMapping(value = "/project-info")
    @ResponseBody
    public Map<String, Object> getMemberScoredProjectInfo(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/member-scored/project-info");
        String userId = checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        ArrayList<Object> myProjectData = new ArrayList<>();
        if (userService.findUserFromUserId(userId).getUserRole() == SysRoleCode.FGLCode || userService.findUserFromUserId(userId).getUserRole() == SysRoleCode.FOCode) {
            List<ScoredStateModeTimeBean> scoredStateModeTimeBeans = scoredStateModeTimeService.findScoredStateModeTimeBeanFromIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
            String scoreTime;
            if (scoredStateModeTimeBeans != null && scoredStateModeTimeBeans.size() == 1) {
                scoreTime = scoredStateModeTimeBeans.get(0).getScoreTime();
            } else {
                details.put("projectData", myProjectData);
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
                return map;
            }
            if (!scoreTime.equals(CommonMethod.getLastMonth())) {
                List<ProjectScoringMemberBean> projectScoringMemberBeans = projectScoringMemberService.findProjectScoringMemberBeanFromUserIdAndScoreTime(userId, scoreTime);
                for (ProjectScoringMemberBean projectScoringMemberBean : projectScoringMemberBeans) {
                    Map<String, Object> projectData = new HashMap<>();
                    projectData.put("projectName", projectScoringMemberBean.getCurrentProjectName());
                    if (projectScoringMemberBean.getCurrentRole() == ProjectRoleCode.FO) {
                        projectData.put("projectRole", ProjectRoleCode.FO_NAME);
                    } else if (projectScoringMemberBean.getCurrentRole() == ProjectRoleCode.FGL) {
                        projectData.put("projectRole", ProjectRoleCode.FGL_NAME);
                    } else if (projectScoringMemberBean.getCurrentRole() == ProjectRoleCode.SPDM) {
                        projectData.put("projectRole", ProjectRoleCode.SPDM_NAME);
                    } else if (projectScoringMemberBean.getCurrentRole() == ProjectRoleCode.ARC) {
                        projectData.put("projectRole", ProjectRoleCode.ARC_NAME);
                    } else if (projectScoringMemberBean.getCurrentRole() == ProjectRoleCode.RTL) {
                        projectData.put("projectRole", ProjectRoleCode.RTL_NAME);
                    } else {
                        projectData.put("projectRole", ProjectRoleCode.UNKNOWN_NAME);
                    }
                    projectData.put("userId", userId);
                    projectData.put("userName", userService.findUserFromUserId(userId).getUserName());
                    projectData.put("myBossUserId", projectScoringMemberBean.getRatingUserId());
                    projectData.put("myBoss", userService.findUserFromUserId(projectScoringMemberBean.getRatingUserId()).getUserName());
                    projectData.put("scoreResult", projectScoringMemberBean.getScore());
                    projectData.put("scoreWeight", projectScoringMemberBean.getWeight());
                    projectData.put("time", scoreTime);
                    try {
                        projectData.put("workContent", projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndCurrentProjectAndScoreTime(userId, projectScoringMemberBean.getCurrentProjectName(), scoreTime).getWorkContent());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    myProjectData.add(projectData);
                }
            } else {
                List<Integer> projectNames = projectMemberService.findProjectNameFromProjectMember(userId);
                if (projectNames != null && projectNames.size() > 0) {
                    boolean isBossProjectResult = true;
                    for (int i = 0; i < projectNames.size(); i++) {
                        Integer projectNameId = projectNames.get(i);
                        if (projectNameId == SpecialRecordCode.BOSS_PROJECT_ID) {
                            isBossProjectResult = false;
                        }
                    }
                    if (isBossProjectResult) {
                        for (int i = 0; i < projectNames.size(); i++) {
                            Integer projectId = projectNames.get(i);
                            Integer projectNameRole = projectMemberService.findProjectNameRole(projectId, userId);
                            if (projectNameRole == ProjectRoleCode.FO) {
                                List<String> projectFGLIds = projectMemberService.findProjectMemberFromProjectIdAndRole(projectId, ProjectRoleCode.FGL);
                                if (projectFGLIds != null && projectFGLIds.size() > 0) {
                                    String projectFGLId = projectFGLIds.get(0);
                                    Map<String, Object> projectData = setProjectMemberScoreJson(userId, scoreTime, projectId, projectFGLId, ScoreWeightNameCode.FGL_SCORING_FO);
                                    myProjectData.add(projectData);
                                }
                            } else if (projectNameRole == ProjectRoleCode.FGL) {
                                List<String> projectSPDMIds = projectMemberService.findProjectMemberFromProjectIdAndRole(projectId, ProjectRoleCode.SPDM);
                                if (projectSPDMIds != null && projectSPDMIds.size() > 0) {
                                    for (int j = 0; j < projectSPDMIds.size(); j++) {
                                        Map<String, Object> projectData = setProjectMemberScoreJson(userId, scoreTime, projectId, projectSPDMIds.get(j), ScoreWeightNameCode.SPDM_SCORING_FGL);
                                        myProjectData.add(projectData);
                                    }
                                }
                            } else if (projectNameRole == ProjectRoleCode.SPDM || projectNameRole == ProjectRoleCode.ARC) {
                                List<String> projectSPDMOrARCIds = projectMemberService.findProjectMemberFromProjectIdAndRole(projectId, projectNameRole);
                                //只有一条数据
                                List<ProjectMemberBean> bossBean = projectMemberService.findBossFromProjectMemberTable(SpecialRecordCode.BOSS_PROJECT_ID);
                                if (projectSPDMOrARCIds != null && projectSPDMOrARCIds.size() > 0) {
                                    for (int j = 0; j < projectSPDMOrARCIds.size(); j++) {
                                        if (bossBean.size() > 0) {
                                            Map<String, Object> projectData = setProjectMemberScoreJson(userId, scoreTime, projectId, bossBean.get(0).getProjectMember(), ScoreWeightNameCode.RTL_SCORING_SPDM_OR_ARC);
                                            myProjectData.add(projectData);
                                        } else {
                                            myProjectData.add(null);
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            details.put("projectData", myProjectData);
        } else {
            details.put("projectData", myProjectData);
        }
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    private Map<String, Object> setProjectMemberScoreJson(String userId, String currentTime, Integer projectId, String ratingUserId, int leaderScoring) {
        Map<String, Object> projectData = new HashMap<>();
        ProjectNameBean projectNameBean = projectNameService.findProjectNameFromId(projectId);
        projectData.put("projectName", projectNameBean.getProjectName());
        Integer projectNameRoleId = projectMemberService.findProjectNameRole(projectId, userId);
        String projectRole = CommonMethod.transformProjectRole(projectNameRoleId);
        projectData.put("projectRole", projectRole);
        projectData.put("userId", userId);
        projectData.put("userName", userService.findUserFromUserId(userId).getUserName());
        projectData.put("myBossUserId", ratingUserId);
        projectData.put("myBoss", userService.findUserFromUserId(ratingUserId).getUserName());

        ProjectScoringMemberBean scored = projectScoringMemberService.findProjectScoringMemberBeanFromUserIdAndProjectNameAndScoreTime(userId, projectNameBean.getProjectName(), currentTime);
        if (scored != null) {
            projectData.put("scoreResult", scored.getScore());
        } else {
            projectData.put("scoreResult", null);
        }
        Integer weightValue = scoreWeightService.findScoreValueFromWeightName(leaderScoring);
        if (weightValue != null) {
            projectData.put("scoreWeight", weightValue);
        } else {
            projectData.put("scoreWeight", null);
        }
        ProjectOwnScoringBean projectOwnScoringBean = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndCurrentProjectAndScoreTime(userId, projectNameBean.getProjectName(), currentTime);
        if (projectOwnScoringBean != null) {
            projectData.put("workContent", projectOwnScoringBean.getWorkContent());
        } else {
            projectData.put("workContent", null);
        }
        projectData.put("time", currentTime);
        return projectData;
    }
}
