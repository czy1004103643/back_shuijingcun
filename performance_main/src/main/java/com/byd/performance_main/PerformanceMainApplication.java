package com.byd.performance_main;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

@SpringBootApplication
@MapperScan("com.byd.performance_main.dao")
//@EnableFeignClients
public class PerformanceMainApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(PerformanceMainApplication.class, args);
    }

    //    设置打包为war包
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(PerformanceMainApplication.class);
    }
}
