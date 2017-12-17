package com.aik.external;

import com.aik.dto.response.WeatherDetailRespDTO;
import com.aik.dto.response.WeatherInfoRespDTO;
import com.aik.dto.request.WeatherQueryReqDTO;
import com.aik.properties.WeatherProperties;
import com.aik.util.HttpClientUtils;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * Desc: 第三方获取天气
 * Create by as on 2017/12/13
 */
@Component
public class WeatherService {

    @Autowired
    private WeatherProperties weatherProperties;

    public WeatherDetailRespDTO getWeatherInfo(WeatherQueryReqDTO reqDTO) {
        if (null == reqDTO || StringUtils.isBlank(reqDTO.getLongitude()) || StringUtils.isBlank(reqDTO.getLatitude())) {
            throw new RuntimeException("第三方获取天气异常：请求参数不完整");
        }

        WeatherDetailRespDTO respDTO;
        try {
            Map<String, Object> params = new HashMap<>();
            params.put("location", reqDTO.getLatitude() + "," + reqDTO.getLongitude());
            Map<String, Object> headers = new HashMap<>();
            headers.put("Authorization", weatherProperties.getAuthSign() + weatherProperties.getAppCode());
            String response = HttpClientUtils.doGet(weatherProperties.getApi(), params, "utf-8", headers);
            ObjectMapper objectMapper = new ObjectMapper();
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,false);
            WeatherInfoRespDTO weatherInfoRespDTO = objectMapper.readValue(response, WeatherInfoRespDTO.class);
            if (!"0".equals(weatherInfoRespDTO.getStatus())) {
                throw new RuntimeException("调用第三方获取天气接口异常：" + weatherInfoRespDTO.getStatus() + " " + weatherInfoRespDTO.getMsg());
            } else {
                respDTO = weatherInfoRespDTO.getResult();
            }
        } catch (Exception e) {
            throw new RuntimeException("调用第三方获取天气接口异常：" + e.getMessage());
        }

        return respDTO;
    }
}
