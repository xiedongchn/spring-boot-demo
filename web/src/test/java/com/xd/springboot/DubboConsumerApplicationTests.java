package com.xd.springboot;

import com.xd.springboot.config.RedisProperties;
import com.xd.springboot.controller.UserController;
import com.xd.springboot.service.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.hamcrest.Matchers.equalTo;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * 测试程序
 *
 * @author xd
 * Created on 2018/7/23 15:08
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
public class DubboConsumerApplicationTests {

	private MockMvc mvc;

	@Autowired
	private RedisProperties redisProperties;//自定义的redis配置读取类

	@Autowired
	private RedisProperties redisProperties1;//Spring boot框架提供的Redis配置读取类，由其源码可以看到将会获取spring.redis开头的属性进行注入

	@Autowired
	private UserService userSerivce;

	@Before
	public void setUp() {
		mvc = MockMvcBuilders.standaloneSetup(new UserController()).build();
		// 准备，清空user表
		userSerivce.deleteAllUsers();
	}

	@Test
	public void getProperties() {
		Assert.assertEquals(redisProperties.getPort(), new Integer(6379));
		Assert.assertEquals("Not Equals", "63791", redisProperties1.getHost());
	}

	@Test
	public void testUserController() throws Exception {
		// 测试UserController
		// 1、get查一下user列表，应该为空
		RequestBuilder builder = get("/user/");
		mvc.perform(builder).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));

		// 2、post提交一个user
		builder = post("/user/")
				.param("id", "1")
				.param("name", "测试大师")
				.param("age", "20");
		mvc.perform(builder).andExpect(content().string(equalTo("success")));

		// 3、get获取user列表，应该有刚才插入的数据
		builder = get("/user/");
		mvc.perform(builder)
				.andExpect(status().isOk())
				.andExpect(content().string(equalTo("[{\"id\":1,\"name\":\"测试大师\",\"age\":20}]")));

		// 4、put修改id为1的user
		builder = put("/user/1").
				param("name", "测试终极大师").
				param("age", "30");
		mvc.perform(builder).andExpect(content().string(equalTo("success")));

		// 5、get一个id为1的user
		builder = get("/user/1");
		mvc.perform(builder)
				.andExpect(content().string(equalTo("{\"id\":1,\"name\":\"测试终极大师\",\"age\":30}")));

		// 6、del删除id为1的user
		builder = delete("/user/1");
		mvc.perform(builder).andExpect(content().string(equalTo("success")));

		// 7、get查一下user列表，应该为空
		builder = get("/user/");
		mvc.perform(builder).andExpect(status().isOk()).andExpect(content().string(equalTo("[]")));
	}

	@Test
	public void test() {
		// 插入5个用户
		userSerivce.create("a", 1);
		userSerivce.create("b", 2);
		userSerivce.create("c", 3);
		userSerivce.create("d", 4);
		userSerivce.create("e", 5);

		// 查数据库，应该有5个用户
		Assert.assertEquals(5, userSerivce.getAllUsers().intValue());

		// 删除两个用户
		userSerivce.deleteByName("a");
		userSerivce.deleteByName("e");

		// 查数据库，应该有3个用户
		Assert.assertEquals(3, userSerivce.getAllUsers().intValue());
	}

	public RedisProperties getRedisProperties() {
		return redisProperties;
	}

	public void setRedisProperties(RedisProperties redisProperties) {
		this.redisProperties = redisProperties;
	}

	public RedisProperties getRedisProperties1() {
		return redisProperties1;
	}

	public void setRedisProperties1(RedisProperties redisProperties1) {
		this.redisProperties1 = redisProperties1;
	}
}
