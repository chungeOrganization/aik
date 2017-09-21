package com.aik.service.setting;

import com.aik.dao.AikDoctorFeedbackMapper;
import com.aik.dao.AikUserFeedbackMapper;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikDoctorFeedback;
import com.aik.model.AikUserFeedback;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private AikDoctorFeedbackMapper aikDoctorFeedbackMapper;

    private AikUserFeedbackMapper aikUserFeedbackMapper;

    @Autowired
    public void setAikDoctorFeedbackMapper(AikDoctorFeedbackMapper aikDoctorFeedbackMapper) {
        this.aikDoctorFeedbackMapper = aikDoctorFeedbackMapper;
    }

    @Autowired
    public void setAikUserFeedbackMapper(AikUserFeedbackMapper aikUserFeedbackMapper) {
        this.aikUserFeedbackMapper = aikUserFeedbackMapper;
    }

    @Override
    public void addDoctorFeedback(AikDoctorFeedback doctorFeedback) throws ApiServiceException {
        doctorFeedback.setCreateDate(new Date());
        aikDoctorFeedbackMapper.insertSelective(doctorFeedback);
    }

    @Override
    public void addUserFeedback(AikUserFeedback userFeedback) throws ApiServiceException {
        userFeedback.setCreateDate(new Date());
        aikUserFeedbackMapper.insertSelective(userFeedback);
    }
}
