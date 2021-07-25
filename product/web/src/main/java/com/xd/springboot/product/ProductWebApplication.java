package com.xd.springboot.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;

import javax.annotation.PostConstruct;

/**
 * @author xd
 */
//@MapperScan配合实体类上的@Mapper一起使用，会自动扫描对应包下的实体
@MapperScan("com.xd.springboot.product.mapper")
//@ImportResource("classpath:*.xml")  //加载xml文件
@EnableHystrix
@EnableEurekaClient
@EnableFeignClients(basePackages = {"com.xd.springboot"})
@SpringBootApplication
public class ProductWebApplication/* implements WebMvcConfigurer*/ {

	public static void main(String[] args) {
		SpringApplication.run(ProductWebApplication.class, args);
	}

	/*@Override
	public void addInterceptors(InterceptorRegistry registry) {
		registry.addInterceptor(new UserControllerInterceptor());
	}*/

	@PostConstruct
	public void check() {
		System.out.println("check start");
	}
}
