package com.byd.performance_main.service.impl;

import com.byd.performance_main.dao.ScheduleDao;
import com.byd.performance_main.model.ScheduleBean;
import com.byd.performance_main.service.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "scheduleService")
public class ScheduleServiceImpl implements ScheduleService {
    @Autowired
    private ScheduleDao scheduleDao;

    @Override
    public int addSchedule(ScheduleBean scheduleBean) {
        return scheduleDao.addSchedule(scheduleBean);
    }

    @Override
    public int updateSchedule(ScheduleBean scheduleBean) {
        return scheduleDao.updateSchedule(scheduleBean);
    }

    @Override
    public int deleteSchedule(String case_id) {
        return scheduleDao.deleteSchedule(case_id);
    }

    @Override
    public List<ScheduleBean> findAllScheduleFromUserIdAndProjectName(String userId, String project_name) {
        return scheduleDao.findAllScheduleFromUserIdAndProjectName(userId,project_name);
    }

    @Override
    public List<ScheduleBean> findAllScheduleFromProjectName(String projectName) {
        return scheduleDao.findAllScheduleFromProjectName(projectName);
    }

    @Override
    public ScheduleBean findScheduleFromCaseId(String case_id) {
        return scheduleDao.findScheduleFromCaseId(case_id);
    }
}
