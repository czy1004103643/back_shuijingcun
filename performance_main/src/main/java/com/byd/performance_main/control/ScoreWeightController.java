package com.byd.performance_main.control;


import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.ScoreWeightBean;
import com.byd.performance_utils.code.PermissionCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/weight")
public class ScoreWeightController extends BaseController {

    @ResponseBody
    @PostMapping("/add")
    public Object addScoreWeight(@RequestParam(value = "cookie") String cookie,
                                 ScoreWeightBean scoreWeightBean) {
        urlLogInfo("/weight/add");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_PROJECT_MEMBER_PERMISSION);

        checkScoreWeightValid(scoreWeightBean);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        int add_result = scoreWeightService.addScoreWeight(scoreWeightBean);
        if (add_result == 1) {
            message = "新权重添加成功";
            result = true;
        } else {
            message = "新权重添加失败";
        }
        details.put("add_score_weight", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;

    }

    @ResponseBody
    @PostMapping("/delete")
    public Object deleteScoreWeight(@RequestParam(value = "cookie") String cookie,
                                    @RequestParam(value = "id") Integer id) {
        urlLogInfo("/weight/delete");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_PROJECT_MEMBER_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = scoreWeightService.delScoreWeight(id);
        if (del_result == 1) {
            message = "权重删除成功";
            result = true;
        } else {
            message = "权重删除失败";
        }

        details.put("delete_score_weight", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @PostMapping("/update")
    public Object updateScoreWeight(@RequestParam(value = "cookie") String cookie,
                                    ScoreWeightBean scoreWeightBean) {
        urlLogInfo("/weight/update");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_PROJECT_MEMBER_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = scoreWeightService.updateScoreWeight(scoreWeightBean);
        if (update_result == 1) {
            message = "权重更新成功";
            result = true;
        } else {
            message = "权重更新失败";
        }

        details.put("update_score_weight", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @PostMapping("/update-weight")
    public Object updateWeightNameFromValue(@RequestParam(value = "cookie") String cookie,
                                            @RequestParam(value = "weightName") Integer weightName,
                                            @RequestParam(value = "scoreValue") Integer scoreValue) {
        urlLogInfo("/weight/update-weight");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_PROJECT_MEMBER_PERMISSION);

        checkScoreWeightValid(weightName, scoreValue);

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = scoreWeightService.updateWeightNameFromValue(weightName, scoreValue);
        if (update_result == 1) {
            message = "权重更新成功";
            result = true;
        } else {
            message = "权重更新失败";
        }

        details.put("update_score_weight", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @PostMapping("/find-score")
    public Object findScoreValueFromWeightName(@RequestParam(value = "cookie") String cookie,
                                               @RequestParam(value = "weightName") Integer weightName) {
        urlLogInfo("/weight/find-score");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_PROJECT_MEMBER_PERMISSION);

        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        Integer scoreValue = scoreWeightService.findScoreValueFromWeightName(weightName);
        if (scoreValue != null) {
            message = "权重值查询成功";
        } else {
            message = "权重值查询失败";
            scoreValue = 0;
        }

        details.put("find_score", scoreValue);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/all")
    public Map<String, Object> findAllScoreWeight(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/weight/all");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_PROJECT_MEMBER_PERMISSION);

        List<ScoreWeightBean> allScoreWeight = scoreWeightService.findAllScoreWeight();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("allScoreWeight", allScoreWeight);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

}
