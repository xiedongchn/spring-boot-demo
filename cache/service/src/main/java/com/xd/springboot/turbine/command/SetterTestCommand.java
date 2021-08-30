package com.xd.springboot.turbine.command;

import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.xd.springboot.product.vo.ProductInfoVO;

public class SetterTestCommand extends HystrixCommand<ProductInfoVO> {
    private final Long id;

    protected SetterTestCommand(Long id) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"));
        this.id = id;

    }

    @Override
    protected ProductInfoVO run() throws Exception {

        return null;
    }
}