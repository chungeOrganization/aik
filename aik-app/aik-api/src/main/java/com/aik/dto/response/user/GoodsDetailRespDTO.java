package com.aik.dto.response.user;

import lombok.Data;

import java.math.BigDecimal;

/**
 * Description:
 * Created by as on 2017/10/23.
 */
@Data
public class GoodsDetailRespDTO {

    private Integer goodsId;

    private String goodsName;

    private String goodsImg;

    private BigDecimal goodsPrice;

    private Integer goodsStock;

    private Boolean isCollection;

    private Integer cartGoodsCount;

    private MerchantInfoRespDTO merchantInfo;
}
