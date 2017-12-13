package com.aik.dto;

/**
 * Desc: 天气信息DTO
 * Create by as on 2017/12/13
 */
public class WeatherInfoRespDTO {
    private String status;
    private String msg;
    private WeatherDetailRespDTO result;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public WeatherDetailRespDTO getResult() {
        return result;
    }

    public void setResult(WeatherDetailRespDTO result) {
        this.result = result;
    }
}
