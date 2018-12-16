package com.byd.performance_utils.utils;

import com.byd.performance_utils.base.BaseCommonController;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetAddress;
import java.net.UnknownHostException;

public class ServerMessage {
    private static Logger logger = LoggerFactory.getLogger(BaseCommonController.class);

    /**
     * 获取本地IP地址
     */
    public static String getHostAddress() {
        InetAddress address;
        String ip;
        try {
            address = InetAddress.getLocalHost();
            ip = address.getHostAddress();//获得本机IP
        } catch (UnknownHostException e) {
            e.printStackTrace();
            logger.error(e.getMessage(), e);
            ip = "UnknownHost";
        }
        return ip;
    }
}
