package com.xd.springboot.common.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;


/**
 * 计算MD5摘要
 * @author xd
 */
public class MD5 {

    /**
     * 直接计算MD5
     *
     * @param buf buf
     * @return String
     */
    public static String encoderByMd5(String buf) {
        try {
            MessageDigest digist = MessageDigest.getInstance("MD5");
            byte[] rs = digist.digest(buf.getBytes(StandardCharsets.UTF_8));
            StringBuilder digestHexStr = new StringBuilder();
            for (int i = 0; i < 16; i++) {
                digestHexStr.append(byteHex(rs[i]));
            }
            return digestHexStr.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 强化的MD5计算
     *
     * @param inBuf 需做md5的字符串
     * @return String
     */
    public static String encodeByMd5WithSalt(String inBuf, String salt) {
        return encoderByMd5(encoderByMd5(inBuf + salt));
    }

    public static String encodeByMd5AndSalt(String inbuf, String salt) {
        if (salt == null || "".equals(salt.trim())) {
            return encoderByMd5(encoderByMd5(inbuf) + "HXWcjvQWVG1wI4FQBLZpQ3pWj48AV63d");
        } else {
            return encoderByMd5(encoderByMd5(inbuf) + salt);
        }
    }

    private static String byteHex(byte ib) {
        char[] digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
        char[] ob = new char[2];
        ob[0] = digit[(ib >>> 4) & 0X0F];
        ob[1] = digit[ib & 0X0F];
        return new String(ob);
    }

    public static void main(String[] args) throws Exception {
        BufferedReader br = new BufferedReader(new FileReader("C:\\Users\\Administrator\\Desktop\\1.txt"));
        String l = br.readLine();
        br.close();
        System.out.println("md5(\"" + l + "\")\n=\n" + encodeByMd5WithSalt(l, "8aa16b3b-fbbd-424e-badf-788ccce0d1b1"));
    }
}
