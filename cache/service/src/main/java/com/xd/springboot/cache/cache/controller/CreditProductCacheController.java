package com.xd.springboot.cache.cache.controller;

import com.netflix.hystrix.HystrixThreadPoolMetrics;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.xd.springboot.product.client.ProductClient;
import com.xd.springboot.product.vo.ProductInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xd
 */
@RestController
@RequestMapping(path = "/credit/product")
public class CreditProductCacheController {

    @Autowired
    private ProductClient productClient;

    /**
     * threadPoolProperties可设置的属性参见
     *
     * @param id 产品主键
     * @return String
     * @see com.netflix.hystrix.HystrixThreadPoolProperties
     */
    @HystrixCommand(commandKey = "getProductInfo", groupKey = "ProductInfo", threadPoolKey = "ProductInfo",
            threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "100"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "1"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "1440")
            })
    @RequestMapping("/getProductInfo/{id}")
    public ProductInfoVO getProductInfo(@PathVariable Long id) {
        return productClient.getProductInfo(id);
    }

    @RequestMapping("/getProductInfo/{threadPoolKey}")
    public HystrixThreadPoolMetrics getProductInfo(@PathVariable String threadPoolKey) {
        return HystrixThreadPoolMetrics.getInstance(() -> threadPoolKey);
    }
}
