package com.xd.springbootdemo.redis.domain;

import lombok.Data;

import java.math.BigDecimal;

/**
 * 产品额度
 *
 * @author xd
 * Created on 2021/6/1
 */
@Data
public class ProductQuota {

    private Long productId;

    private BigDecimal totalQuota;
}
