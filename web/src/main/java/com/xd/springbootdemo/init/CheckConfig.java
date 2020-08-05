package com.xd.springbootdemo.init;

import javax.annotation.PostConstruct;

/**
 * Description
 *
 * @author xd
 * Created on 2020/7/31 00:02
 */
public class CheckConfig {

    //@PostConstruct
    public void check() {
        System.out.println("check start");
    }
}
