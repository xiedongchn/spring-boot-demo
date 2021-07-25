package com.xd.springboot.product.controller;

import com.xd.springboot.product.domain.UserDO;
import com.xd.springboot.product.mapper.UserMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.*;

/**
 * @author xd
 * Created on 2018/7/23 22:08
 */
@Scope("prototype")
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private UserMapper userMapper;

    @PostMapping("/queryUser")
    public UserDO queryUser(@Param("id") Integer id) {
        return userMapper.selectById(id);
    }

    @PostMapping("/queryUserList")
    public List<UserDO> queryUserList() {
        return userMapper.selectList(null);
    }

    //创建线程安全的map
    private static final Map<Long, UserDO> userMap = Collections.synchronizedMap(new HashMap<>());

    @GetMapping(value = "/getUserList")
    public List<UserDO> getUserList() {
        //处理"/user/"的get请求，用来获取用户列表
        return new ArrayList<>(userMap.values());
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST)
    public String postUser(@ModelAttribute UserDO userDO) {
        // 处理"/users/"的POST请求，用来创建User
        // 除了@ModelAttribute绑定参数之外，还可以通过@RequestParam从页面中传递参数
        userMap.put(userDO.getId(), userDO);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    public UserDO getUser(@PathVariable("id") Long id) {
        // 处理"/users/{id}"的GET请求，用来获取url中id值的User信息
        // url中的id可通过@PathVariable绑定到函数的参数中
        return userMap.get(id);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.PUT)
    public String putUser(@PathVariable("id") Long id, @ModelAttribute UserDO userDO) {
        // 处理"/users/{id}"的PUT请求，用来更新User信息
        UserDO u = userMap.get(id);
        u.setName(userDO.getName());
        u.setAge(userDO.getAge());
        userMap.put(id, u);
        return "success";
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable("id") Long id) {
        // 处理"/users/{id}"的DELETE请求，用来删除User
        userMap.remove(id);
        return "success";
    }
}
