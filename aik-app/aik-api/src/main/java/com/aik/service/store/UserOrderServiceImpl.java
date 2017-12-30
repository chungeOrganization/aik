package com.aik.service.store;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.*;
import com.aik.dto.PayStoOrderDTO;
import com.aik.dto.request.user.AppraiseOrderReqDTO;
import com.aik.dto.request.user.AtOncePurchaseGoodsReqDTO;
import com.aik.dto.response.user.OrderLogisticsInfoRespDTO;
import com.aik.enums.DelFlagEnum;
import com.aik.enums.UserFileTypeEnum;
import com.aik.enums.UserOrderStatusEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.resource.SystemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.*;

/**
 * Description:
 * Created by as on 2017/9/2.
 */
@Service
public class UserOrderServiceImpl implements UserOrderService {

    private static final Logger logger = LoggerFactory.getLogger(UserOrderServiceImpl.class);

    private StoUserOrderMapper stoUserOrderMapper;

    private StoUserOrderDetailMapper stoUserOrderDetailMapper;

    private StoAcceptAddressMapper stoAcceptAddressMapper;

    private StoOrderLogisticsInfoMapper stoOrderLogisticsInfoMapper;

    private SysLogisticsCompanyMapper sysLogisticsCompanyMapper;

    private StoLogisticsTrackInfoMapper stoLogisticsTrackInfoMapper;

    private AccUserFileMapper accUserFileMapper;

    private StoGoodsMapper stoGoodsMapper;

    private StoShoppingCartMapper stoShoppingCartMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setStoUserOrderMapper(StoUserOrderMapper stoUserOrderMapper) {
        this.stoUserOrderMapper = stoUserOrderMapper;
    }

    @Autowired
    public void setStoUserOrderDetailMapper(StoUserOrderDetailMapper stoUserOrderDetailMapper) {
        this.stoUserOrderDetailMapper = stoUserOrderDetailMapper;
    }

    @Autowired
    public void setStoAcceptAddressMapper(StoAcceptAddressMapper stoAcceptAddressMapper) {
        this.stoAcceptAddressMapper = stoAcceptAddressMapper;
    }

    @Autowired
    public void setStoOrderLogisticsInfoMapper(StoOrderLogisticsInfoMapper stoOrderLogisticsInfoMapper) {
        this.stoOrderLogisticsInfoMapper = stoOrderLogisticsInfoMapper;
    }

    @Autowired
    public void setSysLogisticsCompanyMapper(SysLogisticsCompanyMapper sysLogisticsCompanyMapper) {
        this.sysLogisticsCompanyMapper = sysLogisticsCompanyMapper;
    }

    @Autowired
    public void setStoLogisticsTrackInfoMapper(StoLogisticsTrackInfoMapper stoLogisticsTrackInfoMapper) {
        this.stoLogisticsTrackInfoMapper = stoLogisticsTrackInfoMapper;
    }

    @Autowired
    public void setAccUserFileMapper(AccUserFileMapper accUserFileMapper) {
        this.accUserFileMapper = accUserFileMapper;
    }

    @Autowired
    public void setStoGoodsMapper(StoGoodsMapper stoGoodsMapper) {
        this.stoGoodsMapper = stoGoodsMapper;
    }

    @Autowired
    public void setStoShoppingCartMapper(StoShoppingCartMapper stoShoppingCartMapper) {
        this.stoShoppingCartMapper = stoShoppingCartMapper;
    }

    @Override
    public List<Map<String, Object>> getUserOrderList(Map<String, Object> params) throws ApiServiceException {
        List<Map<String, Object>> orderList = new ArrayList<>();

        List<StoUserOrder> userOrders = stoUserOrderMapper.selectByParams(params);
        for (StoUserOrder userOrder : userOrders) {
            Map<String, Object> userOrderMap = new HashMap<>();
            userOrderMap.put("orderId", userOrder.getId());
            userOrderMap.put("orderNum", userOrder.getOrderNum());
            userOrderMap.put("status", userOrder.getStatus());
            userOrderMap.put("amount", userOrder.getAmount());

            StoUserOrderDetail searchUO = new StoUserOrderDetail();
            searchUO.setOrderId(userOrder.getId());
            searchUO.setUserId(userOrder.getUserId());
            List<Map<String, Object>> orderGoodsList = stoUserOrderDetailMapper.selectDetailsBySelective(searchUO);
            for (Map<String, Object> orderGoods : orderGoodsList) {
                orderGoods.put("goodsImg", systemResource.getApiFileUri() + orderGoods.get("goodsImg"));
            }

            userOrderMap.put("orderGoodsList", orderGoodsList);

            orderList.add(userOrderMap);
        }

        return orderList;
    }

    @Override
    @Transactional
    public Map<String, Object> shoppingCartSettle(Integer userId, List<Integer> scIds) throws ApiServiceException {
        if (null == scIds || scIds.size() == 0) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        BigDecimal amount = BigDecimal.ZERO;
        List<StoUserOrderDetail> orderDetails = new ArrayList<>();
        for (Integer scId : scIds) {
            StoShoppingCart shoppingCart = stoShoppingCartMapper.selectByPrimaryKey(scId);
            StoGoods goods = stoGoodsMapper.selectByPrimaryKey(shoppingCart.getGoodsId());
            amount = amount.add(goods.getPrice().multiply(BigDecimal.valueOf(shoppingCart.getNumber())));

            StoUserOrderDetail orderDetail = new StoUserOrderDetail();
            orderDetail.setUserId(userId);
            orderDetail.setGoodsId(goods.getId());
            orderDetail.setNumber(shoppingCart.getNumber());
            orderDetail.setAmount(goods.getPrice().multiply(BigDecimal.valueOf(shoppingCart.getNumber())));
            orderDetail.setCreateDate(new Date());
            orderDetails.add(orderDetail);
        }

        StoUserOrder userOrder = new StoUserOrder();
        userOrder.setOrderNum(System.currentTimeMillis() + "");
        userOrder.setUserId(userId);
        userOrder.setAmount(amount);
        userOrder.setCreateDate(new Date());

        stoUserOrderMapper.insertSelective(userOrder);

        for(StoUserOrderDetail userOrderDetail : orderDetails) {
            userOrderDetail.setOrderId(userOrder.getId());

            stoUserOrderDetailMapper.insertSelective(userOrderDetail);
        }

        return getConfirmOrderDetail(userOrder.getId());
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public Map<String, Object> getConfirmOrderDetail(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        StoUserOrder userOrder = stoUserOrderMapper.selectByPrimaryKey(orderId);
        if (null == userOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005001);
        }

        if (userOrder.getStatus() != UserOrderStatusEnum.WAITING_PAY.getStatus()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005002);
        }

        Map<String, Object> orderDetail = new HashMap<>();
        orderDetail.put("orderId", orderId);

        // 收货地址
        Integer acceptAddressId = userOrder.getAcceptAddressId();
        StoAcceptAddress acceptAddress;
        if (null == acceptAddressId) {
            acceptAddress = stoAcceptAddressMapper.selectUserDefaultAddress(userOrder.getUserId());
        } else {
            acceptAddress = stoAcceptAddressMapper.selectByPrimaryKey(acceptAddressId);
        }

        if (null != acceptAddress) {
            Map<String, Object> acceptAddressMap = new HashMap<>();
            acceptAddressMap.put("addressId", acceptAddress.getId());
            acceptAddressMap.put("name", acceptAddress.getName());
            acceptAddressMap.put("mobileNo", acceptAddress.getMobileNo());
            acceptAddressMap.put("province", acceptAddress.getProvince());
            acceptAddressMap.put("city", acceptAddress.getCity());
            acceptAddressMap.put("area", acceptAddress.getArea());
            acceptAddressMap.put("address", acceptAddress.getAddress());

            orderDetail.put("address", acceptAddressMap);
        } else {
            orderDetail.put("address", new HashMap<>());
        }

        // TODO：运费
        orderDetail.put("freightAmount", userOrder.getFreightAmount());

        // 商品详情
        StoUserOrderDetail searchUO = new StoUserOrderDetail();
        searchUO.setOrderId(userOrder.getId());
        searchUO.setUserId(userOrder.getUserId());
        List<Map<String, Object>> orderGoodsList = stoUserOrderDetailMapper.selectDetailsBySelective(searchUO);
        for (Map<String, Object> orderGoods : orderGoodsList) {
            orderGoods.put("goodsImg", systemResource.getApiFileUri() + orderGoods.get("goodsImg"));
        }

        orderDetail.put("orderGoodsList", orderGoodsList);

        // 商品数量
        int goodsCount = stoUserOrderDetailMapper.selectGoodsCountBySelective(searchUO);
        orderDetail.put("goodsCount", goodsCount);

        // 订单总价
        orderDetail.put("goodsAmount", userOrder.getAmount());

        // 仓库名称
        orderDetail.put("storageName", "华纳自营仓库");

        return orderDetail;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void payOrderSuccess(PayStoOrderDTO payStoOrderDTO) throws ApiServiceException {
        if (null == payStoOrderDTO || null == payStoOrderDTO.getOrderId() ||
                null == payStoOrderDTO.getAcceptAddressId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        StoUserOrder userOrder = stoUserOrderMapper.selectByPrimaryKey(payStoOrderDTO.getOrderId());
        if (null == userOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005001);
        }

        if (userOrder.getStatus() != UserOrderStatusEnum.WAITING_PAY.getStatus()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005002);
        }

        userOrder.setAcceptAddressId(payStoOrderDTO.getAcceptAddressId());
        userOrder.setStatus(UserOrderStatusEnum.WAITING_DELIVERY.getStatus());
        userOrder.setPayTime(payStoOrderDTO.getPayTime());
        userOrder.setLeaveMsg(payStoOrderDTO.getLeaveMsg());
        userOrder.setUpdateDate(new Date());

        stoUserOrderMapper.updateByPrimaryKeySelective(userOrder);
    }

    @Override
    public void cancelUserOrder(Integer orderId, Integer userId) throws ApiServiceException {
        if (null == orderId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        StoUserOrder userOrder = stoUserOrderMapper.selectByPrimaryKey(orderId);
        if (null == userOrder || userOrder.getUserId().intValue() != userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005001);
        }

        if (userOrder.getStatus() != UserOrderStatusEnum.WAITING_PAY.getStatus()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005002);
        }

        StoUserOrder updateUserOrder = new StoUserOrder();
        updateUserOrder.setId(userOrder.getId());
        updateUserOrder.setDelFlag(DelFlagEnum.DELETED.getCode());
        updateUserOrder.setUpdateDate(new Date());
        stoUserOrderMapper.updateByPrimaryKeySelective(updateUserOrder);
    }

    @Override
    public OrderLogisticsInfoRespDTO getOrderLogisticsInfo(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        StoOrderLogisticsInfo orderLogisticsInfo = stoOrderLogisticsInfoMapper.selectByOrderId(orderId);
        if (null == orderLogisticsInfo) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005003);
        }

        OrderLogisticsInfoRespDTO orderLogisticsInfoResp = new OrderLogisticsInfoRespDTO();
        SysLogisticsCompany logisticsCompany = sysLogisticsCompanyMapper.selectByPrimaryKey(orderLogisticsInfo.getLogisticsCompany());

        orderLogisticsInfoResp.setLogisticsCompany(logisticsCompany.getCompanyName());
        orderLogisticsInfoResp.setExpressNum(orderLogisticsInfo.getExpressNum());
        orderLogisticsInfoResp.setPlaceTime(orderLogisticsInfo.getPlaceTime());
        orderLogisticsInfoResp.setOfficialPhone(logisticsCompany.getOfficialPhone());
        orderLogisticsInfoResp.setLogisticsImg(systemResource.getApiFileUri() + logisticsCompany.getLogisticsImg());

        List<String> trackInfos = new ArrayList<>();
        List<StoLogisticsTrackInfo> trackInfoList = stoLogisticsTrackInfoMapper.selectByOrderId(orderId);
        for (StoLogisticsTrackInfo logisticsTrackInfo : trackInfoList) {
            trackInfos.add(logisticsTrackInfo.getTrackInfo());
        }
        orderLogisticsInfoResp.setTrackInfos(trackInfos);

        return orderLogisticsInfoResp;
    }

    @Override
    public void confirmReceipt(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        StoUserOrder userOrder = stoUserOrderMapper.selectByPrimaryKey(orderId);
        if (null == userOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005001);
        }

        if (userOrder.getStatus() != UserOrderStatusEnum.DELIVERED.getStatus()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005002);
        }

        StoUserOrder updateUserOrder = new StoUserOrder();
        updateUserOrder.setId(userOrder.getId());
        updateUserOrder.setStatus(UserOrderStatusEnum.COMPLETE.getStatus());
        updateUserOrder.setUpdateDate(new Date());
        stoUserOrderMapper.updateByPrimaryKeySelective(updateUserOrder);
    }

    @Override
    public void returnOrder(Integer orderId) throws ApiServiceException {
        if (null == orderId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        StoUserOrder userOrder = stoUserOrderMapper.selectByPrimaryKey(orderId);
        if (null == userOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005001);
        }

        if (userOrder.getStatus() != UserOrderStatusEnum.DELIVERED.getStatus()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005002);
        }

        StoUserOrder updateUserOrder = new StoUserOrder();
        updateUserOrder.setId(userOrder.getId());
        updateUserOrder.setStatus(UserOrderStatusEnum.RETURN.getStatus());
        updateUserOrder.setUpdateDate(new Date());
        stoUserOrderMapper.updateByPrimaryKeySelective(updateUserOrder);

        // TODO:是否插入退货申请
    }

    @Override
    public void againOrder(Integer orderId) throws ApiServiceException {
        // TODO:再来一单
    }

    @Override
    public void appraiseOrder(AppraiseOrderReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        StoUserOrder userOrder = stoUserOrderMapper.selectByPrimaryKey(reqDTO.getOrderId());
        if (null == userOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005001);
        }

        if (userOrder.getStatus() != UserOrderStatusEnum.COMPLETE.getStatus()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1005002);
        }

        StoUserOrder updateUserOrder = new StoUserOrder();
        updateUserOrder.setId(userOrder.getId());
        updateUserOrder.setAppraise(reqDTO.getAppraise());
        updateUserOrder.setGoodsQuality(reqDTO.getGoodsQuality().byteValue());
        updateUserOrder.setLogisticsSpeed(reqDTO.getLogisticsSpeed().byteValue());
        updateUserOrder.setUpdateDate(new Date());
        stoUserOrderMapper.updateByPrimaryKeySelective(updateUserOrder);

        List<String> files = reqDTO.getFiles();
        if (null != files && files.size() > 0) {
            for (String fileUrl : files) {
                AccUserFile userFile = new AccUserFile();
                userFile.setUserId(userOrder.getUserId());
                userFile.setType(UserFileTypeEnum.STORE_APPRAISE.getCode());
                userFile.setFileUrl(fileUrl);
                userFile.setRelationId(userOrder.getId());
                userFile.setCreateDate(new Date());

                accUserFileMapper.insertSelective(userFile);
            }
        }
    }

    @Override
    public Map<String, Object> atOncePurchaseGoods(AtOncePurchaseGoodsReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        StoGoods goods = stoGoodsMapper.selectByPrimaryKey(reqDTO.getGoodsId());
        BigDecimal amount = goods.getPrice().multiply(new BigDecimal(reqDTO.getGoodsNumber()));

        StoUserOrder userOrder = new StoUserOrder();
        userOrder.setOrderNum(System.currentTimeMillis() + "");
        userOrder.setUserId(reqDTO.getUserId());
        userOrder.setAmount(amount);
        userOrder.setCreateDate(new Date());
        stoUserOrderMapper.insertSelective(userOrder);

        // 插入userOrderDetail
        StoUserOrderDetail userOrderDetail = new StoUserOrderDetail();
        userOrderDetail.setUserId(reqDTO.getUserId());
        userOrderDetail.setOrderId(userOrder.getId());
        userOrderDetail.setGoodsId(reqDTO.getGoodsId());
        userOrderDetail.setNumber(reqDTO.getGoodsNumber());
        userOrderDetail.setAmount(amount);
        userOrderDetail.setCreateDate(new Date());

        stoUserOrderDetailMapper.insertSelective(userOrderDetail);

        return getConfirmOrderDetail(userOrder.getId());
    }
}
