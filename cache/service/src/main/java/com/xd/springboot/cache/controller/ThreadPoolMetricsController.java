package com.xd.springboot.cache.controller;

import com.netflix.hystrix.HystrixThreadPoolMetrics;
import org.springframework.web.bind.annotation.*;

/**
 * @author xd
 */
@RestController
@RequestMapping(path = "/threadpool")
public class ThreadPoolMetricsController {

    @GetMapping("/getMetrics/{threadPoolKey}")
    public HystrixThreadPoolMetrics getMetrics(@PathVariable("threadPoolKey") String threadPoolKey) {
        return HystrixThreadPoolMetrics.getInstance(() -> threadPoolKey);
    }
}
