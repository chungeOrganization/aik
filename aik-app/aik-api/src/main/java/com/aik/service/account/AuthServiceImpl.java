package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AccDoctorAccountMapper;
import com.aik.dao.AccDoctorWalletMapper;
import com.aik.dao.AccUserAccountMapper;
import com.aik.dto.LoginDTO;
import com.aik.dto.RegisterDTO;
import com.aik.dto.request.doctor.DoctorRegisterReqDTO;
import com.aik.dto.response.doctor.LoginRespDTO;
import com.aik.enums.DoctorIsCompleteInfoEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccDoctorAccount;
import com.aik.model.AccDoctorWallet;
import com.aik.model.AccUserAccount;
import com.aik.security.JwtTokenUtil;
import com.aik.security.JwtUser;
import com.aik.util.MD5Utils;
import com.aik.util.StringValidUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
@Service
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private AccDoctorAccountMapper accDoctorAccountMapper;

    private AccUserAccountMapper accUserAccountMapper;

    private DoctorAccountService doctorAccountService;

    private UserAccountService userAccountService;

    private AuthenticationManager authenticationManager;

    private UserDetailsService userDetailService;

    private JwtTokenUtil jwtTokenUtil;

    private AccDoctorWalletMapper accDoctorWalletMapper;

    @Value("${jwt.tokenHead}")
    private String tokenHead;

    @Value("${jwt.doctor-sign}")
    private String jwtDoctorSign;

    @Value("${jwt.user-sign}")
    private String jwtUserSign;

    @Autowired
    public void setAccDoctorAccountMapper(AccDoctorAccountMapper accDoctorAccountMapper) {
        this.accDoctorAccountMapper = accDoctorAccountMapper;
    }

    @Autowired
    public void setAccUserAccountMapper(AccUserAccountMapper accUserAccountMapper) {
        this.accUserAccountMapper = accUserAccountMapper;
    }

    @Autowired
    public void setDoctorAccountService(DoctorAccountService doctorAccountService) {
        this.doctorAccountService = doctorAccountService;
    }

    @Autowired
    public void setUserAccountService(UserAccountService userAccountService) {
        this.userAccountService = userAccountService;
    }

    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Autowired
    public void setUserDetailService(UserDetailsService userDetailService) {
        this.userDetailService = userDetailService;
    }

    @Autowired
    public void setJwtTokenUtil(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Autowired
    public void setAccDoctorWalletMapper(AccDoctorWalletMapper accDoctorWalletMapper) {
        this.accDoctorWalletMapper = accDoctorWalletMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public AccDoctorAccount doctorRegister(DoctorRegisterReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (!validDoctorRegisterDTO(reqDTO)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        // 验证手机号是否已被使用
        if (doctorAccountService.validMobileNoIsUsed(reqDTO.getMobileNo())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001006);
        }

        // 验证用户名是否已被使用
        if (doctorAccountService.validUserNameIsUsed(reqDTO.getUserName())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001005);
        }

        AccDoctorAccount doctorAccount = new AccDoctorAccount();
        doctorAccount.setMobileNo(reqDTO.getMobileNo());
        doctorAccount.setUserName(reqDTO.getUserName());
        String md5Password = MD5Utils.md5(reqDTO.getPassword());
        if (StringUtils.isBlank(md5Password)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000001);
        }
        doctorAccount.setPassword(md5Password);
        doctorAccount.setRealName(reqDTO.getRealName());
        doctorAccount.setSex(reqDTO.getSex());
        doctorAccount.setAreaProvince(reqDTO.getAreaProvince());
        doctorAccount.setAreaCity(reqDTO.getAreaCity());
        doctorAccount.setBirthday(reqDTO.getBirthday());
        doctorAccount.setIdentityCard(reqDTO.getIdentityCard());
        doctorAccount.setEmail(reqDTO.getEmail());
        doctorAccount.setHosName(reqDTO.getHosName());
        doctorAccount.setHosDepartment(reqDTO.getHosDepartment());
        doctorAccount.setSkill(reqDTO.getSkill());
        doctorAccount.setDepartmentPhone(reqDTO.getDepartmentPhone());
        doctorAccount.setDevType(reqDTO.getDevType());
        doctorAccount.setCreateDate(new Date());
        doctorAccount.setIsCompleteInfo(DoctorIsCompleteInfoEnum.TRUE.getCode());

        accDoctorAccountMapper.insertSelective(doctorAccount);

        // 图片信息
        if (StringUtils.isNotBlank(reqDTO.getFileUrl())) {
            doctorAccountService.uploadDoctorFile(doctorAccount.getId(), reqDTO.getFileUrl());
        }

        // 医生钱包
        AccDoctorWallet doctorWallet = new AccDoctorWallet();
        doctorWallet.setId(doctorAccount.getId());
        doctorWallet.setCreateTime(new Date());
        accDoctorWalletMapper.insertSelective(doctorWallet);

        return doctorAccount;
    }

    @Override
    public LoginRespDTO doctorLogin(LoginDTO loginDTO) throws ApiServiceException {
        if (null == loginDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (!validLoginDTO(loginDTO)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByUserNameAndPwd(
                loginDTO.getUserName(), loginDTO.getPassword());

        if (null == doctorAccount) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1002001);
        }

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(loginDTO.getUserName() + jwtDoctorSign,
                loginDTO.getPassword());
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailService.loadUserByUsername(loginDTO.getUserName() + jwtDoctorSign);
        final String token = jwtTokenUtil.generateToken(userDetails, jwtDoctorSign);

        LoginRespDTO respDTO = new LoginRespDTO();
        respDTO.setToken(token);
        respDTO.setAuditStatus(doctorAccount.getAuditStatus());
        respDTO.setIsCompleteInfo(doctorAccount.getIsCompleteInfo());

        return respDTO;
    }

    @Override
    public String doctorRefreshToken(String oldToken) throws ApiServiceException {
        if (StringUtils.isBlank(oldToken)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailService.loadUserByUsername(username + jwtDoctorSign);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    @Override
    public AccUserAccount userRegister(RegisterDTO registerDTO) throws ApiServiceException {
        if (null == registerDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (!validRegisterDTO(registerDTO)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        // 验证用户名是否已被使用
        if (userAccountService.validUserNameIsUsed(registerDTO.getUserName())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001005);
        }

        AccUserAccount userAccount = new AccUserAccount();
        userAccount.setMobileNo(registerDTO.getMobileNo());
        userAccount.setUserName(registerDTO.getUserName());
        String md5Password = MD5Utils.md5(registerDTO.getPassword());
        if (StringUtils.isBlank(md5Password)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000001);
        }
        userAccount.setPassword(md5Password);
        userAccount.setDevType(registerDTO.getDevType());
        userAccount.setCreateDate(new Date());

        accUserAccountMapper.insertSelective(userAccount);

        return userAccount;
    }

    @Override
    public String userLogin(LoginDTO loginDTO) throws ApiServiceException {
        if (null == loginDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (!validLoginDTO(loginDTO)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        UsernamePasswordAuthenticationToken upToken = new UsernamePasswordAuthenticationToken(loginDTO.getUserName() + jwtUserSign,
                loginDTO.getPassword());
        // Perform the security
        final Authentication authentication = authenticationManager.authenticate(upToken);
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Reload password post-security so we can generate token
        final UserDetails userDetails = userDetailService.loadUserByUsername(loginDTO.getUserName() + jwtUserSign);
        final String token = jwtTokenUtil.generateToken(userDetails, jwtUserSign);

        return token;
    }

    @Override
    public String userRefreshToken(String oldToken) throws ApiServiceException {
        if (StringUtils.isBlank(oldToken)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        final String token = oldToken.substring(tokenHead.length());
        String username = jwtTokenUtil.getUsernameFromToken(token);
        JwtUser user = (JwtUser) userDetailService.loadUserByUsername(username + jwtUserSign);
        if (jwtTokenUtil.canTokenBeRefreshed(token, user.getLastPasswordResetDate())) {
            return jwtTokenUtil.refreshToken(token);
        }
        return null;
    }

    @Override
    public void doctorValidUserNameAndPwd(RegisterDTO registerDTO) throws ApiServiceException {
        if (null == registerDTO || StringUtils.isEmpty(registerDTO.getUserName()) ||
                StringUtils.isEmpty(registerDTO.getPassword())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (!StringValidUtils.validPassword(registerDTO.getPassword()) ||
                !StringValidUtils.validUserName(registerDTO.getUserName())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        // 验证用户名是否已被使用
        if (doctorAccountService.validUserNameIsUsed(registerDTO.getUserName())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001005);
        }
    }

    /**
     * 校验医生注册
     *
     * @param reqDTO request DTO
     * @return true：校验通过 false：校验不通过
     */
    private Boolean validDoctorRegisterDTO(DoctorRegisterReqDTO reqDTO) {
        if (null == reqDTO) {
            return false;
        }

        if (StringUtils.isBlank(reqDTO.getUserName()) || StringUtils.isBlank(reqDTO.getPassword()) ||
                StringUtils.isBlank(reqDTO.getMobileNo())) {
            return false;
        }

        if (!StringValidUtils.validUserName(reqDTO.getUserName())) {
            return false;
        } else if (!StringValidUtils.validPassword(reqDTO.getPassword())) {
            return false;
        } else if (!StringValidUtils.validMobileNo(reqDTO.getMobileNo())) {
            return false;
        }

        if (StringUtils.isBlank(reqDTO.getRealName()) || null == reqDTO.getSex() ||
                StringUtils.isBlank(reqDTO.getAreaProvince()) || StringUtils.isBlank(reqDTO.getAreaCity()) ||
                StringUtils.isBlank(reqDTO.getHosName()) || StringUtils.isBlank(reqDTO.getHosDepartment()) ||
                StringUtils.isBlank(reqDTO.getSkill()) || StringUtils.isBlank(reqDTO.getDepartmentPhone()) ||
                StringUtils.isBlank(reqDTO.getFileUrl())) {
            return false;
        }

        return true;
    }

    /**
     * 校验注册DTO
     *
     * @param registerDTO 注册DTO
     * @return true：校验通过 false：校验不通过
     */
    private Boolean validRegisterDTO(RegisterDTO registerDTO) {
        if (null == registerDTO) {
            return false;
        }

        if (StringUtils.isBlank(registerDTO.getUserName()) || StringUtils.isBlank(registerDTO.getPassword()) ||
                StringUtils.isBlank(registerDTO.getMobileNo())) {
            return false;
        }

        if (!StringValidUtils.validUserName(registerDTO.getUserName())) {
            return false;
        } else if (!StringValidUtils.validPassword(registerDTO.getPassword())) {
            return false;
        } else if (!StringValidUtils.validMobileNo(registerDTO.getMobileNo())) {
            return false;
        }

        return true;
    }

    /**
     * 校验登录信息
     *
     * @param loginDTO 登录DTO
     * @return true：校验通过 false：校验不通过
     */
    private Boolean validLoginDTO(LoginDTO loginDTO) {
        if (null == loginDTO) {
            return false;
        }

        if (StringUtils.isBlank(loginDTO.getUserName()) || StringUtils.isBlank(loginDTO.getPassword())) {
            return false;
        }

        return true;
    }
}
