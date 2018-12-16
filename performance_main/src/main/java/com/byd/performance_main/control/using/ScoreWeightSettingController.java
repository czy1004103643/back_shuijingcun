package com.byd.performance_main.control.using;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.ScoreWeightBean;
import com.byd.performance_utils.code.ScoreWeightNameCode;
import com.byd.performance_utils.code.SpecialRecordCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.code.SysMenuCode;
import com.byd.performance_utils.exception.ParamInvalidException;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/score-weight")
public class ScoreWeightSettingController extends BaseController {

    @Transactional
    @ResponseBody
    @PostMapping("/update-weight1")
    public Object updateWeightValueOne(@RequestParam(value = "cookie") String cookie,
                                       @RequestParam(value = "FO_SCORING_MYSELF") Integer scoreValue1,
                                       @RequestParam(value = "RTL_SCORING_FO") Integer scoreValue2,
                                       @RequestParam(value = "FGL_SCORING_FO") Integer scoreValue3) {
        urlLogInfo("/score-weight/update-weight1");
        checkCookieValid(cookie);
        checkScoreValueValid(scoreValue1);
        checkScoreValueValid(scoreValue2);
        checkScoreValueValid(scoreValue3);
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.AssessmentControlCode);
        if ((scoreValue1 + scoreValue2 + scoreValue3) != 100) {
            throw new ParamInvalidException("The all score value is not equal 100.");
        }
        int update_result1 = scoreWeightService.updateWeightNameFromValue(ScoreWeightNameCode.FO_SCORING_MYSELF, scoreValue1);
        int update_result2 = scoreWeightService.updateWeightNameFromValue(ScoreWeightNameCode.RTL_SCORING_FO, scoreValue2);
        int update_result3 = scoreWeightService.updateWeightNameFromValue(ScoreWeightNameCode.FGL_SCORING_FO, scoreValue3);
        if (update_result1 == SpecialRecordCode.SQL_RESULT_FAIL
                || update_result2 == SpecialRecordCode.SQL_RESULT_FAIL
                || update_result3 == SpecialRecordCode.SQL_RESULT_FAIL) {
            throw new ParamInvalidException("update fail");
        }
        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        details.put("update_score_weight", "update success");
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @Transactional
    @ResponseBody
    @PostMapping("/update-weight2")
    public Object updateWeightValueTwo(@RequestParam(value = "cookie") String cookie,
                                       @RequestParam(value = "FGL_SCORING_MYSELF") Integer scoreValue1,
                                       @RequestParam(value = "RTL_SCORING_FGL") Integer scoreValue2,
                                       @RequestParam(value = "SPDM_SCORING_FGL") Integer scoreValue3) {
        urlLogInfo("/score-weight/update-weight2");
        checkCookieValid(cookie);
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.AssessmentControlCode);
        checkScoreValueValid(scoreValue1);
        checkScoreValueValid(scoreValue2);
        checkScoreValueValid(scoreValue3);
        if ((scoreValue1 + scoreValue2 + scoreValue3) != 100) {
            throw new ParamInvalidException("The all score value is not equal 100.");
        }
        int update_result1 = scoreWeightService.updateWeightNameFromValue(ScoreWeightNameCode.FGL_SCORING_MYSELF, scoreValue1);
        int update_result2 = scoreWeightService.updateWeightNameFromValue(ScoreWeightNameCode.RTL_SCORING_FGL, scoreValue2);
        int update_result3 = scoreWeightService.updateWeightNameFromValue(ScoreWeightNameCode.SPDM_SCORING_FGL, scoreValue3);
        if (update_result1 == SpecialRecordCode.SQL_RESULT_FAIL
                || update_result2 == SpecialRecordCode.SQL_RESULT_FAIL
                || update_result3 == SpecialRecordCode.SQL_RESULT_FAIL) {
            throw new ParamInvalidException("update fail");
        }
        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        details.put("update_score_weight", "update success");
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @Transactional
    @ResponseBody
    @PostMapping("/update-weight3")
    public Object updateWeightValueThree(@RequestParam(value = "cookie") String cookie,
                                         @RequestParam(value = "SPDM_OR_ARC_SCORING_MYSELF") Integer scoreValue1,
                                         @RequestParam(value = "RTL_SCORING_SPDM_OR_ARC") Integer scoreValue2) {
        urlLogInfo("/score-weight/update-weight3");
        checkCookieValid(cookie);
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.AssessmentControlCode);
        checkScoreValueValid(scoreValue1);
        checkScoreValueValid(scoreValue2);
        if ((scoreValue1 + scoreValue2) != 100) {
            throw new ParamInvalidException("The all score value is not equal 100.");
        }
        int update_result1 = scoreWeightService.updateWeightNameFromValue(ScoreWeightNameCode.SPDM_OR_ARC_SCORING_MYSELF, scoreValue1);
        int update_result2 = scoreWeightService.updateWeightNameFromValue(ScoreWeightNameCode.RTL_SCORING_SPDM_OR_ARC, scoreValue2);
        if (update_result1 == SpecialRecordCode.SQL_RESULT_FAIL
                || update_result2 == SpecialRecordCode.SQL_RESULT_FAIL) {
            throw new ParamInvalidException("update fail");
        }
        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        details.put("update_score_weight", "update success");
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @Transactional
    @ResponseBody
    @PostMapping("/update-weight4")
    public Object updateWeightValueFour(@RequestParam(value = "cookie") String cookie,
                                        @RequestParam(value = "GROUP_MEMBER_AVG") Integer scoreValue1,
                                        @RequestParam(value = "BOSS_SCORING_RTL") Integer scoreValue2) {
        urlLogInfo("/score-weight/update-weight4");
        checkCookieValid(cookie);
        checkModifyPermission(checkCookieValid(cookie), SysMenuCode.AssessmentControlCode);
        checkScoreValueValid(scoreValue1);
        checkScoreValueValid(scoreValue2);
        if ((scoreValue1 + scoreValue2) != 100) {
            throw new ParamInvalidException("The all score value is not equal 100.");
        }
        int update_result1 = scoreWeightService.updateWeightNameFromValue(ScoreWeightNameCode.GROUP_MEMBER_AVG, scoreValue1);
        int update_result2 = scoreWeightService.updateWeightNameFromValue(ScoreWeightNameCode.BOSS_SCORING_RTL, scoreValue2);
        if (update_result1 == SpecialRecordCode.SQL_RESULT_FAIL
                || update_result2 == SpecialRecordCode.SQL_RESULT_FAIL) {
            throw new ParamInvalidException("update fail");
        }
        HashMap<String, Object> details = new HashMap<>();
        String message = "";
        details.put("update_score_weight", "update success");
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
    }

    @ResponseBody
    @GetMapping("/all")
    public Map<String, Object> findScoreWeight(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/score-weight/all");
        checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        List<ScoreWeightBean> allScoreWeight = scoreWeightService.findAllScoreWeight();
        ArrayList<Object> weightDetail = new ArrayList<>();
        Map<String, Object> formula1 = new HashMap<>();
        Map<String, Object> formula2 = new HashMap<>();
        Map<String, Object> formula3 = new HashMap<>();
        Map<String, Object> formula4 = new HashMap<>();
        for (int i = 0; i < allScoreWeight.size(); i++) {
            ScoreWeightBean scoreWeightBean = allScoreWeight.get(i);
            if (scoreWeightBean.getWeightName().equals(ScoreWeightNameCode.FO_SCORING_MYSELF)) {
                formula1.put("FO_SCORING_MYSELF", scoreWeightBean.getScoreValue());
            } else if (scoreWeightBean.getWeightName().equals(ScoreWeightNameCode.RTL_SCORING_FO)) {
                formula1.put("RTL_SCORING_FO", scoreWeightBean.getScoreValue());
            } else if (scoreWeightBean.getWeightName().equals(ScoreWeightNameCode.FGL_SCORING_FO)) {
                formula1.put("FGL_SCORING_FO", scoreWeightBean.getScoreValue());
            } else if (scoreWeightBean.getWeightName().equals(ScoreWeightNameCode.FGL_SCORING_MYSELF)) {
                formula2.put("FGL_SCORING_MYSELF", scoreWeightBean.getScoreValue());
            } else if (scoreWeightBean.getWeightName().equals(ScoreWeightNameCode.RTL_SCORING_FGL)) {
                formula2.put("RTL_SCORING_FGL", scoreWeightBean.getScoreValue());
            } else if (scoreWeightBean.getWeightName().equals(ScoreWeightNameCode.SPDM_SCORING_FGL)) {
                formula2.put("SPDM_SCORING_FGL", scoreWeightBean.getScoreValue());
            } else if (scoreWeightBean.getWeightName().equals(ScoreWeightNameCode.GROUP_MEMBER_AVG)) {
                formula4.put("GROUP_MEMBER_AVG", scoreWeightBean.getScoreValue());
            } else if (scoreWeightBean.getWeightName().equals(ScoreWeightNameCode.BOSS_SCORING_RTL)) {
                formula4.put("BOSS_SCORING_RTL", scoreWeightBean.getScoreValue());
            } else if (scoreWeightBean.getWeightName().equals(ScoreWeightNameCode.SPDM_OR_ARC_SCORING_MYSELF)) {
                formula3.put("SPDM_OR_ARC_SCORING_MYSELF", scoreWeightBean.getScoreValue());
            } else if (scoreWeightBean.getWeightName().equals(ScoreWeightNameCode.RTL_SCORING_SPDM_OR_ARC)) {
                formula3.put("RTL_SCORING_SPDM_OR_ARC", scoreWeightBean.getScoreValue());
            }
        }
        weightDetail.add(formula1);
        weightDetail.add(formula2);
        weightDetail.add(formula3);
        weightDetail.add(formula4);
        details.put("weightDetail", weightDetail);
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
    }
}
