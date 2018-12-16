package com.byd.performance_main.handler;

import com.byd.performance_utils.utils.CommonMethod;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(value = Exception.class)
    @ResponseBody
    private Map<String, Object> exceptionHandler(HttpServletRequest httpServletRequest, Exception e) {
        return CommonMethod.formatJsonMessage(e);
    }
}
