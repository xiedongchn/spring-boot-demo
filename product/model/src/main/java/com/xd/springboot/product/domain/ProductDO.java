package com.xd.springboot.product.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 贷款品种
 *
 * @author xd
 * Created on 2021/6/1
 */
@Data
public class ProductDO {

    /**
     * 产品id
     */
    private Long id;

    /**
     * 产品总额度
     */
    private BigDecimal totalQuota;
}
