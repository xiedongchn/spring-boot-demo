package com.xd.springboot.product.feign.client;

import com.xd.springboot.product.client.ProductClient;
import com.xd.springboot.product.service.IProductService;
import com.xd.springboot.product.vo.ProductInfoVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author xd
 */
@RestController
public class ProductController implements ProductClient {

    @Autowired
    private IProductService productService;

    @Override
    public ProductInfoVO getProductInfo(Long id) {
        return null;
    }
}
