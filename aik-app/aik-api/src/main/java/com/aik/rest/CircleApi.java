package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.IssueCircleDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccCircleComment;
import com.aik.security.AuthUserDetailsThreadLocal;
import com.aik.service.account.CircleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
@Path("/circle")
@Produces(MediaType.APPLICATION_JSON)
public class CircleApi {

    private static final Logger logger = LoggerFactory.getLogger(CircleApi.class);

    private CircleService circleService;

    @Inject
    public void setCircleService(CircleService circleService) {
        this.circleService = circleService;
    }

    @POST
    @Path("/userIssueCircle")
    public ApiResult userIssueCircle(IssueCircleDTO issueCircleDTO) {
        ApiResult result = new ApiResult();

        try {
            issueCircleDTO.setUserId(AuthUserDetailsThreadLocal.getCurrentUserId());
            circleService.userIssueCircle(issueCircleDTO);
        } catch (ApiServiceException e) {
            logger.error("user issue circle error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user issue circle error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getChoicenessCircles")
    public ApiResult getChoicenessCircles(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> choicenessCircles = circleService.getChoicenessCircles(params,
                    AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("choicenessCircles", choicenessCircles);
        } catch (ApiServiceException e) {
            logger.error("get choiceness circles error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get choiceness circles error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getUserCircles")
    public ApiResult getUserCircles(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> circles = circleService.getUserCircles(params,
                    AuthUserDetailsThreadLocal.getCurrentUserId());
            result.withDataKV("circles", circles);
        } catch (ApiServiceException e) {
            logger.error("get user circles error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user circles error:  ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/likeCircle")
    public ApiResult likeCircle(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer circleId = Integer.valueOf(params.get("circleId").toString());
            circleService.userLikeCircle(circleId, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("user like circle error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user like circle error:  ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/cancelLikeCircle")
    public ApiResult cancelLikeCircle(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer circleId = Integer.valueOf(params.get("circleId").toString());
            circleService.userCancelLikeCircle(circleId, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("user cancel like circle error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user cancel like circle error:  ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/commentCircle")
    public ApiResult commentCircle(AccCircleComment circleComment) {
        ApiResult result = new ApiResult();

        try {
            circleComment.setCommenterId(AuthUserDetailsThreadLocal.getCurrentUserId());
            circleService.userCommentCircle(circleComment);
        } catch (ApiServiceException e) {
            logger.error("user comment circle error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user comment circle error:  ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }
}
