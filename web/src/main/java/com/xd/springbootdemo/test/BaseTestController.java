package com.xd.springbootdemo.test;

import com.alibaba.fastjson.JSONObject;
import com.xd.springbootdemo.util.*;

import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

public class BaseTestController {
    protected static String BASE_URL = "http://127.0.0.1:7777";

    private String requestUrl = null;

    private Map<String, String> params;

    public BaseTestController() {
        params = new HashMap<>();
        params.put("timestamp", "1407381075247");
        params.put("sourceId", "android");
    }

    /**
     * 设置 token
     *
     * @return
     */
    protected void setToken(String token) {
        params.put("token", token);
    }

    /**
     * 请求
     */
    public String doRequest() {
        StringBuffer jsonParams = new StringBuffer();
        String param = JSONObject.toJSONString(params);
        try {
            String str = Des.encryptDES(param, "D2T3Y4H5");
            str = str.replaceAll("\\+", "%2B");
            str = str.replaceAll("\\ ", "+");
            System.out.println("==========>" + str);
            jsonParams.append("param=" + str);
        } catch (Exception e1) {
            e1.printStackTrace();
        }
        jsonParams.append("&");
        jsonParams.append("sign=" + MD5.encodeByMd5AndSalt(param, "FQBLZPQ3PWJ48AV63DWEWTRE345"));
        jsonParams.append("&");
        jsonParams.append("os=" + "android");

        System.out.println("jsonParam:" + jsonParams);

        Response response = null;
        try {
            response = HttpClient.httpPostRequest(BASE_URL + requestUrl, jsonParams.toString());
            //response = HttpClient.httpPostRequest(BASE_URL + requestUrl, URLEncoder.encode(jsonParams.toString(),"UTF-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        String res = response.asString();
        System.out.println("============>" + res);
        return res;
    }

    /**
     * 添加请求参数
     */
    public void addParam(String key, String value) {
        params.put(key, value);
    }

    public void clearParams() {
        params.clear();
    }

    /**
     * 设置请求的 url
     */
    public void setRequestUrl(String requestUrl) {
        this.requestUrl = requestUrl;
    }

}
