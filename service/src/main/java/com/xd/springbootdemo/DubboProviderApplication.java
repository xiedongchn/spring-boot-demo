package com.xd.springbootdemo;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

@SpringBootApplication
@ImportResource("classpath:*.xml")  //加载xml文件
public class DubboProviderApplication {

    private static Logger logger = LoggerFactory.getLogger(DubboProviderApplication.class);

    public static void main(String[] args) {
        logger.info("Initial context start.");
        SpringApplication.run(DubboProviderApplication.class, args);
        logger.info("Initial context end.");
    }
}
