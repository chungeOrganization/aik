package com.aik.dto.response.user;

import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/10/23.
 */
@Data
public class GoodsDetailRespDTO {

    private Integer goodsId;

    private String goodsName;

    private List<String> goodsImgs;

    private BigDecimal goodsPrice;

    private Integer goodsStock;

    private Boolean isCollection;

    private Integer cartGoodsCount;

    private MerchantInfoRespDTO merchantInfo;

    private String serviceTel;
}
