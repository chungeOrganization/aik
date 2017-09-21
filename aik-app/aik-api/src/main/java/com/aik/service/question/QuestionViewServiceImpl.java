package com.aik.service.question;

import com.aik.dao.AikQuestionViewHisMapper;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikQuestionViewHis;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/28.
 */
@Service
public class QuestionViewServiceImpl implements QuestionViewService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionViewServiceImpl.class);

    private AikQuestionViewHisMapper aikQuestionViewHisMapper;

    @Autowired
    public void setAikQuestionViewHisMapper(AikQuestionViewHisMapper aikQuestionViewHisMapper) {
        this.aikQuestionViewHisMapper = aikQuestionViewHisMapper;
    }

    @Override
    public Integer getQuestionViewCount(Integer userId) throws ApiServiceException {
        AikQuestionViewHis searchAQ = new AikQuestionViewHis();
        searchAQ.setUserId(userId);

        return aikQuestionViewHisMapper.selectCountBySelective(searchAQ);
    }

    @Override
    public List<Map<String, Object>> getQuestionViewHis(Map<String, Object> params) throws ApiServiceException {
        return aikQuestionViewHisMapper.selectViewHisByParams(params);
    }
}
