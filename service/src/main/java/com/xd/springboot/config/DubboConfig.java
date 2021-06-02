package com.xd.springboot.config;

import com.alibaba.dubbo.config.ApplicationConfig;
import com.alibaba.dubbo.config.ProtocolConfig;
import com.alibaba.dubbo.config.RegistryConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;

/*@Configuration
@PropertySource("classpath:dubbo.properties")
@ImportResource({"classpath:*.xml"})*/
public class DubboConfig {

    private String scan;

    private ApplicationConfig applicationConfig;

    private RegistryConfig registryConfig;

    private ProtocolConfig protocolConfig;

}