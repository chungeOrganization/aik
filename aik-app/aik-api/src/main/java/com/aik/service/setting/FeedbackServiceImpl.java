package com.aik.service.setting;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AikDoctorFeedbackMapper;
import com.aik.dao.AikFeedbackFileMapper;
import com.aik.dao.AikFeedbackMapper;
import com.aik.dao.AikUserFeedbackMapper;
import com.aik.dto.request.FeedbackReqDTO;
import com.aik.enums.FeedbackEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikFeedback;
import com.aik.model.AikFeedbackFile;
import com.aik.model.AikUserFeedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private AikFeedbackMapper aikFeedbackMapper;

    private AikFeedbackFileMapper aikFeedbackFileMapper;

    @Autowired
    public void setAikFeedbackMapper(AikFeedbackMapper aikFeedbackMapper) {
        this.aikFeedbackMapper = aikFeedbackMapper;
    }

    @Autowired
    public void setAikFeedbackFileMapper(AikFeedbackFileMapper aikFeedbackFileMapper) {
        this.aikFeedbackFileMapper = aikFeedbackFileMapper;
    }

    @Override
    public void addFeedback(FeedbackReqDTO feedbackReq, FeedbackEnum.FbUserTypeEnum userTypeEnum) throws ApiServiceException {
        if (null == feedbackReq) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikFeedback aikFeedback = new AikFeedback();
        aikFeedback.setUserType(userTypeEnum.getCode());
        aikFeedback.setUserId(feedbackReq.getUserId());
        aikFeedback.setType(feedbackReq.getType());
        aikFeedback.setDescription(feedbackReq.getDescription());
        aikFeedback.setCreateDate(new Date());
        aikFeedbackMapper.insertSelective(aikFeedback);

        if (null != feedbackReq.getFiles() && feedbackReq.getFiles().size() > 0) {
            List<String> files = feedbackReq.getFiles();

            for (String fileUri : files) {
                AikFeedbackFile feedbackFile = new AikFeedbackFile();
                feedbackFile.setFeedbackId(aikFeedback.getId());
                feedbackFile.setFileUrl(fileUri);
                feedbackFile.setCreateDate(new Date());
                aikFeedbackFileMapper.insertSelective(feedbackFile);
            }
        }
    }
}
