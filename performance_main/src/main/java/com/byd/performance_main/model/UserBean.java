package com.byd.performance_main.model;

public class UserBean {
    private Integer id;

    private String userId;

    private String userName;

    private String userPassword;

    private String employeeLevel;

    private String userAvatar;

    private String userCookie;

    private Integer userRole;

    private String createrId;

    private String createtime;

    public UserBean() {

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

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    public String getEmployeeLevel() {
        return employeeLevel;
    }

    public void setEmployeeLevel(String employeeLevel) {
        this.employeeLevel = employeeLevel;
    }

    public String getUserAvatar() {
        return userAvatar;
    }

    public void setUserAvatar(String userAvatar) {
        this.userAvatar = userAvatar;
    }

    public String getUserCookie() {
        return userCookie;
    }

    public void setUserCookie(String userCookie) {
        this.userCookie = userCookie;
    }

    public Integer getUserRole() {
        return userRole;
    }

    public void setUserRole(Integer userRole) {
        this.userRole = userRole;
    }

    public String getCreaterId() {
        return createrId;
    }

    public void setCreaterId(String createrId) {
        this.createrId = createrId;
    }

    public String getCreatetime() {
        return createtime;
    }

    public void setCreatetime(String createtime) {
        this.createtime = createtime;
    }

    @Override
    public String toString() {
        return "UserBean{" +
                "id=" + id +
                ", userId='" + userId + '\'' +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", employeeLevel='" + employeeLevel + '\'' +
                ", userAvatar='" + userAvatar + '\'' +
                ", userCookie='" + userCookie + '\'' +
                ", userRole=" + userRole +
                ", createrId='" + createrId + '\'' +
                ", createtime='" + createtime + '\'' +
                '}';
    }
}
