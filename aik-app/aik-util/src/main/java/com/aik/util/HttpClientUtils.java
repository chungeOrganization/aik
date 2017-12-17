package com.aik.util;


import com.aik.dto.response.WeatherInfoRespDTO;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.http.*;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

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

    public static HttpUriRequest getRequestMethod(Map<String, Object> paramsMap, String url, String method) {
        return getRequestMethod(paramsMap, url, method, null);
    }

    public static HttpUriRequest getRequestMethod(Map<String, Object> paramsMap, String url, String method, Map<String, Object> headersMap) {
        List<NameValuePair> params = new ArrayList<>();
        Set<Map.Entry<String, Object>> entrySet = paramsMap.entrySet();
        for (Map.Entry<String, Object> e : entrySet) {
            String name = e.getKey();
            Object value = e.getValue();
            NameValuePair pair = new BasicNameValuePair(name, value.toString());
            params.add(pair);
        }

        List<Header> headerList = new ArrayList<>();
        if (null != headersMap) {
            Set<Map.Entry<String, Object>> headersSet = headersMap.entrySet();

            for (Map.Entry<String, Object> e : headersSet) {
                headerList.add(new BasicHeader(e.getKey(), e.getValue().toString()));
            }
        }
        Header[] headers = new Header[headerList.size()];
        headers = headerList.toArray(headers);

        HttpUriRequest reqMethod = null;
        if ("post".equals(method)) {
            reqMethod = RequestBuilder.post().setUri(url)
                    .addParameters(params.toArray(new NameValuePair[params.size()]))
                    .setConfig(requestConfig).build();

            reqMethod.setHeaders(headers);
        } else if ("get".equals(method)) {
            reqMethod = RequestBuilder.get().setUri(url)
                    .addParameters(params.toArray(new NameValuePair[params.size()]))
                    .setConfig(requestConfig).build();

            reqMethod.setHeaders(headers);
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

    public static String doGet(String url, Map<String, Object> params, String defaultCharset, Map<String, Object> headers) throws Exception {
        HttpClient client = getConnection();
        HttpUriRequest post = getRequestMethod(params, url, "get", headers);
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
            String url = "http://jisutqybmf.market.alicloudapi.com/weather/query";

            Map<String, Object> map = new HashMap<>();
            map.put("location", "39.90143,116.408293");
            Map<String, Object> headers = new HashMap<>();
            headers.put("Authorization", "APPCODE baa9bd7b24084523a73d276164c4ec11");

            String response = doGet(url, map, "UTF-8", headers);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            WeatherInfoRespDTO weatherInfoRespDTO = objectMapper.readValue(response, WeatherInfoRespDTO.class);

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
