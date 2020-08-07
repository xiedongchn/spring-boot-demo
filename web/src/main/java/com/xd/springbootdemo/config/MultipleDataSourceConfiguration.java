package com.xd.springbootdemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

/**
 * 注意，使用注解式的数据库配置，会覆盖配置文件的数据库配置
 * 使用@Configuration，配合@Bean注解，会将构造出来的DataSource注入到类似mybatis中，如下
 * {@link org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration#sqlSessionFactory}
 */
//@Configuration
public class MultipleDataSourceConfiguration {

    @Bean
    @Primary
    public DataSource masterDataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        return builder.driverClassName("com.mysql.cj.jdbc.Driver").
                url("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true").
                username("root").
                password("87654321").build();
    }

    @Bean
    public DataSource slaveDataSource() {
        DataSourceBuilder<?> builder = DataSourceBuilder.create();
        return builder.driverClassName("com.mysql.cj.jdbc.Driver").
                url("jdbc:mysql://127.0.0.1:3306/test?serverTimezone=UTC&useUnicode=true&characterEncoding=utf-8&useSSL=false&allowPublicKeyRetrieval=true").
                username("root").
                password("87654321").build();
    }
}
