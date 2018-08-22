package com.xied.springbootdemo.domain.entity;

import java.io.Serializable;

/**
 * @author xd
 * Created on 2018/7/23 17:04
 */
public class User implements Serializable {
    private static final long serialVersionUID = 7208147318987767346L;

    private Long id;

    private String name;

    private Integer age;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
