package com.aik.service;

import com.aik.model.AikFeedback;
import com.github.pagehelper.Page;

/**
 * Description:
 * Created by as on 2017/9/29.
 */
public interface FeedbackService {

    Page<AikFeedback> findPage();
}
