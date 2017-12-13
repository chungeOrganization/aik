package com.aik.dto;

/**
 * Desc: 天气查询请求DTO
 * Create by as on 2017/12/13
 */
public class WeatherQueryReqDTO {
    // 经度
    private String longitude;

    // 纬度
    private String latitude;

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }
}
