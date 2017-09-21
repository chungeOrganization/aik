package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.*;
import com.aik.dto.DoctorInfoDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.security.AuthUserDetailsThreadLocal;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.*;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
@Service
public class DoctorAccountServiceImpl implements DoctorAccountService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorAccountServiceImpl.class);

    private AccDoctorAccountMapper accDoctorAccountMapper;

    private AccDoctorFileMapper accDoctorFileMapper;

    private AccDoctorWalletMapper accDoctorWalletMapper;

    private AccDoctorBankCardMapper accDoctorBankCardMapper;

    @Autowired
    public void setAccDoctorAccountMapper(AccDoctorAccountMapper accDoctorAccountMapper) {
        this.accDoctorAccountMapper = accDoctorAccountMapper;
    }

    @Autowired
    public void setAccDoctorFileMapper(AccDoctorFileMapper accDoctorFileMapper) {
        this.accDoctorFileMapper = accDoctorFileMapper;
    }

    @Autowired
    public void setAccDoctorWalletMapper(AccDoctorWalletMapper accDoctorWalletMapper) {
        this.accDoctorWalletMapper = accDoctorWalletMapper;
    }

    @Autowired
    public void setAccDoctorBankCardMapper(AccDoctorBankCardMapper accDoctorBankCardMapper) {
        this.accDoctorBankCardMapper = accDoctorBankCardMapper;
    }

    @Override
    public Boolean validMobileNoIsUsed(String mobileNo) throws ApiServiceException {
        if (StringUtils.isBlank(mobileNo)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccDoctorAccount doctorAccount = new AccDoctorAccount();
        doctorAccount.setMobileNo(mobileNo);

        List<AccDoctorAccount> doctorAccounts = accDoctorAccountMapper.selectBySelective(doctorAccount);

        return doctorAccounts.size() > 0;
    }

    @Override
    public Boolean validUserNameIsUsed(String userName) throws ApiServiceException {
        if (StringUtils.isBlank(userName)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccDoctorAccount doctorAccount = new AccDoctorAccount();
        doctorAccount.setUserName(userName);

        List<AccDoctorAccount> doctorAccounts = accDoctorAccountMapper.selectBySelective(doctorAccount);

        return doctorAccounts.size() > 0;
    }

    @Override
    public void fillDoctorInfo(DoctorInfoDTO doctorInfoDTO) throws ApiServiceException {
        if (null == doctorInfoDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (!validDoctorInfoDTO(doctorInfoDTO)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(doctorInfoDTO.getAccountId());
        if (null == doctorAccount) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001007);
        }

        doctorAccount.setRealName(doctorInfoDTO.getRealName());
        doctorAccount.setSex(doctorInfoDTO.getSex());
        doctorAccount.setAreaProvince(doctorInfoDTO.getAreaProvince());
        doctorAccount.setAreaCity(doctorInfoDTO.getAreaCity());
        doctorAccount.setBirthday(doctorInfoDTO.getBirthday());
        doctorAccount.setIdentityCard(doctorInfoDTO.getIdentityCard());
        doctorAccount.setEmail(doctorInfoDTO.getEmail());
        doctorAccount.setHosName(doctorInfoDTO.getHosName());
        doctorAccount.setHosDepartment(doctorInfoDTO.getHosDepartment());
        doctorAccount.setSkill(doctorInfoDTO.getSkill());
        doctorAccount.setDepartmentPhone(doctorInfoDTO.getDepartmentPhone());
        doctorAccount.setUpdateDate(new Date());

        accDoctorAccountMapper.updateByPrimaryKeySelective(doctorAccount);
    }

    @Override
    public void editDoctorInfo(DoctorInfoDTO doctorInfoDTO) throws ApiServiceException {
        AccDoctorAccount doctorAccount = new AccDoctorAccount();

        doctorAccount.setId(AuthUserDetailsThreadLocal.getCurrentUserId());
        doctorAccount.setRealName(doctorInfoDTO.getRealName());
        doctorAccount.setSex(doctorInfoDTO.getSex());
        doctorAccount.setAreaProvince(doctorInfoDTO.getAreaProvince());
        doctorAccount.setAreaCity(doctorInfoDTO.getAreaCity());
        doctorAccount.setBirthday(doctorInfoDTO.getBirthday());
        doctorAccount.setIdentityCard(doctorInfoDTO.getIdentityCard());
        doctorAccount.setEmail(doctorInfoDTO.getEmail());
        doctorAccount.setHosName(doctorInfoDTO.getHosName());
        doctorAccount.setHosDepartment(doctorInfoDTO.getHosDepartment());
        doctorAccount.setSkill(doctorInfoDTO.getSkill());
        doctorAccount.setDepartmentPhone(doctorInfoDTO.getDepartmentPhone());
        doctorAccount.setUpdateDate(new Date());
        accDoctorAccountMapper.updateByPrimaryKeySelective(doctorAccount);
    }

    @Override
    public AccDoctorFile uploadDoctorFile(Integer accountId, String fileUrl) throws ApiServiceException {
        if (null == accountId || null == fileUrl) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccDoctorAccount accDoctorAccount = accDoctorAccountMapper.selectByPrimaryKey(accountId);
        if (null == accDoctorAccount) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001007);
        }

        AccDoctorFile accDoctorFile = new AccDoctorFile();
        accDoctorFile.setDoctorId(accountId);
        accDoctorFile.setFileUrl(fileUrl);
        accDoctorFile.setCreateDate(new Date());

        accDoctorFileMapper.insertSelective(accDoctorFile);

        return accDoctorFile;
    }

    @Override
    public BigDecimal getWallet(Integer doctorId) throws ApiServiceException {
        AccDoctorWallet accDoctorWallet = accDoctorWalletMapper.selectByPrimaryKey(doctorId);
        if (null == accDoctorWallet) {
            return BigDecimal.ZERO;
        }

        return accDoctorWallet.getAmount();
    }

    @Override
    public List<Map<String, Object>> getBankCards(Integer doctorId) throws ApiServiceException {
        return accDoctorBankCardMapper.selectDoctorBankCards(doctorId);
    }

    @Override
    public void addBankCard(AccDoctorBankCard bankCard) throws ApiServiceException {
        if (!validBankCard(bankCard)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        bankCard.setCreateTime(new Date());
        accDoctorBankCardMapper.insertSelective(bankCard);
    }

    @Override
    public AccDoctorAccount getDoctorAccount(Integer doctorId) throws ApiServiceException {
        if (null == doctorId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(doctorId);
        if (null == doctorAccount) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003008);
        }

        return doctorAccount;
    }

    @Override
    public Map<String, Object> getQRCode(Integer doctorId) throws ApiServiceException {
        AccDoctorAccount doctorAccount = getDoctorAccount(doctorId);

        Map<String, Object> rsMap = new HashMap<>();
        rsMap.put("headImg", doctorAccount.getHeadImg());
        rsMap.put("realName", doctorAccount.getRealName());
        rsMap.put("hosDepartment", doctorAccount.getHosDepartment());
        rsMap.put("position", doctorAccount.getPosition());
        rsMap.put("hosName", doctorAccount.getHosName());
        rsMap.put("qrCode", "xxxxx.jpg");

        return rsMap;
    }

    /**
     * 校验医生注册信息DTO
     *
     * @param doctorInfoDTO 医生信息DTO
     * @return true：校验通过 false：校验不通过
     */
    private Boolean validDoctorInfoDTO(DoctorInfoDTO doctorInfoDTO) {
        if (null == doctorInfoDTO) {
            return false;
        }

        if (StringUtils.isBlank(doctorInfoDTO.getRealName()) || null == doctorInfoDTO.getSex() ||
                StringUtils.isBlank(doctorInfoDTO.getAreaProvince()) || StringUtils.isBlank(doctorInfoDTO.getAreaCity()) ||
                StringUtils.isBlank(doctorInfoDTO.getHosName()) || StringUtils.isBlank(doctorInfoDTO.getHosDepartment()) ||
                StringUtils.isBlank(doctorInfoDTO.getSkill()) || StringUtils.isBlank(doctorInfoDTO.getDepartmentPhone())) {
            return false;
        }

        return true;
    }

    private AccDoctorAccount validAuthUserDetailWithResult() throws ApiServiceException {
        UserDetails userDetails = AuthUserDetailsThreadLocal.getCurrentUser();

        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByUserName(userDetails.getUsername());
        if (null == doctorAccount) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        return doctorAccount;
    }

    private Boolean validBankCard(AccDoctorBankCard bankCard) {
        if (null == bankCard) {
            return false;
        }

        if (null == bankCard.getDoctorId() || null == bankCard.getBankId() ||
                StringUtils.isBlank(bankCard.getBankName()) || StringUtils.isBlank(bankCard.getCardCode())) {
            return false;
        }

        // TODO:银行卡校验

        return true;
    }
}
