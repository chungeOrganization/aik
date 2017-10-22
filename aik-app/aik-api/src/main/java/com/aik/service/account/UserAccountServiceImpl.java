package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.AccUserAccountMapper;
import com.aik.dto.UserInfoDTO;
import com.aik.dto.request.user.UserResetPwdReqDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccUserAccount;
import com.aik.util.MD5Utils;
import com.aik.util.StringValidUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
@Service
public class UserAccountServiceImpl implements UserAccountService {

    private static final Logger logger = LoggerFactory.getLogger(UserAccountServiceImpl.class);

    private AccUserAccountMapper accUserAccountMapper;

    @Autowired
    public void setAccUserAccountMapper(AccUserAccountMapper accUserAccountMapper) {
        this.accUserAccountMapper = accUserAccountMapper;
    }

    @Override
    public Boolean validMobileNoIsUsed(String mobileNo) throws ApiServiceException {
        if (StringUtils.isBlank(mobileNo)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccUserAccount userAccount = new AccUserAccount();
        userAccount.setMobileNo(mobileNo);

        List<AccUserAccount> doctorAccounts = accUserAccountMapper.selectBySelective(userAccount);

        return doctorAccounts.size() > 0;
    }

    @Override
    public Boolean validUserNameIsUsed(String userName) throws ApiServiceException {
        if (StringUtils.isBlank(userName)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccUserAccount userAccount = new AccUserAccount();
        userAccount.setUserName(userName);

        List<AccUserAccount> doctorAccounts = accUserAccountMapper.selectBySelective(userAccount);

        return doctorAccounts.size() > 0;
    }

    @Override
    public void fillUserInfo(UserInfoDTO userInfoDTO) throws ApiServiceException {
        if (null == userInfoDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (!validUserInfoDTO(userInfoDTO)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        AccUserAccount userAccount = accUserAccountMapper.selectByPrimaryKey(userInfoDTO.getAccountId());
        if (null == userAccount) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001007);
        }

        userAccount.setUserType(userInfoDTO.getUserType());
        userAccount.setRealName(userInfoDTO.getRealName());
        userAccount.setSex(userInfoDTO.getSex());
        userAccount.setDevType(userInfoDTO.getDevType());
        userAccount.setUpdateDate(new Date());

        accUserAccountMapper.updateByPrimaryKeySelective(userAccount);
    }

    @Override
    public AccUserAccount getUserAccount(Integer userId) throws ApiServiceException {
        if (null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        return accUserAccountMapper.selectByPrimaryKey(userId);
    }

    @Override
    public void updateUserInfo(UserInfoDTO userInfoDTO) throws ApiServiceException {
        if (null == userInfoDTO || null == userInfoDTO.getAccountId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccUserAccount userAccount = new AccUserAccount();

        userAccount.setId(userInfoDTO.getAccountId());
        userAccount.setHeadImg(userInfoDTO.getHeadImg());
        userAccount.setNickName(userInfoDTO.getNickName());
        userAccount.setSex(userInfoDTO.getSex());
        userAccount.setBirthday(userInfoDTO.getBirthday());
        userAccount.setAreaProvince(userInfoDTO.getAreaProvince());
        userAccount.setAreaCity(userInfoDTO.getAreaCity());
        userAccount.setIsElseIllness(userInfoDTO.getIsElseIllness());
        userAccount.setUpdateDate(new Date());
        accUserAccountMapper.updateByPrimaryKeySelective(userAccount);
    }

    @Override
    public void updateUserPwd(UserResetPwdReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO || StringUtils.isBlank(reqDTO.getMobileNo()) || StringUtils.isBlank(reqDTO.getPassword())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccUserAccount userAccount = accUserAccountMapper.selectByMobileNo(reqDTO.getMobileNo());
        if (null == userAccount) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001009);
        }

        if (!StringValidUtils.validPassword(reqDTO.getPassword())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000004);
        }

        AccUserAccount updateUserAccount = new AccUserAccount();
        updateUserAccount.setId(userAccount.getId());
        updateUserAccount.setPassword(MD5Utils.md5(reqDTO.getPassword()));
        updateUserAccount.setUpdateDate(new Date());
        accUserAccountMapper.updateByPrimaryKeySelective(updateUserAccount);
    }

    private boolean validUserInfoDTO(UserInfoDTO userInfoDTO) {
        if (null == userInfoDTO) {
            return false;
        }

        if (StringUtils.isBlank(userInfoDTO.getRealName()) || null == userInfoDTO.getSex() ||
                null == userInfoDTO.getUserType()) {
            return false;
        }

        return true;
    }
}
