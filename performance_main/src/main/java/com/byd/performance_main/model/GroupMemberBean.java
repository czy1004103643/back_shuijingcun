package com.byd.performance_main.model;

public class GroupMemberBean {
    private Integer id;

    private Integer groupName;

    private String groupMember;

    private Integer isLeader;

    public GroupMemberBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getGroupName() {
        return groupName;
    }

    public void setGroupName(Integer groupName) {
        this.groupName = groupName;
    }

    public String getGroupMember() {
        return groupMember;
    }

    public void setGroupMember(String groupMember) {
        this.groupMember = groupMember;
    }

    public Integer getIsLeader() {
        return isLeader;
    }

    public void setIsLeader(Integer isLeader) {
        this.isLeader = isLeader;
    }

    @Override
    public String toString() {
        return "GroupMemberBean{" +
                "id=" + id +
                ", groupName=" + groupName +
                ", groupMember='" + groupMember + '\'' +
                ", isLeader=" + isLeader +
                '}';
    }
}
