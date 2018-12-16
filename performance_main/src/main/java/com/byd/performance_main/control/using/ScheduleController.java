package com.byd.performance_main.control.using;

import com.alibaba.fastjson.JSONObject;
import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.ScheduleBean;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.code.SysMenuCode;
import com.byd.performance_utils.code.SysRoleCode;
import com.byd.performance_utils.exception.ParamInvalidException;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/*
Schedule模块
 */
@RestController
@RequestMapping(value = "/schedule")
public class ScheduleController extends BaseController {
    /*
    查询
     */
    @ResponseBody
    @GetMapping(value = "/info")
    public Map<String,Object> scheduleInformation(@RequestParam("cookie") String cookie){
        urlLogInfo("/schedule/info");
        String userId=checkCookieValid(cookie);
        Map<String,Object> detail=new HashMap();
        ArrayList schedule=new ArrayList();
        if(userService.findUserFromUserId(userId).getUserRole()==SysRoleCode.SPDMCode){
            List<Integer> allProject=projectMemberService.findProjectNameFromProjectMember(userId);
            if(allProject!=null&&allProject.size()>0){
                for(Integer projectId : allProject){
                    Map<String,Object> projectHashMap=new HashMap<>();
                    String project_name=projectNameService.findProjectNameFromId(projectId).getProjectName();
                    projectHashMap.put("projectName",project_name);
                    List<ScheduleBean> allSchedule=scheduleService.findAllScheduleFromUserIdAndProjectName(userId,project_name);
                    ArrayList allCase=new ArrayList();
                    if(allSchedule!=null&&allSchedule.size()>0){
                        for(ScheduleBean scheduleBean:allSchedule){
                            HashMap _case=new HashMap();
                            _case.put("id",scheduleBean.getCase_id());
                            _case.put("text",scheduleBean.getCase_name());
                            _case.put("start_date",scheduleBean.getCase_start_time());
                            _case.put("duration",scheduleBean.getCase_total_time());
                            _case.put("progress",scheduleBean.getCase_process());
                            allCase.add(_case);
                            projectHashMap.put("tasks",allCase);
                        }
                    }else {
                        HashMap _case=new HashMap();
                        projectHashMap.put("tasks",allCase);
                    }
                    schedule.add(projectHashMap);
                }
            }
        }
        String message="";
        detail.put("schedule",schedule);
        Map<String,Object> map= CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS,message,detail);
        return map;
    }

    /*
    保存
     */
    @ResponseBody
    @PostMapping(value = "/save")
    public Map<String,Object> updateSchedule(@RequestParam("cookie") String cookie,
                                             @RequestParam("id") String case_id,
                                             @RequestParam("text") String case_name,
                                             @RequestParam("start_date") String case_start_time,
                                             @RequestParam("duration") Integer case_total_time,
                                             @RequestParam("progress") Double case_process,
                                             @RequestParam("projectName") String project_name){
        urlLogInfo("/schedule/save");
        String userId=checkCookieValid(cookie);
        checkModifyPermission(userId, SysMenuCode.ProjectScheduleCode);
        ScheduleBean scheduleBean=new ScheduleBean();
        DecimalFormat df = new DecimalFormat("#.00");
        int result=0;
        Map detail=new HashMap();
        Map<String,Object> map=new HashMap<>();
        scheduleBean.setCase_id(case_id);
        scheduleBean.setUserId(userId);
        scheduleBean.setCase_name(case_name);
        scheduleBean.setCase_start_time(case_start_time);
        scheduleBean.setCase_total_time(case_total_time);
        if(case_process>=0&&case_process<=1) {
            scheduleBean.setCase_process(Double.parseDouble(df.format(case_process)));
        }else{
            throw new ParamInvalidException("任务进度不能大于100%");
        }
        scheduleBean.setProject_name(project_name);
        ScheduleBean scheduleBeans=scheduleService.findScheduleFromCaseId(case_id);
        if(scheduleBeans==null){
            result=scheduleService.addSchedule(scheduleBean);
        }else {
            result=scheduleService.updateSchedule(scheduleBean);
        }
        if(result==1){
            detail.put("result",true);
            map=CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS,"保存成功！",detail);
        }else{
            detail.put("result",false);
            map=CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR,"保存失败！",detail);
        }
        return map;
    }

    /*
    删除
     */
    @ResponseBody
    @PostMapping(value = "delete")
    public Map<String,Object> deleteSchedule(@RequestParam("id") String case_id,
                                             @RequestParam("cookie") String cookie){
        String userId=checkCookieValid(cookie);
        checkModifyPermission(userId, SysMenuCode.ProjectScheduleCode);
        Map<String,Object> details=new HashMap<>();
        Map<String,Object> map=new HashMap<>();
        int result=scheduleService.deleteSchedule(case_id);
        if(result==1){
            details.put("result",true);
            map=CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS,"删除成功！",details);
        }else{
            details.put("result",false);
            map=CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR,"删除失败！",details);
        }
        return map;
    }
}
