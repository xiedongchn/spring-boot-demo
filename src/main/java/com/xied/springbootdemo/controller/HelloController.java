package com.xied.springbootdemo.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xd
 * Created on 2018/7/23 11:10
 */
@RestController
public class HelloController {

    @GetMapping("/hello")
    public String index() {
        return "Hello World!";
    }
}
