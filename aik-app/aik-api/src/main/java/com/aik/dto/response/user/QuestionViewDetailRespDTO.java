package com.aik.dto.response.user;

/**
 * Description:
 * Created by as on 2017/10/23.
 */
public class QuestionViewDetailRespDTO {

    private QuestionOrderDetailRespDTO orderDetail;

    private Integer viewCount;

    public QuestionOrderDetailRespDTO getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(QuestionOrderDetailRespDTO orderDetail) {
        this.orderDetail = orderDetail;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }
}
