package com.xd.springboot.product.controller;

import com.xd.springboot.product.service.UserService;
import org.springframework.data.annotation.Reference;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xd
 * Created on 2018/7/23 11:10
 */
@RestController
public class HelloController {

    //注入服务提供方暴露的接口，通过@Reference注解，dubbo会在扫描的时候自动代理接口，然后通过rpc调用远程服务。
    //如果用xml配置方式，需要将@Reference换成@Autowired。
    //@Reference
    @Reference
    private UserService userService;

    @GetMapping("/hello")
    public String index() {
        return userService.sayHello("Bob");
    }
}
