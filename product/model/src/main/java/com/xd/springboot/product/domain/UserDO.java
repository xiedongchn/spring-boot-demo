package com.xd.springboot.product.domain;

import com.xd.springboot.product.validator.CardNumberValidator;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author xd
 * Created on 2018/7/23 17:04
 */
@Data
public class UserDO implements Serializable {
    private static final long serialVersionUID = 7208147318987767346L;

    @NotNull
    @Max(value = 10000)
    private Long id;

    @NotNull
    private String name;

    @NotNull
    @CardNumberValidator(message = "卡号必须以001开头接'-'，数字结尾")
    private String idNo;

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

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }
}
