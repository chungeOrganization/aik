package com.aik.service.relation;

import com.aik.assist.ErrorCodeEnum;
import com.aik.assist.RelationTypeUtil;
import com.aik.dao.*;
import com.aik.dto.request.doctor.SickListReqDTO;
import com.aik.dto.request.doctor.SickOrderListReqDTO;
import com.aik.dto.response.doctor.SickListRespDTO;
import com.aik.dto.response.doctor.SickOrderListRespDTO;
import com.aik.enums.QuestionOrderEnum.*;
import com.aik.enums.SexEnum;
import com.aik.enums.UserAccountUserTypeEnum;
import com.aik.enums.UserAttentionTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.resource.SystemResource;
import com.aik.service.account.UserHealthRecordService;
import com.aik.util.BeansUtils;
import com.aik.util.ScrawlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/12.
 */
@Service
public class DoctorRelationServiceImpl implements DoctorRelationService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorRelationServiceImpl.class);

    private AccUserAttentionMapper accUserAttentionMapper;

    private AikDoctorSickGroupMapper aikDoctorSickGroupMapper;

    private AikDoctorSickMapper aikDoctorSickMapper;

    private AikQuestionOrderMapper aikQuestionOrderMapper;

    private AccDoctorAttentionMapper accDoctorAttentionMapper;

    private UserHealthRecordService userHealthRecordService;

    private SystemResource systemResource;

    @Autowired
    public void setAccUserAttentionMapper(AccUserAttentionMapper accUserAttentionMapper) {
        this.accUserAttentionMapper = accUserAttentionMapper;
    }

    @Autowired
    public void setAikDoctorSickGroupMapper(AikDoctorSickGroupMapper aikDoctorSickGroupMapper) {
        this.aikDoctorSickGroupMapper = aikDoctorSickGroupMapper;
    }

    @Autowired
    public void setAikDoctorSickMapper(AikDoctorSickMapper aikDoctorSickMapper) {
        this.aikDoctorSickMapper = aikDoctorSickMapper;
    }

    @Autowired
    public void setAikQuestionOrderMapper(AikQuestionOrderMapper aikQuestionOrderMapper) {
        this.aikQuestionOrderMapper = aikQuestionOrderMapper;
    }

    @Autowired
    public void setAccDoctorAttentionMapper(AccDoctorAttentionMapper accDoctorAttentionMapper) {
        this.accDoctorAttentionMapper = accDoctorAttentionMapper;
    }

    @Autowired
    public void setUserHealthRecordService(UserHealthRecordService userHealthRecordService) {
        this.userHealthRecordService = userHealthRecordService;
    }

    @Autowired
    public void setSystemResource(SystemResource systemResource) {
        this.systemResource = systemResource;
    }

    @Override
    public Integer getDoctorFansCount(Integer doctorId) throws ApiServiceException {
        AccUserAttention searchAU = new AccUserAttention();
        searchAU.setAttentionId(doctorId);
        searchAU.setType(UserAttentionTypeEnum.ATTENTION_DOCTOR.getCode());
        return accUserAttentionMapper.selectCountBySelective(searchAU);
    }

    @Override
    public List<Map<String, Object>> getDoctorFansList(Map<String, Object> params) throws ApiServiceException {
        List<Map<String, Object>> rsList = accUserAttentionMapper.selectDoctorFansPage(params);
        for (Map<String, Object> map : rsList) {
            byte fansSex = null != map.get("fansSex") ? Byte.valueOf(map.get("fansSex").toString()) : 0;
            map.put("fansSex", SexEnum.getDescFromCode(fansSex));

            byte userType = null != map.get("fansUserType") ? Byte.valueOf(map.get("fansUserType").toString()) : 0;
            map.put("fansUserType", UserAccountUserTypeEnum.getDescFromCode(userType));

            if (null != map.get("fansHeadImg")) {
                map.put("fansHeadImg", systemResource.getApiFileUri() + map.get("fansHeadImg").toString());
            }
        }
        return rsList;
    }

    @Override
    public List<AikDoctorSickGroup> getDoctorSickGroups(Integer doctorId) throws ApiServiceException {
        List<AikDoctorSickGroup> sickGroups = aikDoctorSickGroupMapper.selectByDoctorId(doctorId);
        sickGroups.add(0, new AikDoctorSickGroup(-1, doctorId, "全部患者"));
        sickGroups.add(new AikDoctorSickGroup(0, doctorId, "待分组"));
        return sickGroups;
    }

    @Override
    public List<SickListRespDTO> getSickList(SickListReqDTO sickListReqDTO) throws ApiServiceException {
        Map<String, Object> params = BeansUtils.transBean2Map(sickListReqDTO);
        List<Map<String, Object>> sickListMap = aikDoctorSickMapper.selectListByParams(params);

        List<SickListRespDTO> sickList = BeansUtils.transListMap2ListBean(sickListMap, SickListRespDTO.class);
//        for (Map<String, Object> map : sickList) {
//            byte sickSex = null != map.get("sickSex") ? Byte.valueOf(map.get("sickSex").toString()) : 0;
//            map.put("sickSex", SexEnum.getDescFromCode(sickSex));
//
//            // 获取该用户提问订单id
//            Integer questionOrderId = Integer.valueOf(map.get("questionOrderId").toString());
//            AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(questionOrderId);
//
//            // 描述
//            if (questionOrder.getStatus() == QuestionOrderStatusEnum.NORMAL_END.getCode() &&
//                    questionOrder.getServiceAttitude() == 5 && questionOrder.getAnswerQuality() == 5) {
//                map.put("description", "给您评了五星");
//            } else {
//                map.put("description", questionOrder.getDescription());
//            }
//
//            // 订单状态
//            if (questionOrder.getStatus() == QuestionOrderStatusEnum.ON_HANDLE.getCode()) {
//                map.put("questionStatus", "待回答");
//            } else {
//                map.put("questionStatus", "完成");
//            }
//        }

        return sickList;
    }

    @Override
    public List<SickOrderListRespDTO> getSickOrderList(SickOrderListReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO || null == reqDTO.getSickId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Map<String, Object> params = BeansUtils.transBean2Map(reqDTO);
        List<Map<String, Object>> sickOrderListMap = aikQuestionOrderMapper.selectSickOrders(params);

        List<SickOrderListRespDTO> sickOrderList = BeansUtils.transListMap2ListBean(sickOrderListMap, SickOrderListRespDTO.class);
        for (SickOrderListRespDTO sickOrder : sickOrderList) {
            sickOrder.setDescription(ScrawlUtils.aikStringOmit(sickOrder.getDescription()));
            // 订单状态
            if (sickOrder.getStatus() == QuestionOrderStatusEnum.ON_HANDLE.getCode()) {
                sickOrder.setAnswerStatus("待回答");
            } else {
                sickOrder.setAnswerStatus("完成");
            }
        }

        return sickOrderList;
    }

    @Override
    public Map<String, Object> getSickDetail(Integer sickId, Integer doctorId) throws ApiServiceException {
        if (null == sickId || null == doctorId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        // 患者基本信息
        Map<String, Object> sickDetail = aikDoctorSickMapper.selectSickDetailBySickId(sickId);
        if (null == sickDetail) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003001);
        }

        // 患者性别转换
        byte sickSex = null != sickDetail.get("sickSex") ? Byte.valueOf(sickDetail.get("sickSex").toString()) : 0;
        sickDetail.put("sickSex", SexEnum.getDescFromCode(sickSex));

        // 患者用户类型转换
        byte sickUserType = null != sickDetail.get("sickUserType") ? Byte.valueOf(sickDetail.get("sickUserType").toString()) : 0;
        sickDetail.put("sickUserType", UserAccountUserTypeEnum.getDescFromCode(sickUserType));

        // 相互关注
        Integer userId = Integer.valueOf(sickDetail.get("userId").toString());
        sickDetail.remove("userId");
        // 患者是否关注医生
        AccUserAttention searchAU = new AccUserAttention();
        searchAU.setUserId(userId);
        searchAU.setAttentionId(doctorId);
        searchAU.setType(UserAttentionTypeEnum.ATTENTION_DOCTOR.getCode());
        boolean isSickRdoctor = accUserAttentionMapper.selectCountBySelective(searchAU) > 0;
        // 医生是否关注患者
        AccDoctorAttention searchAD = new AccDoctorAttention();
        searchAD.setDoctorId(doctorId);
        searchAD.setUserId(userId);
        boolean isDoctorRsick = accDoctorAttentionMapper.selectCountBySelective(searchAD) > 0;
        sickDetail.put("relationType", RelationTypeUtil.getABRelation(isDoctorRsick, isSickRdoctor));

        // 用户健康档案
        sickDetail.put("healthRecord", userHealthRecordService.getLastHealthRecordDetail(userId));
        return sickDetail;
    }

    @Override
    public Integer getSickGroup(Integer sickId) throws ApiServiceException {
        if (null == sickId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikDoctorSick aikDoctorSick = aikDoctorSickMapper.selectByPrimaryKey(sickId);
        if (null == aikDoctorSick) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003001);
        }

        return aikDoctorSick.getGroupId();
    }

    @Override
    public void addSickGroup(AikDoctorSickGroup aikDoctorSickGroup) throws ApiServiceException {
        boolean groupNameIsUsed = aikDoctorSickGroupMapper.selectCountBySelective(aikDoctorSickGroup) > 0;
        if (groupNameIsUsed) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003002);
        }

        aikDoctorSickGroup.setCreateDate(new Date());
        aikDoctorSickGroupMapper.insertSelective(aikDoctorSickGroup);
    }

    @Override
    public void deleteSickGroup(Integer sickGroupId) throws ApiServiceException {
        if (null == aikDoctorSickGroupMapper.selectByPrimaryKey(sickGroupId)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003003);
        }

        aikDoctorSickGroupMapper.deleteByPrimaryKey(sickGroupId);
    }

    @Override
    public void updateSickGroup(Integer sickId, Integer sickGroupId) throws ApiServiceException {
        if (null == sickId || null == sickGroupId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        sickGroupId = -1 == sickGroupId ? 0 : sickGroupId;
        AikDoctorSick aikDoctorSick = aikDoctorSickMapper.selectByPrimaryKey(sickId);
        if (null == aikDoctorSick) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003001);
        }
        aikDoctorSick.setGroupId(sickGroupId);
        aikDoctorSick.setUpdateDate(new Date());

        aikDoctorSickMapper.updateByPrimaryKeySelective(aikDoctorSick);
    }
}
