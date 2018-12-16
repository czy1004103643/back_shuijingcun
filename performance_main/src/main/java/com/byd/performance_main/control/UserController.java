package com.byd.performance_main.control;


import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.UserBean;
import com.byd.performance_utils.code.PermissionCode;
import com.byd.performance_utils.code.StateCode;
import com.byd.performance_utils.utils.CommonMethod;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping(value = "/user")
public class UserController extends BaseController {

    @ResponseBody
    @PostMapping("/add")
    public Map<String, Object> addUser(@RequestParam(value = "cookie") String cookie,
                                       UserBean user) {
        urlLogInfo("/user/add");
        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_USER);

        checkUserIdIsDuplicateFromUserBean(user.getUserId());

        Map<String, Object> details = new HashMap<>();
        boolean result = false;
        String message = "";
        if (user.getUserName() == null || user.getUserName().isEmpty()) {
            message = "用户姓名不能为空";
        } else if (user.getUserPassword() == null || user.getUserPassword().isEmpty()) {
            message = "密码不能为空";
        } else {
            user.setUserCookie(null);
            if (user.getUserAvatar() != null && user.getUserAvatar().isEmpty()) {
                user.setUserAvatar(null);
            }
            int add_result = userService.addUser(user);
            if (add_result == 1) {
                message = "用户添加成功";
                result = true;
            }
        }

        details.put("add_user", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);

        return map;
    }


    @ResponseBody
    @PostMapping("/delete-one")
    public Map<String, Object> deleteUser(@RequestParam(value = "cookie") String cookie,
                                          @RequestParam(value = "userId") String userId) {
        urlLogInfo("/user/delete_one");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_USER);

        Map<String, Object> details = new HashMap<>();
        boolean result = false;

        int add_result = userService.deleteUser(userId);
        if (add_result == 1)
            result = true;

        details.put("delete_one", result);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);

        return map;
    }

    @ResponseBody
    @PostMapping("/delete-many")
    public Map<String, Object> deleteUsers(@RequestParam(value = "cookie") String cookie,
                                           @RequestParam(value = "userIds") String userIds) {
        urlLogInfo("/user/delete_many");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_MODIFY_USER);

        Map<String, Object> details = new HashMap<>();
        Map<String, Boolean> delete_result_map = new HashMap<>();
        String[] _ids = userIds.split(",");

        ArrayList<String> ids_array = new ArrayList<>();
        for (int i = 0; i < _ids.length; i++) {
            if (!ids_array.contains(_ids[i])) {
                ids_array.add(_ids[i]);
            }
        }

        for (int i = 0; i < ids_array.size(); i++) {
            delete_result_map.put(ids_array.get(i), false);
        }

        delete_result_map = userService.deleteUser(ids_array);

        details.put("delete_many", delete_result_map);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);

        return map;
    }

    @ResponseBody
    @GetMapping("/ownUser")
    public Object findOwnUser(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/user/ownUser");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_USER);

        HashMap<String, Object> details = new HashMap<>();
        UserBean userBean = userService.findUserFromUserId(userService.findUserIdFromCookie(cookie));

        if (userBean != null) {
            userBean.setUserPassword(null);
            userBean.setUserCookie(null);
            details.put("user", userBean);
        } else {
            details.put("user", null);
        }
        return CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
    }

    @ResponseBody
    @GetMapping("/allUsers")
    public Map<String, Object> findAllUser(@RequestParam(value = "cookie") String cookie,
                                           @RequestParam(name = "pageNum", required = false, defaultValue = "1")
                                                   int pageNum,
                                           @RequestParam(name = "pageSize", required = false, defaultValue = "10")
                                                   int pageSize) {
        urlLogInfo("/user/allUsers");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_USER);

        HashMap<String, Object> details = new HashMap<>();
        PageInfo<UserBean> allUser = userService.findAllUser(pageNum, pageSize);

        details.put("allUsers", allUser);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    @ResponseBody
    @GetMapping("/all")
    public Map<String, Object> findAllUser(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/user/all");

        checkCookieAndRole(cookie, PermissionCode.ALLOW_VIEW_USER);
        Map<String, Object> details = new HashMap<>();

        List<UserBean> userBeans = userService.findAllUser();

        details.put("allUsers", userBeans);
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

}
