package com.aik.service.account;

import com.aik.dto.DoctorInfoDTO;
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
}
