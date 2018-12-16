package com.byd.performance_main.control.using;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement;
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

import java.math.RoundingMode;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/yourself")
public class YourselfWriteController extends BaseController {

    /*
    查询数据
     */
    @ResponseBody
    @GetMapping("/information")
    public Map<String, Object> getInformation(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/yourself/information");
        String userId = checkCookieValid(cookie);//工号
        Map<String, Object> details = new HashMap<>();
        ArrayList<Object> userInfoList = new ArrayList<>();
        Map<String, Object> userInfo = new HashMap<>();
        //项目名称
        String message = "";
        UserBean user = userService.findUserFromUserId(userId);
        if (user.getUserId().equals(SpecialRecordCode.ADMIN_USERID)) {
            details.put("userInfo", userInfoList);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
            return map;
        }
        String userName = user.getUserName();//姓名
        ArrayList projects = new ArrayList();
        List<ScoredStateModeTimeBean> scoredStateModeTimeBeans = scoredStateModeTimeService.findScoredStateModeTimeBeanFromIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
        String scoreTime;
        if (scoredStateModeTimeBeans != null && scoredStateModeTimeBeans.size() == 1) {
            scoreTime = scoredStateModeTimeBeans.get(0).getScoreTime();
        } else {
            details.put("userInfo", userInfoList);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
            return map;
        }
        if (!scoreTime.equals(CommonMethod.getLastMonth())) {
            List<ProjectOwnScoringBean> projectOwnScoringBeans = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndScoreTime(userId, scoreTime);
            for (int i = 0; i < projectOwnScoringBeans.size(); i++) {
                ProjectOwnScoringBean projectOwnScoringBean = projectOwnScoringBeans.get(i);
                HashMap<String, Object> projectInfo = new HashMap<>();
                if (projectOwnScoringBean.getUserRole() == 1) {
                    projectInfo.put("projectRole", "组员");
                }
                projectInfo.put("weights", projectOwnScoringBean.getWeight());
                projectInfo.put("workContent", projectOwnScoringBean.getWorkContent());
                projectInfo.put("score", projectOwnScoringBean.getScore());
                if (projectOwnScoringBean.getUserRole() == ProjectRoleCode.RTL) {
                    projectInfo.put("projectName", null);
                    projectInfo.put("projectRole", null);
                } else {
                    projectInfo.put("projectName", projectOwnScoringBean.getCurrentProjectName());
                    if (projectOwnScoringBean.getUserRole() == ProjectRoleCode.FO) {
                        projectInfo.put("projectRole", ProjectRoleCode.FO_NAME);
                    } else if (projectOwnScoringBean.getUserRole() == ProjectRoleCode.FGL) {
                        projectInfo.put("projectRole", ProjectRoleCode.FGL_NAME);
                    } else if (projectOwnScoringBean.getUserRole() == ProjectRoleCode.SPDM) {
                        projectInfo.put("projectRole", ProjectRoleCode.SPDM_NAME);
                    } else if (projectOwnScoringBean.getUserRole() == ProjectRoleCode.ARC) {
                        projectInfo.put("projectRole", ProjectRoleCode.ARC_NAME);
                    }
                }
                projects.add(projectInfo);
            }
        } else {
            List<Integer> projectNameFromProjectMembers = projectMemberService.findProjectNameFromProjectMember(userId);
            if (projectNameFromProjectMembers != null && projectNameFromProjectMembers.size() > 0&&userService.findUserFromUserId(userId).getUserRole()!=SysRoleCode.RTLCode) {
                for (int i = 0; i < projectNameFromProjectMembers.size(); i++) {
                    HashMap<String, Object> projectInfo = new HashMap<>();
                    Integer projectId = projectNameFromProjectMembers.get(i);
                    if (projectId.equals(SpecialRecordCode.BOSS_PROJECT_ID)
                    ) {
                        continue;
                    }
                    ProjectNameBean projectNameBean = projectNameService.findProjectNameFromId(projectId);
                    Integer projectRoleForUser = projectMemberService.findProjectNameRole(projectId, userId);
                    String projectRole = CommonMethod.transformProjectRole(projectRoleForUser);
                    projectInfo.put("projectRole", projectRole);
                    ProjectOwnScoringBean projectOwnScoringBean = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndCurrentProjectAndScoreTime(userId, projectNameService.findProjectNameFromId(projectId).getProjectName(), scoreTime);
                    String workContent;
                    long score;
                    if (projectOwnScoringBean != null) {
                        workContent = projectOwnScoringBean.getWorkContent();
                        score = projectOwnScoringBean.getScore();
                    } else {
                        workContent = null;
                        if(userService.findUserFromUserId(userId).getUserRole()==SysRoleCode.FOCode) {
                            score=0;
                        }else {
                            score = ARCorSPDMorFGLScoring(projectNameBean.getProjectName());
                        }
                    }
                    if (projectRole.equals(ProjectRoleCode.FO_NAME)) {
                        Integer weights = scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.FO_SCORING_MYSELF);
                        projectInfo.put("weights", weights);
                    } else if (projectRole.equals(ProjectRoleCode.FGL_NAME)) {
                        Integer weights = scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.FGL_SCORING_MYSELF);
                        projectInfo.put("weights", weights);
                    } else if (projectRole.equals(ProjectRoleCode.SPDM_NAME) || projectRole.equals(ProjectRoleCode.ARC_NAME)) {
                        Integer weights = scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.SPDM_OR_ARC_SCORING_MYSELF);
                        projectInfo.put("weights", weights);
                    } else if (projectRole.equals(ProjectRoleCode.RTL_NAME)) {
                        Integer weights = scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.GROUP_MEMBER_AVG);
                        projectInfo.put("weights", weights);
                    } else {
                        projectInfo.put("weights", null);
                    }
                    projectInfo.put("projectName", projectNameBean.getProjectName());
                    projectInfo.put("workContent", workContent);
                    projectInfo.put("score", score);
                    projects.add(projectInfo);
                }
            } else {
                //组长
                HashMap<String, Object> projectInfo = new HashMap<>();
                projectInfo.put("projectRole", null);
                projectInfo.put("projectName", null);
                List<ProjectOwnScoringBean> projectOwnScoringBean = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndScoreTime(userId, scoreTime);
                if (projectOwnScoringBean != null && projectOwnScoringBean.size() > 0) {
                    projectInfo.put("workContent", projectOwnScoringBean.get(0).getWorkContent());
                    projectInfo.put("score", projectOwnScoringBean.get(0).getScore());
                } else {
                    projectInfo.put("workContent", null);
                    long rtlScore = RTLScoreMyself(userId,scoreTime);
                    if (rtlScore != 0) {
                        projectInfo.put("score", rtlScore);
                    } else {
                        projectInfo.put("score", 0);
                    }
                }
                if (user.getUserRole() == SysRoleCode.SPDMCode || user.getUserRole() == SysRoleCode.ARCCode) {
                    Integer soreWeight = scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.SPDM_OR_ARC_SCORING_MYSELF);
                    projectInfo.put("weights", soreWeight);
                } else if (user.getUserRole() == SysRoleCode.RTLCode) {
                    Integer soreWeight = scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.GROUP_MEMBER_AVG);
                    projectInfo.put("weights", soreWeight);
                } else if (user.getUserRole() == SysRoleCode.FGLCode) {
                    Integer soreWeight = scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.FGL_SCORING_MYSELF);
                    projectInfo.put("weights", soreWeight);
                } else if (user.getUserRole() == SysRoleCode.FOCode) {
                    Integer soreWeight = scoreWeightService.findScoreValueFromWeightName(ScoreWeightNameCode.FO_SCORING_MYSELF);
                    projectInfo.put("weights", soreWeight);
                }
                projects.add(projectInfo);
            }
        }
        userInfo.put("assessMouth", scoreTime);
        userInfo.put("project", projects);
        userInfo.put("userId", userId);
        userInfo.put("userName", userName);
        userInfoList.add(userInfo);
        details.put("userInfo", userInfoList);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    /*
    提交数据
     */
    @Transactional
    @ResponseBody
    @PostMapping("/submit")
    public Map<String, Object> postInformation(@RequestBody JSONObject jsonParam) {
        urlLogInfo("/yourself/submit");
        logger.info(jsonParam.toJSONString());
        String cookie;
        String loginUserId;
        JSONArray userInfo;
        String message = "";
        HashMap<String, Object> details = new HashMap<>();
        try {
            cookie = jsonParam.getString("cookie");
            loginUserId = checkCookieValid(cookie);
            userInfo = jsonParam.getJSONArray("userInfo");
        } catch (Exception e) {
            e.printStackTrace();
            throw new ParamInvalidException("userInfo " + e.getMessage());
        }
        //检查是否有提交权限
        UserBean userBean = userService.findUserFromUserId(loginUserId);
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.PerformanceWriteCode);
        //循环userInfo数组数据
        for (int i = 0; i < userInfo.size(); i++) {
            JSONObject jsonObject = (JSONObject) userInfo.get(i);
            String scoreTime = jsonObject.getString("assessMouth");//考核月份
            if (CommonMethod.judgeStringIsNull(scoreTime).equals("") || !CommonMethod.isValidDate(scoreTime, CommonMethod.TIME_FORMAT_yyyy_MM)) {
                throw new ParamInvalidException("assessMouth");
            }
            scoreTime.trim();
            List<ScoredStateModeTimeBean> scoredStateModeTimeBeans = scoredStateModeTimeService.findScoredStateModeTimeBeanFromIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
            checkScoreTimeValid(scoreTime, scoredStateModeTimeBeans);
            String userId = jsonObject.getString("userId");//工号
            if (userId.equals(SpecialRecordCode.ADMIN_USERID)) {
                throw new ParamInvalidException("userId is " + SpecialRecordCode.ADMIN_USERID);
            }
            String userName = jsonObject.getString("userName");//姓名
            JSONArray project = jsonObject.getJSONArray("project");
            //循环project数组
            for (int j = 0; j < project.size(); j++) {
                JSONObject projectOne = (JSONObject) project.get(j);
                String scoreString = projectOne.getString("score");
                if(Integer.parseInt(scoreString)==0){
                    message = "分数不能0！";
                    details.put("result", false);
                    Map<String, Object> map=new HashMap<>();
                    map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, message, details);
                    return map;
                }
                if (scoreString != null) {
                    Integer code=userService.findUserFromUserId(userId).getUserRole();
                    Map<String, Object> map=new HashMap<>();
                    if (code== SysRoleCode.RTLCode) {
                        if (Integer.parseInt(scoreString) == RTLScoreMyself(userId, scoreTime)) {
                        } else {
                            message = "分数不能修改！";
                            details.put("result", false);
                            map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, message, details);
                            return map;
                        }
                    }else if(code== SysRoleCode.SPDMCode||code==SysRoleCode.ARCCode||code==SysRoleCode.FGLCode){
                        if (Integer.parseInt(scoreString) == ARCorSPDMorFGLScoring(projectOne.getString("projectName"))) {
                        } else {
                            message = "分数不能修改！";
                            details.put("result", false);
                            map= CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, message, details);
                            return map;
                        }
                    }
                }
                String workContent = projectOne.getString("workContent");
                Integer scoreInteger;
                if (workContent == null || workContent.equals("")) {
                    message = "工作内容不能为空！";
                    details.put("result", false);
                    Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.PARAM_INVALID, message, details);
                    return map;
                }
                if (scoreString != "" && scoreString != null) {
                    scoreInteger = Integer.parseInt(scoreString);
                    if (scoreInteger > 100 || scoreInteger < 0) {
                        message = "自评分数必须在0到100之间！";
                        details.put("result", false);
                        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.PARAM_INVALID, message, details);
                        return map;
                    }
                } else {
                    message = "自评分数不能为空！";
                    details.put("result", false);
                    Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.PARAM_INVALID, message, details);
                    return map;
                }
                String weights = projectOne.getString("weights");
                String projectName = projectOne.getString("projectName");
                ProjectOwnScoringBean projectOwnScoringBean = new ProjectOwnScoringBean();
                if (scoreTime.equals(CommonMethod.getLastMonth())) {
                    ProjectNameBean projectNameBean = projectNameService.findProjectNameFromName(projectName);
                    if (scoreTime.equals(CommonMethod.getLastMonth())) {
                        Integer projectId;
                        if (projectNameBean != null) {
                            projectId = projectNameBean.getId();
                            if (projectId != null) {
                                projectOwnScoringBean.setCurrentProjectName(projectName);//考核项目Id
                                Integer projectRoleForUser = projectMemberService.findProjectNameRole(projectId, userId);
                                projectOwnScoringBean.setUserRole(projectRoleForUser);
                            } else {
                                throw new ParamInvalidException("projectId");
                            }
                        } else if (userBean.getUserRole() == SysRoleCode.RTLCode) {
                            projectOwnScoringBean.setUserRole(ProjectRoleCode.RTL);
                            projectOwnScoringBean.setCurrentProjectName("组内自评均值");//考核项目Id
                        } else {
                            projectOwnScoringBean.setUserRole(null);
                            projectOwnScoringBean.setCurrentProjectName(null);//考核项目Id
                        }
                    }
                }
                projectOwnScoringBean.setUserId(userId);//工号
                projectOwnScoringBean.setWeight(Integer.parseInt(weights));//工号
                projectOwnScoringBean.setWorkContent(workContent);//项目工作内容
                projectOwnScoringBean.setScore(scoreInteger);//自评分数
                projectOwnScoringBean.setScoreTime(scoreTime);//考核月份
                int result = SpecialRecordCode.SQL_RESULT_FAIL;
                if (!scoreTime.equals(CommonMethod.getLastMonth())) {
                    ProjectOwnScoringBean OwnScoringBean = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndCurrentProjectAndScoreTime(userId, projectName, scoreTime);
                    if (OwnScoringBean != null) {
                        projectOwnScoringBean.setId(OwnScoringBean.getId());
                        projectOwnScoringBean.setWeight(OwnScoringBean.getWeight());
                        result = projectOwnScoringService.updateProjectOwnScoring(projectOwnScoringBean);
                        message = "修改成功";
                    }
                } else {
                    ProjectOwnScoringBean OwnScoringBean = new ProjectOwnScoringBean();
                    if (userService.findUserFromUserId(loginUserId).getUserRole() == SysRoleCode.RTLCode) {
                        OwnScoringBean = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndCurrentProjectAndScoreTime(userId, "组内自评均值", scoreTime);
                    } else {
                        OwnScoringBean = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndCurrentProjectAndScoreTime(userId, projectName, scoreTime);
                    }
                    if (OwnScoringBean == null) {
                        result = projectOwnScoringService.addProjectOwnScoring(projectOwnScoringBean);
                        message = "提交成功";
                    } else {
                        projectOwnScoringBean.setId(OwnScoringBean.getId());
                        projectOwnScoringBean.setWeight(OwnScoringBean.getWeight());
                        result = projectOwnScoringService.updateProjectOwnScoring(projectOwnScoringBean);
                        message = "修改成功";
                    }
                }
                if (result == SpecialRecordCode.SQL_RESULT_FAIL) {
                    throw new DatabaseOperationFailedException();
                }
            }
        }
        boolean result = true;
        details.put("result", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }
}
