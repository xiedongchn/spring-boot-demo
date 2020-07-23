package com.xd.springbootdemo.impl;

//import com.alibaba.dubbo.config.annotation.Service;
import com.xd.springbootdemo.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;

/**
 * @author xd
 * Created on 2018/7/24 09:29
 */
//@Service  //该Service注解是dubbo的注解，不是spring的。若使用xml配置方式暴露接口，则不需要该注解。
public class UserServiceImpl implements UserService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public String sayHello(String something) {
        return "Hello, " + something;
    }

    @Override
    public void create(String name, Integer age) {
        jdbcTemplate.update("insert into USER(NAME, AGE) values(?, ?)", name, age);
    }

    @Override
    public void deleteByName(String name) {
        jdbcTemplate.update("delete from USER where NAME = ?", name);
    }

    @Override
    public Integer getAllUsers() {
        return jdbcTemplate.queryForObject("select count(1) from USER", Integer.class);
    }

    @Override
    public void deleteAllUsers() {
        jdbcTemplate.update("delete from USER");
    }
}
