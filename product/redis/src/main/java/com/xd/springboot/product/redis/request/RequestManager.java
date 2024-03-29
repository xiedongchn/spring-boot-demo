package com.xd.springboot.product.redis.request;

import com.xd.springboot.product.redis.request.impl.ProductQuotaCacheUpdateRequest;
import com.xd.springboot.product.redis.request.impl.ProductQuotaDbUpdateRequest;
import com.xd.springboot.product.redis.requestHandler.IRequestHandler;
import com.xd.springboot.product.redis.requestHandler.impl.ProductQuotaDbUpdateHandler;
import com.xd.springboot.product.redis.requestHandler.impl.ProductQuotaCacheUpdateHandler;

import java.util.HashMap;
import java.util.Map;

/**
 * 请求管理
 *
 * @author xd
 */
public class RequestManager {

    private static final Map<String, IRequestHandler<?>> MAP = new HashMap<>();

    static {
        MAP.put(AbstractRequest.PQDU, new ProductQuotaDbUpdateHandler());
        MAP.put(AbstractRequest.PQCU, new ProductQuotaCacheUpdateHandler());
    }

    public static void handle(AbstractRequest request) {
        if (request instanceof ProductQuotaDbUpdateRequest) {
            ProductQuotaDbUpdateHandler u = (ProductQuotaDbUpdateHandler) MAP.get(AbstractRequest.PQDU);
            u.process((ProductQuotaDbUpdateRequest) request);
        } else if (request instanceof ProductQuotaCacheUpdateRequest) {
            ProductQuotaCacheUpdateHandler u = (ProductQuotaCacheUpdateHandler) MAP.get(AbstractRequest.PQCU);
            u.process((ProductQuotaCacheUpdateRequest) request);
        }
    }
}
