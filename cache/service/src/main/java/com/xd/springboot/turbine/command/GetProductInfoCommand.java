package com.xd.springboot.turbine.command;

import com.netflix.hystrix.*;
import com.xd.springboot.product.vo.ProductInfoVO;

/**
 * @author xd
 */
public class GetProductInfoCommand extends HystrixCommand<ProductInfoVO> {
    private Long id;
    private String name;

    /**
     * 线程池属性
     */
    private static final HystrixThreadPoolProperties.Setter THREAD_POOL_SETTER = HystrixThreadPoolProperties.Setter().
            // 控制queue满后reject的threshold，因为maxQueueSize不允许热修改，因此提供这个参数可以热修改，控制队列的最大大小
            withQueueSizeRejectionThreshold(100);

    /**
     * 这里默认的线程池名称会取Group command group的名称，可通过andThreadPoolKey另外设置
     * command group是用来在逻辑上组合一堆command的，比如将对某个服务的请求都放在一个group中，或者某个服务的某个功能模块放在一个group，
     * 那么监控和报警就可以一起看
     *
     * 如果说对某个依赖服务暴露出来的几个接口，访问量差异非常大，此时可以将这些接口区分不同的command group，并且对应不同的command，
     * 做更细粒度的资源隔离
     *
     * 线程池线程数量估算：
     * 1、估算当前服务对依赖服务的QPS，比如说100
     * 2、如果当前服务部署了10个实例，则线程池大小设置为10即可
     */
    private static final Setter CACHED_SETTER = Setter.
            // 设置组
            withGroupKey(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup")).
            // 设置命令key
            andCommandKey(HystrixCommandKey.Factory.asKey("GetProductInfo")).
            // 设置线程池的属性
            andThreadPoolPropertiesDefaults(THREAD_POOL_SETTER).
            // 设置命令属性
            // execution.isolation.semaphore.maxConcurrentRequests
            // 设置使用SEMAPHORE隔离策略的时候，允许访问的最大并发量，超过这个最大并发量，请求直接被reject
            // 这个并发量的设置，跟线程池大小的设置，应该是类似的，但是基于信号量的话，性能会好很多，而且hystrix框架本身的开销会小很多
            // 默认值是10，设置的小一些，否则因为信号量是基于调用线程去执行command的，而且不能从timeout中抽离，因此一旦设置的太大，而且有延时发生，可能瞬间导致tomcat本身的线程资源本占满
            andCommandPropertiesDefaults(
                    HystrixCommandProperties.Setter().withExecutionIsolationSemaphoreMaxConcurrentRequests(10));

    protected GetProductInfoCommand(Long id) {
        super(HystrixCommandGroupKey.Factory.asKey("GetProductInfoGroup"));
        this.id = id;
    }

    protected GetProductInfoCommand(Long id, String name) {
        // 通过setter的方式去指定
        super(CACHED_SETTER);
        this.id = id;
        this.name = name;

    }

    @Override
    protected ProductInfoVO run() throws Exception {
        System.out.println(1);
        return null;
    }
}