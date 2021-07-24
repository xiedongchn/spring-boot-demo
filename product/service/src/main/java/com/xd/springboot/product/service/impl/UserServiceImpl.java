package com.xd.springboot.product.service.impl;

import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.xd.springboot.product.domain.UserDO;
import com.xd.springboot.product.mapper.UserMapper;
import com.xd.springboot.product.service.UserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * dubbo也有@Service注解，不是spring的。若使用xml配置方式暴露接口，则不需要该注解。
 *
 * @author xd
 * Created on 2018/7/24 09:29
 */
@Service
public class UserServiceImpl implements UserService {

    @Resource
    private UserMapper userMapper;

    @Override
    public String sayHello(String something) {
        return "Hello, " + something;
    }

    @Override
    public void create(String name, Integer age) {
        UserDO userDO = new UserDO();
        userDO.setName(name);
        userDO.setAge(age);
        userMapper.insert(userDO);
    }

    @Override
    public void deleteByName(String name) {
        userMapper.delete((new UpdateWrapper<UserDO>()).eq("name", name));
    }

    @Override
    public Integer getAllUsers() {
        return userMapper.selectCount(null);
    }

    @Override
    public void deleteAllUsers() {
        userMapper.delete(null);
    }
}
