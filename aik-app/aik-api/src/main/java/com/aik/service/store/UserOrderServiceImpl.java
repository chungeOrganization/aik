package com.aik.service.store;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.*;
import com.aik.dto.PayStoOrderDTO;
import com.aik.dto.request.user.AppraiseOrderReqDTO;
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

import javax.annotation.Resource;
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

        return orderDetail;
    }

    @Override
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
}
