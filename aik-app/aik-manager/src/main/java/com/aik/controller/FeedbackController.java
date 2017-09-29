package com.aik.controller;

import com.aik.model.AikFeedback;
import com.aik.service.FeedbackService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description:
 * Created by as on 2017/9/29.
 */
@RestController
@RequestMapping("/feedback")
public class FeedbackController {

    private static final Logger logger = LoggerFactory.getLogger(FeedbackController.class);

    private FeedbackService feedbackService;

    @Autowired
    public void setFeedbackService(FeedbackService feedbackService) {
        this.feedbackService = feedbackService;
    }

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("basicData/feedbackIndex");
    }

    @RequestMapping("/goto/{num}/{size}")
    public ModelAndView queryPage(@PathVariable Integer num, @PathVariable Integer size) {
        ModelAndView mv = new ModelAndView("feedbackIndex/feedbackIndex");
        Page<AikFeedback> feedbackPageInfo = new Page<AikFeedback>();
        try {
            PageHelper.startPage(num, size);
            feedbackPageInfo = feedbackService.findPage();
        } catch (Exception e) {
            //ignore
        }
        mv.addObject("feedbackPageInfo", feedbackPageInfo);
        return mv;
    }
}
