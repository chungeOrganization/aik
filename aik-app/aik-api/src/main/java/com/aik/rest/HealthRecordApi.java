package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.AddHealthRecordDTO;
import com.aik.dto.response.HealthRecordRespDTO;
import com.aik.exception.ApiServiceException;
import com.aik.security.AuthUserDetailsThreadLocal;
import com.aik.service.account.UserHealthRecordService;
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
 * Created by as on 2017/9/5.
 */
@Path("/healthRecord")
@Produces(MediaType.APPLICATION_JSON)
public class HealthRecordApi {

    private static final Logger logger = LoggerFactory.getLogger(HealthRecordApi.class);

    private UserHealthRecordService userHealthRecordService;

    @Inject
    public void setUserHealthRecordService(UserHealthRecordService userHealthRecordService) {
        this.userHealthRecordService = userHealthRecordService;
    }

    @POST
    @Path("/getUserHealthRecords")
    public ApiResult getUserHealthRecords(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            params.put("userId", AuthUserDetailsThreadLocal.getCurrentUserId());
            List<Map<String, Object>> healthRecords = userHealthRecordService.getUserHealthRecords(params);
            result.withDataKV("healthRecords", healthRecords);
        } catch (ApiServiceException e) {
            logger.error("get user health records error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user health records error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getHealthRecordDetail")
    public ApiResult getHealthRecordDetail(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            Integer healthRecordId = Integer.valueOf(params.get("healthRecordId").toString());
            HealthRecordRespDTO healthRecordDetail = userHealthRecordService.getHealthRecordDetail(healthRecordId);
            result.withDataKV("healthRecordDetail", healthRecordDetail);
        } catch (ApiServiceException e) {
            logger.error("get user health record detail error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get user health record detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/addHealthRecord")
    public ApiResult addHealthRecord(AddHealthRecordDTO addHealthRecordDTO) {
        ApiResult result = new ApiResult();

        try {
            userHealthRecordService.addHealthRecord(addHealthRecordDTO, AuthUserDetailsThreadLocal.getCurrentUserId());
        } catch (ApiServiceException e) {
            logger.error("add health record error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("add health record detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    
}
