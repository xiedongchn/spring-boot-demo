package com.xd.springbootdemo.config;

import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * 如果数据库中的命名是xx_xx ，pojo中的对象是xxXx，此时注解的方式就是映射不上，解决如下
 * 注意：但是未测试过是否生效
 *
 * @author xd
 * Created on 2020.08.07
 */
//@Configuration
public class MybatisConfig {

    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return configuration -> configuration.setMapUnderscoreToCamelCase(true);
    }
}
