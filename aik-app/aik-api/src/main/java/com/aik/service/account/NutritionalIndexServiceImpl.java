package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AikNutritionalIndexMapper;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikNutritionalIndex;
import com.aik.service.NutritionLesson.NutritionLessonServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Description:
 * Created by as on 2017/9/9.
 */
@Service
public class NutritionalIndexServiceImpl implements NutritionalIndexService {

    private static final Logger logger = LoggerFactory.getLogger(NutritionLessonServiceImpl.class);

    private AikNutritionalIndexMapper aikNutritionalIndexMapper;

    @Autowired
    public void setAikNutritionalIndexMapper(AikNutritionalIndexMapper aikNutritionalIndexMapper) {
        this.aikNutritionalIndexMapper = aikNutritionalIndexMapper;
    }

    @Override
    public AikNutritionalIndex getUserNutritionalIndex(Integer userId) throws ApiServiceException {
        if (null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        return aikNutritionalIndexMapper.selectByUserId(userId);
    }

    @Override
    public void updateUserNutritionalIndex(AikNutritionalIndex nutritionalIndex) throws ApiServiceException {
        if (null == nutritionalIndex || null == nutritionalIndex.getHeight() || null == nutritionalIndex.getWeight()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        BigDecimal bmi = countBMI(nutritionalIndex.getHeight(), nutritionalIndex.getWeight());

        if (null == nutritionalIndex.getId()) {
            nutritionalIndex.setBmi(bmi);
            nutritionalIndex.setCreateDate(new Date());

            aikNutritionalIndexMapper.insertSelective(nutritionalIndex);
        } else {
            nutritionalIndex.setBmi(bmi);
            nutritionalIndex.setUpdateDate(new Date());

            aikNutritionalIndexMapper.updateByPrimaryKeySelective(nutritionalIndex);
        }
    }

    private BigDecimal countBMI(BigDecimal height, BigDecimal weight) {
        height = height.divide(new BigDecimal(100), 2, BigDecimal.ROUND_HALF_UP);
        return weight.divide(height.multiply(height), 2, BigDecimal.ROUND_HALF_UP);
    }
}
