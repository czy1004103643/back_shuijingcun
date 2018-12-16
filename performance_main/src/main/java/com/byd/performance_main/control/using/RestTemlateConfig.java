package com.byd.performance_main.control.using;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

@Configuration
public class RestTemlateConfig {
    /*
    @Bean
    public RestTemlate restTemlate(ClientHttpRequestFactory factory){
        return new RestTemlate(factory);
    }
    */

    @Bean
    public ClientHttpRequestFactory simpleClientHttpRequestFactory(){
        SimpleClientHttpRequestFactory factory=new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(15000);
        factory.setReadTimeout(5000);
        return factory;
    }
}
