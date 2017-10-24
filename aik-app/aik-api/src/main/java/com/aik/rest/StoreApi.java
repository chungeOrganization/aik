package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.PayStoOrderDTO;
import com.aik.dto.UpdateShoppingCartDTO;
import com.aik.dto.request.user.AppraiseOrderReqDTO;
import com.aik.dto.request.user.AtOncePurchaseGoodsReqDTO;
import com.aik.dto.request.user.ShoppingCartAddGoodsReqDTO;
import com.aik.dto.response.user.GoodsDetailRespDTO;
import com.aik.dto.response.user.OrderLogisticsInfoRespDTO;
import com.aik.enums.GoodsTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.StoAcceptAddress;
import com.aik.resource.SystemResource;
import com.aik.security.AuthUserDetailsThreadLocal;
import com.aik.service.store.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import javax.annotation.Resource;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/2.
 */
@Path("/store")
@Produces(MediaType.APPLICATION_JSON)
public class StoreApi {
    private static final Logger logger = LoggerFactory.getLogger(StoreApi.class);

    private UserOrderService userOrderService;

    private ShoppingCartService shoppingCartService;

    private UserCouponService userCouponService;

    private AcceptAddressService acceptAddressService;

    private GoodsService goodsService;

    private SystemResource systemResource;

    @Inject
    public void setUserOrderService(UserOrderService userOrderService) {
        this.userOrderService = userOrderService;
    }

    @Inject
    public void setShoppingCartService(ShoppingCartService shoppingCartService) {
        this.shoppingCartService = shoppingCartService;
    }

    @Inject
    public void setUserCouponService(UserCouponService userCouponService) {
        this.userCouponService = userCouponService;
    }

    @Inject
    public void setAcceptAddressService(AcceptAddressService acceptAddressService) {
        this.acceptAddressService = acceptAddressService;
    }

    @Inject
    public void setGoodsService(GoodsService goodsService) {
        this.goodsService = goodsService;
    }

    @Inject
    public void setSystemResource(SystemResource systemResource) {
        this.systemResource = systemResource;
    }

    @POST
    @Path("/getMyOrder")
    public ApiResult getMyOrder(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("userId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> orderList = userOrderService.getUserOrderList(params);
            result.withDataKV("orderList", orderList);
        } catch (ApiServiceException e) {
            logger.error("get my order error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get my order error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/cancelUserOrder/{orderId}")
    public ApiResult cancelUserOrder(@PathParam("orderId") Integer orderId) {
        ApiResult result = new ApiResult();

        try {
            userOrderService.cancelUserOrder(orderId, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("cancel order error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("cancel order error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/getOrderLogisticsInfo/{orderId}")
    public ApiResult getOrderLogisticsInfo(@PathParam("orderId") Integer orderId) {
        ApiResult result = new ApiResult();

        try {
            OrderLogisticsInfoRespDTO orderLogisticsInfo = userOrderService.getOrderLogisticsInfo(orderId);
            result.withDataKV("orderLogisticsInfo", orderLogisticsInfo);
        } catch (ApiServiceException e) {
            logger.error("get order logistics info error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get order logistics info error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/confirmReceipt/{orderId}")
    public ApiResult confirmReceipt(@PathParam("orderId") Integer orderId) {
        ApiResult result = new ApiResult();

        try {
            userOrderService.confirmReceipt(orderId);
        } catch (ApiServiceException e) {
            logger.error("confirm receipt error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("confirm receipt error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/returnOrder/{orderId}")
    public ApiResult returnOrder(@PathParam("orderId") Integer orderId) {
        ApiResult result = new ApiResult();

        try {
            userOrderService.returnOrder(orderId);
        } catch (ApiServiceException e) {
            logger.error("return order error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("return order error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/againOrder/{orderId}")
    public ApiResult againOrder(@PathParam("orderId") Integer orderId) {
        ApiResult result = new ApiResult();

        try {
            userOrderService.againOrder(orderId);
        } catch (ApiServiceException e) {
            logger.error("again order error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("again order error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/appraiseOrder")
    public ApiResult appraiseOrder(AppraiseOrderReqDTO reqDTO) {
        ApiResult result = new ApiResult();

        try {
            userOrderService.appraiseOrder(reqDTO);
        } catch (ApiServiceException e) {
            logger.error("appraise order error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("appraise order error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getConfirmOrderDetail")
    public ApiResult getConfirmOrderDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer orderId = Integer.valueOf(params.get("orderId").toString());
            Map<String, Object> orderDetail = userOrderService.getConfirmOrderDetail(orderId);
            result.withDataKV("orderDetail", orderDetail);
        } catch (ApiServiceException e) {
            logger.error("get confirm order detail error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get confirm order detail error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/payOrderSuccess")
    public ApiResult payOrderSuccess(PayStoOrderDTO payStoOrderDTO) {
        ApiResult result = new ApiResult();

        try {
            userOrderService.payOrderSuccess(payStoOrderDTO);
        } catch (ApiServiceException e) {
            logger.error("pay order success error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("pay order success error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getMyShoppingCart")
    public ApiResult getMyShoppingCart() {
        ApiResult result = new ApiResult();

        try {
            Integer userId = AuthUserDetailsThreadLocal.getCurrentUserId();
            List<Map<String, Object>> shoppingCarts = shoppingCartService.getUserShoppingCart(userId);
            result.withDataKV("shoppingCarts", shoppingCarts);
        } catch (ApiServiceException e) {
            logger.error("get my shopping cart error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get my shopping cart error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/updateShoppingCart")
    public ApiResult updateShoppingCart(UpdateShoppingCartDTO updateShoppingCartDTO) {
        ApiResult result = new ApiResult();

        try {
            shoppingCartService.updateShoppingCart(updateShoppingCartDTO);
        } catch (ApiServiceException e) {
            logger.error("update shopping cart error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("update shopping cart error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getUserCoupons")
    public ApiResult getUserCoupons(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("userId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> userCoupons = userCouponService.getUserCoupons(params);
            result.withDataKV("userCoupons", userCoupons);
        } catch (ApiServiceException e) {
            logger.error("get user coupons error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user coupons error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getUserAcceptAddresses")
    public ApiResult getUserAcceptAddresses() {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> acceptAddresses = acceptAddressService.getUserAcceptAddresses(
                    AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("acceptAddresses", acceptAddresses);
        } catch (ApiServiceException e) {
            logger.error("get user accept addresses error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user accept addresses error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/updateUserAcceptAddress")
    public ApiResult updateUserAcceptAddress(StoAcceptAddress acceptAddress) {
        ApiResult result = new ApiResult();

        try {
            acceptAddress.setUserId(AuthUserDetailsThreadLocal.getCurrentUserId());
            acceptAddressService.updateAcceptAddress(acceptAddress);
        } catch (ApiServiceException e) {
            logger.error("update user accept addresses error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("update user accept addresses error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/addUserAcceptAddress")
    public ApiResult addUserAcceptAddress(StoAcceptAddress acceptAddress) {
        ApiResult result = new ApiResult();

        try {
            acceptAddress.setUserId(AuthUserDetailsThreadLocal.getCurrentUserId());
            acceptAddressService.addAcceptAddress(acceptAddress);
        } catch (ApiServiceException e) {
            logger.error("add user accept addresses error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("add user accept addresses error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/delUserAcceptAddress")
    public ApiResult delUserAcceptAddress(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer acceptAddressId = Integer.valueOf(params.get("acceptAddressId").toString());
            acceptAddressService.delAcceptAddress(acceptAddressId);
        } catch (ApiServiceException e) {
            logger.error("delete user accept addresses error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("delete user accept addresses error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getStoreGoodsTypes")
    public ApiResult getStoreGoodsTypes() {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> goodsTypes = new ArrayList<>();
            for (GoodsTypeEnum goodsTypeEnum : GoodsTypeEnum.values()) {
                Map<String, Object> goodsTypeMap = new HashMap<>();
                goodsTypeMap.put("type", goodsTypeEnum.getCode());
                goodsTypeMap.put("typeDesc", goodsTypeEnum.getDesc());

                goodsTypes.add(goodsTypeMap);
            }
            result.withDataKV("goodsTypes", goodsTypes);
        } catch (Exception e) {
            logger.error("get store goods types error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getRecommendPage")
    public ApiResult getRecommendPage() {
        ApiResult result = new ApiResult();

        try {
            // TODO:商店头部图片
            result.withDataKV("headImage", systemResource.getApiFileUri() + "/headImage.jpg");
            // TODO:商店底部图片
            result.withDataKV("bottomImage", systemResource.getApiFileUri() + "/bottomImage.jpg");

            result.withDataKV("recommendGoods", goodsService.getRecommendGoods());
        } catch (ApiServiceException e) {
            logger.error("get store recommend page error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get store recommend page error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getGoodsWithType")
    public ApiResult getGoodsWithType(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> goodsList = goodsService.getGoodsWithType(params);
            result.withDataKV("goodsList", goodsList);
        } catch (ApiServiceException e) {
            logger.error("get goods with type error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get goods with type error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/getGoodsDetail/{goodsId}")
    public ApiResult getGoodsDetail(@PathParam("goodsId") Integer goodsId) {
        ApiResult result = new ApiResult();

        try {
            GoodsDetailRespDTO goodsDetail = goodsService.getGoodsDetail(goodsId, AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("goodsDetail", goodsDetail);
        } catch (ApiServiceException e) {
            logger.error("get goods detail error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get goods detail error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/userCollectionGoods/{goodsId}")
    public ApiResult userCollectionGoods(@PathParam("goodsId") Integer goodsId) {
        ApiResult result = new ApiResult();

        try {
            goodsService.collectionGoods(goodsId, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("user collection goods error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user collection goods error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/userCancelCollectionGoods/{goodsId}")
    public ApiResult userCancelCollectionGoods(@PathParam("goodsId") Integer goodsId) {
        ApiResult result = new ApiResult();

        try {
            goodsService.cancelCollectionGoods(goodsId, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("user cancel collection goods error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user cancel collection goods error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/shoppingCartAddGoods")
    public ApiResult shoppingCartAddGoods(ShoppingCartAddGoodsReqDTO reqDTO) {
        ApiResult result = new ApiResult();

        try {
            reqDTO.setUserId(AuthUserDetailsThreadLocal.getCurrentUserId());
            shoppingCartService.shoppingCartAddGoods(reqDTO);
        } catch (ApiServiceException e) {
            logger.error("shopping cart add goods error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("shopping cart add goods error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/atOncePurchaseGoods")
    public ApiResult atOncePurchaseGoods(AtOncePurchaseGoodsReqDTO reqDTO) {
        ApiResult result = new ApiResult();

        try {
            reqDTO.setUserId(AuthUserDetailsThreadLocal.getCurrentUserId());
            Map<String, Object> orderDetail = userOrderService.atOncePurchaseGoods(reqDTO);
            result.withDataKV("orderDetail", orderDetail);
        } catch (ApiServiceException e) {
            logger.error("at once purchase goods error:", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("at once purchase goods error:", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }
}
