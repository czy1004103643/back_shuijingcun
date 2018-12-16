package com.byd.performance_main.common_control;

import com.byd.performance_main.base.BaseController;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

//设置默认404页面
@Controller
public class ErrorPageController extends BaseController implements ErrorController {

    @Override
    public String getErrorPath() {
        urlLogInfo("/404 自定义错误页面");
        return "/404";
    }

    @RequestMapping(value = "error")
    public String showErrorPage() {
        return getErrorPath();
    }
}
