package com.byd.performance_main.control;


import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.ScoredStateModeTimeBean;
import com.byd.performance_utils.code.ScoredStateCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/scored-mode-time")
public class ScoredStateModeTimeController extends BaseController {

    @ResponseBody
    @PostMapping("/add")
    public Object add(ScoredStateModeTimeBean scoredStateModeTimeBean) {
        urlLogInfo("/scored-mode-time/add");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        int add_result = scoredStateModeTimeService.addScoredStateModeTime(scoredStateModeTimeBean);
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
        urlLogInfo("/scored-mode-time/delete");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = scoredStateModeTimeService.delScoredStateModeTime(id);
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
    public Object update(ScoredStateModeTimeBean scoredStateModeTimeBean) {
        urlLogInfo("/scored-mode-time/update");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = scoredStateModeTimeService.updateScoredStateModeTime(scoredStateModeTimeBean);
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
    @GetMapping("/score-time")
    public Map<String, Object> findId(@RequestParam(value = "scoreTime") String scoreTime) {
        urlLogInfo("/scored-mode-time/score-time");

        ScoredStateModeTimeBean scoredStateModeTimeBean = scoredStateModeTimeService.findScoredStateModeTimeBeanFromScoredTime(scoreTime);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("score-time", scoredStateModeTimeBean);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/is-score")
    public Map<String, Object> isScored(@RequestParam(value = "isScored") boolean isScored) {
        urlLogInfo("/scored-mode-time/is-score");

        List<ScoredStateModeTimeBean> scoredStateModeTimeBeans;
        if (isScored) {
            scoredStateModeTimeBeans = scoredStateModeTimeService.findScoredStateModeTimeBeanFromIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
        } else {
            scoredStateModeTimeBeans = scoredStateModeTimeService.findScoredStateModeTimeBeanFromIsScored(ScoredStateCode.ScoredState_Turn_OFF_Code);
        }

        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("is-score", scoredStateModeTimeBeans);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/all")
    public Map<String, Object> findAll() {
        urlLogInfo("/scored-mode-time/all");

        List<ScoredStateModeTimeBean> allScoredBeanStateModeTime = scoredStateModeTimeService.findAllScoredStateModeTimeBean();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("all", allScoredBeanStateModeTime);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

}
