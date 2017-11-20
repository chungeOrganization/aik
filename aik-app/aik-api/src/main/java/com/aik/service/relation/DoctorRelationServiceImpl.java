package com.aik.service.relation;

import com.aik.assist.ErrorCodeEnum;
import com.aik.assist.RelationTypeUtil;
import com.aik.dao.*;
import com.aik.dto.request.doctor.SickListReqDTO;
import com.aik.dto.request.doctor.SickOrderListReqDTO;
import com.aik.dto.response.doctor.SickDataDetailRespDTO;
import com.aik.dto.response.doctor.SickDetailRespDTO;
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
import com.github.pagehelper.util.StringUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    private AikQuestionMapper aikQuestionMapper;

    private AccUserAccountMapper accUserAccountMapper;

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

    @Autowired
    public void setAikQuestionMapper(AikQuestionMapper aikQuestionMapper) {
        this.aikQuestionMapper = aikQuestionMapper;
    }

    @Autowired
    public void setAccUserAccountMapper(AccUserAccountMapper accUserAccountMapper) {
        this.accUserAccountMapper = accUserAccountMapper;
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
        sickGroups.add(new AikDoctorSickGroup(0, doctorId, "待分组"));
        return sickGroups;
    }

    @Override
    public List<SickListRespDTO> getSickList(SickListReqDTO sickListReqDTO) throws ApiServiceException {
        Map<String, Object> params = BeansUtils.transBean2Map(sickListReqDTO);
        List<Map<String, Object>> sickListMap = aikDoctorSickMapper.selectListByParams(params);
        List<SickListRespDTO> sickList = BeansUtils.transListMap2ListBean(sickListMap, SickListRespDTO.class);
        for (SickListRespDTO sick: sickList) {
            // 头像增加prefix地址
            if (StringUtils.isNotEmpty(sick.getUserHeadImg())) {
                sick.setUserHeadImg(systemResource.getApiFileUri() + sick.getUserHeadImg());
            }

            // 获取患者咨询医生订单最新提问或评价
            AikQuestion question = aikQuestionMapper.selectSickLastQuestionByDoctorId(sick.getUserId(),
                    sickListReqDTO.getDoctorId());

            if (null != question) {
                sick.setSickQuestion(ScrawlUtils.aikStringOmit(question.getDescription()));
            }
        }
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
    public SickDataDetailRespDTO getSickDetail(Integer sickId, Integer doctorId) throws ApiServiceException {
        if (null == sickId || null == doctorId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        // 患者基本信息
        Map<String, Object> sickDetailMap = aikDoctorSickMapper.selectSickDetailBySickId(sickId);
        if (null == sickDetailMap) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003001);
        }
        SickDetailRespDTO sickDetail = BeansUtils.transMap2Bean(sickDetailMap, SickDetailRespDTO.class);

        SickDataDetailRespDTO sickDataDetail = new SickDataDetailRespDTO();

        // 用户头像
        sickDetail.setHeadImg(systemResource.getApiFileUri() + sickDetail.getHeadImg());
        // 相互关注
        Integer userId = sickDetail.getUserId();
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
        sickDetail.setRelation(Integer.valueOf(RelationTypeUtil.getABRelation(isDoctorRsick, isSickRdoctor)));

        // 患者信息
        sickDataDetail.setSickDetail(sickDetail);
        // 用户健康档案
        sickDataDetail.setHealthRecord(userHealthRecordService.getLastHealthRecordDetail(userId));

        return sickDataDetail;
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
    @Transactional(rollbackFor = Exception.class)
    public void deleteSickGroup(Integer sickGroupId) throws ApiServiceException {
        AikDoctorSickGroup sickGroup = aikDoctorSickGroupMapper.selectByPrimaryKey(sickGroupId);

        if (null == sickGroup) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1003003);
        }

        // 患者列表中有该分组用户分组重置为未分组
        aikDoctorSickMapper.clearDoctorSickGroup(sickGroup.getDoctorId(), sickGroupId);

        // 删除分组
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

    @Override
    public void attentionUser(Integer userId, Integer doctorId) throws ApiServiceException {
        if (null == userId || null == doctorId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccDoctorAttention doctorAttention = accDoctorAttentionMapper.selectByDoctorIdAndUserId(doctorId, userId);
        if (null == doctorAttention) {
            doctorAttention = new AccDoctorAttention();
            doctorAttention.setUserId(userId);
            doctorAttention.setDoctorId(doctorId);
            doctorAttention.setCreateDate(new Date());
            accDoctorAttentionMapper.insertSelective(doctorAttention);
        }
    }

    @Override
    public void cancelAttentionUser(Integer userId, Integer doctorId) throws ApiServiceException {
        if (null == userId || null == doctorId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccDoctorAttention doctorAttention = accDoctorAttentionMapper.selectByDoctorIdAndUserId(doctorId, userId);
        if (null != doctorAttention) {
            accDoctorAttentionMapper.deleteByPrimaryKey(doctorAttention.getId());
        }
    }

    @Override
    public AikDoctorSick getDoctorSick(Integer sickId) throws ApiServiceException {
        if (null == sickId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        return aikDoctorSickMapper.selectByPrimaryKey(sickId);
    }

    @Override
    public void updateSickRemark(Integer sickId, String remark) throws ApiServiceException {
        if (null == sickId || StringUtils.isEmpty(remark)) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikDoctorSick updateSick = new AikDoctorSick();
        updateSick.setId(sickId);
        updateSick.setRemark(remark);
        updateSick.setUpdateDate(new Date());
        aikDoctorSickMapper.updateByPrimaryKeySelective(updateSick);
    }

    @Override
    public void addDoctorSick(Integer userId, Integer doctorId) throws ApiServiceException {
        if (null == doctorId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AikDoctorSick doctorSick = aikDoctorSickMapper.selectByDoctorIdAndUserId(doctorId, userId);
        if (null == doctorSick) {
            doctorSick = new AikDoctorSick();
            doctorSick.setDoctorId(doctorId);
            doctorSick.setUserId(userId);
            doctorSick.setCreateDate(new Date());

            AccUserAccount userAccount = accUserAccountMapper.selectByPrimaryKey(userId);
            doctorSick.setRemark(userAccount.getRealName());
            aikDoctorSickMapper.insertSelective(doctorSick);
        }
    }
}
