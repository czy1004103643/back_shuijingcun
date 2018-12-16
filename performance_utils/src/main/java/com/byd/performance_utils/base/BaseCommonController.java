package com.byd.performance_utils.base;

import com.byd.performance_utils.utils.ServerMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

public class BaseCommonController {

    @Value("${environment}")
    private String env;

    @Value("${server.port}")
    private String port;

    public Logger logger = LoggerFactory.getLogger(BaseCommonController.class);

    public void urlLogInfo(Object object) {
//        logger.info(env);
        if (env.equals("product"))
            return;
        logger.info("已访问url： http://" + ServerMessage.getHostAddress() + ":" + port + String.valueOf(object));
    }

    public void logInfo(Object object) {
        if (env.equals("product"))
            return;
        logger.info(String.valueOf(object));
    }
}
