package com.aik.service;

import com.aik.dao.AikFeedbackMapper;
import com.aik.model.AikFeedback;
import com.aik.vo.FeedbackVo;
import com.github.pagehelper.Page;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Created by as on 2017/9/29.
 */
@Service
public class FeedbackServiceImpl implements FeedbackService {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackServiceImpl.class);

    private AikFeedbackMapper aikFeedbackMapper;

    @Autowired
    public void setAikFeedbackMapper(AikFeedbackMapper aikFeedbackMapper) {
        this.aikFeedbackMapper = aikFeedbackMapper;
    }

    @Override
    public Page<FeedbackVo> findPage(FeedbackVo feedbackVo) {
        return aikFeedbackMapper.selectByPage(feedbackVo);
    }

    @Override
    public FeedbackVo findDetail(Integer id) {
        return aikFeedbackMapper.selectDetailById(id);
    }
}
