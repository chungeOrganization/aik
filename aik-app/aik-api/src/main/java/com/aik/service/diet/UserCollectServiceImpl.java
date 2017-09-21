package com.aik.service.diet;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.DietUserCollectFoodMapper;
import com.aik.exception.ApiServiceException;
import com.aik.model.DietUserCollectFood;
import com.aik.resource.SystemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
@Service
public class UserCollectServiceImpl implements UserCollectService {

    private static final Logger logger = LoggerFactory.getLogger(UserCollectServiceImpl.class);

    private DietUserCollectFoodMapper dietUserCollectFoodMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setDietUserCollectFoodMapper(DietUserCollectFoodMapper dietUserCollectFoodMapper) {
        this.dietUserCollectFoodMapper = dietUserCollectFoodMapper;
    }

    @Override
    public List<Map<String, Object>> getUserCollectFoods(Integer userId) throws ApiServiceException {
        if (null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        List<Map<String, Object>> collectFoods = dietUserCollectFoodMapper.selectUserCollectFoods(userId);
        for (Map<String, Object> collectFood : collectFoods) {
            collectFood.put("image", systemResource.getApiFileUri() + collectFood.get("image"));
        }

        return collectFoods;
    }

    @Override
    public void delUserCollectFoods(List<Integer> collectFoodIds) throws ApiServiceException {
        if (null == collectFoodIds || collectFoodIds.size() == 0) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        for (Integer collectFoodId : collectFoodIds) {
            dietUserCollectFoodMapper.deleteByPrimaryKey(collectFoodId);
        }
    }

    @Override
    public void userCollectFood(Integer foodId, Integer userId) throws ApiServiceException {
        if (null == foodId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        DietUserCollectFood searchDF = new DietUserCollectFood();
        searchDF.setFoodId(foodId);
        searchDF.setUserId(userId);
        boolean isCollect = dietUserCollectFoodMapper.selectCountBySelective(searchDF) > 0;
        if (!isCollect) {
            DietUserCollectFood userCollectFood = new DietUserCollectFood();
            userCollectFood.setUserId(userId);
            userCollectFood.setFoodId(foodId);
            userCollectFood.setCreateDate(new Date());

            dietUserCollectFoodMapper.insertSelective(userCollectFood);
        }
    }

    @Override
    public void userCancelCollectFood(Integer foodId, Integer userId) throws ApiServiceException {
        if (null == foodId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        DietUserCollectFood searchDF = new DietUserCollectFood();
        searchDF.setFoodId(foodId);
        searchDF.setUserId(userId);
        List<DietUserCollectFood> userCollectFoods = dietUserCollectFoodMapper.selectBySelective(searchDF);

        if (userCollectFoods.size() > 0) {
            for (DietUserCollectFood userCollectFood : userCollectFoods) {
                dietUserCollectFoodMapper.deleteByPrimaryKey(userCollectFood.getId());
            }
        }
    }
}
