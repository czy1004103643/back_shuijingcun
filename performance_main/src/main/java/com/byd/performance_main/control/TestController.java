package com.byd.performance_main.control;

import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.UserPermissionBean;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.HashMap;

@RestController
@RequestMapping(value = "/mytest")
public class TestController extends BaseController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    @ResponseBody
    public String getHello() {
        urlLogInfo("/user/hello");
        Date currentDate = CommonMethod.getCurrentDate();

        return "Current Date: " + currentDate.toString() + " hello world";
    }

    @RequestMapping(method = RequestMethod.GET)
    @ResponseBody
    public String showIndexHtml() {
        urlLogInfo("/user");
        return "/index";
    }

    @RequestMapping(value = "test", method = RequestMethod.POST)
    @ResponseBody
    public int test(@RequestParam(value = "userCookie") String userCookie,
                    @RequestParam(value = "userName") String userName) {
        urlLogInfo("/test");
        int num = userService.updateUserCookie(userCookie, userName);
        return num;
    }

    @RequestMapping(value = "/permission", method = RequestMethod.GET)
    @ResponseBody
    public Object showPermission() {
        urlLogInfo("/mytest/permission");
//        HashMap<String, Object> map = new HashMap<>();
//        String permissionName0 = permissionNameService.findPermissionNameFromCode(0);
//        map.put("PermissionName0", permissionName0);
//        String permissionName1 = permissionNameService.findPermissionNameFromCode(1);
//        map.put("PermissionName1", permissionName1);
//        String permissionName2 = permissionNameService.findPermissionNameFromCode(2);
//        map.put("PermissionName2", permissionName2);
//        String permissionName3 = permissionNameService.findPermissionNameFromCode(3);
//        map.put("PermissionName3", permissionName3);
//        String permissionName4 = permissionNameService.findPermissionNameFromCode(4);
//        map.put("PermissionName4", permissionName4);
//        List<PermissionNameBean> allPermissionName = permissionNameService.findAllPermissionName();
//        map.put("allPermissionName", allPermissionName);
//        return map;

//        PermissionNameBean permissionNameBean = new PermissionNameBean();
//        permissionNameBean.setId(5);
//        permissionNameBean.setPermissionCode(4);
//        permissionNameBean.setPermissionName("哈哈哈");
//        return permissionNameService.addPermissionName(permissionNameBean);

//        HashMap<String, Object> map = new HashMap<>();
//        List<RolePermissionBean> allRolePermission = rolePermissionService.findAllRolePermission();
//        map.put("allRolePermission", allRolePermission);
//        return map;

//        HashMap<String, Object> map = new HashMap<>();
//        map.put("RolePermission0", rolePermissionService.findOwnRolePermissionBeanFromRole(0));
//        map.put("RolePermission1", rolePermissionService.findOwnRolePermissionBeanFromRole(1));
//        return map;

//        HashMap<String, Object> map = new HashMap<>();
//        List<UserPermissionBean> allUserPermission = userPermissionService.findAllUserPermission();
//        map.put("allUserPermission", allUserPermission);
//        return map;

        HashMap<String, Object> map = new HashMap<>();
        UserPermissionBean admin = userPermissionService.findOwnUserPermissionBeanFromUserId("admin");
        UserPermissionBean other = userPermissionService.findOwnUserPermissionBeanFromUserId("other");
        map.put("userPermission admin", admin);
        map.put("userPermission other", other);
        return map;
    }
}
