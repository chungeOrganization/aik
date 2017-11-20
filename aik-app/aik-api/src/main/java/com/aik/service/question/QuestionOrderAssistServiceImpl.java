package com.aik.service.question;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AikQuestionOrderAssistMapper;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikQuestionOrderAssist;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description:
 * Created by as on 2017/11/20.
 */
@Service
public class QuestionOrderAssistServiceImpl implements QuestionOrderAssistService {

    private static final Logger logger = LoggerFactory.getLogger(QuestionOrderAssistServiceImpl.class);

    private AikQuestionOrderAssistMapper aikQuestionOrderAssistMapper;

    @Autowired
    public void setAikQuestionOrderAssistMapper(AikQuestionOrderAssistMapper aikQuestionOrderAssistMapper) {
        this.aikQuestionOrderAssistMapper = aikQuestionOrderAssistMapper;
    }

    @Override
    public void addQuestionOrderAssist(Integer orderId, String content) throws ApiServiceException {
        if (null == orderId || StringUtils.isEmpty(content)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Date now = new Date();
        AikQuestionOrderAssist orderAssist = aikQuestionOrderAssistMapper.selectByOrderId(orderId);
        if (null == orderAssist) {
            orderAssist = new AikQuestionOrderAssist();
            orderAssist.setOrderId(orderId);
            orderAssist.setLatelyReplyContent(content);
            orderAssist.setLatelyReplyDate(now);
            orderAssist.setCreateDate(now);

            aikQuestionOrderAssistMapper.insertSelective(orderAssist);
        } else {
            orderAssist.setLatelyReplyContent(content);
            orderAssist.setLatelyReplyDate(now);
            orderAssist.setUpdateDate(now);

            aikQuestionOrderAssistMapper.updateByPrimaryKeySelective(orderAssist);
        }
    }
}
