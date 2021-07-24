package com.xd.springboot.product.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * @author xd
 */
@Getter
@Setter
@ToString
public class ProductInfoVO implements Serializable {
    private static final long serialVersionUID = -8823621472879893131L;

    private Long id;

    private String name;

    private BigDecimal price;
}
