package com.aik.service.nutritionLesson;

import com.aik.dto.request.PageReqDTO;
import com.aik.dto.request.user.CollectNutritionLessonReqDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikNutritionLesson;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public interface NutritionLessonService {

    /**
     * 获取营养课堂
     *
     * @param params 参数
     * @return 营养课堂
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getNutritionLessons(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取营养课堂详情
     *
     * @param nutritionLessonId 营养课堂id
     * @param userId            用户id
     * @return 营养课堂详情
     * @throws ApiServiceException Api服务异常
     */
    AikNutritionLesson getNutritionLessonDetail(Integer nutritionLessonId, Integer userId) throws ApiServiceException;

    /**
     * 获取营养课堂收藏
     *
     * @param reqDTO request
     * @param userId 用户id
     * @return 营养课堂收藏列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getNutritionLessonCollect(PageReqDTO reqDTO, Integer userId) throws ApiServiceException;

    /**
     * 收藏|取消收藏 营养课堂
     * @throws ApiServiceException
     */
    void collectNutritionLesson(CollectNutritionLessonReqDTO reqDTO) throws ApiServiceException;
}
