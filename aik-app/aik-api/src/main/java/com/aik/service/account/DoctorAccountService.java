package com.aik.service.account;

import com.aik.dto.DoctorInfoDTO;
import com.aik.dto.request.doctor.*;
import com.aik.dto.request.user.ResetPwdReqDTO;
import com.aik.dto.response.doctor.ApplyWithdrawRespDTO;
import com.aik.dto.response.doctor.ShowBankWithdrawRespDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/6.
 */
public interface DoctorAccountService {

    /**
     * 校验手机号是否已被使用
     *
     * @param mobileNo 手机号
     * @return true：已被使用 false：未被使用
     * @throws ApiServiceException Api服务异常
     */
    Boolean validMobileNoIsUsed(String mobileNo) throws ApiServiceException;

    /**
     * 校验用户名是否已被使用
     *
     * @param userName 用户名
     * @return true：已被使用 false：未被使用
     * @throws ApiServiceException Api服务异常
     */
    Boolean validUserNameIsUsed(String userName) throws ApiServiceException;

    /**
     * 填充医生信息
     *
     * @param doctorInfoDTO 医生信息DTO
     * @throws ApiServiceException Api服务异常
     */
    void fillDoctorInfo(DoctorInfoDTO doctorInfoDTO) throws ApiServiceException;

    /**
     * 获取医生账户信息
     *
     * @param accountId 账户id
     * @return 医生账户信息
     * @throws ApiServiceException Api服务异常
     */
    DoctorInfoDTO getDoctorInfo(Integer accountId) throws ApiServiceException;

    /**
     * 编辑医生信息
     *
     * @param doctorInfoDTO 医生信息DTO
     * @throws ApiServiceException Api服务异常
     */
    void editDoctorInfo(DoctorInfoDTO doctorInfoDTO) throws ApiServiceException;

    /**
     * 上传医生文件
     *
     * @param accountId 手机号
     * @param fileUrl   文件地址
     * @return file对象
     * @throws ApiServiceException Api服务异常
     */
    AccDoctorFile uploadDoctorFile(Integer accountId, String fileUrl) throws ApiServiceException;

    /**
     * 获取医生钱包
     *
     * @param doctorId 医生id
     * @return 余额
     * @throws ApiServiceException Api服务异常
     */
    BigDecimal getWallet(Integer doctorId) throws ApiServiceException;

    /**
     * 获取医生银行卡
     *
     * @param doctorId 医生用户id
     * @return 医生银行卡
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getBankCards(Integer doctorId) throws ApiServiceException;

    /**
     * 添加银行卡信息
     *
     * @param bankCard 银行卡信息
     * @throws ApiServiceException Api服务异常
     */
    void addBankCard(AccDoctorBankCard bankCard) throws ApiServiceException;

    /**
     * 获取医生信息
     *
     * @param doctorId 医生id
     * @return 医生信息
     * @throws ApiServiceException
     */
    AccDoctorAccount getDoctorAccount(Integer doctorId) throws ApiServiceException;

    /**
     * 获取医生二维码
     *
     * @param doctorId 医生id
     * @return 二维码信息
     * @throws ApiServiceException
     */
    Map<String, Object> getQRCode(Integer doctorId) throws ApiServiceException;

    /**
     * 修改密码
     *
     * @param updatePwdDTO DTO
     * @throws ApiServiceException
     */
    void updatePassword(UpdatePwdReqDTO updatePwdDTO) throws ApiServiceException;

    /**
     * 重新绑定手机号
     *
     * @param rebindingMobileReqDTO DTO
     * @throws ApiServiceException
     */
    void rebindingMobileNo(RebindingMobileReqDTO rebindingMobileReqDTO) throws ApiServiceException;

    /**
     * 设置支付密码
     *
     * @param reqDTO DTO
     * @throws ApiServiceException
     */
    void setPayPassword(PayPasswordReqDTO reqDTO) throws ApiServiceException;

    /**
     * 校验支付密码
     *
     * @param reqDTO DTO
     * @return true false
     * @throws ApiServiceException
     */
    Boolean validPayPassword(PayPasswordReqDTO reqDTO) throws ApiServiceException;

    /**
     * 重置支付密码
     *
     * @param reqDTO DTO
     * @return true false
     * @throws ApiServiceException
     */
    void resetPayPassword(ResetPayPasswordReqDTO reqDTO) throws ApiServiceException;

    /**
     * 重置密码
     *
     * @param reqDTO DTO
     * @throws ApiServiceException
     */
    void resetPassword(ResetPwdReqDTO reqDTO) throws ApiServiceException;

    /**
     * 医生发起提现
     *
     * @param reqDTO 参数
     * @return
     * @throws ApiServiceException Api服务异常
     */
    ApplyWithdrawRespDTO applyWithdraw(ApplyWithdrawReqDTO reqDTO) throws ApiServiceException;

    /**
     * 显示银行卡提现
     *
     * @param doctorId 医生id
     * @return 银行卡提现内容
     * @throws ApiServiceException Api服务异常
     */
    ShowBankWithdrawRespDTO showBankWithdraw(Integer doctorId) throws ApiServiceException;

    /**
     * 支付医生咨询订单金额
     *
     * @param orderId 订单id
     * @throws ApiServiceException
     */
    void payDoctorOrderAmount(Integer orderId) throws ApiServiceException;
}
