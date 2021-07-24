package com.xd.springboot.product.redis.requestHandler.impl;

import com.xd.springboot.product.redis.request.impl.ProductQuotaCacheUpdateRequest;
import com.xd.springboot.product.redis.requestHandler.IRequestHandler;

/**
 * @author xd
 * Created on 2021/6/1
 */
public class ProductQuotaCacheUpdateHandler implements IRequestHandler<ProductQuotaCacheUpdateRequest> {

    @Override
    public void process(ProductQuotaCacheUpdateRequest request) {

    }

    @Override
    public boolean isForceRefresh() {
        return false;
    }
}
