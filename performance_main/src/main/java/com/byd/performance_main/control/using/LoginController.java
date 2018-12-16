package com.byd.performance_main.control.using;

import com.alibaba.fastjson.JSONObject;
import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.UserBean;
import com.byd.performance_utils.code.SysMenuCode;
import com.byd.performance_utils.utils.CommonMethod;
import com.byd.performance_utils.code.StateCode;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.Cookie;
import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping(value = "/login")
public class LoginController extends BaseController {

    @PostMapping(value = "/account")
    @ResponseBody
    /*
    旧登录接口
     */
    public Map<String, Object> check_login(
            @RequestParam(value = "account") String account,
            @RequestParam(value = "passwd") String passwd) {
        urlLogInfo("/login/account");
        String password = null;
        String message = "";
        if (account != null && !account.isEmpty()) {
            password = userService.findPasswordFromUserId(account);
        }
        HashMap<String, Object> details = new HashMap<>();
        if (password != null && !password.isEmpty()) {
            //初始密码默认等于用户名，不用加密
            if (password.equals(passwd) || md5_32bites(passwd).equals(password)) {
                details.put("success", true);
                Cookie cookie = new Cookie("byd_performance", account + "_" + String.valueOf(CommonMethod.getCurrentTimestamp()));
                details.put("cookie", cookie);
                String userCookie = cookie.getName() + "=" + cookie.getValue();
                userService.updateUserCookie(userCookie, account);
            } else {
                message = "登陆失败！密码和工号不匹配。";
                details.put("result", false);
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.ACCOUNT_OR_PASSWORD_FAILED, message, details);
                return map;
            }
        } else {
            message = "登陆失败！密码和工号不匹配。";
            details.put("result", false);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.ACCOUNT_OR_PASSWORD_FAILED, message, details);
            return map;
        }
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    /*
    新登录接口(common account system)
     */
    @PostMapping(value = "/account_new")
    @ResponseBody
    public Map<String, Object> check_login_new(@RequestParam("account") String account,
                                                  @RequestParam("passwd") String passwd){
        urlLogInfo("/login/account_new");
        Map<String, Object> details = new HashMap<>();
        Map<String, Object> mapResult = new HashMap<>();
        RestTemplate restTemplate = new RestTemplate();
        String url = "http://10.4.0.190:8086/api/example/userlogin";
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
        //header.set
        MultiValueMap<String, String> map= new LinkedMultiValueMap<>();
        map.add("emp_id",account);//参数1
        map.add("pass",passwd);//参数2
        map.add("X-API-KEY", "TEST@123");//接口凭证

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(map, headers);
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(url, request, String.class);
            //转格式
            JSONObject jsonObject = JSONObject.parseObject(response.getBody());
            String userId = jsonObject.getString("ed_emp_id");//工号
            String userName = jsonObject.getString("ed_emp_name");//姓名
            mapResult = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", jsonObject);
            return mapResult;
        }catch (Exception ex){
            if(ex.getMessage().contains("403")) {
                details.put("result","KEY值错误！");
                return mapResult = CommonMethod.formatJsonMessage(StateCode.PERMISSION_DENIED, ex.getMessage(), details);
            }else if(ex.getMessage().contains("404")){
                details.put("result", "工号或密码错误！");
                return mapResult = CommonMethod.formatJsonMessage(StateCode.PARAM_INVALID, ex.getMessage(), details);
            }else if(ex.getMessage().contains("405")){
                details.put("result", "URL错误!");
                return mapResult = CommonMethod.formatJsonMessage(StateCode.MISS_PARAMETER, ex.getMessage(), details);
            }else{
                details.put("result", "未知错误!");
                return mapResult = CommonMethod.formatJsonMessage(StateCode.UNKNOWN_ERROR, ex.getMessage(), details);
            }
        }
    }


    @PostMapping(value = "/cookie")
    @ResponseBody
    public Map<String, Object> check_login(@RequestParam(value = "cookie") String cookie) {
        urlLogInfo("/login/cookie");
        String userId = null;
        if (cookie != null && !cookie.isEmpty()) {
            userId = userService.findUserIdFromCookie(cookie);
        }
        Map<String, Object> details = new HashMap<>();
        if (userId != null && !userId.isEmpty()) {
            details.put("success", true);
        } else {
            details.put("success", false);
        }

        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    @GetMapping(value = "/logout")
    @ResponseBody
    public Map<String, Object> logout(@RequestParam(value = "cookie") String cookie) {

        urlLogInfo("/login/logout");
        Map<String, Object> details = new HashMap<>();
        int result = userService.updateUserCookie("", checkCookieValid(cookie));
        if (result == 1) {
            details.put("logout_success", true);
        } else {
            details.put("logout_success", false);
        }
        Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, "", details);
        return map;
    }

    /*
    修改密码
     */

    @PostMapping(value = "/changePassword")
    @ResponseBody
    public Map<String, Object> changePassword(@RequestParam("cookie") String cookie, @RequestParam("oldPassword") String oldPassword,
                                              @RequestParam("newPassword") String newPassword) {

        urlLogInfo("/login/changePassword");
        String userId = checkCookieValid(cookie);
        Map<String, Object> details = new HashMap<>();
        String message = "";
        UserBean userBean = new UserBean();
        String Password = userService.findPasswordFromUserId(userId);
        if (oldPassword.equals(userId) || md5_32bites(oldPassword).equals(Password)) {

        } else {
            message = "旧密码与工号不匹配!";
            details.put("result", true);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.DATABASE_OPERATION_FAILED, message, details);
            return map;
        }
        int passwordLength = newPassword.length();//判断密码长度是否为6-16位
        if ((passwordLength < 5) || (passwordLength >= 16)) {
            message = "密码长度必须大于5位且小于16位！";
            details.put("result", false);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.ACCOUNT_OR_PASSWORD_FAILED, message, details);
            return map;
        }
        int passwordNumber = newPassword.matches(".*\\d+.*") ? 1 : 0;//判断密码是否包含数字
        int passwordAbc = newPassword.matches(".*[a-zA-Z]+.*") ? 1 : 0;//判断密码是否包含字母
        int passwordSpecialCharacters = newPassword.matches(".*[~!@#$%^&*()_+|<>,.?/:;'\\[\\]{}\"]+.*") ? 1 : 0;//判断密码是否含有特殊字符
        if ((passwordNumber == 1 && passwordAbc == 1) || (passwordNumber == 1 && passwordSpecialCharacters == 1) || (passwordAbc == 1 && passwordSpecialCharacters == 1)) {
            String encryptPassword = md5_32bites(newPassword);
            //将密码更新到数据库
            userBean.setUserId(checkCookieValid(cookie));
            userBean.setUserPassword(encryptPassword);
            int encryptResult = userService.updatePassword(userBean);
            if (encryptResult == 1) {
                message = "密码更改成功!";
                details.put("result", true);
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
                return map;
            } else {
                message = "密码更改失败!";
                details.put("result", false);
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, message, details);
                return map;
            }
        } else {
            message = "密码必须含有数字、字母和特殊字符中的两种!";
            details.put("result", false);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.ACCOUNT_OR_PASSWORD_FAILED, message, details);
            return map;
        }
    }

    /**
     * 简单加密
     */
    public static String convertMD5(String password) {

        char[] a = password.toCharArray();
        for (int i = 0; i < a.length; i++) {
            a[i] = (char) (a[i] ^ 't');
        }
        String s = new String(a);
        return s;
    }

    /*
    32位加密
     */
    public static String md5_32bites(String encryptPassword) {
        MessageDigest messageDigest;
        try {
            messageDigest = MessageDigest.getInstance("MD5");
            byte[] messageDigestBytes = messageDigest.digest(encryptPassword.getBytes());
            StringBuffer hexValue = new StringBuffer();
            for (int i = 0; i < messageDigestBytes.length; i++) {
                int val = ((int) messageDigestBytes[i]) & 0xff;
                if (val < 16)
                    hexValue.append("0");
                hexValue.append(Integer.toHexString(val));
            }
            encryptPassword = hexValue.toString();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        return encryptPassword;
    }

    /*
    重置密码
     */
    @PostMapping(value = "resetPassword")
    @ResponseBody
    public Map<String, Object> retrievePassword(@RequestParam("cookie") String cookie, @RequestParam("userId") String userId) {
        urlLogInfo("/login/resetPassword");
        checkCookieValid(cookie);
        UserBean userBean = userService.findUserFromUserId(userId);
        String resetPasswordUserId = userBean.getUserId();
        Map<String, Object> details = new HashMap<>();
        String message = "";
        checkModifyPermission(checkCookieValid(cookie),SysMenuCode.UserManagementCode);
        if (resetPasswordUserId != null && !resetPasswordUserId.equals("")) {
            userBean.setUserPassword(resetPasswordUserId);
            int result = userService.updatePassword(userBean);
            if (result == 1) {
                message = "密码重置成功！";
                details.put("result", true);
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.SUCCESS_PROCESS, message, details);
                return map;

            } else {
                message = "密码重置失败！";
                details.put("result", false);
                Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.DATABASE_ERROR, message, details);
                return map;
            }
        } else {
            message = "工号不存在！";
            details.put("result", false);
            Map<String, Object> map = CommonMethod.formatJsonMessage(StateCode.USERID_DUPLCATE, message, details);
            return map;
        }
    }
}
