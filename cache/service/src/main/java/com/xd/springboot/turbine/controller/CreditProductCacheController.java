package com.xd.springboot.turbine.controller;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.xd.springboot.product.client.ProductClient;
import com.xd.springboot.product.vo.ProductInfoVO;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xd
 */
@Log4j2
@RestController
@RequestMapping(path = "/credit/product")
public class CreditProductCacheController {

    @Autowired
    private ProductClient productClient;

    /**
     * threadPoolProperties可设置的属性参见HystrixThreadPoolProperties
     *
     * @param id 产品主键
     * @return String
     * @see com.netflix.hystrix.HystrixThreadPoolProperties
     */
    @HystrixCommand(commandKey = "getProductInfo", groupKey = "ProductInfo", fallbackMethod = "fallback",
            threadPoolKey = "ProductInfo", threadPoolProperties = {
                    @HystrixProperty(name = "coreSize", value = "30"),
                    @HystrixProperty(name = "maxQueueSize", value = "100"),
                    @HystrixProperty(name = "keepAliveTimeMinutes", value = "1"),
                    @HystrixProperty(name = "metrics.rollingStats.timeInMilliseconds", value = "12"),
                    @HystrixProperty(name = "metrics.rollingStats.numBuckets", value = "1440")
            })
    @RequestMapping(value = "/getProductInfo/{id}", method = RequestMethod.GET)
    public ProductInfoVO getProductInfo(@PathVariable("id") Long id) {
        System.out.println("adsfsadfsdf");
        return productClient.getProductInfo(id);
    }

    public ProductInfoVO fallback(Long id) {
        log.error("fallback error");
        return null;
    }
}