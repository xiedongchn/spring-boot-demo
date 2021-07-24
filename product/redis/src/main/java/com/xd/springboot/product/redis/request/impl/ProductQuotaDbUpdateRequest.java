package com.xd.springboot.product.redis.request.impl;

import com.xd.springboot.product.redis.request.AbstractRequest;
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
public class ProductQuotaDbUpdateRequest extends AbstractRequest {
    private static final long serialVersionUID = -1803771308303534712L;

    private Long productId;

    private BigDecimal amount;

}
