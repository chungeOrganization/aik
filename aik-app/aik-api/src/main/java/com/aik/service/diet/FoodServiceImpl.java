package com.aik.service.diet;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.*;
import com.aik.enums.ExcursionEnum;
import com.aik.enums.FoodBrightSpotEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.resource.SystemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
@Service
public class FoodServiceImpl implements FoodService {

    private static final Logger logger = LoggerFactory.getLogger(FoodServiceImpl.class);

    private DietFoodMapper dietFoodMapper;

    private DietUserCollectFoodMapper dietUserCollectFoodMapper;

    private DietFoodBloodSugarMapper dietFoodBloodSugarMapper;

    private DietFoodProteinMapper dietFoodProteinMapper;

    private DietFoodFatMapper dietFoodFatMapper;

    private DietFoodMoreElementMapper dietFoodMoreElementMapper;

    private DietFoodCategoryMapper dietFoodCategoryMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setDietFoodMapper(DietFoodMapper dietFoodMapper) {
        this.dietFoodMapper = dietFoodMapper;
    }

    @Autowired
    public void setDietUserCollectFoodMapper(DietUserCollectFoodMapper dietUserCollectFoodMapper) {
        this.dietUserCollectFoodMapper = dietUserCollectFoodMapper;
    }

    @Autowired
    public void setDietFoodBloodSugarMapper(DietFoodBloodSugarMapper dietFoodBloodSugarMapper) {
        this.dietFoodBloodSugarMapper = dietFoodBloodSugarMapper;
    }

    @Autowired
    public void setDietFoodProteinMapper(DietFoodProteinMapper dietFoodProteinMapper) {
        this.dietFoodProteinMapper = dietFoodProteinMapper;
    }

    @Autowired
    public void setDietFoodFatMapper(DietFoodFatMapper dietFoodFatMapper) {
        this.dietFoodFatMapper = dietFoodFatMapper;
    }

    @Autowired
    public void setDietFoodMoreElementMapper(DietFoodMoreElementMapper dietFoodMoreElementMapper) {
        this.dietFoodMoreElementMapper = dietFoodMoreElementMapper;
    }

    @Autowired
    public void setDietFoodCategoryMapper(DietFoodCategoryMapper dietFoodCategoryMapper) {
        this.dietFoodCategoryMapper = dietFoodCategoryMapper;
    }

    @Override
    public Map<String, Object> getFoodDetailWithUser(Integer foodId, Integer userId) throws ApiServiceException {
        if (null == foodId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Map<String, Object> foodDetail = new HashMap<>();

        // 食物信息
        DietFood food = dietFoodMapper.selectByPrimaryKey(foodId);
        if (null == food) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004006);
        }

        Map<String, Object> foodInfo = new HashMap<>();
        foodInfo.put("image", systemResource.getApiFileUri() + food.getImage());
        foodInfo.put("name", food.getName());
        foodInfo.put("recommend", food.getRecommend());

        DietUserCollectFood userCollectFood = new DietUserCollectFood();
        userCollectFood.setUserId(userId);
        userCollectFood.setFoodId(foodId);
        boolean isCollect = dietUserCollectFoodMapper.selectCountBySelective(userCollectFood) > 0;
        foodInfo.put("isCollect", isCollect);
        foodInfo.put("brightSpot", food.getBrightSpot());
        foodInfo.put("brightSpotDesc", FoodBrightSpotEnum.getDescFromCode(food.getBrightSpot()));
        foodInfo.put("spotRank", food.getSpotRank());
        foodInfo.put("proteinRadio", food.getProteinRadio());
        foodInfo.put("fatRadio", food.getFatRadio());
        foodInfo.put("carbsRadio", food.getCarbsRadio());
        foodInfo.put("naRadio", food.getNaRadio());

        foodDetail.put("foodInfo", foodInfo);

        // 血糖相关
        Map<String, Object> foodBloodSugarMap = new HashMap<>();
        DietFoodBloodSugar foodBloodSugar = dietFoodBloodSugarMapper.selectByFoodId(foodId);
        if (null != foodBloodSugar) {
            foodBloodSugarMap.put("gi", foodBloodSugar.getGi());
            // TODO:对比设定值
            foodBloodSugarMap.put("giExcursion", ExcursionEnum.NORMAL.getDesc());
            foodBloodSugarMap.put("gl", foodBloodSugar.getGl());
            // TODO:对比设定值
            foodBloodSugarMap.put("glExcursion", ExcursionEnum.NORMAL.getDesc());
        }

        foodDetail.put("foodBloodSugar", foodBloodSugarMap);

        // 蛋白质
        Map<String, Object> foodProteinMap = new HashMap<>();
        DietFoodProtein foodProtein = dietFoodProteinMapper.selectByFoodId(foodId);
        if (null != foodProtein) {
            foodProteinMap.put("aminoAcid1", foodProtein.getAminoAcid1());
            // TODO:对比设定值
            foodProteinMap.put("aminoAcid1Excursion", ExcursionEnum.NORMAL.getDesc());
            foodProteinMap.put("aminoAcid2", foodProtein.getAminoAcid2());
            // TODO:对比设定值
            foodProteinMap.put("aminoAcid2Excursion", ExcursionEnum.NORMAL.getDesc());
            foodProteinMap.put("aminoAcid3", foodProtein.getAminoAcid3());
            // TODO:对比设定值
            foodProteinMap.put("aminoAcid3Excursion", ExcursionEnum.NORMAL.getDesc());
        }

        foodDetail.put("foodProtein", foodProteinMap);

        // 脂肪
        Map<String, Object> foodFatMap = new HashMap<>();
        DietFoodFat foodFat = dietFoodFatMapper.selectByFoodId(foodId);
        if (null != foodFat) {
            foodFatMap.put("saturatedFattyAcid", foodFat.getSaturatedFattyAcid());
            // TODO:对比设定值
            foodFatMap.put("saturatedFattyAcidExcursion", ExcursionEnum.NORMAL.getDesc());
            foodFatMap.put("unsaturatedFattyAcid", foodFat.getUnsaturatedFattyAcid());
            // TODO:对比设定值
            foodFatMap.put("unsaturatedFattyAcidExcursion", ExcursionEnum.NORMAL.getDesc());
        }

        foodDetail.put("foodFat", foodFatMap);

        return foodDetail;
    }

    @Override
    public List<Map<String, Object>> getFoodMoreElements(Integer foodId) throws ApiServiceException {
        if (null == foodId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        return dietFoodMoreElementMapper.selectFoodMoreElements(foodId);
    }

    @Override
    public List<Map<String, Object>> getFoodCategories() throws ApiServiceException {
        List<Map<String, Object>> foodCategoryList = new ArrayList<>();

        List<DietFoodCategory> foodCategories = dietFoodCategoryMapper.selectAllFoodCategory();
        for (DietFoodCategory foodCategory : foodCategories) {
            Map<String, Object> foodCategoryMap = new HashMap<>();
            foodCategoryMap.put("id", foodCategory.getId());
            foodCategoryMap.put("image", systemResource.getApiFileUri() + foodCategory.getImage());
            foodCategoryMap.put("name", foodCategory.getName());

            foodCategoryList.add(foodCategoryMap);
        }

        return foodCategoryList;
    }

    @Override
    public List<Map<String, Object>> getFoods(Map<String, Object> params) throws ApiServiceException {
        List<Map<String, Object>> foods = dietFoodMapper.selectByParams(params);
        for (Map<String, Object> food : foods) {
            food.put("image", systemResource.getApiFileUri() + food.get("image"));
        }

        return foods;
    }
}
