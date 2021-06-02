package com.xd.springboot.util;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.SecureRandom;

/**
 * Q-p
 * AES（高级加密算法）是一种目前流行的对称加密算法
 * 优点：计算量小，加密效率高，安全可靠
 * 缺点：需要确保密钥传输的安全性，密钥管理不方便
 * 对512KB以下的数据进行加密解密
 * 对称加密算法AES(高级加密算法)，对称加密体系中比较流行的加密算法
 * 优点：计算量小，加密效率快，加密解密使用同一个密钥，足够安全
 * 缺点：密钥管理麻烦，必须先通过安全可靠的渠道将密钥传播出去
 * 加解密过程所有数据流均采用UTF-8编码
 * 本程序采用128位密钥，即密钥长度为16字节
 * 其实可以设置256位的密钥，但是需要替换${java_home}/jre/lib/security/ 下面的local_policy.jar和US_export_policy.jar
 * 否则会报异常：Illegal key size or default parameters
 */
public class AES {
    private static final int MAX_SIZE = 1024 * 512;
    private static final String PADDING_ALG = "AES/ECB/PKCS5Padding";
    private static final String ENCODE = "utf-8";

    /**
     * @param keyStr 密钥字符串，必须是16字节
     * @throws Exception
     * @return 返回一个密钥对象
     */
    public static Key generateKey(byte[] keyStr) {

        if (keyStr == null) throw new NullPointerException("输入参数为空：keyStr=null");
        if (keyStr.length != 16) throw new RuntimeException("密钥长度必须是16位");
        SecretKeySpec skeySpec = new SecretKeySpec(keyStr, "AES");
        Key key = (Key) skeySpec;
        return key;
    }

    /**
     * 随机生成一个AES对称秘钥（16字节[128位]长度的字节序列，不一定是可见的ascii码，可以通过Base64编码实现可视化）
     *
     * @throws Exception
     */
    public static Key generateKey() throws Exception {
        KeyGenerator keygen = KeyGenerator.getInstance("AES");
        SecureRandom random = new SecureRandom();
        keygen.init(random);
        Key key = keygen.generateKey();
        return key;
    }

    /**
     * 将密钥进行Base64编码，进行可视化输出
     *
     * @param key 密钥
     * @return 经过base64编码处理过的key
     */
    public static String keyToBase64(Key key) {

        if (key == null) throw new NullPointerException("输入参数为空");

        byte[] buff = key.getEncoded();
        return Base64.encode(buff);
    }


    /**
     * @param keyStr 密钥字符串，必须是16字节
     * @throws Exception
     * @return 返回一个密钥对象
     */
    public static Key generateKey(String keyStr) throws Exception {

        if (keyStr == null) throw new NullPointerException("输入参数为空：keyStr=null");
        if (keyStr.length() != 16) throw new Exception("密钥长度必须是16位");

        byte[] raw = keyStr.getBytes(ENCODE);
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        Key key = (Key) skeySpec;
        return key;
    }

    /**
     * @param sSrc 需要加密的字符串
     * @param key  16字节密钥
     * @return 加密后的字符串（为了可视化，加密后的字符串以Base64编码形式呈现）
     * @throws Exception
     */
    public static String encrypt(String sSrc, Key key) {


        try {
            if (sSrc == null) throw new NullPointerException("要加密的字符串为空");
            if (sSrc.length() > MAX_SIZE) throw new RuntimeException("加密的数据不能超过512KB");

            Cipher cipher = Cipher.getInstance(PADDING_ALG);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] encrypted = cipher.doFinal(sSrc.getBytes(ENCODE));
            return Base64.encode(encrypted);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }


    /**
     * @param sSrc 要解密的数据
     * @param key  密钥
     * @throws Exception
     * @return 解密后的数据
     */
    public static String decrypt(String sSrc, Key key) throws Exception {
        if (sSrc == null) throw new NullPointerException("要解密的字符串为空");
        if (sSrc.length() > MAX_SIZE * 4) throw new Exception("要解密的数据不能超过2MB");

        Cipher cipher = Cipher.getInstance(PADDING_ALG);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] base64Bytes = Base64.decode(sSrc);// 先用base64解码
        byte[] original = cipher.doFinal(base64Bytes);
        String originalString = new String(original, ENCODE);
        return originalString;
    }

    private static void generateKeyAndSalt() {

        try {
            /*String key = AES.keyToBase64(AES.generateByPass("d82f*a+df"));
            System.out.println(key);
            String res = encrypt("1234", AES.generateByPass("d82f*a+df"));
            System.out.println(res);*/

            System.out.println(decrypt("PRmbB5sCv/Igh8Qe76cYef3zKLYaC2Cku0AxyVaZkfY=",
                    generateKey(Base64.decode("DBY+ndP430JhvR2jgy7gEQ=="))));

        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        generateKeyAndSalt();
    }
}
