package com.xd.springboot.util;

import javax.crypto.Cipher;
import java.security.Key;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.SecureRandom;

/**
 * @author 少举
 * 非对称加密算法RSA(三个科学家名字的第一个字母)，非对称加密体系中比较流行的加密算法
 * 优点：加密密钥和解密密钥可以不一样，方便管理密钥，安全
 * 缺点：计算量大，加密效率低，所以一般用来传输对称加密的密钥，然后通信使用对称加密密钥进行加解密。结合起来使用来提高效率
 * 加解密过程所有数据流均采用UTF-8编码
 * 公钥，私钥是相对的概念，都可以用来加密解密，用来加密的一般叫私钥，需要保存好。公钥是可以在公开的。
 * 一般是用通信双方各有一个密钥对，用对方的公钥加密，收到数据后用自己的私钥解密，不存在密钥传播的问题
 */
public class RSA {

    public static final int KEYSIZE = 512;

    private KeyPair keyPair;

    /**
     * 生成秘钥对
     *
     * @return
     * @throws Exception
     */
    public KeyPair generateKeyPair() throws Exception {
        KeyPairGenerator pairgen = KeyPairGenerator.getInstance("RSA");
        SecureRandom random = new SecureRandom();
        pairgen.initialize(RSA.KEYSIZE, random);
        this.keyPair = pairgen.generateKeyPair();
        return this.keyPair;
    }

    /**
     * 加密秘钥
     *
     * @param key
     * @return
     * @throws Exception
     */
    public byte[] wrapKey(Key key) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.WRAP_MODE, this.keyPair.getPrivate());
        byte[] wrappedKey = cipher.wrap(key);
        return wrappedKey;
    }

    /**
     * 解密秘钥
     *
     * @param wrapedKeyBytes
     * @return
     * @throws Exception
     */
    public Key unwrapKey(byte[] wrapedKeyBytes) throws Exception {
        Cipher cipher = Cipher.getInstance("RSA");
        cipher.init(Cipher.UNWRAP_MODE, this.keyPair.getPublic());
        Key key = cipher.unwrap(wrapedKeyBytes, "AES", Cipher.SECRET_KEY);
        return key;
    }

}

