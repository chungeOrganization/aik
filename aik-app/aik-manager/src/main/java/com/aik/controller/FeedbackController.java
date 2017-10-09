package com.aik.controller;

import com.aik.service.FeedbackService;
import com.aik.vo.FeedbackVo;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * Description: 意见反馈
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
        return new ModelAndView("feedback/feedbackIndex");
    }

    @RequestMapping("/goto/{num}/{size}")
    public ModelAndView queryPage(@PathVariable Integer num, @PathVariable Integer size, FeedbackVo feedbackVo) {
        ModelAndView mv = new ModelAndView("feedback/feedbackList");
        Page<FeedbackVo> feedbackPageInfo = new Page<FeedbackVo>();
        try {
            PageHelper.startPage(num, size);
            feedbackPageInfo = feedbackService.findPage(feedbackVo);
        } catch (Exception e) {
            //ignore
        }
        mv.addObject("pageInfo", new PageInfo<>(feedbackPageInfo));
        return mv;
    }

    @RequestMapping("/view/{id}")
    public ModelAndView view(@PathVariable Integer id) {
        ModelAndView mv = new ModelAndView("feedback/feedbackView");
        FeedbackVo feedbackVo = new FeedbackVo();
        try {
            feedbackVo = feedbackService.findDetail(id);
        } catch (Exception e) {
            //ignore
        }
        mv.addObject("feedback", feedbackVo);
        return mv;
    }
}
