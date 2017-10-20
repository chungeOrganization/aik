package com.aik.service.relation;

import com.aik.dto.request.doctor.SickListReqDTO;
import com.aik.dto.request.doctor.SickOrderListReqDTO;
import com.aik.dto.response.doctor.SickListRespDTO;
import com.aik.dto.response.doctor.SickOrderListRespDTO;
import com.aik.exception.ApiServiceException;
import com.aik.model.AikDoctorSickGroup;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
public interface DoctorRelationService {

    /**
     * 获取医生fans总数
     *
     * @param doctorId 医生用户id
     * @return 医生fans总数
     * @throws ApiServiceException
     */
    Integer getDoctorFansCount(Integer doctorId) throws ApiServiceException;

    /**
     * 获取医生粉丝详情
     *
     * @param params 参数
     * @return 医生粉丝详情
     * @throws ApiServiceException
     */
    List<Map<String, Object>> getDoctorFansList(Map<String, Object> params) throws ApiServiceException;

    /**
     * 获取患者分组
     *
     * @param doctorId 医生账户id
     * @return 患者分组
     * @throws ApiServiceException Api服务异常
     */
    List<AikDoctorSickGroup> getDoctorSickGroups(Integer doctorId) throws ApiServiceException;

    /**
     * 获取患者列表
     *
     * @param sickListReqDTO request DTO
     * @return 患者列表
     * @throws ApiServiceException Api服务异常
     */
    List<SickListRespDTO> getSickList(SickListReqDTO sickListReqDTO) throws ApiServiceException;

    /**
     * 获取患者订单列表
     * @param reqDTO request DTO
     * @return 患者订单列表
     * @throws ApiServiceException
     */
    List<SickOrderListRespDTO> getSickOrderList(SickOrderListReqDTO reqDTO) throws ApiServiceException;

    /**
     * 获取患者详情
     *
     * @param sickId   医生患者id
     * @param doctorId 医生id
     * @return 患者详情
     * @throws ApiServiceException Api服务异常
     */
    Map<String, Object> getSickDetail(Integer sickId, Integer doctorId) throws ApiServiceException;

    /**
     * 获取患者分组
     *
     * @param sickId 患者id
     * @return 分组id
     * @throws ApiServiceException Api服务异常
     */
    Integer getSickGroup(Integer sickId) throws ApiServiceException;

    /**
     * 新增医生患者分组
     *
     * @param aikDoctorSickGroup 分组对象
     * @throws ApiServiceException Api服务异常
     */
    void addSickGroup(AikDoctorSickGroup aikDoctorSickGroup) throws ApiServiceException;

    /**
     * 删除医生患者分组
     *
     * @param sickGroupId 分组id
     * @throws ApiServiceException Api服务异常
     */
    void deleteSickGroup(Integer sickGroupId) throws ApiServiceException;

    /**
     * 修改医生患者的分组
     *
     * @param sickId      患者id
     * @param sickGroupId 分组id
     * @throws ApiServiceException Api服务异常
     */
    void updateSickGroup(Integer sickId, Integer sickGroupId) throws ApiServiceException;
}
