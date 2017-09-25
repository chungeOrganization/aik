package com.aik.service.nutritionLesson;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AikNutritionLessonMapper;
import com.aik.dao.AikNutritionLessonViewMapper;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikNutritionLesson;
import com.aik.model.AikNutritionLessonView;
import com.aik.resource.SystemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
@Service
public class NutritionLessonServiceImpl implements NutritionLessonService{

    private static final Logger logger = LoggerFactory.getLogger(NutritionLessonServiceImpl.class);

    private AikNutritionLessonMapper aikNutritionLessonMapper;

    private AikNutritionLessonViewMapper aikNutritionLessonViewMapper;

    @Autowired
    public void setAikNutritionLessonMapper(AikNutritionLessonMapper aikNutritionLessonMapper) {
        this.aikNutritionLessonMapper = aikNutritionLessonMapper;
    }

    @Autowired
    public void setAikNutritionLessonViewMapper(AikNutritionLessonViewMapper aikNutritionLessonViewMapper) {
        this.aikNutritionLessonViewMapper = aikNutritionLessonViewMapper;
    }

    @Resource
    private SystemResource systemResource;

    @Override
    public List<Map<String, Object>> getNutritionLessons(Map<String, Object> params) throws ApiServiceException {
        List<Map<String, Object>> nutritionLessonList = new ArrayList<>();

        List<AikNutritionLesson> nutritionLessons = aikNutritionLessonMapper.selectByParams(params);
        for (AikNutritionLesson nutritionLesson : nutritionLessons) {
            Map<String, Object> nutritionLessonMap = new HashMap<>();
            nutritionLessonMap.put("id", nutritionLesson.getId());
            nutritionLessonMap.put("image", systemResource.getApiFileUri() + nutritionLesson.getImage());
            nutritionLessonMap.put("title", nutritionLesson.getTitle());
            nutritionLessonMap.put("issueDate", nutritionLesson.getIssueDate());

            AikNutritionLessonView searchNL = new AikNutritionLessonView();
            searchNL.setLessonId(nutritionLesson.getId());
            nutritionLessonMap.put("viewCount", aikNutritionLessonViewMapper.selectCountBySelective(searchNL));

            nutritionLessonList.add(nutritionLessonMap);
        }
        return nutritionLessonList;
    }

    @Override
    public AikNutritionLesson getNutritionLessonDetail(Integer nutritionLessonId, Integer userId) throws ApiServiceException {
        if (null == nutritionLessonId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikNutritionLesson nutritionLesson = aikNutritionLessonMapper.selectByPrimaryKey(nutritionLessonId);
        if (null == nutritionLesson) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004008);
        }

        AikNutritionLessonView searchNL = new AikNutritionLessonView();
        searchNL.setUserId(userId);
        searchNL.setLessonId(nutritionLessonId);
        boolean isView = aikNutritionLessonViewMapper.selectCountBySelective(searchNL) > 0;
        if (!isView) {
            AikNutritionLessonView nutritionLessonView = new AikNutritionLessonView();
            nutritionLessonView.setUserId(userId);
            nutritionLessonView.setLessonId(nutritionLessonId);
            nutritionLessonView.setCreateDate(new Date());

            aikNutritionLessonViewMapper.insertSelective(nutritionLessonView);
        }

        return nutritionLesson;
    }
}
