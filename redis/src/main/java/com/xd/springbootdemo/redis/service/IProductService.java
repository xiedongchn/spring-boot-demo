package com.xd.springbootdemo.redis.service;

import com.xd.springbootdemo.domain.Product;

/**
 * 贷款品种服务接口
 *
 * @author xd
 * Created on 2021/6/1
 */
public interface IProductService {

    /**
     * 更新产品额度
     *
     * @param quota 产品信息
     */
    void updateProductQuota(Product quota);

    /**
     * 删除产品额度缓存信息
     *
     * @param quota 产品信息
     */
    void removeProductQuotaCache(Product quota);

    /**
     * 获取产品额度
     *
     * @param productId 产品id
     */
    void getProductQuota(Long productId);

    /**
     * 设置产品额度缓存
     *
     * @param quota 产品信息
     */
    void setProductQuotaCache(Product quota);

    /**
     * 获取产品额度缓存
     *
     * @param productId 产品id
     */
    void getProductQuotaCache(Long productId);


}
