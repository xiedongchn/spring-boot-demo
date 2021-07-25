package com.xd.springboot.product.client;

import com.xd.springboot.product.vo.ProductInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 1、FeignClient注解的name属性，指的是服务的id，需要与配置文件中的{application.name}一致，否则会报错如下：
 * Load balancer does not have available server for client
 * 2、当接口上只使用FeignClient注解而不使用RequestMapping注解时，如果配置文件没有配置feign.hystrix.enable=true，也会报上面的错
 * 当FeignClient和RequestMapping同时使用，不配置feign.hystrix.enable=true，不会报错
 * 3、FeignClient注解的path属性，必须与配置文件重的server.servlet.context-path一致，否则会报404，找不到服务
 *
 * @author xd
 */
@FeignClient(name = "product", path = "/product", contextId = "ProductClient")
public interface ProductClient {

    /**
     * 获取产品信息
     *
     * @param id 产品id
     * @return String
     */
    @RequestMapping(path = "/getProductInfo/{id}")
    ProductInfoVO getProductInfo(@PathVariable("id") Long id);
}
