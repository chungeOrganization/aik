package com.aik.service.store;

import com.aik.dao.AccDoctorDealDetailMapper;
import com.aik.enums.DoctorDealTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccDoctorDealDetail;
import com.aik.resource.SystemResource;
import com.aik.util.DateUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
@Service
public class DoctorDealServiceImpl implements DoctorDealService{

    private static final Logger logger = LoggerFactory.getLogger(DoctorDealServiceImpl.class);

    private AccDoctorDealDetailMapper accDoctorDealDetailMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setAccDoctorDealDetailMapper(AccDoctorDealDetailMapper accDoctorDealDetailMapper) {
        this.accDoctorDealDetailMapper = accDoctorDealDetailMapper;
    }

    @Override
    public Integer getSellDealCount(Integer doctorId) throws ApiServiceException {
        byte dealType = DoctorDealTypeEnum.SELL_COMMISSION.getCode();
        AccDoctorDealDetail searchAD = new AccDoctorDealDetail();
        searchAD.setDoctorId(doctorId);
        searchAD.setDealType(dealType);

        return accDoctorDealDetailMapper.selectCountBySelective(searchAD);
    }

    @Override
    public Map<String, Object> getSellDealDetails(Map<String, Object> params) throws ApiServiceException {
        Map<String, Object> rsData = new HashMap<>();
        byte dealType = DoctorDealTypeEnum.SELL_COMMISSION.getCode();

        // 查询时间处理
        if (null == params.get("yearMonth") || StringUtils.isBlank(params.get("yearMonth").toString())) {
            params.put("yearMonth", DateUtils.showDate(new Date(), "yyyy-MM"));
        }

        params.put("dealType", dealType);
        BigDecimal sumAmount = accDoctorDealDetailMapper.selectSumAmountByParams(params);
        rsData.put("sumIncome", sumAmount);

        List<Map<String, Object>> orderList = accDoctorDealDetailMapper.selectDetailsByParams(params);
        for (Map<String, Object> map : orderList) {
            if (null != map.get("userHeadImg")) {
                map.put("userHeadImg", systemResource.getApiFileUri() + map.get("userHeadImg").toString());
            }
        }
        rsData.put("orderList", orderList);

        // yearMonth
        String yearMonth = params.get("yearMonth").toString();
        rsData.put("yearMonth", DateUtils.showDate(DateUtils.parseDate(yearMonth, "yyyy-MM"), "yyyy年MM月"));

        return rsData;
    }

    @Override
    public Map<String, Object> getDealDetails(Map<String, Object> params) throws ApiServiceException {
        if (null != params.get("dealType") && "-1".equals(params.get("dealType").toString())) {
            params.remove("dealType");
        }

        // 查询时间处理
        if (null == params.get("yearMonth") || StringUtils.isBlank(params.get("yearMonth").toString())) {
            params.put("yearMonth", DateUtils.showDate(new Date(), "yyyy-MM"));
        }

        Map<String, Object> rsData = new HashMap<>();
        BigDecimal sumAmount = accDoctorDealDetailMapper.selectSumAmountByParams(params);
        rsData.put("sumIncome", sumAmount);

        List<Map<String, Object>> orderList = accDoctorDealDetailMapper.selectDetailsByParams(params);
        for (Map<String, Object> map : orderList) {
            if (null != map.get("userHeadImg")) {
                map.put("userHeadImg", systemResource.getApiFileUri() + map.get("userHeadImg").toString());
            }

            if(DoctorDealTypeEnum.WITHDRAW.getCode() == Byte.valueOf(map.get("dealType").toString())) {
                // TODO:这里需要处理,包括提现状态
                map.put("userHeadImg", systemResource.getApiFileUri() + "system/withdraw-headImg.png");

            }
        }
        rsData.put("orderList", orderList);

        // yearMonth
        String yearMonth = params.get("yearMonth").toString();
        rsData.put("yearMonth", DateUtils.showDate(DateUtils.parseDate(yearMonth, "yyyy-MM"), "yyyy年MM月"));

        return rsData;
    }
}
