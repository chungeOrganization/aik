package com.aik.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("basicData/feedbackIndex");
    }
}
