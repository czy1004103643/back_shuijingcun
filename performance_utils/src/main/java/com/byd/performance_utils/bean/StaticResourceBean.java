package com.byd.performance_utils.bean;

public class StaticResourceBean {
    private String name;
    private String absolutePath;
    private String relativePath;

    public StaticResourceBean() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbsolutePath() {
        return absolutePath;
    }

    public void setAbsolutePath(String absolutePath) {
        this.absolutePath = absolutePath;
    }

    public String getRelativePath() {
        return relativePath;
    }

    public void setRelativePath(String relativePath) {
        this.relativePath = relativePath;
    }

    @Override
    public String toString() {
        return "StaticResourceBean{" +
                "name='" + name + '\'' +
                ", absolutePath='" + absolutePath + '\'' +
                ", relativePath='" + relativePath + '\'' +
                '}';
    }
}
