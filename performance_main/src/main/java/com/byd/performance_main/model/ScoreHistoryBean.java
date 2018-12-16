package com.byd.performance_main.model;

public class ScoreHistoryBean {

    private Integer id;

    private String userId;

//    private Integer userGroupRole;
//
//    private String currentGroupName;
//
//    private Integer userProjectRole;
//
//    private String currentProjectName;

    private Integer groupScore;

    private Integer groupScoreWeight;

    private Integer projectScore;

    private Integer projectScoreWeight;

    private Integer ownScore;

    private Integer ownScoreWeight;

    //private Integer memberAllScore;

    //private Integer memberAllScoreWeight;

//    private Integer bossScore;
//
//    private Integer bossScoreWeight;

    private Integer totalScore;

    private String scoreTime;

    private String updateTime;

    public ScoreHistoryBean() {
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

//    public Integer getUserGroupRole() {
//        return userGroupRole;
//    }
//
//    public void setUserGroupRole(Integer userGroupRole) {
//        this.userGroupRole = userGroupRole;
//    }
//
//    public String getCurrentGroupName() {
//        return currentGroupName;
//    }
//
//    public void setCurrentGroupName(String currentGroupName) {
//        this.currentGroupName = currentGroupName;
//    }
//
//    public Integer getUserProjectRole() {
//        return userProjectRole;
//    }
//
//    public void setUserProjectRole(Integer userProjectRole) {
//        this.userProjectRole = userProjectRole;
//    }
//
//    public String getCurrentProjectName() {
//        return currentProjectName;
//    }
//
//    public void setCurrentProjectName(String currentProjectName) {
//        this.currentProjectName = currentProjectName;
//    }

    public Integer getGroupScore() {
        return groupScore;
    }

    public void setGroupScore(Integer groupScore) {
        this.groupScore = groupScore;
    }

    public Integer getGroupScoreWeight() {
        return groupScoreWeight;
    }

    public void setGroupScoreWeight(Integer groupScoreWeight) {
        this.groupScoreWeight = groupScoreWeight;
    }

    public Integer getProjectScore() {
        return projectScore;
    }

    public void setProjectScore(Integer projectScore) {
        this.projectScore = projectScore;
    }

    public Integer getProjectScoreWeight() {
        return projectScoreWeight;
    }

    public void setProjectScoreWeight(Integer projectScoreWeight) {
        this.projectScoreWeight = projectScoreWeight;
    }

    public Integer getOwnScore() {
        return ownScore;
    }

    public void setOwnScore(Integer ownScore) {
        this.ownScore = ownScore;
    }

    public Integer getOwnScoreWeight() {
        return ownScoreWeight;
    }

    public void setOwnScoreWeight(Integer ownScoreWeight) {
        this.ownScoreWeight = ownScoreWeight;
    }
    /*
    public Integer getMemberAllScore() {
        return memberAllScore;
    }

    public void setMemberAllScore(Integer memberAllScore) {
        this.memberAllScore = memberAllScore;
    }

    public Integer getMemberAllScoreWeight() {
        return memberAllScoreWeight;
    }

    public void setMemberAllScoreWeight(Integer memberAllScoreWeight) {
        this.memberAllScoreWeight = memberAllScoreWeight;
    }
    */

//    public Integer getBossScore() {
//        return bossScore;
//    }
//
//    public void setBossScore(Integer bossScore) {
//        this.bossScore = bossScore;
//    }
//
//    public Integer getBossScoreWeight() {
//        return bossScoreWeight;
//    }
//
//    public void setBossScoreWeight(Integer bossScoreWeight) {
//        this.bossScoreWeight = bossScoreWeight;
//    }

    public Integer getTotalScore() {
        return totalScore;
    }

    public void setTotalScore(Integer totalScore) {
        this.totalScore = totalScore;
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
        return "ScoreHistoryBean{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
//                ", userGroupRole=" + userGroupRole +
//                ", currentGroupName='" + currentGroupName + '\'' +
//                ", userProjectRole=" + userProjectRole +
//                ", currentProjectName='" + currentProjectName + '\'' +
                ", groupScore=" + groupScore +
                ", groupScoreWeight=" + groupScoreWeight +
                ", projectScore=" + projectScore +
                ", projectScoreWeight=" + projectScoreWeight +
                ", ownScore=" + ownScore +
                ", ownScoreWeight=" + ownScoreWeight +
               // ", memberAllScore=" + memberAllScore +
               // ", memberAllScoreWeight=" + memberAllScoreWeight +
//                ", bossScore=" + bossScore +
//                ", bossScoreWeight=" + bossScoreWeight +
                ", totalScore=" + totalScore +
                ", scoreTime='" + scoreTime + '\'' +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
