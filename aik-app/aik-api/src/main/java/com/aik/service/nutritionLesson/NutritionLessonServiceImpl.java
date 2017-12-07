package com.aik.service.nutritionLesson;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AikNutritionLessonCollectMapper;
import com.aik.dao.AikNutritionLessonMapper;
import com.aik.dao.AikNutritionLessonViewMapper;
import com.aik.dto.request.PageReqDTO;
import com.aik.dto.request.user.CollectNutritionLessonReqDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikNutritionLesson;
import com.aik.model.AikNutritionLessonCollect;
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
public class NutritionLessonServiceImpl implements NutritionLessonService {

    private static final Logger logger = LoggerFactory.getLogger(NutritionLessonServiceImpl.class);

    private AikNutritionLessonMapper aikNutritionLessonMapper;

    private AikNutritionLessonViewMapper aikNutritionLessonViewMapper;

    private AikNutritionLessonCollectMapper aikNutritionLessonCollectMapper;

    @Autowired
    public void setAikNutritionLessonMapper(AikNutritionLessonMapper aikNutritionLessonMapper) {
        this.aikNutritionLessonMapper = aikNutritionLessonMapper;
    }

    @Autowired
    public void setAikNutritionLessonViewMapper(AikNutritionLessonViewMapper aikNutritionLessonViewMapper) {
        this.aikNutritionLessonViewMapper = aikNutritionLessonViewMapper;
    }

    @Autowired
    public void setAikNutritionLessonCollectMapper(AikNutritionLessonCollectMapper aikNutritionLessonCollectMapper) {
        this.aikNutritionLessonCollectMapper = aikNutritionLessonCollectMapper;
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
        nutritionLesson.setImage(systemResource.getApiFileUri() + nutritionLesson.getImage());

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

    @Override
    public List<Map<String, Object>> getNutritionLessonCollect(PageReqDTO reqDTO, Integer userId) throws ApiServiceException {
        Map<String, Object> params = new HashMap<>();
        params.put("page", reqDTO.getPage());
        params.put("size", reqDTO.getSize());
        params.put("userId", userId);

        List<AikNutritionLesson> nutritionLessons = aikNutritionLessonMapper.selectUserCollect(params);

        List<Map<String, Object>> nutritionLessonList = new ArrayList<>();
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
    public void collectNutritionLesson(CollectNutritionLessonReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO || null == reqDTO.getId() || null == reqDTO.getCollect() || null == reqDTO.getUserId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikNutritionLessonCollect entity = aikNutritionLessonCollectMapper.selectByLessonIdAndUserId(reqDTO.getUserId(), reqDTO.getId());

        // 收藏
        if (reqDTO.getCollect()) {
            if (entity != null) {
                return;
            }

            AikNutritionLessonCollect addEntity = new AikNutritionLessonCollect();
            addEntity.setUserId(reqDTO.getUserId());
            addEntity.setLessonId(reqDTO.getId());
            addEntity.setCreateDate(new Date());

            aikNutritionLessonCollectMapper.insertSelective(addEntity);
        }
        // 取消收藏
        else {
            if (entity == null) {
                return;
            }

            aikNutritionLessonCollectMapper.deleteByPrimaryKey(entity.getId());
        }
    }
}
