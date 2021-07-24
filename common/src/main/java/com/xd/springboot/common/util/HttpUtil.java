package com.xd.springboot.common.util;

import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import javax.net.ssl.*;
import java.io.*;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;
import java.util.Map;

/**
 * 和HTTP有关的一些工具方法
 *
 * @author Q-p
 */
public final class HttpUtil {
    private static final Logger logger = LoggerFactory.getLogger(HttpUtil.class);

    /**
     * 发送POST请求
     */
    public static String get(String url, Map<String, String> params) {
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("请求地址为空");
        }

        StringBuilder sb = new StringBuilder("");
        for (String key : params.keySet()) {
            sb.append(key).append("=").append(params.get(key)).append("&");
        }
        String str = sb.substring(0, sb.length() - 1);
        url = url + "?" + str;

        BufferedReader in = null;
        StringBuilder result = new StringBuilder("");
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestMethod("GET");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");

            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString().trim();
    }

    /**
     * 发送POST请求
     */
    public static String post(String url, Map<String, String> params) {
        if (StringUtils.isEmpty(url)) {
            throw new RuntimeException("请求地址为空");
        }
        PrintWriter out = null;
        BufferedReader in = null;
        StringBuilder result = new StringBuilder("");
        try {
            URL realUrl = new URL(url);
            URLConnection conn = realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            out = new PrintWriter(conn.getOutputStream());
            StringBuilder sb = new StringBuilder("");
            for (String key : params.keySet()) {
                sb.append(key).append("=").append(params.get(key)).append("&");
            }

            String str = sb.substring(0, sb.length() - 1);
            out.print(str);
            out.flush();
            in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append("\n");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return result.toString();
    }

    public static String send(String url, String type, String data, int timeout) {
        if (StringUtils.isEmpty(url)) {
            return "";
        }

        if (url.startsWith("https")) {
            SSLContext sslcontext;
            try {
                sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
                TrustManager[] tm = {new MyX509TrustManager()};
                sslcontext.init(null, tm, new SecureRandom());
                HostnameVerifier ignoreHostnameVerifier = (s, sslsession) -> {
                    System.out.println("WARNING: Hostname is not matched for cert.");
                    return true;
                };
                HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
                HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        PrintWriter out = null;
        BufferedReader in = null;
        type = "POST".equalsIgnoreCase(type) ? "POST" : "GET";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(type);
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            out = new PrintWriter(conn.getOutputStream());
            out.print(data);
            out.flush();
            InputStream is = null;

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }

            in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append("\n");
            }
            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static String sendByJson(String url, String type, String data, int timeout) {
        logger.info("url{}type{}data{}timeout{}", url, type, data, timeout);
        if (StringUtils.isEmpty(url)) {
            return "";
        }

        if (url.startsWith("https")) {
            SSLContext sslcontext;
            try {
                sslcontext = SSLContext.getInstance("SSL", "SunJSSE");
                TrustManager[] tm = {new MyX509TrustManager()};
                sslcontext.init(null, tm, new SecureRandom());
                HostnameVerifier ignoreHostnameVerifier = (s, sslsession) -> {
                    System.out.println("WARNING: Hostname is not matched for cert.");
                    return true;
                };
                HttpsURLConnection.setDefaultHostnameVerifier(ignoreHostnameVerifier);
                HttpsURLConnection.setDefaultSSLSocketFactory(sslcontext.getSocketFactory());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        PrintWriter out = null;
        BufferedReader in = null;
        type = "POST".equalsIgnoreCase(type) ? "POST" : "GET";
        try {
            URL realUrl = new URL(url);
            HttpURLConnection conn = (HttpURLConnection) realUrl.openConnection();
            conn.setRequestProperty("accept", "*/*");
            conn.setRequestProperty("connection", "Keep-Alive");
            conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1; SV1)");
            conn.setRequestProperty("Content-Type",
                    "application/json;charset=UTF-8");
            conn.setDoOutput(true);
            conn.setDoInput(true);
            conn.setRequestMethod(type);
            conn.setConnectTimeout(timeout);
            conn.setReadTimeout(timeout);
            out = new PrintWriter(conn.getOutputStream());
            out.print(data);
            out.flush();
            InputStream is = null;

            if (conn.getResponseCode() == 200) {
                is = conn.getInputStream();
            } else {
                is = conn.getErrorStream();
            }

            in = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));

            StringBuilder result = new StringBuilder();
            String line;
            while ((line = in.readLine()) != null) {
                result.append(line).append("\n");
            }
            return result.toString();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        } finally {
            try {
                if (out != null) {
                    out.close();
                }
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    public static JSONObject doGet(String requestUrl, String params)
            throws Exception {
        return httpsRequest(requestUrl, "GET", params);
    }

    public static JSONObject doPost(String requestUrl, String params)
            throws Exception {
        return httpsRequest(requestUrl, "POST", params);
    }

    public static JSONObject httpsRequest(String requestUrl, String requestMethod, String outputStr) throws Exception {
        logger.info("send https requestUrl:" + requestUrl);
        logger.info("send https requestMethod:" + requestMethod);
        logger.info("send https param:" + outputStr);
        JSONObject jsonObject = new JSONObject();
        StringBuilder buffer = new StringBuilder();
        InputStream inputStream = null;
        InputStreamReader inputStreamReader = null;
        BufferedReader bufferedReader = null;

        try {
            TrustManager[] e = new TrustManager[]{new MyAllTrustManager()};
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, e, new SecureRandom());
            SSLSocketFactory ssf = sslContext.getSocketFactory();
            URL url = new URL(requestUrl);
            HttpsURLConnection httpUrlConn = (HttpsURLConnection) url
                    .openConnection();
            httpUrlConn.setSSLSocketFactory(ssf);
            httpUrlConn.setDoOutput(true);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod(requestMethod);
            httpUrlConn.setRequestProperty("accept", "application/json");
            httpUrlConn.setRequestProperty("Content-Type", "application/json;charset=UTF-8");
            httpUrlConn.setRequestProperty("connection", "Keep-Alive");
            httpUrlConn.connect();
            if ("GET".equalsIgnoreCase(requestMethod)) {
                httpUrlConn.connect();
            }

            OutputStream str;
            if (!StringUtils.isEmpty(outputStr)) {
                str = httpUrlConn.getOutputStream();
                str.write(outputStr.getBytes(StandardCharsets.UTF_8));
                str.close();
            }

            if (httpUrlConn.getResponseCode() == 200) {
                inputStream = httpUrlConn.getInputStream();
            } else {
                inputStream = httpUrlConn.getErrorStream();
            }

            inputStreamReader = new InputStreamReader(inputStream, StandardCharsets.UTF_8);
            bufferedReader = new BufferedReader(inputStreamReader);

            String str1;
            while ((str1 = bufferedReader.readLine()) != null) {
                buffer.append(str1);
            }

            if (httpUrlConn.getResponseCode() == 200) {
                jsonObject = (JSONObject) JSONObject.parse(buffer.toString());
                logger.info("response code:" + httpUrlConn.getResponseCode());
                logger.info("response jsonObject:" + jsonObject);
            } else {
                jsonObject.put("code", "400");
                jsonObject.put("message", "Bad Request");
                jsonObject.put("data", buffer.toString());
                logger.error(buffer.toString());
            }

            httpUrlConn.disconnect();
            return jsonObject;
        } catch (ConnectException arg21) {
            logger.info("server connection timed out.");
            throw new ConnectException("server connection timed out.");
        } catch (Exception arg22) {
            logger.info("https request error:" + arg22);
            throw new Exception("https request error:" + arg22);
        } finally {
            try {
                bufferedReader.close();
                inputStreamReader.close();
                inputStream.close();
            } catch (IOException arg20) {
                arg20.printStackTrace();
            }

        }
    }

    /**
     * 调用核算接口的通用方法。
     *
     * @param reqMessage 请求报文
     * @return String
     */
    public static String callHsMethod(String reqMessage) {
        String hsUrl = "http://127.0.0.1:8180/ycloans/Cmis2YcloansHttpChannel";
        return callHsMethod(hsUrl, reqMessage);
    }


    public static String callHsMethod(String hsUrl, String reqMessage) {
        String repMessage = "";
        try {
            URL url = new URL(hsUrl);
            HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
            httpURLConnection.setDoOutput(true);
            httpURLConnection.setDoInput(true);
            httpURLConnection.setRequestMethod("POST");
            httpURLConnection.setConnectTimeout(10000);
            httpURLConnection.setRequestProperty("content-type", "text/xml; charset=gbk");
            httpURLConnection.getOutputStream().write(reqMessage.getBytes("GBK"));
            httpURLConnection.getOutputStream().flush();
            httpURLConnection.getOutputStream().close();
            httpURLConnection.connect();

            InputStream in = httpURLConnection.getInputStream();
            StringBuilder sb = new StringBuilder();
            byte[] buffer = new byte[2048];
            int length;
            while ((length = in.read(buffer, 0, buffer.length)) != -1) {
                sb.append(new String(buffer, 0, length, "GB2312"));
            }
            in.close();

            repMessage = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return repMessage;
    }

    public static void main(String[] args) {
        String url = "http://stage-payment-center.vipkid-qa.com.cn/api/paymentcenter/cmbc/instalment/payment/callback";
        String resp = send(url, "POST", "{}", 1000);
        System.out.println(resp);
    }
}
