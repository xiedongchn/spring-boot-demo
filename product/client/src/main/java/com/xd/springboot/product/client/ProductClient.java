package com.xd.springboot.product.client;

import com.xd.springboot.product.vo.ProductInfoVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author xd
 */
@FeignClient(name = "ProductClient", path = "/product")
public interface ProductClient {

    /**
     * 获取产品信息
     *
     * @param id 产品id
     * @return String
     */
    @RequestMapping(path = "/getProductInfo/{id}")
    ProductInfoVO getProductInfo(@PathVariable Long id);
}
