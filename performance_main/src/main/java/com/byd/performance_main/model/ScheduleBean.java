package com.byd.performance_main.model;

public class ScheduleBean {
    private int id;
    private String userId;
    private String case_id;
    private String project_name;
    private String case_name;
    private String case_start_time;
    private int case_total_time;
    private double case_process;
    private String case_update_time;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCase_id() {
        return case_id;
    }

    public void setCase_id(String case_id) {
        this.case_id = case_id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProject_name() {
        return project_name;
    }

    public void setProject_name(String project_name) {
        this.project_name = project_name;
    }

    public String getCase_name() {
        return case_name;
    }

    public void setCase_name(String case_name) {
        this.case_name = case_name;
    }

    public String getCase_start_time() {
        return case_start_time;
    }

    public void setCase_start_time(String case_start_time) {
        this.case_start_time = case_start_time;
    }

    public int getCase_total_time() {
        return case_total_time;
    }

    public void setCase_total_time(int case_total_time) {
        this.case_total_time = case_total_time;
    }

    public double getCase_process() {
        return case_process;
    }

    public void setCase_process(double case_process) {
        this.case_process = case_process;
    }

    public String getCase_update_time() {
        return case_update_time;
    }

    public void setCase_update_time(String case_update_time) {
        this.case_update_time = case_update_time;
    }
}
