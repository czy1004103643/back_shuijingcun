package com.byd.performance_main.control;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.ProjectMemberBean;
import com.byd.performance_utils.code.PermissionCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.exception.ProjectMemberDuplicateException;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/project-member")
public class ProjectMemberController extends BaseController {

    @ResponseBody
    @PostMapping(value = "/add-project-member")
    public Object add(@RequestParam(value = "cookie") String cookie, ProjectMemberBean projectMemberBean) {
        urlLogInfo("/project-member/add-project-member");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        Integer projectName = projectMemberBean.getProjectName();
        String projectMember = projectMemberBean.getProjectMember();
        Integer id = projectMemberService.findProjectMemberBeanFromProjectNameAndProjectMember(projectName, projectMember).getId();

        if (id == null) {
            throw new ProjectMemberDuplicateException();
        }
        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        int add_result = projectMemberService.addProjectMember(projectMemberBean);
        if (add_result == 1) {
            result = true;
            message = "组员添加成功";
        } else {
            result = false;
            message = "组员添加失败";
        }
        details.put("add-project-member", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }


    @ResponseBody
    @PostMapping(value = "/delete-project-member")
    public Object delete(@RequestParam(value = "cookie") String cookie, @RequestParam(value = "id")
            Integer id) {
        urlLogInfo("/project-member/delete-project-member");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        Integer delete_back = projectMemberService.delProjectMember(id);
        if (delete_back == 1) {
            result = true;
            message = "成员删除成功";
        } else {
            result = false;
            message = "成员删除失败";
        }
        details.put("delete-project-member", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @PostMapping(value = "/update-project-member")
    public Object updateProjectMember(@RequestParam(value = "cookie") String cookie, ProjectMemberBean
            projectMemberBean) {
        urlLogInfo("/project-member/update-project-member");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        Integer update_result = projectMemberService.updateProjectMember(projectMemberBean);
        if (update_result == 1) {
            result = true;
            message = "成员信息更新成功";
        } else {
            result = false;
            message = "成员信息更新失败";
        }
        details.put("update-project-member", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping(value = "/find-id")
    public Map<String, Object> findId(@RequestParam(value = "cookie") String cookie,
                                      @RequestParam(value = "projectName") Integer projectName, @RequestParam(value = "projectMember") String projectMember) {
        urlLogInfo("/project-member/find-id");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        Integer id = projectMemberService.findProjectMemberBeanFromProjectNameAndProjectMember(projectName, projectMember).getId();
        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        details.put("findIdFromProjectNameAndProjectMember", id);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping(value = "/find-project-member")
    public Map<String, Object> findProjectMember(@RequestParam(value = "cookie") String cookie,
                                                 @RequestParam(value = "projectName") Integer projectName) {
        urlLogInfo("/project-member/project-member");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        List<String> projectMembers = projectMemberService.findProjectMemberFromProjectName(projectName);
        //List<String> groupMembers = groupMemberService.findGroupMemberFromGroupName(groupName);
        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        details.put("findProjectMemberFromProjectName", projectMembers);

        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping(value = "/find-project-name")
    public Map<String, Object> findProjectName(@RequestParam(value = "cookie") String cookie,
                                               @RequestParam(value = "projectMember") String projectMember) {
        urlLogInfo("/project-member/find-project-name");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);
        List<Integer> projectName = projectMemberService.findProjectNameFromProjectMember(projectMember);
        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        details.put("findProjectNameFromProjectMember", projectName);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping(value = "/find-Role")
    public Map<String, Object> findProjectNameRole(@RequestParam(value = "cookie") String cookie,
                                                   @RequestParam(value = "projectName") Integer projectName, @RequestParam(value = "projectMember") String projectMember) {
        urlLogInfo("/project-member/findProjectNameRole");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);

        Integer ProjectNameRole = projectMemberService.findProjectNameRole(projectName, projectMember);
        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        details.put("findProjectNameRole", ProjectNameRole);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;

    }

    @ResponseBody
    @GetMapping(value = "/find-all")
    public Map<String, Object> findAllProjectMemberBean(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/project-member/find-all");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_GROUP_MEMBER_PERMISSION);

        List<ProjectMemberBean> allProjectMemberBean = projectMemberService.findAllProjectMemberBean();
        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        details.put("findAllProjectMemberBean", allProjectMemberBean);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }
}
