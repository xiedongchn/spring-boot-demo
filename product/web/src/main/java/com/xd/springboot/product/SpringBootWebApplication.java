package com.xd.springboot.product;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;

/**
 * @author xd
 */
//@MapperScan配合实体类上的@Mapper一起使用，会自动扫描对应包下的实体
@MapperScan("com.xd.springboot.product.mapper")
//@ImportResource("classpath:*.xml")  //加载xml文件
@SpringBootApplication
public class SpringBootWebApplication/* implements WebMvcConfigurer*/ {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootWebApplication.class, args);
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
