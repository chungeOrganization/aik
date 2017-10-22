package com.aik.dto.request.user;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/10/23.
 */
public class AppraiseOrderReqDTO {

    private Integer orderId;

    private String appraise;

    private Integer goodsQuality;

    private Integer logisticsSpeed;

    private List<String> files;

    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public String getAppraise() {
        return appraise;
    }

    public void setAppraise(String appraise) {
        this.appraise = appraise;
    }

    public Integer getGoodsQuality() {
        return goodsQuality;
    }

    public void setGoodsQuality(Integer goodsQuality) {
        this.goodsQuality = goodsQuality;
    }

    public Integer getLogisticsSpeed() {
        return logisticsSpeed;
    }

    public void setLogisticsSpeed(Integer logisticsSpeed) {
        this.logisticsSpeed = logisticsSpeed;
    }

    public List<String> getFiles() {
        return files;
    }

    public void setFiles(List<String> files) {
        this.files = files;
    }
}
