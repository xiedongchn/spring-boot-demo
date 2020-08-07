package com.xd.springbootdemo;

import com.xd.springbootdemo.interceptor.UserControllerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.annotation.PostConstruct;

//@MapperScan配合实体类上的@Mapper一起使用，会自动扫描对应包下的实体
//@MapperScan("com.xd.springbootdemo.domain")
@SpringBootApplication
public class SpringBootDemoApplication/* implements WebMvcConfigurer*/ {

	public static void main(String[] args) {
		SpringApplication.run(SpringBootDemoApplication.class, args);
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
