package com.aik.dto.request.user;

/**
 * Description:
 * Created by as on 2017/10/23.
 */
public class ShoppingCartAddGoodsReqDTO {

    private Integer userId;

    private Integer goodsId;

    private Integer goodsNumber;

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public Integer getGoodsNumber() {
        return goodsNumber;
    }

    public void setGoodsNumber(Integer goodsNumber) {
        this.goodsNumber = goodsNumber;
    }
}
