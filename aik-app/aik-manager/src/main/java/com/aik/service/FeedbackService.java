package com.aik.service;

import com.aik.vo.FeedbackVo;
import com.github.pagehelper.Page;

/**
 * Description: 意见反馈接口
 * Created by as on 2017/9/29.
 */
public interface FeedbackService {

    /**
     * 获取意见反馈列表（分页）
     *
     * @param feedbackVo 查询条件
     * @return 意见反馈分页信息
     */
    Page<FeedbackVo> findPage(FeedbackVo feedbackVo);

    /**
     * 获取意见反馈详情
     *
     * @param id 反馈id
     * @return 意见反馈详情
     */
    FeedbackVo findDetail(Integer id);

    /**
     * 删除意见反馈
     *
     * @param id 反馈id
     * @return true false
     */
    boolean deleteFeedback(Integer id);
}
