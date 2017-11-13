package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.dao.*;
import com.aik.dto.DoctorInfoDTO;
import com.aik.dto.request.doctor.*;
import com.aik.dto.request.user.ResetPwdReqDTO;
import com.aik.dto.response.doctor.ApplyWithdrawRespDTO;
import com.aik.dto.response.doctor.ShowBankWithdrawRespDTO;
import com.aik.enums.DoctorDealTypeEnum;
import com.aik.enums.DoctorWithdrawChannelEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.resource.SystemResource;
import com.aik.security.AuthUserDetailsThreadLocal;
import com.aik.util.MD5Utils;
import com.aik.util.ScrawlUtils;
import com.aik.util.StringValidUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
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

    @Resource
    private SystemResource systemResource;

    private AccDoctorWithdrawMapper accDoctorWithdrawMapper;

    private AccDoctorDealDetailMapper accDoctorDealDetailMapper;

    private SysBankMapper sysBankMapper;

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

    @Autowired
    public void setAccDoctorWithdrawMapper(AccDoctorWithdrawMapper accDoctorWithdrawMapper) {
        this.accDoctorWithdrawMapper = accDoctorWithdrawMapper;
    }

    @Autowired
    public void setAccDoctorDealDetailMapper(AccDoctorDealDetailMapper accDoctorDealDetailMapper) {
        this.accDoctorDealDetailMapper = accDoctorDealDetailMapper;
    }

    @Autowired
    public void setSysBankMapper(SysBankMapper sysBankMapper) {
        this.sysBankMapper = sysBankMapper;
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
    public DoctorInfoDTO getDoctorInfo(Integer accountId) throws ApiServiceException {
        if (null == accountId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccDoctorAccount accDoctorAccount = accDoctorAccountMapper.selectByPrimaryKey(accountId);
        if (null == accDoctorAccount) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003008);
        }

        DoctorInfoDTO doctorInfoDTO = new DoctorInfoDTO();
        doctorInfoDTO.setAccountId(accDoctorAccount.getId());
        if (StringUtils.isNotBlank(accDoctorAccount.getHeadImg())) {
            doctorInfoDTO.setHeadImg(systemResource.getApiFileUri() + accDoctorAccount.getHeadImg());
        }
        doctorInfoDTO.setRealName(accDoctorAccount.getRealName());
        doctorInfoDTO.setSex(accDoctorAccount.getSex());
        doctorInfoDTO.setAreaProvince(accDoctorAccount.getAreaProvince());
        doctorInfoDTO.setAreaCity(accDoctorAccount.getAreaCity());
        doctorInfoDTO.setBirthday(accDoctorAccount.getBirthday());
        doctorInfoDTO.setIdentityCard(accDoctorAccount.getIdentityCard());
        doctorInfoDTO.setEmail(accDoctorAccount.getEmail());
        doctorInfoDTO.setHosName(accDoctorAccount.getHosName());
        doctorInfoDTO.setHosDepartment(accDoctorAccount.getHosDepartment());
        doctorInfoDTO.setSkill(accDoctorAccount.getSkill());
        doctorInfoDTO.setDepartmentPhone(accDoctorAccount.getDepartmentPhone());
        doctorInfoDTO.setDevType(accDoctorAccount.getDevType());

        return doctorInfoDTO;
    }

    @Override
    public void editDoctorInfo(DoctorInfoDTO doctorInfoDTO) throws ApiServiceException {
        AccDoctorAccount doctorAccount = new AccDoctorAccount();

        doctorAccount.setId(AuthUserDetailsThreadLocal.getCurrentUserId());
        doctorAccount.setHeadImg(doctorInfoDTO.getHeadImg());
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
        List<Map<String, Object>> bankCards = accDoctorBankCardMapper.selectDoctorBankCards(doctorId);

        for (Map<String, Object> map : bankCards) {
            if (null != map.get("cardCode")) {
                String cardCode = map.get("cardCode").toString();
                map.put("cardCode", ScrawlUtils.bankCardShowHandle(cardCode));
            }

            if (null != map.get("bankBackImg")) {
                map.put("bankBackImg", systemResource.getApiFileUri() + map.get("bankBackImg").toString());
            }
        }

        return bankCards;
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
        rsMap.put("headImg", systemResource.getApiFileUri() + doctorAccount.getHeadImg());
        rsMap.put("realName", doctorAccount.getRealName());
        rsMap.put("hosDepartment", doctorAccount.getHosDepartment());
        rsMap.put("position", doctorAccount.getPosition());
        rsMap.put("hosName", doctorAccount.getHosName());
        // TODO
        rsMap.put("qrCode", systemResource.getApiFileUri() + "doctor/doctor-qrcode-1.png");

        return rsMap;
    }

    @Override
    public void updatePassword(UpdatePwdReqDTO updatePwdDTO) throws ApiServiceException {
        if (null == updatePwdDTO || StringUtils.isBlank(updatePwdDTO.getOldPassword()) ||
                StringUtils.isBlank(updatePwdDTO.getNewPassword())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccDoctorAccount doctorAccount = getDoctorAccount(updatePwdDTO.getAccountId());
        if (!updatePwdDTO.getOldPassword().equals(doctorAccount.getPassword())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003009);
        }

        if (!StringValidUtils.validPassword(updatePwdDTO.getNewPassword())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        AccDoctorAccount updateAccount = new AccDoctorAccount();
        updateAccount.setId(updatePwdDTO.getAccountId());
        updateAccount.setPassword(MD5Utils.md5(updatePwdDTO.getNewPassword()));
        updateAccount.setUpdateDate(new Date());

        accDoctorAccountMapper.updateByPrimaryKeySelective(updateAccount);
    }

    @Override
    public void rebindingMobileNo(RebindingMobileReqDTO rebindingMobileReqDTO) throws ApiServiceException {
        if (null == rebindingMobileReqDTO || null == rebindingMobileReqDTO.getAccountId() ||
                StringUtils.isBlank(rebindingMobileReqDTO.getMobileNo())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByMobileNo(rebindingMobileReqDTO.getMobileNo());
        if (null != doctorAccount) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001006);
        }

        AccDoctorAccount updateDoctorAccount = new AccDoctorAccount();
        updateDoctorAccount.setId(rebindingMobileReqDTO.getAccountId());
        updateDoctorAccount.setMobileNo(rebindingMobileReqDTO.getMobileNo());
        updateDoctorAccount.setUpdateDate(new Date());
        accDoctorAccountMapper.updateByPrimaryKeySelective(updateDoctorAccount);
    }

    @Override
    public void setPayPassword(PayPasswordReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (!StringValidUtils.validPayPassword(reqDTO.getPayPassword())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        AccDoctorWallet doctorWallet = accDoctorWalletMapper.selectByPrimaryKey(reqDTO.getAccountId());
        doctorWallet.setPayPassword(MD5Utils.md5(reqDTO.getPayPassword()));
        doctorWallet.setUpdateTime(new Date());
        accDoctorWalletMapper.updateByPrimaryKeySelective(doctorWallet);
    }

    @Override
    public Boolean validPayPassword(PayPasswordReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccDoctorWallet doctorWallet = accDoctorWalletMapper.selectByPrimaryKey(reqDTO.getAccountId());

        return doctorWallet.getPayPassword().equals(reqDTO.getPayPassword());
    }

    @Override
    public void resetPayPassword(ResetPayPasswordReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (!StringValidUtils.validPayPassword(reqDTO.getPayPassword())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000003);
        }

        AccDoctorWallet doctorWallet = accDoctorWalletMapper.selectByPrimaryKey(reqDTO.getAccountId());
        doctorWallet.setPayPassword(MD5Utils.md5(reqDTO.getPayPassword()));
        doctorWallet.setUpdateTime(new Date());
        accDoctorWalletMapper.updateByPrimaryKeySelective(doctorWallet);
    }

    @Override
    public void resetPassword(ResetPwdReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO || StringUtils.isBlank(reqDTO.getMobileNo()) || StringUtils.isBlank(reqDTO.getPassword())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByMobileNo(reqDTO.getMobileNo());
        if (null == doctorAccount) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1001009);
        }

        if (!StringValidUtils.validPassword(reqDTO.getPassword())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000004);
        }

        AccDoctorAccount updateDoctorAccount = new AccDoctorAccount();
        updateDoctorAccount.setId(doctorAccount.getId());
        updateDoctorAccount.setPassword(MD5Utils.md5(reqDTO.getPassword()));
        updateDoctorAccount.setUpdateDate(new Date());
        accDoctorAccountMapper.updateByPrimaryKeySelective(updateDoctorAccount);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ApplyWithdrawRespDTO applyWithdraw(ApplyWithdrawReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        if (null == reqDTO.getAmount() || reqDTO.getAmount().compareTo(BigDecimal.ZERO) <= 0) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003012);
        }

        // 判断余额是否足够 TODO：手续费怎么办
        AccDoctorWallet wallet = accDoctorWalletMapper.selectByPrimaryKeyForUpdate(reqDTO.getDoctorId());
        if (wallet.getAmount().subtract(reqDTO.getAmount()).compareTo(BigDecimal.ZERO) < 0) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003012);
        }

        DoctorWithdrawChannelEnum channelEnum = DoctorWithdrawChannelEnum.getChannelEnum(reqDTO.getChannel());
        if (null == channelEnum) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003010);
        }

        BigDecimal charge = BigDecimal.ZERO;

        // 校验银行卡是否存在
        if (channelEnum.equals(DoctorWithdrawChannelEnum.BANK) && null == reqDTO.getBankId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003011);
        } else if (channelEnum.equals(DoctorWithdrawChannelEnum.BANK) && null != reqDTO.getBankId()) {
            AccDoctorBankCard doctorBank = accDoctorBankCardMapper.selectByPrimaryKey(reqDTO.getBankId());
            if (null == doctorBank) {
                throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003011);
            }

            SysBank bank = sysBankMapper.selectByPrimaryKey(doctorBank.getBankId());
            charge = bank.getChargeFee().multiply(reqDTO.getAmount()).
                    divide(new BigDecimal("100"),2, BigDecimal.ROUND_HALF_DOWN);
        }

        // 校验提现账户
        if (!channelEnum.equals(DoctorWithdrawChannelEnum.BANK) && StringUtils.isBlank(reqDTO.getChannelAccount())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003013);
        }

        AccDoctorWithdraw doctorWithdraw = new AccDoctorWithdraw();
        doctorWithdraw.setDoctorId(reqDTO.getDoctorId());
        doctorWithdraw.setChannel(reqDTO.getChannel());
        doctorWithdraw.setChannelAccount(reqDTO.getChannelAccount());
        doctorWithdraw.setBankId(reqDTO.getBankId());
        doctorWithdraw.setAmount(reqDTO.getAmount());
        // TODO:手续费处理怎么算
        doctorWithdraw.setCharge(charge);
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_YEAR, 3);
        doctorWithdraw.setExpectAccountTime(calendar.getTime());
        doctorWithdraw.setCreateDate(new Date());

        accDoctorWithdrawMapper.insertSelective(doctorWithdraw);

        // 插入流水
        AccDoctorDealDetail doctorDealDetail = new AccDoctorDealDetail();
        doctorDealDetail.setDoctorId(reqDTO.getDoctorId());
        doctorDealDetail.setPayAmount(reqDTO.getAmount());
        doctorDealDetail.setDealType(DoctorDealTypeEnum.WITHDRAW.getCode());
        // TODO:描述怎么处理？
        doctorDealDetail.setDecription("佣金提现");
        doctorDealDetail.setRelationId(doctorWithdraw.getId());
        doctorDealDetail.setCreateTime(new Date());

        accDoctorDealDetailMapper.insertSelective(doctorDealDetail);

        // 钱包金额减少
        AccDoctorWallet updateWallet = new AccDoctorWallet();
        updateWallet.setId(reqDTO.getDoctorId());
        updateWallet.setAmount(wallet.getAmount().subtract(reqDTO.getAmount()));
        updateWallet.setUpdateTime(new Date());
        accDoctorWalletMapper.updateByPrimaryKeySelective(updateWallet);

        ApplyWithdrawRespDTO respDTO = new ApplyWithdrawRespDTO();
        respDTO.setCharge(doctorWithdraw.getCharge());
        respDTO.setCreateDate(doctorWithdraw.getCreateDate());
        respDTO.setExpectAccountTime("预计3-5个工作日内到账");

        return respDTO;
    }

    @Override
    public ShowBankWithdrawRespDTO showBankWithdraw(Integer doctorId) throws ApiServiceException {
        ShowBankWithdrawRespDTO respDTO = new ShowBankWithdrawRespDTO();

        AccDoctorWithdraw doctorWithdraw = accDoctorWithdrawMapper.selectLastBankWithdraw(doctorId);
        if (null != doctorWithdraw) {
            AccDoctorBankCard bankCard = accDoctorBankCardMapper.selectByPrimaryKey(doctorWithdraw.getBankId());
            SysBank sysBank = sysBankMapper.selectByPrimaryKey(bankCard.getBankId());

            respDTO.setBankId(bankCard.getId());
            respDTO.setBankName(sysBank.getBankName());
            respDTO.setBankType(sysBank.getBankType());
            respDTO.setChargeFee(sysBank.getChargeFee());
        }

        respDTO.setBalance(accDoctorWalletMapper.selectByPrimaryKey(doctorId).getAmount());
        return respDTO;
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
                StringUtils.isBlank(bankCard.getBankName()) || StringUtils.isBlank(bankCard.getCardCode()) ||
                StringUtils.isBlank(bankCard.getHolderName()) || StringUtils.isBlank(bankCard.getMobileNo())) {
            return false;
        }

        // TODO:银行卡校验，三要素校验等
        // TODO:校验银行卡是否重复绑定

        return true;
    }
}
