package com.byd.performance_main.base;

import com.byd.performance_main.model.*;
import com.byd.performance_main.service.*;
import com.byd.performance_utils.base.BaseCommonController;
import com.byd.performance_utils.code.*;
import com.byd.performance_utils.exception.*;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BaseController extends BaseCommonController {
    @Autowired
    protected UserService userService;
    @Autowired
    protected RoleService roleService;
    @Autowired
    protected PermissionNameService permissionNameService;
    @Autowired
    protected RolePermissionService rolePermissionService;
    @Autowired
    protected UserPermissionService userPermissionService;
    @Autowired
    protected GroupNameService groupNameService;
    @Autowired
    protected GroupMemberService groupMemberService;
    @Autowired
    protected ProjectNameService projectNameService;
    @Autowired
    protected ProjectMemberService projectMemberService;
    @Autowired
    protected ScoreWeightService scoreWeightService;
    @Autowired
    protected GroupScoringMemberService groupScoringMemberService;
    @Autowired
    protected ProjectScoringMemberService projectScoringMemberService;
    @Autowired
    protected ProjectOwnScoringService projectOwnScoringService;
    @Autowired
    protected ScoredStateModeTimeService scoredStateModeTimeService;
    @Autowired
    protected ScoreHistoryService scoreHistoryService;
    @Autowired
    protected SysUserRoleService sysUserRoleService;
    @Autowired
    protected SysRoleMenuService sysRoleMenuService;
    @Autowired
    protected ScheduleService scheduleService;

    /**
     * 验证cookie有效性及权限是否足够
     * return userId
     */
    protected String checkCookieAndRole(String cookie, int permission_code) {
        String userId = checkCookieValid(cookie);
        checkPermission(userId, permission_code);
        return userId;
    }

    /**
     * 验证cookie是否有效
     * return userId
     */
    protected String checkCookieValid(String cookie) {
        if (cookie == null || cookie.isEmpty())
            throw new CookieInvalidException();
        String userId = userService.findUserIdFromCookie(cookie);
        if (userId == null || userId.isEmpty())
            throw new CookieInvalidException();
        return userId;
    }

    /**
     * 验证用户是否有对应的权限，没有就报异常
     */
    protected void checkPermission(String userId, int permission_code) {
        boolean rolePermission = checkPermissionContent(userId, permission_code);

        if (!rolePermission) {
            throw new PermissionDeniedException();
        }
    }

    /**
     * 判断用户是否有对应权限
     */
    protected boolean checkPermissionContent(String userId, int permission_code) {

        UserBean userBean = userService.findUserFromUserId(userId);
        Integer userRole = userBean.getUserRole();
        if (userRole == null) {
            userRole = PermissionRoleCode.ROLE_GUEST_LEVEL;
        }

        //获取当前用户的权限，有没有进行修改
        UserPermissionBean userPermissionBean = userPermissionService.findOwnUserPermissionBeanFromUserId(userId);
        Integer userAllPermissionCode = 0;
        if (userPermissionBean != null) {
            userAllPermissionCode = userPermissionBean.getPermissionAllCode();
        }

        boolean isModifiedUserPermission = false;
        if (userAllPermissionCode != 0) {
            //判断是否修改了用户对应的权限
            isModifiedUserPermission = CommonMethod.verifyUserPermissionIsModified(userAllPermissionCode, permission_code);
        }

        //获取当前用户的对应角色的权限
        RolePermissionBean rolePermissionBean = rolePermissionService.findOwnRolePermissionBeanFromRole(CommonMethod.checkIsBadRoleLevel(userRole));
        if (rolePermissionBean == null) {
            throw new PermissionDeniedException();
        }
        Integer roleAllPermission = rolePermissionBean.getPermissionAllCode();
        boolean rolePermission = CommonMethod.verifyPermissionState(roleAllPermission, permission_code);

        //如果该角色正好对相应权限做了修改，则将该权限的内容取反
        if (isModifiedUserPermission) {
            rolePermission = !rolePermission;
        }
        return rolePermission;
    }

    /**
     * 检测User数据表中是否已存在userId
     */
    protected void checkUserIdIsDuplicateFromUserBean(String userId) {
        if (userId == null || userId.isEmpty()) {
            throw new NecessaryParameterException();
        }

        UserBean userBeans = userService.findUserFromUserId(userId);
        if (userBeans != null) {
            throw new UserIdDuplicateException();
        }
    }

    /**
     * 检测UserPermission数据表中是否已存在userId
     */
    protected void checkUserIdIsDuplicateFromUserPermissionBean(String userId) {
        UserPermissionBean userPermissionBean = userPermissionService.findOwnUserPermissionBeanFromUserId(userId);
        if (userPermissionBean != null) {
            throw new UserIdDuplicateException();
        }
    }

    /**
     * 从权限标识码，获取对应的权限名称
     */
    protected String getPermissionName(int permission_code) {
        return permissionNameService.findPermissionNameFromCode(permission_code);
    }

    /**
     * 验证权重值修改是否满足基本要求
     * return ScoreWeightBean
     */
    protected ScoreWeightBean checkScoreWeightValid(int weightName, int scoreValue) {

        checkWeightNameValid(weightName);
        checkScoreValueValid(scoreValue);

        ScoreWeightBean scoreWeightBean = new ScoreWeightBean();
        scoreWeightBean.setWeightName(weightName);
        scoreWeightBean.setScoreValue(scoreValue);
        return scoreWeightBean;
    }

    /**
     * 验证权重名是否存在
     * return weightName
     */
    protected void checkWeightNameValid(int weightName) {
        if (!((weightName >= ScoreWeightNameCode.FO_SCORING_MYSELF) && (weightName <= ScoreWeightNameCode.RTL_SCORING_SPDM_OR_ARC))) {
            throw new ParamInvalidException();
        }
    }

    /*
    组长自评分数为组员自评分数均值
     */
    public long RTLScoreMyself(String userId, String scoreTime) {
        double totalScore = 0;
        Integer totalSum = 0;
        if (userService.findUserFromUserId(userId).getUserRole() == SysRoleCode.RTLCode) {
            List<Integer> allGroupName = groupMemberService.findGroupNameFromGroupMember(userId);
            if (allGroupName != null && allGroupName.size() > 0) {
                for (int a = 0; a < allGroupName.size(); a++) {
                    //所有组员
                    List<String> allGroupMember = groupMemberService.findGroupMemberFromGroupName(allGroupName.get(a));
                    for (int b = 0; b < allGroupMember.size(); b++) {
                        if (allGroupMember.get(b).equals(userId)) {
                            continue;
                        }
                        Integer scoreSum = 0;
                        double singleScore = 0;
                        List<ProjectOwnScoringBean> projectOwnScoringBeans = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndScoreTime(allGroupMember.get(b), scoreTime);
                        if (projectOwnScoringBeans != null && projectOwnScoringBeans.size() > 0) {
                            for (int c = 0; c < projectOwnScoringBeans.size(); c++) {
                                singleScore += projectOwnScoringBeans.get(c).getScore();
                                scoreSum++;
                            }
                            if (scoreSum != 0) {
                                singleScore /= scoreSum;
                            }
                            totalScore += singleScore;
                            totalSum++;
                        }
                    }
                }
                if (totalSum != 0) {
                    totalScore /= totalSum;
                    //结果值四舍五入
                    return Math.round(totalScore);
                } else {
                    return 0;
                }
            } else {
                return 0;
            }
        } else {
            return 0;
        }
    }

    /*
    ARC、SPDM、FGL自评分数计算公式
     */
    public long ARCorSPDMorFGLScoring(String projectName){
        //所有项目所有Schedule
        List<ScheduleBean> allScheduleBean=scheduleService.findAllScheduleFromProjectName(projectName);
        double score=0;
        int totalSum=0;
        if(allScheduleBean!=null&&allScheduleBean.size()>0){
            for (ScheduleBean schedule:allScheduleBean){
                score+=schedule.getCase_process()*100;
                totalSum++;
            }
        }else {
            score =0;
        }
        if(totalSum!=0) {
            return Math.round(score /totalSum);
        }else{
            return 0;
        }
    }

    /*
    检查是否有页面操作权限
     */
    public void checkModifyPermission(String userId,String menuCode) {
        Map<String, Object> details = new HashMap<>();
        UserBean userBean = userService.findUserFromUserId(userId);
        Integer loginUserRole = userBean.getUserRole();
        int isModified = sysUserRoleService.queryIsModifiedFromRoleIdAndBackupsModuleCode(loginUserRole,menuCode);
        if (isModified == 1) {

        } else {
            throw new PermissionDeniedException();
        }
    }



    /**
     * 验证权重的值是否有效
     * return weightName
     */
    protected void checkScoreValueValid(int scoreValue) {

        if (!((scoreValue >= 0) && (scoreValue <= 100))) {
            throw new ParamInvalidException();
        }
    }

    /**
     * 验证权重值修改是否满足基本要求
     * return ScoreWeightBean
     */
    protected ScoreWeightBean checkScoreWeightValid(ScoreWeightBean scoreWeightBean) {
        if (scoreWeightBean.getWeightName() == null || scoreWeightBean.getScoreValue() == null) {
            throw new ParamInvalidException();
        }
        return checkScoreWeightValid(scoreWeightBean.getWeightName(), scoreWeightBean.getScoreValue());
    }

    /**
     * 验证ScoreTime是否是有效的
     */
    protected void checkScoreTimeValid(String time, List<ScoredStateModeTimeBean> scoredStateModeTimeBeans) {
        if (scoredStateModeTimeBeans != null && scoredStateModeTimeBeans.size() == 1) {
            String scoreTime = scoredStateModeTimeBeans.get(0).getScoreTime();
            if (!scoreTime.equals(time)) {
                throw new ParamInvalidException("time is not equal ScoreTime, time ");
            }
        } else {
            throw new ParamInvalidException("ScoreTime not open, scoreTime ");
        }
    }

    /**
     * 计算人员最终分值
     */
    @Async
    @Transactional
    protected void finalCalculateScoreResult(String scoreTime) {
        List<UserBean> allUsers = userService.findAllUser();
        int result = SpecialRecordCode.SQL_RESULT_FAIL;
        //删除因为项目或小组因为人员变更等原因，产生的错误表格
        if (scoreTime.equals(CommonMethod.getLastMonth())) {
            //处理tb_score_history
            List<Integer> pastScoreHistoryBeanIds = new ArrayList<>();
            List<ScoreHistoryBean> scoreHistoryBeans = new ArrayList<>();
            for (int i = 0; i < allUsers.size(); i++) {
                UserBean userBean = allUsers.get(i);
                String userId = userBean.getUserId();
                if (userBean.getUserRole() == SysRoleCode.AdminCode || userBean.getUserRole() == SysRoleCode.BossCode) {
                    continue;
                }
                ScoreHistoryBean scoreHistoryBean = new ScoreHistoryBean();
                scoreHistoryBean.setUserId(userId);
                float projectScore = 0;
                int projectScoreNum = 0;
                int projectScoreWeight = 0;
                int currentProjectRole = ProjectRoleCode.UNKNOWN;
                List<ProjectScoringMemberBean> projectScoringMemberBeans = projectScoringMemberService.findProjectScoringMemberBeanFromUserIdAndScoreTime(userId, scoreTime);
                for (ProjectScoringMemberBean projectScoringMemberBean : projectScoringMemberBeans) {
                    projectScore += (float) projectScoringMemberBean.getScore();
                    projectScoreNum++;
                }
                if (projectScoreNum != 0) {
                    projectScoreWeight = projectScoringMemberBeans.get(0).getWeight();
                    projectScore /= projectScoreNum;
                    currentProjectRole = projectScoringMemberBeans.get(0).getCurrentRole();
                } else if (scoreTime.equals(CommonMethod.getLastMonth())) {
                    List<Integer> projectNames = projectMemberService.findProjectNameFromProjectMember(userId);
                    if (projectNames.size() > 0) {
                        currentProjectRole = projectMemberService.findProjectNameRole(projectNames.get(0), userId);
                    }
                }
                scoreHistoryBean.setProjectScore((int) projectScore);
                scoreHistoryBean.setProjectScoreWeight(projectScoreWeight);
                float groupScore = 0;
                int groupScoreWeight = 0;
                int groupScoreNum = 0;
                List<GroupScoringMemberBean> groupScoringMemberBeans=groupScoringMemberService.findGroupScoringMemberBeansFromUserIdAndTime(userId,scoreTime);
                for(GroupScoringMemberBean groupScoringMemberBean : groupScoringMemberBeans){
                    groupScore+=(float)groupScoringMemberBean.getScore();
                    groupScoreNum++;
                }
                if(groupScoreNum!=0){
                    groupScore/=groupScoreNum;
                    groupScoreWeight=groupScoringMemberBeans.get(0).getWeight();
                }
                scoreHistoryBean.setGroupScore((int) groupScore);
                scoreHistoryBean.setGroupScoreWeight(groupScoreWeight);
                float projectOwnScore = 0;
                int projectOwnScoreNum = 0;
                int projectOwnScoreWeight = 0;
                List<ProjectOwnScoringBean> projectOwnScoringBeans = projectOwnScoringService.findProjectOwnScoringBeanFromUserIdAndScoreTime(userId, scoreTime);
                for (ProjectOwnScoringBean projectOwnScoringBean : projectOwnScoringBeans) {
                    projectOwnScore += (float) projectOwnScoringBean.getScore();
                    projectOwnScoreNum++;
                }
                if (projectOwnScoreNum != 0) {
                    projectOwnScore /= projectOwnScoreNum;
                    projectOwnScoreWeight = projectOwnScoringBeans.get(0).getWeight();
                }
                scoreHistoryBean.setOwnScore((int) projectOwnScore);
                scoreHistoryBean.setOwnScoreWeight(projectOwnScoreWeight);
                int totalScore = (int) (groupScore * groupScoreWeight / 100
                        + projectScore * projectScoreWeight / 100
                        + projectOwnScore * projectOwnScoreWeight / 100);
                scoreHistoryBean.setTotalScore(totalScore);
                scoreHistoryBean.setScoreTime(scoreTime);
                ScoreHistoryBean pastScoreHistoryBean = scoreHistoryService.findScoreHistoryBeanFromUserIdAndScoreTime(userId, scoreTime);
                if (pastScoreHistoryBean != null) {
                    pastScoreHistoryBeanIds.add(pastScoreHistoryBean.getId());
                }
                scoreHistoryBeans.add(scoreHistoryBean);
            }
            List<Integer> temp_pastScoreHistoryBeanIds = new ArrayList<>();
            for (int i = 0; i < pastScoreHistoryBeanIds.size(); i++) {
                Integer id = pastScoreHistoryBeanIds.get(i);
                temp_pastScoreHistoryBeanIds.add(id);
                if (i % 50 == 0 || i == pastScoreHistoryBeanIds.size() - 1) {
                    result = scoreHistoryService.delManyScoreHistory(temp_pastScoreHistoryBeanIds);
                    if (result == SpecialRecordCode.SQL_RESULT_FAIL) {
                        logInfo("Added result: " + temp_pastScoreHistoryBeanIds.toString());
                    }
                    temp_pastScoreHistoryBeanIds = new ArrayList<>();
                }
            }
            List<ScoreHistoryBean> temp_scoreHistoryBeans = new ArrayList<>();
            for (int i = 0; i < scoreHistoryBeans.size(); i++) {
                ScoreHistoryBean scoreHistoryBean = scoreHistoryBeans.get(i);
                temp_scoreHistoryBeans.add(scoreHistoryBean);
                if (i % 50 == 0 || i == scoreHistoryBeans.size() - 1) {
                    result = scoreHistoryService.addManyScoreHistory(temp_scoreHistoryBeans);
                    if (result == SpecialRecordCode.SQL_RESULT_FAIL) {
                        logInfo("Added result: " + temp_scoreHistoryBeans.toString());
                    }
                    temp_scoreHistoryBeans = new ArrayList<>();
                }
            }
        }
    }
}
