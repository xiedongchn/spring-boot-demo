package com.xd.springbootdemo.redis.service.impl;

import com.xd.springbootdemo.domain.Product;
import com.xd.springbootdemo.redis.service.IProductService;
import org.springframework.stereotype.Service;

/**
 * @author xd
 * Created on 2021/6/2
 */
@Service
public class ProductServiceImpl implements IProductService {

    @Override
    public void updateProductQuota(Product quota) {

    }

    @Override
    public void removeProductQuotaCache(Product quota) {

    }

    @Override
    public void getProductQuota(Long productId) {

    }

    @Override
    public void setProductQuotaCache(Product quota) {

    }

    @Override
    public void getProductQuotaCache(Long productId) {

    }
}
