package com.xd.springboot.common.util;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;


public class FactoryUtil {
    private static final Log log = LogFactory.getLog(FactoryUtil.class);

    public static <T> T getBean(Class<T> class1, JSONObject json) throws Exception {
        try {
            T object = JSONObject.toJavaObject(json, class1);
            if (object == null) {
                throw new Exception("ERROR_011");
            }
            return object;
        } catch (Exception e) {
            e.printStackTrace();
            throw new Exception("ERROR_011");
        }
    }

    public static <T> T getBean(Class<T> class1, String jsonStr) throws Exception {
        return getBean(class1, JSONObject.parseObject(jsonStr));
    }

    /**
     * 进行安全解析,还原真实报文数据
     * 如果安全检查通过返回解密后的数据，否则抛出异常
     *
     * @param aesKey 密钥
     * @param salt   盐
     * @param mac    mac值
     * @param data   数据
     * @return 解密后的数据
     * @throws Exception 异常
     */
    private String securityParse(String aesKey, String salt, String mac, String data) throws Exception {
        log.info("收到的原始加密报文：" + data);

        if (StringUtils.isEmpty(mac)) {
            throw new Exception("ERROR_009");
        }
        if (StringUtils.isEmpty(data)) {
            throw new Exception("ERROR_033");
        }

        String srcData;
        try {
            log.info("解密密钥：" + aesKey);
            //data = URLDecoder.decode(data,"utf-8");
            srcData = AES.decrypt(data, AES.generateKey(Base64.decode(aesKey)));
            log.info("解密后的报文：" + srcData);
        } catch (Exception e) {
            throw new Exception("ERROR_003");
        }

        String localMac = MD5.encodeByMd5WithSalt(srcData, salt);

        log.debug("获取到的摘要：" + mac);
        log.debug("计算出的摘要：" + localMac);

        if (!localMac.equalsIgnoreCase(mac)) {
            throw new Exception("ERROR_002");
        }

        return srcData;
    }

}
