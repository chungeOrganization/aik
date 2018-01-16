package com.aik.service.account;

import com.aik.dto.DoctorInfoDTO;
import com.aik.dto.request.doctor.*;
import com.aik.dto.request.user.ResetPwdReqDTO;
import com.aik.dto.response.doctor.ApplyWithdrawRespDTO;
import com.aik.dto.response.doctor.ShowBankWithdrawRespDTO;
import com.aik.model.AccDoctorAccount;
import com.aik.model.AccDoctorBankCard;

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
     */
    Boolean validMobileNoIsUsed(String mobileNo);

    /**
     * 校验用户名是否已被使用
     *
     * @param userName 用户名
     * @return true：已被使用 false：未被使用
     */
    Boolean validUserNameIsUsed(String userName);

    /**
     * 填充医生信息
     *
     * @param doctorInfoDTO 医生信息DTO
     */
    void fillDoctorInfo(DoctorInfoDTO doctorInfoDTO);

    /**
     * 获取医生账户信息
     *
     * @param accountId 账户id
     * @return 医生账户信息
     */
    DoctorInfoDTO getDoctorInfo(Integer accountId);

    /**
     * 编辑医生信息
     *
     * @param doctorInfoDTO 医生信息DTO
     */
    void editDoctorInfo(DoctorInfoDTO doctorInfoDTO);

    /**
     * 上传医生文件
     *
     * @param accountId 手机号
     * @param fileUrl   文件地址
     */
    void uploadDoctorFile(Integer accountId, String fileUrl);

    /**
     * 获取医生钱包
     *
     * @param doctorId 医生id
     * @return 余额
     */
    BigDecimal getWallet(Integer doctorId);

    /**
     * 获取医生银行卡
     *
     * @param doctorId 医生用户id
     * @return 医生银行卡
     */
    List<Map<String, Object>> getBankCards(Integer doctorId);

    /**
     * 添加银行卡信息
     *
     * @param bankCard 银行卡信息
     */
    void addBankCard(AccDoctorBankCard bankCard);

    /**
     * 获取医生信息
     *
     * @param doctorId 医生id
     * @return 医生信息
     */
    AccDoctorAccount getDoctorAccount(Integer doctorId);

    /**
     * 获取医生二维码
     *
     * @param doctorId 医生id
     * @return 二维码信息
     */
    Map<String, Object> getQRCode(Integer doctorId);

    /**
     * 修改密码
     *
     * @param updatePwdDTO DTO
     */
    void updatePassword(UpdatePwdReqDTO updatePwdDTO);

    /**
     * 重新绑定手机号
     *
     * @param rebindingMobileReqDTO DTO
     */
    void rebindingMobileNo(RebindingMobileReqDTO rebindingMobileReqDTO);

    /**
     * 设置支付密码
     *
     * @param reqDTO DTO
     */
    void setPayPassword(PayPasswordReqDTO reqDTO);

    /**
     * 校验支付密码
     *
     * @param reqDTO DTO
     * @return true false
     */
    Boolean validPayPassword(PayPasswordReqDTO reqDTO);

    /**
     * 重置支付密码
     *
     * @param reqDTO DTO
     */
    void resetPayPassword(ResetPayPasswordReqDTO reqDTO);

    /**
     * 重置密码
     *
     * @param reqDTO DTO
     */
    void resetPassword(ResetPwdReqDTO reqDTO);

    /**
     * 医生发起提现
     *
     * @param reqDTO 参数
     * @return 提现response
     */
    ApplyWithdrawRespDTO applyWithdraw(ApplyWithdrawReqDTO reqDTO);

    /**
     * 显示银行卡提现
     *
     * @param doctorId 医生id
     * @return 银行卡提现内容
     */
    ShowBankWithdrawRespDTO showBankWithdraw(Integer doctorId);

    /**
     * 支付医生咨询订单金额
     *
     * @param orderId 订单id
     */
    void payDoctorOrderAmount(Integer orderId);

    /**
     * 生成医生二维码
     *
     * @param doctorId 医生id
     */
    void generateQrCode(Integer doctorId);
}
