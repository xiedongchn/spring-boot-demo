package com.xd.springbootdemo.redis.requestHandler.impl;

import com.xd.springbootdemo.redis.request.impl.ProductQuotaDbUpdateRequest;
import com.xd.springbootdemo.redis.requestHandler.IRequestHandler;

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
