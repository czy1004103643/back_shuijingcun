package com.byd.performance_main.control;


import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.ProjectScoringMemberBean;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/project-scoring-member")
public class ProjectScoringMemberController extends BaseController {

    @ResponseBody
    @PostMapping("/add")
    public Object add(ProjectScoringMemberBean projectScoringMemberBean) {
        urlLogInfo("/project-scoring-member/add");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        int add_result = projectScoringMemberService.addProjectScoringMember(projectScoringMemberBean);
        if (add_result == 1) {
            message = "添加成功";
            result = true;
        } else {
            message = "添加失败";
        }
        details.put("add", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;

    }

    @ResponseBody
    @PostMapping("/delete")
    public Object delete(@RequestParam(value = "id") Integer id) {
        urlLogInfo("/project-scoring-member/delete");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = projectScoringMemberService.delProjectScoringMember(id);
        if (del_result == 1) {
            message = "删除成功";
            result = true;
        } else {
            message = "删除失败";
        }

        details.put("delete", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @PostMapping("/update")
    public Object update(ProjectScoringMemberBean projectScoringMemberBean) {
        urlLogInfo("/project-scoring-member/update");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = projectScoringMemberService.updateProjectScoringMember(projectScoringMemberBean);
        if (update_result == 1) {
            message = "更新成功";
            result = true;
        } else {
            message = "更新失败";
        }

        details.put("update", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/find-id")
    public Map<String, Object> findId(@RequestParam(value = "userId") String userId) {
        urlLogInfo("/project-scoring-member/find-id");

        List<ProjectScoringMemberBean> groupScoringMemberBeans = projectScoringMemberService.findProjectScoringMemberBeanFromUserId(userId);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("find-id", groupScoringMemberBeans);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/all")
    public Map<String, Object> findAll() {
        urlLogInfo("/project-scoring-member/all");

        List<ProjectScoringMemberBean> allGroupScoringMemberBean = projectScoringMemberService.findAllProjectScoringMemberBean();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("all", allGroupScoringMemberBean);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

}
