package com.aik.external;

import com.aik.properties.BankCardOcrProperties;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2018/1/25.
 */
@Component
public class BankCardOcrService {
    @Autowired
    private BankCardOcrProperties bankCardOcrProperties;



    public static void main(String[] args) throws IOException {
        URL url = new URL("http://yhk.market.alicloudapi.com/rest/160601/ocr/ocr_bank_card.json");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
        urlConnection.setRequestMethod("POST");
        urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
        urlConnection.setRequestProperty("Authorization", "APPCODE d8023309b94d45808193aa5339638ad9");
        urlConnection.setDoInput(true);
        urlConnection.setDoOutput(true);
        urlConnection.connect();
        DataOutputStream dos = new DataOutputStream(urlConnection.getOutputStream());
        Map<String, Object> params = new HashMap<>();
        params.put("dataType", 50);

        InputStream is = new FileInputStream("C:\\Users\\as\\Desktop\\a.png");
        byte[] a = new byte[is.available()];
        is.read(a);
        is.close();

        params.put("dataValue", new BASE64Encoder().encode(a));

        Map<String, Object> imageMap = new HashMap<>();
        imageMap.put("image", params);
        List<Map<String, Object>> imageList = new ArrayList<Map<String, Object>>();
        imageList.add(imageMap);
        Map<String, Object> inputsMap = new HashMap<>();
        inputsMap.put("inputs", imageList);
        String b = JSONObject.toJSONString(inputsMap);
        dos.writeBytes(b);
        dos.flush();
        dos.close();
        if (urlConnection.getResponseCode() == 200) {
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    urlConnection.getInputStream()));
            String str = null;
            while ((str = reader.readLine()) != null) {
                System.out.println(str);
            }
            reader.close();
        }
        System.out.println(urlConnection.getResponseMessage());
        System.out.println(urlConnection.getResponseCode());

    }
}
