package com.xd.springboot.product.redis.requestHandler.impl;

import com.xd.springboot.product.redis.request.impl.ProductQuotaDbUpdateRequest;
import com.xd.springboot.product.redis.requestHandler.IRequestHandler;

/**
 * @author xd
 * Created on 2021/6/1
 */
public class ProductQuotaDbUpdateHandler implements IRequestHandler<ProductQuotaDbUpdateRequest> {

    @Override
    public void process(ProductQuotaDbUpdateRequest request) {

    }

    @Override
    public boolean isForceRefresh() {
        return true;
    }
}
