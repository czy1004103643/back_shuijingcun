package com.byd.performance_main.control.using;

import com.alibaba.druid.sql.ast.statement.SQLIfStatement;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.SysRoleMenuBean;
import com.byd.performance_main.model.SysUserRoleBean;
import com.byd.performance_main.model.UserBean;
import com.byd.performance_utils.code.PermissionRoleCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.code.SysMenuCode;
import com.byd.performance_utils.code.SysRoleCode;
import com.byd.performance_utils.exception.ParamInvalidException;
import com.byd.performance_utils.exception.RoleMenuException;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/Validation")
public class PermissionValidation extends BaseController {

    /*
    模块页面显示
     */
    @ResponseBody
    @GetMapping("/roleInfo")
    public Map<String, Object> roleValidation(@RequestParam("cookie") String cookie) {
        String userId = checkCookieValid(cookie);
        urlLogInfo("/Validation/roleInfo");
        List allRoleId = new ArrayList();
        Map<String, Object> details = new HashMap<>();
        if (userId != null && (!userId.isEmpty())) {
            //用户角色表
            UserBean userBean = userService.findUserFromUserId(userId);
            Integer userRoleCode = userBean.getUserRole();
            if (userRoleCode == null || userRoleCode.equals("")) {
                allRoleId.add(SysMenuCode.PersonalHomepageCode);
                details.put("role", allRoleId);
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "默认权限(用户新增加还没赋给权限角色)", details);
                return map;
                //默认权限
            } else {
                SysRoleMenuBean sysRoleMenuBean = sysRoleMenuService.querySysRoleMenuFromRoleId(userRoleCode);
                if (sysRoleMenuBean != null) {
                    String roleMenuCodeString = sysRoleMenuBean.getAllMenuCode();
                    try {
                        if (roleMenuCodeString.length() % 3 == 0) {
                            for (int start = 0; roleMenuCodeString.length() >= 3; start += 3) {
                                String str = roleMenuCodeString.substring(0, 3);
                                allRoleId.add(str);
                                roleMenuCodeString = roleMenuCodeString.substring(3);
                            }
                        } else {
                            throw new RoleMenuException("权限码格式不正确！");
                        }
                    } catch (Exception e) {

                        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, e.getMessage(), details);
                        return map;
                    }
                }
            }
        }
        details.put("role", allRoleId);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    /*
    获取页面操作权限信息
     */
    @ResponseBody
    @GetMapping("/Setting")
    public Map<String, Object> permissionValidationStetting(@RequestParam("cookie") String cookie) {
        urlLogInfo("/Validation/Setting");
        String userId = checkCookieValid(cookie);
        UserBean userBean = userService.findUserFromUserId(userId);
        Integer userRole = userBean.getUserRole();
        Map<String, Object> details = new HashMap<>();
        //admin
        HashMap moduleCodeHashAdmin = new HashMap();
        HashMap isModifiedHashAdmin = new HashMap();
        ArrayList moduleListAdmin = new ArrayList();
        //boss
        HashMap moduleCodeHashBoss = new HashMap();
        HashMap isModifiedHashBoss = new HashMap();
        ArrayList moduleListBoss = new ArrayList();
        //arc
        HashMap moduleCodeHashARC = new HashMap();
        HashMap isModifiedHashARC = new HashMap();
        ArrayList moduleListARC = new ArrayList();
        //spdm
        HashMap moduleCodeHashSPDM = new HashMap();
        HashMap isModifiedHashSPDM = new HashMap();
        ArrayList moduleListSPDM = new ArrayList();
        //fgl
        HashMap moduleCodeHashFGL = new HashMap();
        HashMap isModifiedHashFGL = new HashMap();
        ArrayList moduleListFGL = new ArrayList();
        //rtl
        HashMap moduleCodeHashRTL = new HashMap();
        HashMap isModifiedHashRTL = new HashMap();
        ArrayList moduleListRTL = new ArrayList();
        //fo
        HashMap moduleCodeHashFO = new HashMap();
        HashMap isModifiedHashFO = new HashMap();
        ArrayList moduleListFO = new ArrayList();
        //guest
        HashMap moduleCodeHashGuest = new HashMap();
        HashMap isModifiedHashGuest = new HashMap();
        ArrayList moduleListGuest = new ArrayList();
        //获取全部权限信息
        List<SysUserRoleBean> sysUserRoleBean = sysUserRoleService.allSysUserRole();
        //判断用户权限数据表记录是否为空
        if (sysUserRoleBean.size() >= 0) {
            for (int i = 0; i < sysUserRoleBean.size(); i++) {
                SysUserRoleBean UserRoleBean = sysUserRoleBean.get(i);
                List roleList = new ArrayList();
                String moduleCode = UserRoleBean.getModuleCode();//菜单码
                Integer isModified = UserRoleBean.getIsModified();//操作权限标记
                Integer userNo = UserRoleBean.getRoleId();//权限角色码
                if (userNo == SysRoleCode.AdminCode) {
                    //管理员权限
                    if (moduleCode.equals(SysMenuCode.PersonalHomepageCode)) {
                        //个人页面
                        moduleCodeHashAdmin.put(SysMenuCode.PersonalHomepageEnglish, moduleCode);
                        isModifiedHashAdmin.put(SysMenuCode.PersonalHomepageEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PerformanceWriteCode)) {
                        //绩效填写页面
                        moduleCodeHashAdmin.put(SysMenuCode.PerformanceWriteEnglish, moduleCode);
                        isModifiedHashAdmin.put(SysMenuCode.PerformanceWriteEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupAssessmentCode)) {
                        //小组考核页面
                        moduleCodeHashAdmin.put(SysMenuCode.GroupAssessmentEnglish, moduleCode);
                        isModifiedHashAdmin.put(SysMenuCode.GroupAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectAssessmentCode)) {
                        //项目考核页面
                        moduleCodeHashAdmin.put(SysMenuCode.ProjectAssessmentEnglish, moduleCode);
                        isModifiedHashAdmin.put(SysMenuCode.ProjectAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupManagementCode)) {
                        //小组管理页面
                        moduleCodeHashAdmin.put(SysMenuCode.GroupManagementEnglish, moduleCode);
                        isModifiedHashAdmin.put(SysMenuCode.GroupManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectManagementCode)) {
                        //项目管理页面
                        moduleCodeHashAdmin.put(SysMenuCode.ProjectManagementEnglish, moduleCode);
                        isModifiedHashAdmin.put(SysMenuCode.ProjectManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.UserManagementCode)) {
                        //用户管理页面
                        moduleCodeHashAdmin.put(SysMenuCode.UserManagementEnglish, moduleCode);
                        isModifiedHashAdmin.put(SysMenuCode.UserManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PermissionManagementCode)) {
                        //权限管理页面
                        moduleCodeHashAdmin.put(SysMenuCode.PermissionManagementEnglish, moduleCode);
                        isModifiedHashAdmin.put(SysMenuCode.PermissionManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.AssessmentControlCode)) {
                        moduleCodeHashAdmin.put(SysMenuCode.AssessmentControlEnglish, moduleCode);
                        isModifiedHashAdmin.put(SysMenuCode.AssessmentControlEnglish, isModified);
                    }else if (moduleCode.equals(SysMenuCode.ProjectScheduleCode)) {
                        moduleCodeHashAdmin.put(SysMenuCode.ProjectScheduleEnglish, moduleCode);
                        isModifiedHashAdmin.put(SysMenuCode.ProjectScheduleEnglish, isModified);
                    }
                } else if (userNo == SysRoleCode.BossCode) {
                    if (moduleCode.equals(SysMenuCode.PersonalHomepageCode)) {
                        //个人页面
                        moduleCodeHashBoss.put(SysMenuCode.PersonalHomepageEnglish, moduleCode);
                        isModifiedHashBoss.put(SysMenuCode.PersonalHomepageEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PerformanceWriteCode)) {
                        //绩效填写页面
                        moduleCodeHashBoss.put(SysMenuCode.PerformanceWriteEnglish, moduleCode);
                        isModifiedHashBoss.put(SysMenuCode.PerformanceWriteEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupAssessmentCode)) {
                        //小组考核页面
                        moduleCodeHashBoss.put(SysMenuCode.GroupAssessmentEnglish, moduleCode);
                        isModifiedHashBoss.put(SysMenuCode.GroupAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectAssessmentCode)) {
                        //项目考核页面
                        moduleCodeHashBoss.put(SysMenuCode.ProjectAssessmentEnglish, moduleCode);
                        isModifiedHashBoss.put(SysMenuCode.ProjectAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupManagementCode)) {
                        //小组管理页面
                        moduleCodeHashBoss.put(SysMenuCode.GroupManagementEnglish, moduleCode);
                        isModifiedHashBoss.put(SysMenuCode.GroupManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectManagementCode)) {
                        //项目管理页面
                        moduleCodeHashBoss.put(SysMenuCode.ProjectManagementEnglish, moduleCode);
                        isModifiedHashBoss.put(SysMenuCode.ProjectManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.UserManagementCode)) {
                        //用户管理页面
                        moduleCodeHashBoss.put(SysMenuCode.UserManagementEnglish, moduleCode);
                        isModifiedHashBoss.put(SysMenuCode.UserManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PermissionManagementCode)) {
                        //权限管理页面
                        moduleCodeHashBoss.put(SysMenuCode.PermissionManagementEnglish, moduleCode);
                        isModifiedHashBoss.put(SysMenuCode.PermissionManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.AssessmentControlCode)) {
                        moduleCodeHashBoss.put(SysMenuCode.AssessmentControlEnglish, moduleCode);
                        isModifiedHashBoss.put(SysMenuCode.AssessmentControlEnglish, isModified);
                    }else if (moduleCode.equals(SysMenuCode.ProjectScheduleCode)) {
                        moduleCodeHashBoss.put(SysMenuCode.ProjectScheduleEnglish, moduleCode);
                        isModifiedHashBoss.put(SysMenuCode.ProjectScheduleEnglish, isModified);
                    }
                } else if (userNo == SysRoleCode.ARCCode) {
                    if (moduleCode.equals(SysMenuCode.PersonalHomepageCode)) {
                        //个人页面
                        moduleCodeHashARC.put(SysMenuCode.PersonalHomepageEnglish, moduleCode);
                        isModifiedHashARC.put(SysMenuCode.PersonalHomepageEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PerformanceWriteCode)) {
                        //绩效填写页面
                        moduleCodeHashARC.put(SysMenuCode.PerformanceWriteEnglish, moduleCode);
                        isModifiedHashARC.put(SysMenuCode.PerformanceWriteEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupAssessmentCode)) {
                        //小组考核页面
                        moduleCodeHashARC.put(SysMenuCode.GroupAssessmentEnglish, moduleCode);
                        isModifiedHashARC.put(SysMenuCode.GroupAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectAssessmentCode)) {
                        //项目考核页面
                        moduleCodeHashARC.put(SysMenuCode.ProjectAssessmentEnglish, moduleCode);
                        isModifiedHashARC.put(SysMenuCode.ProjectAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupManagementCode)) {
                        //小组管理页面
                        moduleCodeHashARC.put(SysMenuCode.GroupManagementEnglish, moduleCode);
                        isModifiedHashARC.put(SysMenuCode.GroupManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectManagementCode)) {
                        //项目管理页面
                        moduleCodeHashARC.put(SysMenuCode.ProjectManagementEnglish, moduleCode);
                        isModifiedHashARC.put(SysMenuCode.ProjectManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.UserManagementCode)) {
                        //用户管理页面
                        moduleCodeHashARC.put(SysMenuCode.UserManagementEnglish, moduleCode);
                        isModifiedHashARC.put(SysMenuCode.UserManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PermissionManagementCode)) {
                        //权限管理页面
                        moduleCodeHashARC.put(SysMenuCode.PermissionManagementEnglish, moduleCode);
                        isModifiedHashARC.put(SysMenuCode.PermissionManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.AssessmentControlCode)) {
                        moduleCodeHashARC.put(SysMenuCode.AssessmentControlEnglish, moduleCode);
                        isModifiedHashARC.put(SysMenuCode.AssessmentControlEnglish, isModified);
                    }else if (moduleCode.equals(SysMenuCode.ProjectScheduleCode)) {
                        moduleCodeHashARC.put(SysMenuCode.ProjectScheduleEnglish, moduleCode);
                        isModifiedHashARC.put(SysMenuCode.ProjectScheduleEnglish, isModified);
                    }
                } else if (userNo == SysRoleCode.SPDMCode) {
                    if (moduleCode.equals(SysMenuCode.PersonalHomepageCode)) {
                        //个人页面
                        moduleCodeHashSPDM.put(SysMenuCode.PersonalHomepageEnglish, moduleCode);
                        isModifiedHashSPDM.put(SysMenuCode.PersonalHomepageEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PerformanceWriteCode)) {
                        //绩效填写页面
                        moduleCodeHashSPDM.put(SysMenuCode.PerformanceWriteEnglish, moduleCode);
                        isModifiedHashSPDM.put(SysMenuCode.PerformanceWriteEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupAssessmentCode)) {
                        //小组考核页面
                        moduleCodeHashSPDM.put(SysMenuCode.GroupAssessmentEnglish, moduleCode);
                        isModifiedHashSPDM.put(SysMenuCode.GroupAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectAssessmentCode)) {
                        //项目考核页面
                        moduleCodeHashSPDM.put(SysMenuCode.ProjectAssessmentEnglish, moduleCode);
                        isModifiedHashSPDM.put(SysMenuCode.ProjectAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupManagementCode)) {
                        //小组管理页面
                        moduleCodeHashSPDM.put(SysMenuCode.GroupManagementEnglish, moduleCode);
                        isModifiedHashSPDM.put(SysMenuCode.GroupManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectManagementCode)) {
                        //项目管理页面
                        moduleCodeHashSPDM.put(SysMenuCode.ProjectManagementEnglish, moduleCode);
                        isModifiedHashSPDM.put(SysMenuCode.ProjectManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.UserManagementCode)) {
                        //用户管理页面
                        moduleCodeHashSPDM.put(SysMenuCode.UserManagementEnglish, moduleCode);
                        isModifiedHashSPDM.put(SysMenuCode.UserManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PermissionManagementCode)) {
                        //权限管理页面
                        moduleCodeHashSPDM.put(SysMenuCode.PermissionManagementEnglish, moduleCode);
                        isModifiedHashSPDM.put(SysMenuCode.PermissionManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.AssessmentControlCode)) {
                        moduleCodeHashSPDM.put(SysMenuCode.AssessmentControlEnglish, moduleCode);
                        isModifiedHashSPDM.put(SysMenuCode.AssessmentControlEnglish, isModified);
                    }else if (moduleCode.equals(SysMenuCode.ProjectScheduleCode)) {
                        moduleCodeHashSPDM.put(SysMenuCode.ProjectScheduleEnglish, moduleCode);
                        isModifiedHashSPDM.put(SysMenuCode.ProjectScheduleEnglish, isModified);
                    }
                } else if (userNo == SysRoleCode.FGLCode) {
                    if (moduleCode.equals(SysMenuCode.PersonalHomepageCode)) {
                        //个人页面
                        moduleCodeHashFGL.put(SysMenuCode.PersonalHomepageEnglish, moduleCode);
                        isModifiedHashFGL.put(SysMenuCode.PersonalHomepageEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PerformanceWriteCode)) {
                        //绩效填写页面
                        moduleCodeHashFGL.put(SysMenuCode.PerformanceWriteEnglish, moduleCode);
                        isModifiedHashFGL.put(SysMenuCode.PerformanceWriteEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupAssessmentCode)) {
                        //小组考核页面
                        moduleCodeHashFGL.put(SysMenuCode.GroupAssessmentEnglish, moduleCode);
                        isModifiedHashFGL.put(SysMenuCode.GroupAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectAssessmentCode)) {
                        //项目考核页面
                        moduleCodeHashFGL.put(SysMenuCode.ProjectAssessmentEnglish, moduleCode);
                        isModifiedHashFGL.put(SysMenuCode.ProjectAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupManagementCode)) {
                        //小组管理页面
                        moduleCodeHashFGL.put(SysMenuCode.GroupManagementEnglish, moduleCode);
                        isModifiedHashFGL.put(SysMenuCode.GroupManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectManagementCode)) {
                        //项目管理页面
                        moduleCodeHashFGL.put(SysMenuCode.ProjectManagementEnglish, moduleCode);
                        isModifiedHashFGL.put(SysMenuCode.ProjectManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.UserManagementCode)) {
                        //用户管理页面
                        moduleCodeHashFGL.put(SysMenuCode.UserManagementEnglish, moduleCode);
                        isModifiedHashFGL.put(SysMenuCode.UserManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PermissionManagementCode)) {
                        //权限管理页面
                        moduleCodeHashFGL.put(SysMenuCode.PermissionManagementEnglish, moduleCode);
                        isModifiedHashFGL.put(SysMenuCode.PermissionManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.AssessmentControlCode)) {
                        moduleCodeHashFGL.put(SysMenuCode.AssessmentControlEnglish, moduleCode);
                        isModifiedHashFGL.put(SysMenuCode.AssessmentControlEnglish, isModified);
                    }else if (moduleCode.equals(SysMenuCode.ProjectScheduleCode)) {
                        moduleCodeHashFGL.put(SysMenuCode.ProjectScheduleEnglish, moduleCode);
                        isModifiedHashFGL.put(SysMenuCode.ProjectScheduleEnglish, isModified);
                    }
                } else if (userNo == SysRoleCode.RTLCode) {
                    if (moduleCode.equals(SysMenuCode.PersonalHomepageCode)) {
                        //个人页面
                        moduleCodeHashRTL.put(SysMenuCode.PersonalHomepageEnglish, moduleCode);
                        isModifiedHashRTL.put(SysMenuCode.PersonalHomepageEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PerformanceWriteCode)) {
                        //绩效填写页面
                        moduleCodeHashRTL.put(SysMenuCode.PerformanceWriteEnglish, moduleCode);
                        isModifiedHashRTL.put(SysMenuCode.PerformanceWriteEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupAssessmentCode)) {
                        //小组考核页面
                        moduleCodeHashRTL.put(SysMenuCode.GroupAssessmentEnglish, moduleCode);
                        isModifiedHashRTL.put(SysMenuCode.GroupAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectAssessmentCode)) {
                        //项目考核页面
                        moduleCodeHashRTL.put(SysMenuCode.ProjectAssessmentEnglish, moduleCode);
                        isModifiedHashRTL.put(SysMenuCode.ProjectAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupManagementCode)) {
                        //小组管理页面
                        moduleCodeHashRTL.put(SysMenuCode.GroupManagementEnglish, moduleCode);
                        isModifiedHashRTL.put(SysMenuCode.GroupManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectManagementCode)) {
                        //项目管理页面
                        moduleCodeHashRTL.put(SysMenuCode.ProjectManagementEnglish, moduleCode);
                        isModifiedHashRTL.put(SysMenuCode.ProjectManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.UserManagementCode)) {
                        //用户管理页面
                        moduleCodeHashRTL.put(SysMenuCode.UserManagementEnglish, moduleCode);
                        isModifiedHashRTL.put(SysMenuCode.UserManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PermissionManagementCode)) {
                        //权限管理页面
                        moduleCodeHashRTL.put(SysMenuCode.PermissionManagementEnglish, moduleCode);
                        isModifiedHashRTL.put(SysMenuCode.PermissionManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.AssessmentControlCode)) {
                        moduleCodeHashRTL.put(SysMenuCode.AssessmentControlEnglish, moduleCode);
                        isModifiedHashRTL.put(SysMenuCode.AssessmentControlEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectScheduleCode)) {
                        moduleCodeHashRTL.put(SysMenuCode.ProjectScheduleEnglish, moduleCode);
                        isModifiedHashRTL.put(SysMenuCode.ProjectScheduleEnglish, isModified);
                    }
                } else if (userNo == SysRoleCode.FOCode) {
                    if (moduleCode.equals(SysMenuCode.PersonalHomepageCode)) {
                        //个人页面
                        moduleCodeHashFO.put(SysMenuCode.PersonalHomepageEnglish, moduleCode);
                        isModifiedHashFO.put(SysMenuCode.PersonalHomepageEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PerformanceWriteCode)) {
                        //绩效填写页面
                        moduleCodeHashFO.put(SysMenuCode.PerformanceWriteEnglish, moduleCode);
                        isModifiedHashFO.put(SysMenuCode.PerformanceWriteEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupAssessmentCode)) {
                        //小组考核页面
                        moduleCodeHashFO.put(SysMenuCode.GroupAssessmentEnglish, moduleCode);
                        isModifiedHashFO.put(SysMenuCode.GroupAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectAssessmentCode)) {
                        //项目考核页面
                        moduleCodeHashFO.put(SysMenuCode.ProjectAssessmentEnglish, moduleCode);
                        isModifiedHashFO.put(SysMenuCode.ProjectAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupManagementCode)) {
                        //小组管理页面
                        moduleCodeHashFO.put(SysMenuCode.GroupManagementEnglish, moduleCode);
                        isModifiedHashFO.put(SysMenuCode.GroupManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectManagementCode)) {
                        //项目管理页面
                        moduleCodeHashFO.put(SysMenuCode.ProjectManagementEnglish, moduleCode);
                        isModifiedHashFO.put(SysMenuCode.ProjectManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.UserManagementCode)) {
                        //用户管理页面
                        moduleCodeHashFO.put(SysMenuCode.UserManagementEnglish, moduleCode);
                        isModifiedHashFO.put(SysMenuCode.UserManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PermissionManagementCode)) {
                        //权限管理页面
                        moduleCodeHashFO.put(SysMenuCode.PermissionManagementEnglish, moduleCode);
                        isModifiedHashFO.put(SysMenuCode.PermissionManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.AssessmentControlCode)) {
                        moduleCodeHashFO.put(SysMenuCode.AssessmentControlEnglish, moduleCode);
                        isModifiedHashFO.put(SysMenuCode.AssessmentControlEnglish, isModified);
                    }else if (moduleCode.equals(SysMenuCode.ProjectScheduleCode)) {
                        moduleCodeHashFO.put(SysMenuCode.ProjectScheduleEnglish, moduleCode);
                        isModifiedHashFO.put(SysMenuCode.ProjectScheduleEnglish, isModified);
                    }
                } else if (userNo == SysRoleCode.GuestCode) {
                    if (moduleCode.equals(SysMenuCode.PersonalHomepageCode)) {
                        //个人页面
                        moduleCodeHashGuest.put(SysMenuCode.PersonalHomepageEnglish, moduleCode);
                        isModifiedHashGuest.put(SysMenuCode.PersonalHomepageEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PerformanceWriteCode)) {
                        //绩效填写页面
                        moduleCodeHashGuest.put(SysMenuCode.PerformanceWriteEnglish, moduleCode);
                        isModifiedHashGuest.put(SysMenuCode.PerformanceWriteEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupAssessmentCode)) {
                        //小组考核页面
                        moduleCodeHashGuest.put(SysMenuCode.GroupAssessmentEnglish, moduleCode);
                        isModifiedHashGuest.put(SysMenuCode.GroupAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectAssessmentCode)) {
                        //项目考核页面
                        moduleCodeHashGuest.put(SysMenuCode.ProjectAssessmentEnglish, moduleCode);
                        isModifiedHashGuest.put(SysMenuCode.ProjectAssessmentEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.GroupManagementCode)) {
                        //小组管理页面
                        moduleCodeHashGuest.put(SysMenuCode.GroupManagementEnglish, moduleCode);
                        isModifiedHashGuest.put(SysMenuCode.GroupManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.ProjectManagementCode)) {
                        //项目管理页面
                        moduleCodeHashGuest.put(SysMenuCode.ProjectManagementEnglish, moduleCode);
                        isModifiedHashGuest.put(SysMenuCode.ProjectManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.UserManagementCode)) {
                        //用户管理页面
                        moduleCodeHashGuest.put(SysMenuCode.UserManagementEnglish, moduleCode);
                        isModifiedHashGuest.put(SysMenuCode.UserManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.PermissionManagementCode)) {
                        //权限管理页面
                        moduleCodeHashGuest.put(SysMenuCode.PermissionManagementEnglish, moduleCode);
                        isModifiedHashGuest.put(SysMenuCode.PermissionManagementEnglish, isModified);
                    } else if (moduleCode.equals(SysMenuCode.AssessmentControlCode)) {
                        moduleCodeHashGuest.put(SysMenuCode.AssessmentControlEnglish, moduleCode);
                        isModifiedHashGuest.put(SysMenuCode.AssessmentControlEnglish, isModified);
                    }else if (moduleCode.equals(SysMenuCode.ProjectScheduleCode)) {
                        moduleCodeHashGuest.put(SysMenuCode.ProjectScheduleEnglish, moduleCode);
                        isModifiedHashGuest.put(SysMenuCode.ProjectScheduleEnglish, isModified);
                    }
                }
            }
            //管理员
            moduleListBoss.add(moduleCodeHashBoss);
            moduleListBoss.add(isModifiedHashBoss);
            //boss
            moduleListAdmin.add(moduleCodeHashAdmin);
            moduleListAdmin.add(isModifiedHashAdmin);
            //架构师
            moduleListARC.add(moduleCodeHashARC);
            moduleListARC.add(isModifiedHashARC);
            //项目经理
            moduleListSPDM.add(moduleCodeHashSPDM);
            moduleListSPDM.add(isModifiedHashSPDM);
            //功能组长
            moduleListFGL.add(moduleCodeHashFGL);
            moduleListFGL.add(isModifiedHashFGL);
            //小组组长
            moduleListRTL.add(moduleCodeHashRTL);
            moduleListRTL.add(isModifiedHashRTL);
            //项目成员
            moduleListFO.add(moduleCodeHashFO);
            moduleListFO.add(isModifiedHashFO);
            //游客
            moduleListGuest.add(moduleCodeHashGuest);
            moduleListGuest.add(isModifiedHashGuest);
            details.put(SysRoleCode.AdminName, moduleListAdmin);
            details.put(SysRoleCode.BossName, moduleListBoss);
            details.put(SysRoleCode.ARCName, moduleListARC);
            details.put(SysRoleCode.SPDMName, moduleListSPDM);
            details.put(SysRoleCode.FGLName, moduleListFGL);
            details.put(SysRoleCode.FOName, moduleListFO);
            details.put(SysRoleCode.GuestName, moduleListGuest);
            details.put(SysRoleCode.RTLName, moduleListRTL);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
            return map;
        } else {
            details.put("result", false);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, "没有数据！", details);
            return map;
        }
    }

    /*
    修改页面操作权限
     */
    @PostMapping(value = "/updateSetting")
    @ResponseBody
    public Map<String, Object> updatePermissionSetting(@RequestBody JSONObject jsonParam) {
        urlLogInfo("/Validation/updateSetting");
        logger.info(jsonParam.toJSONString());
        Map details = new HashMap();
        String message = "";
        int roleId = 0;
        int resultFirst = 0;
        int resultSecond = 0;
        SysUserRoleBean firstSysUserRoleBean = new SysUserRoleBean();
        //验证cookie是否有效
        String userId = checkCookieValid(jsonParam.getString("cookie"));
        //检查是否有提交权限
        UserBean userBean = userService.findUserFromUserId(userId);
        checkModifyPermission(userId,SysMenuCode.PermissionManagementCode);
        String roleName = jsonParam.getString("roleName");
        if (roleName.equals("Admin") || roleName.equals("admin")) {
            details.put("result", false);
            message = "更新失败！管理员权限不能更改。";
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.PERMISSION_DENIED, message, details);
            return map;
        } else if (roleName.equals("Guest") || roleName.equals("guest")) {
            details.put("result", false);
            message = "更新失败！游客权限不能更改。";
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.PERMISSION_DENIED, message, details);
            return map;
        } else {
            if (roleName.equals("Admin")) {
                roleId = 1;
            } else if (roleName.equals("Boss")) {
                roleId = 2;
            } else if (roleName.equals("ARC")) {
                roleId = 3;
            } else if (roleName.equals("SPDM")) {
                roleId = 4;
            } else if (roleName.equals("FGL")) {
                roleId = 5;
            } else if (roleName.equals("RTL")) {
                roleId = 6;
            } else if (roleName.equals("FO")) {
                roleId = 7;
            } else if (roleName.equals("Guest")) {
                roleId = 8;
            }
            if (roleName == null) {
                details.put("result", false);
                message = "roleName不存在！";
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.PARAM_INVALID, message, details);
                return map;
            }
            JSONArray rolePermission = jsonParam.getJSONArray("rolePermission");
            if (rolePermission.size() > 0) {
                String allMenuCode = "";
                for (int i = 0; i < rolePermission.size(); i++) {
                    if (i == 0) {
                        JSONObject menuCode = (JSONObject) rolePermission.get(0);
                        for (String obj : menuCode.keySet()) {
                            if (obj.equals("BeReviewedPage")) {
                                if (menuCode.get(obj) != null) {
                                    allMenuCode += menuCode.get(obj);
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PerformanceWriteCode);
                                    String moduleCode = sysUserRoleService.queryModuleCodeFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PerformanceWriteCode);
                                    if (moduleCode == null || moduleCode.equals("")) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode(SysMenuCode.PerformanceWriteCode);
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                } else {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PerformanceWriteCode);
                                    if (id > 0) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode("");
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                }
                            } else if (obj.equals("GroupManagePage")) {
                                if (menuCode.get(obj) != null) {
                                    allMenuCode += menuCode.get(obj);
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.GroupManagementCode);
                                    String moduleCode = sysUserRoleService.queryModuleCodeFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.GroupManagementCode);
                                    if (moduleCode == null || moduleCode.equals("")) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode(SysMenuCode.GroupManagementCode);
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                } else {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.GroupManagementCode);
                                    if (id > 0) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode("");
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                }
                            } else if (obj.equals("LeaderReviewPage")) {
                                if (menuCode.get(obj) != null) {
                                    allMenuCode += menuCode.get(obj);
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.GroupAssessmentCode);
                                    String moduleCode = sysUserRoleService.queryModuleCodeFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.GroupAssessmentCode);
                                    if (moduleCode == null || moduleCode.equals("")) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode(SysMenuCode.GroupAssessmentCode);
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                } else {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.GroupAssessmentCode);
                                    if (id > 0) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode("");
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                }
                            } else if (obj.equals("PermissionManagePage")) {
                                if (menuCode.get(obj) != null) {
                                    allMenuCode += menuCode.get(obj);
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PermissionManagementCode);
                                    String moduleCode = sysUserRoleService.queryModuleCodeFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PermissionManagementCode);
                                    if (moduleCode == null || moduleCode.equals("")) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode(SysMenuCode.PermissionManagementCode);
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                } else {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PermissionManagementCode);
                                    if (id > 0) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode("");
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                }
                            } else if (obj.equals("PersonalPage")) {
                                if (menuCode.get(obj) != null) {
                                    allMenuCode += menuCode.get(obj);
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PersonalHomepageCode);
                                    String moduleCode = sysUserRoleService.queryModuleCodeFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PersonalHomepageCode);
                                    if (moduleCode == null || moduleCode.equals("")) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode(SysMenuCode.PersonalHomepageCode);
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                } else {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PersonalHomepageCode);
                                    if (id > 0) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode("");
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                }
                            } else if (obj.equals("ProjectReviewPage")) {
                                if (menuCode.get(obj) != null) {
                                    allMenuCode += menuCode.get(obj);
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectAssessmentCode);
                                    String moduleCode = sysUserRoleService.queryModuleCodeFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectAssessmentCode);
                                    if (moduleCode == null || moduleCode.equals("")) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode(SysMenuCode.ProjectAssessmentCode);
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                } else {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectAssessmentCode);
                                    if (id > 0) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode("");
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                }
                            } else if (obj.equals("TimeSettingPage")) {
                                if (menuCode.get(obj) != null) {
                                    allMenuCode += menuCode.get(obj);
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.AssessmentControlCode);
                                    String moduleCode = sysUserRoleService.queryModuleCodeFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.AssessmentControlCode);
                                    if (moduleCode == null || moduleCode.equals("")) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode(SysMenuCode.AssessmentControlCode);
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                } else {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.AssessmentControlCode);
                                    if (id > 0) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode("");
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                }
                            } else if (obj.equals("UserManagePage")) {
                                if (menuCode.get(obj) != null) {
                                    allMenuCode += menuCode.get(obj);
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.UserManagementCode);
                                    String moduleCode = sysUserRoleService.queryModuleCodeFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.UserManagementCode);
                                    if (moduleCode == null || moduleCode.equals("")) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode(SysMenuCode.UserManagementCode);
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                } else {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.UserManagementCode);
                                    if (id > 0) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode("");
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                }
                            } else if (obj.equals("ProjectManagePage")) {
                                if (menuCode.get(obj) != null) {
                                    allMenuCode += menuCode.get(obj);
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectManagementCode);
                                    String moduleCode = sysUserRoleService.queryModuleCodeFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectManagementCode);
                                    if (moduleCode == null || moduleCode.equals("")) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode(SysMenuCode.ProjectManagementCode);
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                } else {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectManagementCode);
                                    if (id > 0) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode("");
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                }
                            } else if (obj.equals("ProjectSchedulePage")) {
                                if (menuCode.get(obj) != null) {
                                    allMenuCode += menuCode.get(obj);
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectScheduleCode);
                                    String moduleCode = sysUserRoleService.queryModuleCodeFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectScheduleCode);
                                    if (moduleCode == null || moduleCode.equals("")) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode(SysMenuCode.ProjectScheduleCode);
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                } else {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectScheduleCode);
                                    if (id > 0) {
                                        firstSysUserRoleBean.setId(id);
                                        firstSysUserRoleBean.setModuleCode("");
                                        int updateResult = sysUserRoleService.updateModuleCodeFromId(firstSysUserRoleBean);
                                    }
                                }
                            }
                        }
                        SysRoleMenuBean sysRoleMenuBean = new SysRoleMenuBean();
                        sysRoleMenuBean.setAllMenuCode(allMenuCode);
                        sysRoleMenuBean.setRoleId(roleId);
                        resultFirst = sysRoleMenuService.updateSysRoleMenu(sysRoleMenuBean);
                    } else {
                        JSONObject menuNameAndMark = (JSONObject) rolePermission.get(1);
                        SysUserRoleBean sysUserRoleBean = new SysUserRoleBean();
                        if (menuNameAndMark.size() > 0) {
                            for (String obj : menuNameAndMark.keySet()) {
                                String menuName = obj;
                                Integer menuMark = (Integer) menuNameAndMark.get(obj);
                                sysUserRoleBean.setIsModified(menuMark);
                                if (menuName.equals("BeReviewedPage")) {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PerformanceWriteCode);
                                    sysUserRoleBean.setId(id);
                                    resultSecond = sysUserRoleService.updateSysUserRole(sysUserRoleBean);
                                } else if (menuName.equals("GroupManagePage")) {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.GroupManagementCode);
                                    sysUserRoleBean.setId(id);
                                    resultSecond = sysUserRoleService.updateSysUserRole(sysUserRoleBean);
                                } else if (menuName.equals("LeaderReviewPage")) {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.GroupAssessmentCode);
                                    sysUserRoleBean.setId(id);
                                    resultSecond = sysUserRoleService.updateSysUserRole(sysUserRoleBean);
                                } else if (menuName.equals("PermissionManagePage")) {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PermissionManagementCode);
                                    sysUserRoleBean.setId(id);
                                    resultSecond = sysUserRoleService.updateSysUserRole(sysUserRoleBean);
                                } else if (menuName.equals("PersonalPage")) {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.PersonalHomepageCode);
                                    sysUserRoleBean.setId(id);
                                    resultSecond = sysUserRoleService.updateSysUserRole(sysUserRoleBean);
                                } else if (menuName.equals("ProjectManagePage")) {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectManagementCode);
                                    sysUserRoleBean.setId(id);
                                    resultSecond = sysUserRoleService.updateSysUserRole(sysUserRoleBean);
                                } else if (menuName.equals("ProjectReviewPage")) {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectAssessmentCode);
                                    sysUserRoleBean.setId(id);
                                    resultSecond = sysUserRoleService.updateSysUserRole(sysUserRoleBean);
                                } else if (menuName.equals("TimeSettingPage")) {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.AssessmentControlCode);
                                    sysUserRoleBean.setId(id);
                                    resultSecond = sysUserRoleService.updateSysUserRole(sysUserRoleBean);
                                } else if (menuName.equals("UserManagePage")) {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.UserManagementCode);
                                    sysUserRoleBean.setId(id);
                                    resultSecond = sysUserRoleService.updateSysUserRole(sysUserRoleBean);
                                }
                                else if (menuName.equals("ProjectSchedulePage")) {
                                    int id = sysUserRoleService.queryIdFromRoleIdAndBackupsModuleCode(roleId, SysMenuCode.ProjectScheduleCode);
                                    sysUserRoleBean.setId(id);
                                    resultSecond = sysUserRoleService.updateSysUserRole(sysUserRoleBean);
                                }
                            }
                        } else {
                            details.put("result", false);
                            message = "rolePermission缺少必要参数！";
                            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.PARAM_INVALID, message, details);
                            return map;

                        }
                    }
                }
            } else {
                details.put("result", false);
                message = "更新失败:没有数据！";
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.PARAM_INVALID, message, details);
                return map;
            }
            if ((resultFirst == 1) && (resultSecond == 1)) {
                details.put("result", true);
                message = "更新成功！";
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
                return map;
            } else {
                details.put("result", false);
                message = "更新失败！";
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.UNKNOWN_ERROR, message, details);
                return map;
            }
        }
    }
}
