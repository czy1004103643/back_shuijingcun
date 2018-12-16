package com.byd.performance_main.model;

public class ScoreWeightBean {
    private Integer id;

    private Integer weightName;

    private Integer scoreValue;

    public ScoreWeightBean() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getWeightName() {
        return weightName;
    }

    public void setWeightName(Integer weightName) {
        this.weightName = weightName;
    }

    public Integer getScoreValue() {
        return scoreValue;
    }

    public void setScoreValue(Integer scoreValue) {
        this.scoreValue = scoreValue;
    }

    @Override
    public String toString() {
        return "ScoreWeightBean{" +
                "id=" + id +
                ", weightName=" + weightName +
                ", scoreValue=" + scoreValue +
                '}';
    }
}
