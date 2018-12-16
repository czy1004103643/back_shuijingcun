package com.byd.performance_main.control;


import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.GroupScoringMemberBean;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/group-scoring-member")
public class GroupScoringMemberController extends BaseController {

    @ResponseBody
    @PostMapping("/add")
    public Object add(GroupScoringMemberBean groupScoringMemberBean) {
        urlLogInfo("/group-scoring-member/add");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        int add_result = groupScoringMemberService.addGroupScoringMember(groupScoringMemberBean);
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
        urlLogInfo("/group-scoring-member/delete");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int del_result = groupScoringMemberService.delGroupScoringMember(id);
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
    public Object update(GroupScoringMemberBean groupScoringMemberBean) {
        urlLogInfo("/group-scoring-member/update");

        HashMap<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";

        int update_result = groupScoringMemberService.updateGroupScoringMember(groupScoringMemberBean);
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
        urlLogInfo("/group-scoring-member/find-id");

        List<GroupScoringMemberBean> groupScoringMemberBeans = groupScoringMemberService.findGroupScoringMemberBeanFromUserId(userId);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("find-id", groupScoringMemberBeans);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

    @ResponseBody
    @GetMapping("/all")
    public Map<String, Object> findAll() {
        urlLogInfo("/group-scoring-member/all");

        List<GroupScoringMemberBean> allGroupScoringMemberBean = groupScoringMemberService.findAllGroupScoringMemberBean();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        details.put("all", allGroupScoringMemberBean);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
        return map;
    }

}
