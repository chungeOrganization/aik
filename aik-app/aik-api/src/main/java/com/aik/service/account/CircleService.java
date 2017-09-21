package com.aik.service.account;

import com.aik.dto.IssueCircleDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccCircleComment;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
public interface CircleService {

    /**
     * 用户发布互助圈
     *
     * @param issueCircleDTO 发布互助圈DTO
     * @throws ApiServiceException Api服务异常
     */
    void userIssueCircle(IssueCircleDTO issueCircleDTO) throws ApiServiceException;

    /**
     * 获取精选互助圈
     *
     * @param params 参数
     * @param userId 用户id
     * @return 精选互助圈
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getChoicenessCircles(Map<String, Object> params, Integer userId) throws ApiServiceException;

    /**
     * 获取用户互助圈
     *
     * @param params 参数
     * @param userId 用户id
     * @return 用户互助圈
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getUserCircles(Map<String, Object> params, Integer userId) throws ApiServiceException;

    /**
     * 用户点赞互助圈
     *
     * @param circleId 互助圈id
     * @param userId   用户id
     * @throws ApiServiceException Api服务异常
     */
    void userLikeCircle(Integer circleId, Integer userId) throws ApiServiceException;

    /**
     * 用户取消点赞互助圈
     *
     * @param circleId 互助圈id
     * @param userId   用户id
     * @throws ApiServiceException Api服务异常
     */
    void userCancelLikeCircle(Integer circleId, Integer userId) throws ApiServiceException;

    /**
     * 用户评论互助圈
     *
     * @param circleComment 互助圈评论
     * @throws ApiServiceException Api服务异常
     */
    void userCommentCircle(AccCircleComment circleComment) throws ApiServiceException;
}
