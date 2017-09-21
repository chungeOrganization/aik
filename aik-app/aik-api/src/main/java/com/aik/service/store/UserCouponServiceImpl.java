package com.aik.service.store;

import com.aik.dao.AikUserCouponMapper;
import com.aik.exception.ApiServiceException;
import com.aik.util.DateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/5.
 */
@Service
public class UserCouponServiceImpl implements UserCouponService {

    private static final Logger logger = LoggerFactory.getLogger(UserCouponServiceImpl.class);

    private AikUserCouponMapper aikUserCouponMapper;

    @Autowired
    public void setAikUserCouponMapper(AikUserCouponMapper aikUserCouponMapper) {
        this.aikUserCouponMapper = aikUserCouponMapper;
    }

    @Override
    public List<Map<String, Object>> getUserCoupons(Map<String, Object> params) throws ApiServiceException {
        List<Map<String, Object>> userCoupons = aikUserCouponMapper.selectByParams(params);
        for (Map<String, Object> userCoupon : userCoupons) {
            userCoupon.put("startDate", DateUtils.aikCouponDate(userCoupon.get("startDate").toString()));
            userCoupon.put("endDate", DateUtils.aikCouponDate(userCoupon.get("endDate").toString()));
        }

        return userCoupons;
    }
}
