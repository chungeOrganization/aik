package com.aik.dto.response.user;

import java.math.BigDecimal;

/**
 * Description:
 * Created by as on 2017/10/23.
 */
public class GoodsDetailRespDTO {

    private Integer goodsId;

    private String goodsName;

    private String goodsImg;

    private BigDecimal goodsPrice;

    private Integer goodsStock;

    private Boolean isCollection;

    private Integer cartGoodsCount;

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public String getGoodsImg() {
        return goodsImg;
    }

    public void setGoodsImg(String goodsImg) {
        this.goodsImg = goodsImg;
    }

    public BigDecimal getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(BigDecimal goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsStock() {
        return goodsStock;
    }

    public void setGoodsStock(Integer goodsStock) {
        this.goodsStock = goodsStock;
    }

    public Boolean getCollection() {
        return isCollection;
    }

    public void setCollection(Boolean collection) {
        isCollection = collection;
    }

    public Integer getCartGoodsCount() {
        return cartGoodsCount;
    }

    public void setCartGoodsCount(Integer cartGoodsCount) {
        this.cartGoodsCount = cartGoodsCount;
    }
}
