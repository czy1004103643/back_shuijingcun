package com.byd.performance_main.control.using;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.GroupScoringMemberBean;
import com.byd.performance_main.model.ProjectOwnScoringBean;
import com.byd.performance_main.model.ProjectScoringMemberBean;
import com.byd.performance_main.model.ScoreHistoryBean;
import com.byd.performance_utils.code.*;
import com.byd.performance_utils.exception.ParamInvalidException;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/score-history")
public class GetScoreHistoryController extends BaseController {
    /*
    显示个人历史考核记录
     */
    @GetMapping(value = "/person-info")
    @ResponseBody
    public Map<String, Object> getPersonInfo(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/score-history/person-info");
        String userId = checkCookieValid(cookie);
        List<Integer> groupNameFromGroupMembers = groupMemberService.findGroupNameFromGroupMember(userId);
        Map<String, Object> details = new HashMap<>();
        ArrayList<Object> historyData = new ArrayList<>();
        List<ScoreHistoryBean> scoreHistoryBeans = scoreHistoryService.findScoreHistoryBeanFromUserId(userId);
        for (ScoreHistoryBean scoreHistoryBean : scoreHistoryBeans) {
            Map<String, Object> historyContent = new HashMap<>();
            String scoreTime = scoreHistoryBean.getScoreTime();
            historyContent.put("scoreTime", scoreTime);
            historyContent.put("userId", userId);
            historyContent.put("userName", userService.findUserFromUserId(userId).getUserName());
            Map<String, Object> groupContent = new HashMap<>();
            GroupScoringMemberBean groupScoringMemberBean = groupScoringMemberService.findGroupScoringMemberBeanFromUserIdAndTime(userId, scoreTime);
            if (groupScoringMemberBean != null) {
                groupContent.put("groupName", groupScoringMemberBean.getCurrentGroupName());
                if(userService.findUserFromUserId(groupScoringMemberBean.getRatingUserId())!=null) {
                    groupContent.put("groupLeader", userService.findUserFromUserId(groupScoringMemberBean.getRatingUserId()).getUserName());
                }else{
                    groupContent.put("groupLeader",null);
                }
                groupContent.put("groupScore", scoreHistoryBean.getGroupScore());
            }
            historyContent.put("groupData", groupContent);
            historyContent.put("groupWeight", scoreHistoryBean.getGroupScoreWeight());
            ArrayList<Object> projectData = new ArrayList<>();
            List<ProjectScoringMemberBean> projectScoringMemberBeans = projectScoringMemberService.findProjectScoringMemberBeanFromUserIdAndScoreTime(userId, scoreTime);
            for (ProjectScoringMemberBean projectScoringMemberBean : projectScoringMemberBeans) {
                Map<String, Object> projectContent = new HashMap<>();
                projectContent.put("projectName", projectScoringMemberBean.getCurrentProjectName());
                if(userService.findUserFromUserId(projectScoringMemberBean.getRatingUserId())!=null) {
                    projectContent.put("projectLeader", userService.findUserFromUserId(projectScoringMemberBean.getRatingUserId()).getUserName());
                }else {
                    projectContent.put("projectLeader",null);
                }
                projectContent.put("projectScore", projectScoringMemberBean.getScore());
                projectData.add(projectContent);
            }
            historyContent.put("projectData", projectData);
            if (projectScoringMemberBeans.size() > 0) {
                historyContent.put("projectWeight", projectScoringMemberBeans.get(0).getWeight());
            } else {
                historyContent.put("projectWeight", 0);
            }
            ArrayList<Object> ownScoreData = new ArrayList<>();
            List<ProjectOwnScoringBean> projectOwnScoringBeans = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndScoreTime(userId, scoreTime);
            for (ProjectOwnScoringBean projectOwnScoringBean : projectOwnScoringBeans) {
                Map<String, Object> ownScoreDataContent = new HashMap<>();
                String currentProjectName = projectOwnScoringBean.getCurrentProjectName();
                ownScoreDataContent.put("projectName", currentProjectName);
                ownScoreDataContent.put("ownScore", projectOwnScoringBean.getScore());
                ownScoreData.add(ownScoreDataContent);
                if (projectOwnScoringBean.getUserRole() != ProjectRoleCode.FGL) {
                    continue;
                }
            }
            if (projectOwnScoringBeans.size() > 0) {
                historyContent.put("ownWeight", projectOwnScoringBeans.get(0).getWeight());
            } else {
                historyContent.put("ownWeight", 0);
            }
            historyContent.put("ownScoreData", ownScoreData);
            historyContent.put("totalScore", scoreHistoryBean.getTotalScore());
            historyData.add(historyContent);
        }
        details.put("historyData", historyData);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }
}
