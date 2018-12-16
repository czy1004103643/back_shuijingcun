package com.byd.performance_main.dao;

import com.byd.performance_main.model.ScheduleBean;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ScheduleDao {
    int addSchedule(ScheduleBean scheduleBean);
    int updateSchedule(ScheduleBean scheduleBean);
    int deleteSchedule(String case_id);
    List<ScheduleBean> findAllScheduleFromUserIdAndProjectName(@Param("userId") String userId,@Param("project_name") String project_name);
    List<ScheduleBean> findAllScheduleFromProjectName(String projectName);
    ScheduleBean findScheduleFromCaseId( String case_id);
}
