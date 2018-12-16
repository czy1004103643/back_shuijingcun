package com.byd.performance_main.control;


import com.byd.performance_main.base.BaseController;
import com.byd.performance_main.model.UserBean;
import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.Date;

@Controller
//@RequestMapping(value = "/html")
public class HtmlController extends BaseController {

    @RequestMapping(value = "/hello", method = RequestMethod.GET)
    public String getHello() {
        Date currentDate = CommonMethod.getCurrentDate();
        urlLogInfo("/hello");
        return "Current Date: " + currentDate.toString() + " hello world";
    }

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String showIndexHtml() {
        urlLogInfo("/index");
        return "/index";
    }

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String showIndexHtml(Model mode) {
        urlLogInfo("/test");
        UserBean userBean = new UserBean();
        userBean.setUserId("3050111");
        userBean.setUserName("玲玲");
        userBean.setUserPassword("12345");
        mode.addAttribute("userBean", userBean);
        return "/test";
    }

}
