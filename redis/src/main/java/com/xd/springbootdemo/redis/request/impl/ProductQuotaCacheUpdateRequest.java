package com.xd.springbootdemo.redis.request.impl;

import com.xd.springbootdemo.redis.request.AbstractRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.math.BigDecimal;

/**
 * 额度更新请求
 *
 * @author xd
 * Created on 2021/6/1
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class ProductQuotaCacheUpdateRequest extends AbstractRequest {
    private static final long serialVersionUID = -4456034642635348307L;

    private Long productId;

    private BigDecimal amount;
}
