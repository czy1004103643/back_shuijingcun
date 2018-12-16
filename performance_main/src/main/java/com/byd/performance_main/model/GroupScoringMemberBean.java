package com.byd.performance_main.model;

public class GroupScoringMemberBean {
    private Integer id;

    private String userId;

    private String currentGroupName;

    private Integer currentRole;

    private String ratingUserId;

    private Integer ratingUserRole;

    private Integer weight;

    private Integer score;

    private String scoreTime;

    private String updateTime;

    public GroupScoringMemberBean() {
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

    public String getCurrentGroupName() {
        return currentGroupName;
    }

    public void setCurrentGroupName(String currentGroupName) {
        this.currentGroupName = currentGroupName;
    }

    public Integer getCurrentRole() {
        return currentRole;
    }

    public void setCurrentRole(Integer currentRole) {
        this.currentRole = currentRole;
    }

    public String getRatingUserId() {
        return ratingUserId;
    }

    public void setRatingUserId(String ratingUserId) {
        this.ratingUserId = ratingUserId;
    }

    public Integer getRatingUserRole() {
        return ratingUserRole;
    }

    public void setRatingUserRole(Integer ratingUserRole) {
        this.ratingUserRole = ratingUserRole;
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
        return "GroupScoringMemberBean{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", currentGroupName='" + currentGroupName + '\'' +
                ", currentRole=" + currentRole +
                ", ratingUserId='" + ratingUserId + '\'' +
                ", ratingUserRole=" + ratingUserRole +
                ", weight=" + weight +
                ", score=" + score +
                ", scoreTime='" + scoreTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
