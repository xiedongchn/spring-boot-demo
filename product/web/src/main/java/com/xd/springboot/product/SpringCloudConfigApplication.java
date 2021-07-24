package com.xd.springboot.product;

import org.springframework.boot.Banner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Description
 *
 * @author xd
 * Created on 2020/6/5 00:12
 */
@SpringBootApplication
public class SpringCloudConfigApplication {

    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(SpringCloudConfigApplication.class);
        application.setWebApplicationType(WebApplicationType.SERVLET);
        application.setBannerMode(Banner.Mode.CONSOLE);
        application.run(args);
    }
}
