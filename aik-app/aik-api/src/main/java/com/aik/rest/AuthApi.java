package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.LoginDTO;
import com.aik.dto.RegisterDTO;
import com.aik.dto.request.doctor.DoctorRegisterReqDTO;
import com.aik.dto.request.user.UserResetPwdReqDTO;
import com.aik.dto.response.doctor.LoginRespDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccDoctorAccount;
import com.aik.model.AccUserAccount;
import com.aik.service.account.AuthService;
import com.aik.service.account.UserAccountService;
import com.aik.util.BeansUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.BadCredentialsException;

import javax.inject.Inject;
import javax.naming.AuthenticationException;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * Description:
 * Created by as on 2017/8/8.
 */
@Path("/")
@Produces(MediaType.APPLICATION_JSON)
public class AuthApi {

    private static final Logger logger = LoggerFactory.getLogger(AuthApi.class);

    @Value("${jwt.header}")
    private String tokenHeader;

    private AuthService authService;

    private UserAccountService userAccountService;

    @Inject
    public void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @Inject
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @POST
    @Path("/auth/doctor/register")
    public ApiResult doctorRegister(DoctorRegisterReqDTO reqDTO) throws AuthenticationException {
        ApiResult result = new ApiResult();

        try {
            AccDoctorAccount doctorAccount = authService.doctorRegister(reqDTO);
            result.withDataKV("accountId", doctorAccount.getId());
        } catch (ApiServiceException e) {
            logger.error("doctor register error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("doctor register error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/auth/doctor/login")
    public ApiResult doctorLogin(LoginDTO loginDTO) throws AuthenticationException {
        ApiResult result = new ApiResult();

        try {
            LoginRespDTO respDTO = authService.doctorLogin(loginDTO);
            // 获取医生审核状态
            result.withData(BeansUtils.transBean2Map(respDTO));
        } catch (ApiServiceException e) {
            logger.error("doctor login error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (BadCredentialsException e) {
            logger.error("user login error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1002001);
        } catch (Exception e) {
            logger.error("doctor login error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/auth/doctor/refresh")
    public ApiResult doctorRefresh(HttpServletRequest request) throws AuthenticationException {
        ApiResult result = new ApiResult();

        try {
            String token = request.getHeader(tokenHeader);
            String refreshedToken = authService.doctorRefreshToken(token);
            if (refreshedToken == null) {
                result.withFailResult(ErrorCodeEnum.ERROR_CODE_1002002);
            }
        } catch (ApiServiceException e) {
            logger.error("doctor refresh error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("doctor refresh error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/auth/user/register")
    public ApiResult userRegister(RegisterDTO registerDTO) throws AuthenticationException {
        ApiResult result = new ApiResult();

        try {
            AccUserAccount userAccount = authService.userRegister(registerDTO);
            result.withDataKV("accountId", userAccount.getId());
        } catch (ApiServiceException e) {
            logger.error("user register error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user register error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/auth/user/login")
    public ApiResult userLogin(LoginDTO loginDTO) throws AuthenticationException {
        ApiResult result = new ApiResult();

        try {
            String token = authService.userLogin(loginDTO);
            result.withDataKV("token", token);
        } catch (ApiServiceException e) {
            logger.error("user login error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (BadCredentialsException e) {
            logger.error("user login error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1002001);
        } catch (Exception e) {
            logger.error("user login error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/auth/user/refresh")
    public ApiResult userRefresh(HttpServletRequest request) throws AuthenticationException {
        ApiResult result = new ApiResult();

        try {
            String token = request.getHeader(tokenHeader);
            String refreshedToken = authService.userRefreshToken(token);
            if (refreshedToken == null) {
                result.withFailResult(ErrorCodeEnum.ERROR_CODE_1002002);
            }
        } catch (ApiServiceException e) {
            logger.error("user refresh error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user refresh error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/auth/user/resetPwd")
    public ApiResult userResetPwd(UserResetPwdReqDTO reqDTO) {
        ApiResult result = new ApiResult();

        try {
            userAccountService.updateUserPwd(reqDTO);
        } catch (ApiServiceException e) {
            logger.error("user reset password error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("user reset password error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/auth/logout")
    public ApiResult logout() {
        ApiResult result = new ApiResult();

        return result;
    }
}
