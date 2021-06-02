package com.xd.springboot.service;

/**
 * @author xd
 * Created on 2018/7/24 09:28
 */
public interface UserService {

    /**
     * 打招呼
     *
     * @param something 想说的话
     */
    String sayHello(String something);

    /**
     * 新增一个用户
     *
     * @param name 名称
     * @param age  年龄
     */
    void create(String name, Integer age);

    /**
     * 根据name删除一个用户高
     *
     * @param name 名称
     */
    void deleteByName(String name);

    /**
     * 获取用户总量
     */
    Integer getAllUsers();

    /**
     * 删除所有用户
     */
    void deleteAllUsers();
}
