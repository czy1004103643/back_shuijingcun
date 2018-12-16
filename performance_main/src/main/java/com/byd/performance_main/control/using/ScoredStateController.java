package com.byd.performance_main.control.using;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.ScoredStateModeTimeBean;
import com.byd.performance_main.model.UserBean;
import com.byd.performance_utils.code.ScoredStateCode;
import com.byd.performance_utils.code.SpecialRecordCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.code.SysMenuCode;
import com.byd.performance_utils.exception.ParamInvalidException;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/scored-mode")
public class ScoredStateController extends BaseController {

    @ResponseBody
    @PostMapping("/setting")
    public Object setScoredState(@RequestParam(value = "cookie") String cookie,
                                 @RequestParam(value = "scoreTime") String scoreTime,
                                 @RequestParam(value = "state") Boolean state) {
        urlLogInfo("/scored-mode/setting");
        checkCookieValid(cookie);
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.AssessmentControlCode);
        scoreTime = CommonMethod.transformScoreTime(CommonMethod.transformScoreTimeToTimeStamp(scoreTime, CommonMethod.TIME_FORMAT_yyyy_MM), CommonMethod.TIME_FORMAT_yyyy_MM);
        long lastMonthTimeStamp = CommonMethod.getLastMonthTimeStamp();
        long scoreTimeTimeStamp = CommonMethod.transformScoreTimeToTimeStamp(scoreTime, CommonMethod.TIME_FORMAT_yyyy_MM);
        if (scoreTimeTimeStamp > lastMonthTimeStamp) {
            throw new ParamInvalidException("ScoreTime is invalid time");
        }
        List<ScoredStateModeTimeBean> scoredStateModeTimeBeans = scoredStateModeTimeService.findScoredStateModeTimeBeanFromIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
        if (state == ScoredStateCode.ScoredState_Turn_ON) {
            if (scoredStateModeTimeBeans != null && scoredStateModeTimeBeans.size() > 0) {
                throw new ParamInvalidException("ScoreTime have a true setting");
            }
        } else {
            if (!(scoredStateModeTimeBeans != null && scoredStateModeTimeBeans.size() > 0 && scoredStateModeTimeBeans.get(0).getScoreTime().equals(scoreTime))) {
                throw new ParamInvalidException("ScoreTime is invalid");
            }
        }
        HashMap<String, Object> details = new HashMap<>();
        boolean result = true;
        String message = "设置成功";
        ScoredStateModeTimeBean scoredStateModeTimeBean = scoredStateModeTimeService.findScoredStateModeTimeBeanFromScoredTime(scoreTime);
        if (scoredStateModeTimeBean != null) {
            Integer isScored = scoredStateModeTimeBean.getIsScored();
            if (state == ScoredStateCode.ScoredState_Turn_ON) {
                if (isScored == ScoredStateCode.ScoredState_Turn_OFF_Code) {
                    scoredStateModeTimeBean.setIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
                    scoredStateModeTimeService.updateScoredStateModeTime(scoredStateModeTimeBean);
                }
            } else {
                if (isScored == ScoredStateCode.ScoredState_Turn_ON_Code) {
                    scoredStateModeTimeBean.setIsScored(ScoredStateCode.ScoredState_Turn_OFF_Code);
                    int update_result = scoredStateModeTimeService.updateScoredStateModeTime(scoredStateModeTimeBean);
                    if (update_result == SpecialRecordCode.SQL_RESULT_FAIL) {
                        throw new ParamInvalidException("scoredStateModeTimeService is error");
                    } else {
                        //计算最终值
                        finalCalculateScoreResult(scoreTime);
                    }
                }
            }
        } else {
            scoredStateModeTimeBean = new ScoredStateModeTimeBean();
            scoredStateModeTimeBean.setScoreTime(scoreTime);
            if (state) {
                scoredStateModeTimeBean.setIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
            } else {
                throw new ParamInvalidException("The scoredTime is not exist");
            }
            int add_result = scoredStateModeTimeService.addScoredStateModeTime(scoredStateModeTimeBean);
            if (add_result == SpecialRecordCode.SQL_RESULT_FAIL) {
                throw new ParamInvalidException("scoredStateModeTimeService is error");
            }
        }
        details.put("setting_info", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/scored-state-turn-on")
    public Object getTurnOnScoredState(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/scored-mode/scored-state-turn-on");
        checkCookieValid(cookie);
        HashMap<String, Object> details = new HashMap<>();
        //检查是否有提交权限
        UserBean userBean = userService.findUserFromUserId(checkCookieValid(cookie));
        String message = "";
        List<ScoredStateModeTimeBean> scoredStateModeTimeBeans = scoredStateModeTimeService.findScoredStateModeTimeBeanFromIsScored(ScoredStateCode.ScoredState_Turn_ON_Code);
        HashMap<String, Object> turnOnScoredTime = new HashMap<>();
        if (scoredStateModeTimeBeans != null && scoredStateModeTimeBeans.size() > 0) {
            turnOnScoredTime.put("scoredTime", scoredStateModeTimeBeans.get(0).getScoreTime());
            turnOnScoredTime.put("state", ScoredStateCode.ScoredState_Turn_ON);
        }
        details.put("scored_turn_on", turnOnScoredTime);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/info")
    public Object getScoredStateInfo(@RequestParam(value = "cookie") String cookie,
                                     @RequestParam(value = "scoreTime") String scoreTime) {
        urlLogInfo("/scored-mode/info");
        checkCookieValid(cookie);
        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        ScoredStateModeTimeBean scoredStateModeTimeBean = scoredStateModeTimeService.findScoredStateModeTimeBeanFromScoredTime(scoreTime);
        details.put("info", scoredStateModeTimeBean);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/all-info")
    public Object getAllScoredStateInfo(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/scored-mode/all-info");
        checkCookieValid(cookie);
        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        List<ScoredStateModeTimeBean> allScoredStateModeTimeBean = scoredStateModeTimeService.findAllScoredStateModeTimeBean();
        details.put("info", allScoredStateModeTimeBean);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }
}
