package com.aik.util;


import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.*;

/**
 * Create by as on 2017/10/18
 */
public class HttpClientUtils {

    private static PoolingHttpClientConnectionManager connectionManager = null;
    private static HttpClientBuilder httpBuilder = null;
    private static RequestConfig requestConfig = null;

    private static int MAX_CONNECTIONS = 200;
    private static int DEFAULT_MAX_CONNECTIONS = 20;
    private static int SOCKET_TIMEOUT = 30000;
    private static int CONNECT_TIMEOUT = 30000;
    private static int CONNECTION_REQUEST_TIMEOUT = 30000;

    static {
        requestConfig = RequestConfig.custom()
                .setSocketTimeout(SOCKET_TIMEOUT)
                .setConnectTimeout(CONNECT_TIMEOUT)
                .setConnectionRequestTimeout(CONNECTION_REQUEST_TIMEOUT)
                .build();

        connectionManager = new PoolingHttpClientConnectionManager();
        connectionManager.setMaxTotal(MAX_CONNECTIONS);
        connectionManager.setDefaultMaxPerRoute(DEFAULT_MAX_CONNECTIONS);
    }

    public static CloseableHttpClient getConnection() {
        httpBuilder = HttpClientBuilder.create();
        return httpBuilder.build();
    }

    public static HttpUriRequest getRequestMethod(Map<String, Object> map, String url, String method) {
        List<NameValuePair> params = new ArrayList<>();
        Set<Map.Entry<String, Object>> entrySet = map.entrySet();
        for (Map.Entry<String, Object> e : entrySet) {
            String name = e.getKey();
            Object value = e.getValue();
            NameValuePair pair = new BasicNameValuePair(name, value.toString());
            params.add(pair);
        }

        HttpUriRequest reqMethod = null;
        if ("post".equals(method)) {
            reqMethod = RequestBuilder.post().setUri(url)
                    .addParameters(params.toArray(new NameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        } else if ("get".equals(method)) {
            reqMethod = RequestBuilder.get().setUri(url)
                    .addParameters(params.toArray(new NameValuePair[params.size()]))
                    .setConfig(requestConfig).build();
        }

        return reqMethod;
    }

    public static String doGet(String url, Map<String, Object> params, String defaultCharset) throws Exception {
        HttpClient client = getConnection();
        HttpUriRequest post = getRequestMethod(params, url, "get");
        HttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity(), defaultCharset);
        } else {
            throw new Exception("do get request exception!");
        }
    }

    public static String doPost(String url, Map<String, Object> params, String defaultCharset) throws Exception {
        HttpClient client = getConnection();
        HttpUriRequest post = getRequestMethod(params, url, "post");
        HttpResponse response = client.execute(post);
        if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
            return EntityUtils.toString(response.getEntity(), defaultCharset);
        } else {
            throw new Exception("do post request exception!");
        }
    }

    public static void main(String args[]) {
        // http://v.juhe.cn/sms/send?mobile=手机号码&tpl_id=短信模板ID&tpl_value=%23code%23%3D654654&key=
        // http://op.juhe.cn/yuntongxun/voice?key=您申请的KEY&valicode=12345678&to=18912312312&playtimes=3
        try {
            String url = "http://op.juhe.cn/yuntongxun/voice";

            Map<String, Object> map = new HashMap<>();
            map.put("valicode", "123456");
            map.put("to", "15707351117");
            map.put("key", "5125ad3ae26b2385cf7f39a84d390c41");
            map.put("playtimes", 2);

            String response = doGet(url, map, "UTF-8");
            System.out.println(response);
        } catch (Exception e) {
            e.printStackTrace();
        }


//        HttpClient client = getConnection();
//        HttpUriRequest post = getRequestMethod(map, "http://v.juhe.cn/sms/send", "post");
//        HttpResponse response = client.execute(post);
//
//        if (response.getStatusLine().getStatusCode() == 200) {
//            HttpEntity entity = response.getEntity();
//            String message = EntityUtils.toString(entity, "utf-8");
//            System.out.println(message);
//        } else {
//            System.out.println("请求失败");
//        }
    }
}
