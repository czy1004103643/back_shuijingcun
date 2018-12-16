package com.byd.performance_main.control;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.ProjectNameBean;
import com.byd.performance_utils.code.PermissionCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/ProjectName")
public class ProjectNameController extends BaseController {

    @ResponseBody
    @PostMapping(value="/addProjectName")
    public Object addProjectName(@RequestParam(value = "cookie") String cookie,
                                 ProjectNameBean projectNameBean) {
        urlLogInfo("/ProjectName/addProjectName");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
            int add_result = projectNameService.addProjectName(projectNameBean);
            if (add_result == 1) {
                message = "项目名称添加成功";
                result = true;
            } else {
                message = "项目名称添加失败";
                result = false;
            }
            details.put("addProjectName", result);

        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @PostMapping(value = "/deleteProjectName")
    @ResponseBody
    public Object deleteProjectName(@RequestParam(value = "cookie") String cookie,@RequestParam(value = "id")
            Integer id){
        urlLogInfo("/ProjectName/deleteProjectName");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = projectNameService.delProjectName(id);
        if (del_result == 1) {
            message = "项目名称删除成功";
            result = true;
        } else {
            message = "项目名称删除失败";
        }

        details.put("deleteProjectName", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @PostMapping( value = "/updateProjectName")
    @ResponseBody
    public Object updateProjectName(@RequestParam(value = "cookie") String cookie,ProjectNameBean projectNameBean){
        urlLogInfo("/ProjectName/updateProjectName");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = projectNameService.updateProjectName(projectNameBean);
        if (del_result == 1) {
            message = "项目名称更新成功";
            result = true;
        } else {
            message = "项目名称更新失败";
        }

        details.put("updateProjectName", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping(value = "findProjectNameFromId")
    public Map<String,Object> findProjectNameFromId(@RequestParam(value="cookie") String cookie,
                                                    @RequestParam(value="id") Integer id){
        urlLogInfo("/ProjectName/findProjectNameFromId");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        ProjectNameBean projectName=projectNameService.findProjectNameFromId(id);

        HashMap<String,Object> details=new HashMap<>();
        String message="";
        details.put("findProjectNameFromId",projectName);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping(value = "/findProjectNameFromName")

    public Map<String,Object> findProjectNameFromName(@RequestParam(value = "cookie") String cookie,
            @RequestParam(value = "projectName") String projectName){
        urlLogInfo("/ProjectName/findProjectNameFromName");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        ProjectNameBean project_Name=projectNameService.findProjectNameFromName(projectName);
        HashMap<String,Object> details=new HashMap<>();
        String message="";
        details.put("findProjectNameFromName",project_Name);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping(value = "findAllProjectName")
    public Map<String,Object> findAllProjectName(@RequestParam(value = "cookie") String cookie){
        urlLogInfo("/ProjectName/findAllProjectName");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        List<ProjectNameBean> AllProjectName=projectNameService.findAllProjectName();
        Map<String,Object> details=new HashMap<>();
        String message="";
        details.put("findAllProjectName",AllProjectName);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;

    }
}
