package com.byd.performance_main.model;

public class ProjectOwnScoringBean {
    private Integer id;

    private String userId;

    private String currentProjectName;

    private Integer userRole;

    private String workContent;

    private Integer weight;

    private Integer score;

    private String scoreTime;

    private String updateTime;

    public ProjectOwnScoringBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getCurrentProjectName() {
        return currentProjectName;
    }

    public void setCurrentProjectName(String currentProjectName) {
        this.currentProjectName = currentProjectName;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getWorkContent() {
        return workContent;
    }

    public void setWorkContent(String workContent) {
        this.workContent = workContent;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(String scoreTime) {
        this.scoreTime = scoreTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ProjectOwnScoringBean{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", currentProjectName='" + currentProjectName + '\'' +
                ", userRole=" + userRole +
                ", workContent='" + workContent + '\'' +
                ", weight=" + weight +
                ", score=" + score +
                ", scoreTime='" + scoreTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
