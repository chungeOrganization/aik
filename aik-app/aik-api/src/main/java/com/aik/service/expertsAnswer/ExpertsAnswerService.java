package com.aik.service.expertsAnswer;

import com.aik.dto.request.PageReqDTO;
import com.aik.dto.request.user.CollectExpertsAnswerReqDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikExpertsAnswer;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public interface ExpertsAnswerService {

    /**
     * 获取用户首页“专家答”
     *
     * @return 用户首页“专家答”
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getHomePageExpertsAnswers() throws ApiServiceException;

    /**
     * 获取“专家答”
     *
     * @param params 参数
     * @return “专家答”
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getExpertsAnswers(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取“专家答”详情
     *
     * @param expertsAnswerId “专家答”id
     * @param userId          用户id
     * @return “专家答”详情
     * @throws ApiServiceException Api服务异常
     */
    AikExpertsAnswer getExpertsAnswerDetail(Integer expertsAnswerId, Integer userId) throws ApiServiceException;

    /**
     * 获取专家答收藏
     *
     * @param reqDTO request
     * @param userId 用户id
     * @return 专家答收藏列表
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getExpertsAnswerCollect(PageReqDTO reqDTO, Integer userId) throws ApiServiceException;

    /**
     * 收藏|取消收藏 营养课堂
     * @throws ApiServiceException
     */
    void collectExpertsAnswer(CollectExpertsAnswerReqDTO reqDTO) throws ApiServiceException;
}
