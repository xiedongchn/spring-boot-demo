package com.xd.springbootdemo.mapper;

import com.xd.springbootdemo.domain.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface UserMapper {

    int insertUser(User user);

    User selectById(Integer id);

    List<User> selectAllUser();
}
