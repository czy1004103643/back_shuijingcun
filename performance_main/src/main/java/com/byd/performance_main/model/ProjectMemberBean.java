package com.byd.performance_main.model;

public class ProjectMemberBean {
    private Integer id;

    private Integer projectName;

    private String projectMember;

    private Integer roleName;

    public ProjectMemberBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getProjectName() {
        return projectName;
    }

    public void setProjectName(Integer projectName) {
        this.projectName = projectName;
    }

    public String getProjectMember() {
        return projectMember;
    }

    public void setProjectMember(String projectMember) {
        this.projectMember = projectMember;
    }

    public Integer getRoleName() {
        return roleName;
    }

    public void setRoleName(Integer roleName) {
        this.roleName = roleName;
    }

    @Override
    public String toString() {
        return "ProjectMemberBean{" +
                "id=" + id +
                ", projectName=" + projectName +
                ", projectMember='" + projectMember + '\'' +
                ", roleName=" + roleName +
                '}';
    }
}
