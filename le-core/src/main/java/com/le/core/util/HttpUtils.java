package com.le.core.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ConnectionKeepAliveStrategy;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.protocol.HttpContext;
import org.apache.http.ssl.SSLContexts;
import org.apache.http.util.EntityUtils;

import javax.net.ssl.SSLContext;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * The Class
 */
@Slf4j
public abstract class HttpUtils {
    public static final String ENCODING = "UTF-8";

    public static final int DEFAULT_CONNECT_TIMEOUT = 60000;
    public static final int DEFAULT_SO_TIMEOUT = 600000;

    private static final int VALIDATE_AFTER_INACTIVITY_DEFAULT = 3000;
    private static final int EVICT_IDLE_CONNECTIONS_DEFAULT = 50000;
    private static final String VALIDATE_AFTER_INACTIVITY = "validateAfterInactivity";
    private static final String EVICT_IDLE_CONNECTIONS = "evictIdleConnections";

    // private static HttpClientBuilder clientBuilder = HttpClientBuilder.create();
    private static final CloseableHttpClient httpClient;

    static {
        // System.setProperty("https.protocols", "TLSv1");
        // clientBuilder.useSystemProperties();
        httpClient = createHttpClient();
    }

    public static CloseableHttpClient createHttpClient() {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(10000);
        cm.setDefaultMaxPerRoute(10000);
        cm.setValidateAfterInactivity(Integer.getInteger(VALIDATE_AFTER_INACTIVITY, VALIDATE_AFTER_INACTIVITY_DEFAULT));

        HttpClientBuilder builder = HttpClientBuilder.create();
        builder.setConnectionManagerShared(false);

        ConnectionKeepAliveStrategy keepAliveStrat = new ConnectionKeepAliveStrategy() {
            @Override
            public long getKeepAliveDuration(HttpResponse response, HttpContext context) {
                // we only close connections based on idle time, not ttl expiration
                return -1;
            }
        };

        builder = builder.setKeepAliveStrategy(keepAliveStrat).evictIdleConnections(
                (long) Integer.getInteger(EVICT_IDLE_CONNECTIONS, EVICT_IDLE_CONNECTIONS_DEFAULT),
                TimeUnit.MILLISECONDS);

        CloseableHttpClient httpClient = builder.setConnectionManager(cm).build();
        return httpClient;
    }

    /**
     * Execute http get.
     *
     * @param url      the url
     * @param paramMap the param map
     * @return the string
     */
    public static String get(String url, Map<String, Object> paramMap) throws IOException {
        StringBuffer buf = new StringBuffer(url);

        if (!url.contains("?")) {
            buf.append("?");
        }

        if (paramMap != null) {
            for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
                buf.append("&").append(entry.getKey()).append("=").append(encodeUrl(entry.getValue() + ""));
            }
        }

        HttpGet httpGet = new HttpGet(buf.toString());
        HttpResponse response = httpClient.execute(httpGet);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, ENCODING);
        return content;
    }

    //携带证书请求
    public static String postSSL(String url, String data, String certPath, String certPwd) throws Exception {

        KeyStore keyStore = KeyStore.getInstance("PKCS12");
        FileInputStream instream = new FileInputStream(new File(certPath));//P12文件目录 证书路径
        try {
            /**
             * 此处要改
             * */
            keyStore.load(instream, certPwd.toCharArray());//这里写密码..默认是你的MCHID
        } finally {
            instream.close();
        }

        // Trust own CA and all self-signed certs
        /**
         * 此处要改
         * */
        SSLContext sslcontext = SSLContexts.custom()
                .loadKeyMaterial(keyStore, certPwd.toCharArray())//这里也是写密码的
                .build();
        // Allow TLSv1 protocol only
        SSLConnectionSocketFactory sslsf = new SSLConnectionSocketFactory(
                sslcontext,
                new String[]{"TLSv1"},
                null,
                SSLConnectionSocketFactory.BROWSER_COMPATIBLE_HOSTNAME_VERIFIER);
        CloseableHttpClient httpclient = HttpClients.custom()
                .setSSLSocketFactory(sslsf)
                .build();
        try {
            HttpPost httpost = new HttpPost(url); // 设置响应头信息
            httpost.addHeader("Connection", "keep-alive");
            httpost.addHeader("Accept", "*/*");
            httpost.addHeader("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8");
            httpost.addHeader("Host", "api.mch.weixin.qq.com");
            httpost.addHeader("X-Requested-With", "XMLHttpRequest");
            httpost.addHeader("Cache-Control", "max-age=0");
            httpost.addHeader("User-Agent", "Mozilla/4.0 (compatible; MSIE 8.0; Windows NT 6.0) ");
            httpost.setEntity(new StringEntity(data, "UTF-8"));
            CloseableHttpResponse response = httpclient.execute(httpost);
            try {
                HttpEntity entity = response.getEntity();

                String jsonStr = EntityUtils.toString(response.getEntity(), "UTF-8");
                EntityUtils.consume(entity);
                return jsonStr;
            } finally {
                response.close();
            }
        } finally {
            httpclient.close();
        }
    }

    /**
     * Execute http post.
     *
     * @param url      the url
     * @param paramMap the param map
     * @return the string
     */
    public static String post(String url, Map<String, Object> paramMap) throws IOException {
        HttpPost httpPost = new HttpPost(url);
        List<NameValuePair> nvps = new ArrayList<>();

        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            Object val = paramMap.get(entry.getKey());
            String value = val == null ? null : val.toString();
            nvps.add(new BasicNameValuePair(entry.getKey(), value));
        }

        httpPost.setEntity(new UrlEncodedFormEntity(nvps, ENCODING));
        HttpResponse response = httpClient.execute(httpPost);

        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, ENCODING);
        EntityUtils.consume(entity);
        return content;
    }

    public static String postXml(String url, String body) throws IOException {
        CloseableHttpClient httpclient = createHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "text/xml; charset=utf-8");
        StringEntity entity1 = new StringEntity(body, ENCODING);
        entity1.setContentType("text/xml;charset=" + ENCODING);
        httpPost.setEntity(entity1);
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, ENCODING);
        EntityUtils.consume(entity);
        return content;
    }

    /**
     * Execute http post.
     *
     * @param url      the url
     * @param paramMap the param map
     * @return the string
     */
    public static String postXml(String url, Map<String, Object> paramMap) throws IOException {
        CloseableHttpClient httpclient = createHttpClient();
        HttpPost httpPost = new HttpPost(url);
        StringBuilder xml = new StringBuilder("<xml>");

        for (Map.Entry<String, Object> entry : paramMap.entrySet()) {
            String key = entry.getKey();
            Object value = String.valueOf(entry.getValue());
            xml.append("<").append(key).append(">").append(value).append("</").append(key).append(">");
        }
        xml.append("</xml>");
        return postXml(url, xml.toString());
    }

    public static String postJson(String url, Map<String, Object> params) throws IOException {
        String json = JsonUtils.toJson(params);
        return postJson(url, json);
    }

    public static String postJson(String url, Object params) throws IOException {
        String json = JsonUtils.toJson(params);
        return postJson(url, json);
    }

    public static String postJson(String url, String body) throws IOException {
        CloseableHttpClient httpclient = createHttpClient();
        HttpPost httpPost = new HttpPost(url);
        httpPost.setHeader("Content-type", "application/json; charset=utf-8");
        StringEntity entity1 = new StringEntity(body, ENCODING);
        entity1.setContentType("application/json;charset=UTF-8");
        httpPost.setEntity(entity1);
        HttpResponse response = httpclient.execute(httpPost);
        HttpEntity entity = response.getEntity();
        String content = EntityUtils.toString(entity, ENCODING);
        EntityUtils.consume(entity);
        return content;
    }

    /**
     * Encode url.
     *
     * @param param the param
     * @return the string
     */
    public static String encodeUrl(String param) {
        try {
            return URLEncoder.encode(param, ENCODING);
        } catch (Exception e) {
            log.debug("参数url编码转换异常", e);
            return param;
        }
    }

}
