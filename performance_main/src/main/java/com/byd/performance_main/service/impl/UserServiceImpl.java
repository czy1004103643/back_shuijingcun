package com.byd.performance_main.service.impl;


import com.byd.performance_main.dao.UserDao;
import com.byd.performance_main.model.UserBean;
import com.byd.performance_main.service.UserService;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;//这里会报错，但是并不会影响

    @Override
    public int addUser(UserBean user) {
        return userDao.insert(user);
    }

    @Override
    public int updateUserCookie(String userCookie, String userId) {
        UserBean userBean = new UserBean();
        userBean.setUserCookie(userCookie);
        userBean.setUserId(userId);
        return userDao.update(userBean);
    }

    @Override
    public int updateUserPassword(String userId, String userPassword) {
        UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        userBean.setUserPassword(userPassword);
        return userDao.update(userBean);
    }

    @Override
    public List<UserBean> adminUser(UserBean userBean) {
        return userDao.adminUser(userBean);
    }

    @Override
    public int updatePassword(UserBean userBean) {
        return userDao.updatePassword(userBean);
    }

    @Override
    public int updateUserRole(UserBean userBean) {
        return userDao.updateUserRole(userBean);
    }

    @Override
    public int deleteUser(String userId) {
        UserBean userBean = new UserBean();
        userBean.setUserId(userId);
        return userDao.delete(userBean);
    }

    @Override
    public HashMap<String, Boolean> deleteUser(List<String> userIds) {
        HashMap<String, Boolean> delete_result = new HashMap<>();
        for (int i = 0; i < userIds.size(); i++) {
            String userId = userIds.get(i);
            int result_type = deleteUser(userId);
            Boolean result = false;
            if (result_type == 1) {
                result = true;
            }
            delete_result.put(userId, result);
        }

        return delete_result;
    }

    @Override
    public String findPasswordFromUserId(String userId) {
        return userDao.queryPasswordFromUserId(userId);
    }

    @Override
    public String findUserIdFromCookie(String cookie) {
        return userDao.queryUserIdFromCookie(cookie);
    }

    @Override
    public UserBean findUserFromUserId(String userId) {
        return userDao.queryUserFromUserId(userId);
    }

    @Override
    public List<UserBean> findUserFromUserName(String userName) {
        return userDao.queryUserFromUserName(userName);
    }

    /*
     * 这个方法中用到了我们开头配置依赖的分页插件pagehelper
     * 很简单，只需要在service层传入参数，然后将参数传递给一个插件的一个静态方法即可；
     * pageNum 开始页数
     * pageSize 每页显示的数据条数
     * */
    @Override
    public PageInfo<UserBean> findAllUser(int pageNum, int pageSize) {
        //将参数传给这个方法就可以实现物理分页了，非常简单。
        PageHelper.startPage(pageNum, pageSize);
        List<UserBean> userBeans = userDao.queryUserBean();
        PageInfo result = new PageInfo(userBeans);
        return result;
    }

    @Override
    public List<UserBean> findAllUser() {
        List<UserBean> userBeans = userDao.queryUserBean();
        return userBeans;
    }

    @Override
    public int updateUser(UserBean userBean) {
        return userDao.update(userBean);
    }
}
