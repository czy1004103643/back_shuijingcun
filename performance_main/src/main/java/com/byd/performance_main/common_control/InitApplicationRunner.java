package com.byd.performance_main.common_control;

import com.byd.performance_utils.utils.CommonMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

/**
 * 服务器初始化时需要执行的一些方法
 *
 * @Order 注解的执行优先级是按value值从小到大顺序
 */
@Component
@Order(value = 1)
public class InitApplicationRunner implements ApplicationRunner {

    //获取上传的文件夹，具体路径参考application.properties中的配置
    @Value("${web.relative-path}")
    private String relativePath;
    @Value("${web.file}")
    private String file;
    @Value("${web.image}")
    private String image;

    private static final Logger logger = LoggerFactory.getLogger(InitApplicationRunner.class);

    @Override
    public void run(ApplicationArguments args) throws Exception {
        logger.info("==服务启动后，初始化数据操作==");

        CommonMethod.initUploadFile(relativePath, image, file);
    }
}
