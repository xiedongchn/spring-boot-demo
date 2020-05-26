package com.xd.springbootdemo.config;

import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import javax.sql.DataSource;

@Configuration
public class MultipleDataSourceConfiguration {

    @Bean
    @Primary
    public DataSource masterDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        return builder.driverClassName("com.mysql.jdbc.Driver").
                url("jdbc:mysql://127.0.0.1:3306/test").username("root").
                password("1234567").build();
    }

    @Bean
    public DataSource slaveDataSource() {
        DataSourceBuilder builder = DataSourceBuilder.create();
        return builder.driverClassName("com.mysql.jdbc.Driver").
                url("jdbc:mysql://127.0.0.1:3306/test2").username("root").
                password("1234567").build();
    }
}
