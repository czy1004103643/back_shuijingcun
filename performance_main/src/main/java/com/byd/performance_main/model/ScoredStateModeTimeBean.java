package com.byd.performance_main.model;

public class ScoredStateModeTimeBean {
    private Integer id;

    private String scoreTime;

    private Integer isScored;

    private String updateTime;

    public ScoredStateModeTimeBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getScoreTime() {
        return scoreTime;
    }

    public void setScoreTime(String scoreTime) {
        this.scoreTime = scoreTime;
    }

    public Integer getIsScored() {
        return isScored;
    }

    public void setIsScored(Integer isScored) {
        this.isScored = isScored;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "ScoredStateModeTimeBean{" +
                "id=" + id +
                ", scoreTime='" + scoreTime + '\'' +
                ", isScored=" + isScored +
                ", updateTime='" + updateTime + '\'' +
                '}';
    }
}
