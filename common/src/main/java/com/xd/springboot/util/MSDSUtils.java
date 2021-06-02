package com.xd.springboot.util;


import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.security.Key;
import java.security.MessageDigest;
import java.security.SecureRandom;

public class MSDSUtils {
    /**
     * 计算MD5摘要
     */
    public static class MD5 {

        /**
         * 直接计算MD5
         *
         * @param buf
         * @return
         */
        public static String EncoderByMd5(String buf) {
            try {
                MessageDigest digist = MessageDigest.getInstance("MD5");
                byte[] rs = digist.digest(buf.getBytes("utf-8"));
                StringBuffer digestHexStr = new StringBuffer();
                for (int i = 0; i < 16; i++) {
                    digestHexStr.append(byteHEX(rs[i]));
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
         * @param inbuf 需做md5的字符串
         * @return
         */
        public static String encodeByMd5WithSalt(String inbuf, String salt) {
            String mac = EncoderByMd5(EncoderByMd5(inbuf + salt));
            return mac;
        }

        private static String byteHEX(byte ib) {
            char[] Digit = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f'};
            char[] ob = new char[2];
            ob[0] = Digit[(ib >>> 4) & 0X0F];
            ob[1] = Digit[ib & 0X0F];
            String s = new String(ob);
            return s;
        }
    }

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
    public static class AES {
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
    }

    /**
     * @author Q-p
     */
    public static class Base64 {

        private static final char S_BASE64CHAR[] = {'A', 'B', 'C', 'D', 'E', 'F',
                'G', 'H', 'I', 'J', 'K', 'L', 'M', 'N', 'O', 'P', 'Q', 'R', 'S',
                'T', 'U', 'V', 'W', 'X', 'Y', 'Z', 'a', 'b', 'c', 'd', 'e', 'f',
                'g', 'h', 'i', 'j', 'k', 'l', 'm', 'n', 'o', 'p', 'q', 'r', 's',
                't', 'u', 'v', 'w', 'x', 'y', 'z', '0', '1', '2', '3', '4', '5',
                '6', '7', '8', '9', '+', '/'};
        private static final byte S_DECODETABLE[];

        static {
            S_DECODETABLE = new byte[128];
            for (int i = 0; i < S_DECODETABLE.length; i++)
                S_DECODETABLE[i] = 127;

            for (int i = 0; i < S_BASE64CHAR.length; i++)
                S_DECODETABLE[S_BASE64CHAR[i]] = (byte) i;

        }

        /**
         * @param ibuf
         * @param obuf
         * @param wp
         * @return
         */
        private static int decode0(char ibuf[], byte obuf[], int wp) {
            int outlen = 3;
            if (ibuf[3] == '=')
                outlen = 2;
            if (ibuf[2] == '=')
                outlen = 1;
            int b0 = S_DECODETABLE[ibuf[0]];
            int b1 = S_DECODETABLE[ibuf[1]];
            int b2 = S_DECODETABLE[ibuf[2]];
            int b3 = S_DECODETABLE[ibuf[3]];
            switch (outlen) {
                case 1: // '\001'
                    obuf[wp] = (byte) (b0 << 2 & 252 | b1 >> 4 & 3);
                    return 1;

                case 2: // '\002'
                    obuf[wp++] = (byte) (b0 << 2 & 252 | b1 >> 4 & 3);
                    obuf[wp] = (byte) (b1 << 4 & 240 | b2 >> 2 & 15);
                    return 2;

                case 3: // '\003'
                    obuf[wp++] = (byte) (b0 << 2 & 252 | b1 >> 4 & 3);
                    obuf[wp++] = (byte) (b1 << 4 & 240 | b2 >> 2 & 15);
                    obuf[wp] = (byte) (b2 << 6 & 192 | b3 & 63);
                    return 3;
            }
            throw new RuntimeException("Internal error");
        }

        /**
         * @param data
         * @param off
         * @param len
         * @return
         */
        public static byte[] decode(char data[], int off, int len) {
            char ibuf[] = new char[4];
            int ibufcount = 0;
            byte obuf[] = new byte[(len / 4) * 3 + 3];
            int obufcount = 0;
            for (int i = off; i < off + len; i++) {
                char ch = data[i];
                if (ch != '='
                        && (ch >= S_DECODETABLE.length || S_DECODETABLE[ch] == 127))
                    continue;
                ibuf[ibufcount++] = ch;
                if (ibufcount == ibuf.length) {
                    ibufcount = 0;
                    obufcount += decode0(ibuf, obuf, obufcount);
                }
            }

            if (obufcount == obuf.length) {
                return obuf;
            } else {
                byte ret[] = new byte[obufcount];
                System.arraycopy(obuf, 0, ret, 0, obufcount);
                return ret;
            }
        }

        /**
         * @param data
         * @return
         */
        public static byte[] decode(String data) {
            char ibuf[] = new char[4];
            int ibufcount = 0;
            byte obuf[] = new byte[(data.length() / 4) * 3 + 3];
            int obufcount = 0;
            for (int i = 0; i < data.length(); i++) {
                char ch = data.charAt(i);
                if (ch != '='
                        && (ch >= S_DECODETABLE.length || S_DECODETABLE[ch] == 127))
                    continue;
                ibuf[ibufcount++] = ch;
                if (ibufcount == ibuf.length) {
                    ibufcount = 0;
                    obufcount += decode0(ibuf, obuf, obufcount);
                }
            }

            if (obufcount == obuf.length) {
                return obuf;
            } else {
                byte ret[] = new byte[obufcount];
                System.arraycopy(obuf, 0, ret, 0, obufcount);
                return ret;
            }
        }

        /**
         * @param data
         * @param off
         * @param len
         * @param ostream
         * @throws IOException
         */
        public static void decode(char data[], int off, int len,
                                  OutputStream ostream) throws IOException {
            char ibuf[] = new char[4];
            int ibufcount = 0;
            byte obuf[] = new byte[3];
            for (int i = off; i < off + len; i++) {
                char ch = data[i];
                if (ch != '='
                        && (ch >= S_DECODETABLE.length || S_DECODETABLE[ch] == 127))
                    continue;
                ibuf[ibufcount++] = ch;
                if (ibufcount == ibuf.length) {
                    ibufcount = 0;
                    int obufcount = decode0(ibuf, obuf, 0);
                    ostream.write(obuf, 0, obufcount);
                }
            }

        }

        /**
         * @param data
         * @param ostream
         * @throws IOException
         */
        public static void decode(String data, OutputStream ostream)
                throws IOException {
            char ibuf[] = new char[4];
            int ibufcount = 0;
            byte obuf[] = new byte[3];
            for (int i = 0; i < data.length(); i++) {
                char ch = data.charAt(i);
                if (ch != '='
                        && (ch >= S_DECODETABLE.length || S_DECODETABLE[ch] == 127))
                    continue;
                ibuf[ibufcount++] = ch;
                if (ibufcount == ibuf.length) {
                    ibufcount = 0;
                    int obufcount = decode0(ibuf, obuf, 0);
                    ostream.write(obuf, 0, obufcount);
                }
            }

        }

        /**
         * @param data
         * @return
         */
        public static String encode(byte data[]) {
            return encode(data, 0, data.length);
        }

        /**
         * @param data
         * @param off
         * @param len
         * @return
         */
        public static String encode(byte data[], int off, int len) {
            if (len <= 0)
                return "";
            char out[] = new char[(len / 3) * 4 + 4];
            int rindex = off;
            int windex = 0;
            int rest;
            for (rest = len - off; rest >= 3; rest -= 3) {
                int i = ((data[rindex] & 255) << 16)
                        + ((data[rindex + 1] & 255) << 8)
                        + (data[rindex + 2] & 255);
                out[windex++] = S_BASE64CHAR[i >> 18];
                out[windex++] = S_BASE64CHAR[i >> 12 & 63];
                out[windex++] = S_BASE64CHAR[i >> 6 & 63];
                out[windex++] = S_BASE64CHAR[i & 63];
                rindex += 3;
            }

            if (rest == 1) {
                int i = data[rindex] & 255;
                out[windex++] = S_BASE64CHAR[i >> 2];
                out[windex++] = S_BASE64CHAR[i << 4 & 63];
                out[windex++] = '=';
                out[windex++] = '=';
            } else if (rest == 2) {
                int i = ((data[rindex] & 255) << 8) + (data[rindex + 1] & 255);
                out[windex++] = S_BASE64CHAR[i >> 10];
                out[windex++] = S_BASE64CHAR[i >> 4 & 63];
                out[windex++] = S_BASE64CHAR[i << 2 & 63];
                out[windex++] = '=';
            }
            return new String(out, 0, windex);
        }

        /**
         * @param data
         * @param off
         * @param len
         * @param ostream
         * @throws IOException
         */
        public static void encode(byte data[], int off, int len,
                                  OutputStream ostream) throws IOException {
            if (len <= 0)
                return;
            byte out[] = new byte[4];
            int rindex = off;
            int rest;
            for (rest = len - off; rest >= 3; rest -= 3) {
                int i = ((data[rindex] & 255) << 16)
                        + ((data[rindex + 1] & 255) << 8)
                        + (data[rindex + 2] & 255);
                out[0] = (byte) S_BASE64CHAR[i >> 18];
                out[1] = (byte) S_BASE64CHAR[i >> 12 & 63];
                out[2] = (byte) S_BASE64CHAR[i >> 6 & 63];
                out[3] = (byte) S_BASE64CHAR[i & 63];
                ostream.write(out, 0, 4);
                rindex += 3;
            }

            if (rest == 1) {
                int i = data[rindex] & 255;
                out[0] = (byte) S_BASE64CHAR[i >> 2];
                out[1] = (byte) S_BASE64CHAR[i << 4 & 63];
                out[2] = 61;
                out[3] = 61;
                ostream.write(out, 0, 4);
            } else if (rest == 2) {
                int i = ((data[rindex] & 255) << 8) + (data[rindex + 1] & 255);
                out[0] = (byte) S_BASE64CHAR[i >> 10];
                out[1] = (byte) S_BASE64CHAR[i >> 4 & 63];
                out[2] = (byte) S_BASE64CHAR[i << 2 & 63];
                out[3] = 61;
                ostream.write(out, 0, 4);
            }
        }

        /**
         * @param data
         * @param off
         * @param len
         * @param writer
         * @throws IOException
         */
        public static void encode(byte data[], int off, int len, Writer writer)
                throws IOException {
            if (len <= 0)
                return;
            char out[] = new char[4];
            int rindex = off;
            int rest = len - off;
            int output = 0;
            do {
                if (rest < 3)
                    break;
                int i = ((data[rindex] & 255) << 16)
                        + ((data[rindex + 1] & 255) << 8)
                        + (data[rindex + 2] & 255);
                out[0] = S_BASE64CHAR[i >> 18];
                out[1] = S_BASE64CHAR[i >> 12 & 63];
                out[2] = S_BASE64CHAR[i >> 6 & 63];
                out[3] = S_BASE64CHAR[i & 63];
                writer.write(out, 0, 4);
                rindex += 3;
                rest -= 3;
                if ((output += 4) % 76 == 0)
                    writer.write("\n");
            } while (true);
            if (rest == 1) {
                int i = data[rindex] & 255;
                out[0] = S_BASE64CHAR[i >> 2];
                out[1] = S_BASE64CHAR[i << 4 & 63];
                out[2] = '=';
                out[3] = '=';
                writer.write(out, 0, 4);
            } else if (rest == 2) {
                int i = ((data[rindex] & 255) << 8) + (data[rindex + 1] & 255);
                out[0] = S_BASE64CHAR[i >> 10];
                out[1] = S_BASE64CHAR[i >> 4 & 63];
                out[2] = S_BASE64CHAR[i << 2 & 63];
                out[3] = '=';
                writer.write(out, 0, 4);
            }
        }
    }
}
