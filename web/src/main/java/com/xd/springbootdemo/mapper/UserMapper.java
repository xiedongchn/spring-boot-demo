package com.xd.springbootdemo.mapper;

import com.xd.springbootdemo.domain.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {

    int insertUser(User user);

    User selectById(Integer id);

}
