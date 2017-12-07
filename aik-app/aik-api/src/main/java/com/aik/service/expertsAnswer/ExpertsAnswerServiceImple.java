package com.aik.service.expertsAnswer;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AikExpertsAnswerCollectMapper;
import com.aik.dao.AikExpertsAnswerMapper;
import com.aik.dao.AikExpertsAnswerViewMapper;
import com.aik.dto.request.PageReqDTO;
import com.aik.dto.request.user.CollectExpertsAnswerReqDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikExpertsAnswer;
import com.aik.model.AikExpertsAnswerCollect;
import com.aik.model.AikExpertsAnswerView;
import com.aik.util.ScrawlUtils;
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

    private AikExpertsAnswerCollectMapper aikExpertsAnswerCollectMapper;

    @Autowired
    public void setAikExpertsAnswerMapper(AikExpertsAnswerMapper aikExpertsAnswerMapper) {
        this.aikExpertsAnswerMapper = aikExpertsAnswerMapper;
    }

    @Autowired
    public void setAikExpertsAnswerViewMapper(AikExpertsAnswerViewMapper aikExpertsAnswerViewMapper) {
        this.aikExpertsAnswerViewMapper = aikExpertsAnswerViewMapper;
    }

    @Autowired
    public void setAikExpertsAnswerCollectMapper(AikExpertsAnswerCollectMapper aikExpertsAnswerCollectMapper) {
        this.aikExpertsAnswerCollectMapper = aikExpertsAnswerCollectMapper;
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
            expertsAnswerMap.put("question", ScrawlUtils.aikStringOmit(aikExpertsAnswer.getQuestion()));

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

    @Override
    public List<Map<String, Object>> getExpertsAnswerCollect(PageReqDTO reqDTO, Integer userId) throws ApiServiceException {
        Map<String, Object> params = new HashMap<>();
        params.put("page", reqDTO.getPage());
        params.put("size", reqDTO.getSize());
        params.put("userId", userId);

        List<AikExpertsAnswer> expertsAnswers = aikExpertsAnswerMapper.selectUserCollect(params);
        List<Map<String, Object>> rs = new ArrayList<>();
        for (AikExpertsAnswer answer : expertsAnswers) {
            Map<String, Object> m = new HashMap<>();
            m.put("id", answer.getId());
            m.put("question", answer.getQuestion());

            rs.add(m);
        }

        return rs;
    }

    @Override
    public void collectExpertsAnswer(CollectExpertsAnswerReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO || null == reqDTO.getId() || null == reqDTO.getCollect() || null == reqDTO.getUserId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikExpertsAnswerCollect entity = aikExpertsAnswerCollectMapper.selectByAnswerIdAndUserId(reqDTO.getUserId(), reqDTO.getId());

        // 收藏
        if (reqDTO.getCollect()) {
            if (entity != null) {
                return;
            }

            AikExpertsAnswerCollect addEntity = new AikExpertsAnswerCollect();
            addEntity.setUserId(reqDTO.getUserId());
            addEntity.setExpertsAnswerId(reqDTO.getId());
            addEntity.setCreateDate(new Date());

            aikExpertsAnswerCollectMapper.insertSelective(addEntity);
        }
        // 取消收藏
        else {
            if (entity == null) {
                return;
            }

            aikExpertsAnswerCollectMapper.deleteByPrimaryKey(entity.getId());
        }
    }
}
