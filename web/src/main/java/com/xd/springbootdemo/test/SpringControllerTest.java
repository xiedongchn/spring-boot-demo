package com.xd.springbootdemo.test;

import com.alibaba.fastjson.JSONObject;
import org.junit.Before;
import org.junit.Test;

public class SpringControllerTest extends BaseTestController {

    @Before
    public void login() {
        this.setRequestUrl("/login/userLogin");
        this.addParam("userName", "13011130002");
        this.addParam("password", "qwer1234");
        this.addParam("sourceId", "XIAOMI-123456");
        this.addParam("sourceName", "XIAOMI-Android-4");

        String response = this.doRequest();
        JSONObject json = JSONObject.parseObject(response);
        String token1 = json.getJSONObject("cd").getString("token");

        this.addParam("token", token1);
    }

    @Test
    public void testPreBindCard() {
        this.setRequestUrl("/modifyBankCard/zyxjPreBindCard");

        this.addParam("realName", "灵二");
        this.addParam("idNo", "110101199003078195");
        this.addParam("cardNo", "1231231231230");
        this.addParam("bankCode", "aaaaaa");
        this.addParam("phoneNo", "13011130002");

        String response = this.doRequest();
        System.out.print(response);
    }
}
