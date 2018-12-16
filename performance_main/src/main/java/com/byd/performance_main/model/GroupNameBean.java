package com.byd.performance_main.model;

public class GroupNameBean {
    private Integer id;

    private String groupName;

    public GroupNameBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    @Override
    public String toString() {
        return "GroupNameBean{" +
                "id=" + id +
                ", groupName='" + groupName + '\'' +
                '}';
    }
}
