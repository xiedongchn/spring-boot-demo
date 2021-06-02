package com.xd.springboot.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 贷款品种
 *
 * @author xd
 * Created on 2021/6/1
 */
@Data
public class Product {

    /**
     * 产品id
     */
    private Long id;

    /**
     * 产品总额度
     */
    private BigDecimal totalQuota;
}
