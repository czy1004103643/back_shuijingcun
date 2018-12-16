package com.byd.performance_main.control;


import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.ProjectOwnScoringBean;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/project-own-scoring")
public class ProjectOwnScoringController extends BaseController {

    @ResponseBody
    @PostMapping("/add")
    public Object add(ProjectOwnScoringBean projectOwnScoringBean) {
        urlLogInfo("/project-own-scoring/add");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        int add_result = projectOwnScoringService.addProjectOwnScoring(projectOwnScoringBean);
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
        urlLogInfo("/project-own-scoring/delete");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = projectOwnScoringService.delProjectOwnScoring(id);
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
    public Object update(ProjectOwnScoringBean projectOwnScoringBean) {
        urlLogInfo("/project-own-scoring/update");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = projectOwnScoringService.updateProjectOwnScoring(projectOwnScoringBean);
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
        urlLogInfo("/project-own-scoring/find-id");

        List<ProjectOwnScoringBean> projectOwnScoringBeans = projectOwnScoringService.findProjectOwnScoringBeanFromUserId(userId);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("find-id", projectOwnScoringBeans);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/all")
    public Map<String, Object> findAll() {
        urlLogInfo("/project-own-scoring/all");

        List<ProjectOwnScoringBean> allProjectOwnScoringBean = projectOwnScoringService.findAllProjectOwnScoringBean();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("all", allProjectOwnScoringBean);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

}
