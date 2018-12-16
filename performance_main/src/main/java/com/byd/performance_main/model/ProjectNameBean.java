package com.byd.performance_main.model;

public class ProjectNameBean {
    private Integer id;

    private String projectName;

    public ProjectNameBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getProjectName() {
        return projectName;
    }

    public void setProjectName(String projectName) {
        this.projectName = projectName;
    }

    @Override
    public String toString() {
        return "ProjectNameBean{" +
                "id=" + id +
                ", projectName='" + projectName + '\'' +
                '}';
    }
}
