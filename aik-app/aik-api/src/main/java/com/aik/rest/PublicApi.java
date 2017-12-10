package com.aik.rest;

import com.aik.assist.ApiResult;
import com.aik.assist.ErrorCodeEnum;
import com.aik.dto.UserInfoDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikCommonProblem;
import com.aik.model.SysBank;
import com.aik.resource.SystemResource;
import com.aik.service.*;
import com.aik.service.account.InviteCodeService;
import com.aik.service.account.SecurityCodeService;
import com.aik.service.account.UserAccountService;
import com.aik.service.question.IllTypeService;
import com.aik.util.AikFileUtils;
import com.aik.vo.AreaVO;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

/**
 * Description: 公共接口
 * Created by as on 2017/8/3.
 */
@Path("/public")
@Produces({MediaType.APPLICATION_JSON})
@Configuration
public class PublicApi {

    private static final Logger logger = LoggerFactory.getLogger(PublicApi.class);

    @Value("${file.upload-root-uri}")
    private String uploadRootUri;

    private SecurityCodeService securityCodeService;

    private InviteCodeService inviteCodeService;

    private AreaService areaService;

    private HospitalService hospitalService;

    private SysBankService sysBankService;

    private UserAccountService userAccountService;

    private SystemResource systemResource;

    private CommonProblemService commonProblemService;

    private SysSettingService sysSettingService;

    private IllTypeService illTypeService;

    @Inject
    public void setSecurityCodeService(SecurityCodeService securityCodeService) {
        this.securityCodeService = securityCodeService;
    }

    @Inject
    public void setInviteCodeService(InviteCodeService inviteCodeService) {
        this.inviteCodeService = inviteCodeService;
    }

    @Inject
    public void setAreaService(AreaService areaService) {
        this.areaService = areaService;
    }

    @Inject
    public void setHospitalService(HospitalService hospitalService) {
        this.hospitalService = hospitalService;
    }

    @Inject
    public void setSysBankService(SysBankService sysBankService) {
        this.sysBankService = sysBankService;
    }

    @Inject
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Inject
    public void setSystemResource(SystemResource systemResource) {
        this.systemResource = systemResource;
    }

    @Inject
    public void setCommonProblemService(CommonProblemService commonProblemService) {
        this.commonProblemService = commonProblemService;
    }

    @Inject
    public void setSysSettingService(SysSettingService sysSettingService) {
        this.sysSettingService = sysSettingService;
    }

    @Inject
    public void setIllTypeService(IllTypeService illTypeService) {
        this.illTypeService = illTypeService;
    }

    @GET
    @Path("/sendSecurityCode/{codeType}/{mobileNo}")
    public ApiResult sendSecurityCode(@PathParam("codeType") String codeType, @PathParam("mobileNo") String mobileNo) {
        ApiResult result = new ApiResult();

        try {
            securityCodeService.generateSecurityCode(codeType, mobileNo);
        } catch (ApiServiceException e) {
            logger.error("get security code error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get security code error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/sendVoiceSecurityCode/{codeType}/{mobileNo}")
    public ApiResult sendVoiceSecurityCode(@PathParam("codeType") String codeType, @PathParam("mobileNo") String mobileNo) {
        ApiResult result = new ApiResult();

        try {
            securityCodeService.generateVoiceSecurityCode(codeType, mobileNo);
        } catch (ApiServiceException e) {
            logger.error("get voice security code error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get voice security code error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/validSecurityCode/{codeType}/{mobileNo}/{securityCode}")
    public ApiResult validSecurityCode(@PathParam("codeType") String codeType, @PathParam("mobileNo") String mobileNo,
                                       @PathParam("securityCode") String securityCode) {
        ApiResult result = new ApiResult();

        try {
            boolean rs = securityCodeService.validSecurityCode(codeType, mobileNo, securityCode);
            if (!rs) {
                result.withFailResult(ErrorCodeEnum.ERROR_CODE_1001002);
            }
        } catch (ApiServiceException e) {
            logger.error("valid security code error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("valid security code error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/validInviteCode/{mobileNo}/{inviteCode}")
    public ApiResult validInviteCode(@PathParam("mobileNo") String mobileNo, @PathParam("inviteCode") String inviteCode) {
        ApiResult result = new ApiResult();

        try {
            boolean rs = inviteCodeService.validInviteCode(mobileNo, inviteCode);
            if (!rs) {
                result.withFailResult(ErrorCodeEnum.ERROR_CODE_1001004);
            }
        } catch (ApiServiceException e) {
            logger.error("valid invite code error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("valid invite code error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/getAreas")
    public ApiResult getAreas() {
        ApiResult result = new ApiResult();

        try {
            Map<String, List<String>> areas = areaService.getProvinceCity();
            result.withDataKV("areas", areas);
        } catch (Exception e) {
            logger.error("get areas error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/getFullAreas")
    public ApiResult getFullAreas() {
        ApiResult result = new ApiResult();

        try {
            List<AreaVO> areas = areaService.getProvinceCityArea();
            result.withDataKV("areas", areas);
        } catch (Exception e) {
            logger.error("get three lv areas error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/getProvinceHospitals")
    public ApiResult getProvinceHospitals() {
        ApiResult result = new ApiResult();

        try {
            Map<String, List<String>> areaHospitals = areaService.getProvinceHospitals();
            result.withDataKV("areaHospitals", areaHospitals);
        } catch (Exception e) {
            logger.error("get province hospitals error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/getHospitals/{city}")
    public ApiResult getHospitals(@PathParam("city") String city) {
        ApiResult result = new ApiResult();

        try {
            List<String> hospitals = hospitalService.getHospitalByArea(city);
            result.withDataKV("hospitals", hospitals);
        } catch (Exception e) {
            logger.error("get hospitals error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/getDepartments/{hospital}")
    public ApiResult getDepartments(@PathParam("hospital") String hospital) {
        ApiResult result = new ApiResult();

        try {
            List<String> departments = hospitalService.getDepartmentByHospital(hospital);
            result.withDataKV("departments", departments);
        } catch (Exception e) {
            logger.error("get departments error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/fillUserInfo")
    public ApiResult fillUserInfo(UserInfoDTO userInfoDTO) {
        ApiResult result = new ApiResult();

        try {
            userAccountService.fillUserInfo(userInfoDTO);
        } catch (ApiServiceException e) {
            logger.error("fill user info error: ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("fill user info error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/uploadDoctorFile")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public ApiResult uploadDoctorFile(@FormDataParam("file") InputStream fileInputStream,
                                      @FormDataParam("file") FormDataContentDisposition disposition) {
        ApiResult result = new ApiResult();

        try {
            String fileName = disposition.getFileName();
            String fileType = fileName.substring(fileName.lastIndexOf("."));
            String imageName = Calendar.getInstance().getTimeInMillis() + "-doctor" + fileType;

            String fileUri = "doctor" + File.separator + imageName;
            String fileUrl = systemResource.getApiFileUri() + fileUri;
            String uploadUrl = uploadRootUri + fileUri;

            AikFileUtils.uploadImg(fileInputStream, uploadUrl);

            result.withDataKV("fileUri", fileUri);
            result.withDataKV("fileUrl", fileUrl);
        } catch (IOException e) {
            logger.error("upload doctor file error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1001008);
        } catch (Exception e) {
            logger.error("upload doctor file error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/bankList")
    public ApiResult bankList() {
        ApiResult result = new ApiResult();

        try {
            List<SysBank> banks = sysBankService.getSysbankList();
            result.withDataKV("bankList", banks);
        } catch (Exception e) {
            logger.error("get sys bank list error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/getHotSearch/{type}")
    public ApiResult getHotSearch(@PathParam("type") Integer type) {
        ApiResult result = new ApiResult();

        List<String> hotWorlds = new ArrayList<>();
        hotWorlds.add("测试");

        result.withDataKV("hotWorlds", hotWorlds);
        return result;
    }

    @GET
    @Path("/getLatestVersion/{devType}")
    public ApiResult getLatestVersion(@PathParam("devType") String type) {
        ApiResult result = new ApiResult();
        try {
            // TODO:
            result.withDataKV("version", "1.0.0");
        } catch (Exception e) {
            logger.error("get latest version error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }
        return result;
    }

    @POST
    @Path("/getCommonProblems")
    public ApiResult getCommonProblems(Map<String, Object> params) {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> commonProblems = commonProblemService.getCommonProblems(params);
            result.withDataKV("commonProblemList", commonProblems);
        } catch (Exception e) {
            logger.error("get common problems error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/getCommonProblemDetail/{problemId}")
    public ApiResult getCommonProblemDetail(@PathParam("problemId") Integer problemId) {
        ApiResult result = new ApiResult();

        try {
            AikCommonProblem commonProblem = commonProblemService.getCommonProblemDetail(problemId);
            if (null != commonProblem) {
                result.withDataKV("answerImg", systemResource.getApiFileUri() + commonProblem.getFileUrl());
            } else {
                result.withDataKV("answerImg", "");
            }
        } catch (Exception e) {
            logger.error("get common problem detail error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/getServicePhone")
    public ApiResult getServicePhone() {
        ApiResult result = new ApiResult();

        try {
            result.withDataKV("servicePhone", sysSettingService.getServicePhone());
        } catch (Exception e) {
            logger.error("get service phone error: ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @POST
    @Path("/getIllTypeTree")
    public ApiResult getIllTypeTree() {
        ApiResult result = new ApiResult();

        try {
            List<Map<String, Object>> illTypeTree = illTypeService.getIllTypeTree();
            result.withDataKV("illTypeTree", illTypeTree);
        } catch (ApiServiceException e) {
            logger.error("get ill type tree error ", e);
            result.withFailResult(e.getErrorCodeEnum());
        } catch (Exception e) {
            logger.error("get ill type tree error ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    @GET
    @Path("/getPageImage/{type}")
    public ApiResult getPageImage(@PathParam("type") Integer type) {
        ApiResult result = new ApiResult();

        try {
            if (PageImageTypeEnum.USER_DIET_PLAN_HEADER.type.equals(type)) {
                result.withDataKV("image", systemResource.getApiFileUri() + "system/dietplan-header.png");
            } else if(PageImageTypeEnum.USER_FOOD_STORE.type.equals(type)) {
                result.withDataKV("image", systemResource.getApiFileUri() + "system/foods-header.png");
            }
        } catch (Exception e) {
            logger.error("get ill type tree error ", e);
            result.withFailResult(ErrorCodeEnum.ERROR_CODE_1000001);
        }

        return result;
    }

    enum PageImageTypeEnum {
        USER_DIET_PLAN_HEADER(1, "用户首页-饮食计划"),
        USER_FOOD_STORE(2, "用户首页-饮食计划-食物库");
        private Integer type;
        private String desc;

        PageImageTypeEnum(Integer type, String desc) {
            this.type = type;
            this.desc = desc;
        }

        public Integer getType() {
            return type;
        }

        public String getDesc() {
            return desc;
        }
    }
}
