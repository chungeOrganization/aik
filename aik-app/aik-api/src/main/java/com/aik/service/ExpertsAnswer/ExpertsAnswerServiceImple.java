package com.aik.service.ExpertsAnswer;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AikExpertsAnswerMapper;
import com.aik.dao.AikExpertsAnswerViewMapper;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikExpertsAnswer;
import com.aik.model.AikExpertsAnswerView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
@Service
public class ExpertsAnswerServiceImple implements ExpertsAnswerService {

    private static final Logger logger = LoggerFactory.getLogger(ExpertsAnswerServiceImple.class);

    private AikExpertsAnswerMapper aikExpertsAnswerMapper;

    private AikExpertsAnswerViewMapper aikExpertsAnswerViewMapper;

    @Autowired
    public void setAikExpertsAnswerMapper(AikExpertsAnswerMapper aikExpertsAnswerMapper) {
        this.aikExpertsAnswerMapper = aikExpertsAnswerMapper;
    }

    @Autowired
    public void setAikExpertsAnswerViewMapper(AikExpertsAnswerViewMapper aikExpertsAnswerViewMapper) {
        this.aikExpertsAnswerViewMapper = aikExpertsAnswerViewMapper;
    }

    @Override
    public List<Map<String, Object>> getHomePageExpertsAnswers() throws ApiServiceException {
        List<Map<String, Object>> expertsAnswerList = new ArrayList<>();

        Map<String, Object> params = new HashMap<>();
        params.put("page", 1);
        // TODO：页面默认显示3条信息
        params.put("size", 3);
        List<AikExpertsAnswer> aikExpertsAnswers = aikExpertsAnswerMapper.selectByParams(params);
        for (AikExpertsAnswer aikExpertsAnswer : aikExpertsAnswers) {
            Map<String, Object> expertsAnswerMap = new HashMap<>();
            expertsAnswerMap.put("id", aikExpertsAnswer.getId());
            expertsAnswerMap.put("question", aikExpertsAnswer.getQuestion());

            expertsAnswerList.add(expertsAnswerMap);
        }

        return expertsAnswerList;
    }

    @Override
    public List<Map<String, Object>> getExpertsAnswers(Map<String, Object> params) throws ApiServiceException {
        List<Map<String, Object>> expertsAnswerList = new ArrayList<>();

        List<AikExpertsAnswer> aikExpertsAnswers = aikExpertsAnswerMapper.selectByParams(params);
        for (AikExpertsAnswer aikExpertsAnswer : aikExpertsAnswers) {
            Map<String, Object> expertsAnswerMap = new HashMap<>();
            expertsAnswerMap.put("id", aikExpertsAnswer.getId());
            expertsAnswerMap.put("question", aikExpertsAnswer.getQuestion());
            expertsAnswerMap.put("expertsName", aikExpertsAnswer.getExpertsName());
            expertsAnswerMap.put("education", aikExpertsAnswer.getEducation());

            AikExpertsAnswerView searchEA = new AikExpertsAnswerView();
            searchEA.setExpertsAnswerId(aikExpertsAnswer.getId());
            expertsAnswerMap.put("viewCount", aikExpertsAnswerViewMapper.selectCountBySelective(searchEA));

            expertsAnswerList.add(expertsAnswerMap);
        }

        return expertsAnswerList;
    }

    @Override
    public AikExpertsAnswer getExpertsAnswerDetail(Integer expertsAnswerId, Integer userId) throws ApiServiceException {
        if (null == expertsAnswerId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikExpertsAnswer expertsAnswer = aikExpertsAnswerMapper.selectByPrimaryKey(expertsAnswerId);
        if (null == expertsAnswer) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004007);
        }

        AikExpertsAnswerView searchEA = new AikExpertsAnswerView();
        searchEA.setUserId(userId);
        searchEA.setExpertsAnswerId(expertsAnswerId);
        boolean isView = aikExpertsAnswerViewMapper.selectCountBySelective(searchEA) > 0;
        if (!isView) {
            AikExpertsAnswerView expertsAnswerView = new AikExpertsAnswerView();
            expertsAnswerView.setUserId(userId);
            expertsAnswerView.setExpertsAnswerId(expertsAnswerId);
            expertsAnswerView.setCreateDate(new Date());

            aikExpertsAnswerViewMapper.insertSelective(expertsAnswerView);
        }

        return expertsAnswer;
    }
}
