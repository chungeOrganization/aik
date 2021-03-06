package com.aik.service.relation;

import com.aik.assist.ErrorCodeEnum;
import com.aik.assist.RelationTypeUtil;
import com.aik.dao.*;
import com.aik.dto.request.user.GetAttentionListReqDTO;
import com.aik.dto.response.user.GetAttentionDoctorRespDTO;
import com.aik.dto.response.user.GetAttentionUserRespDTO;
import com.aik.enums.DoctorPositionEnum;
import com.aik.enums.DoctorTipsTypeEnum;
import com.aik.enums.UserAttentionTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.resource.SystemResource;
import com.aik.service.account.DoctorTipsService;
import com.aik.service.question.AnswerService;
import com.aik.util.BeansUtils;
import com.aik.util.ScrawlUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;

/**
 * Description:
 * Created by as on 2017/8/28.
 */
@Service
public class UserAttentionServiceImpl implements UserAttentionService {

    private static final Logger logger = LoggerFactory.getLogger(UserAttentionServiceImpl.class);

    private AccUserAttentionMapper accUserAttentionMapper;

    private AccDoctorAccountMapper accDoctorAccountMapper;

    private AccUserAccountMapper accUserAccountMapper;

    private AccDoctorAttentionMapper accDoctorAttentionMapper;

    private AnswerService answerService;

    private DoctorTipsService doctorTipsService;

    private DoctorRelationService doctorRelationService;

    private AccMutualCircleMapper accMutualCircleMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setAccUserAttentionMapper(AccUserAttentionMapper accUserAttentionMapper) {
        this.accUserAttentionMapper = accUserAttentionMapper;
    }

    @Autowired
    public void setAccDoctorAccountMapper(AccDoctorAccountMapper accDoctorAccountMapper) {
        this.accDoctorAccountMapper = accDoctorAccountMapper;
    }

    @Autowired
    public void setAccDoctorAttentionMapper(AccDoctorAttentionMapper accDoctorAttentionMapper) {
        this.accDoctorAttentionMapper = accDoctorAttentionMapper;
    }

    @Autowired
    public void setAccUserAccountMapper(AccUserAccountMapper accUserAccountMapper) {
        this.accUserAccountMapper = accUserAccountMapper;
    }

    @Autowired
    public void setAnswerService(AnswerService answerService) {
        this.answerService = answerService;
    }

    @Autowired
    public void setDoctorTipsService(DoctorTipsService doctorTipsService) {
        this.doctorTipsService = doctorTipsService;
    }

    @Autowired
    public void setDoctorRelationService(DoctorRelationService doctorRelationService) {
        this.doctorRelationService = doctorRelationService;
    }

    @Autowired
    public void setAccMutualCircleMapper(AccMutualCircleMapper accMutualCircleMapper) {
        this.accMutualCircleMapper = accMutualCircleMapper;
    }

    @Override
    public Integer getUserAttentionCount(Integer userId) throws ApiServiceException {
        AccUserAttention searchAU = new AccUserAttention();
        searchAU.setUserId(userId);

        return accUserAttentionMapper.selectCountBySelective(searchAU);
    }

    @Override
    public List<GetAttentionDoctorRespDTO> getAttentionDoctorList(GetAttentionListReqDTO reqDTO) throws ApiServiceException {
        if (null == reqDTO) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        List<GetAttentionDoctorRespDTO> rsList = new ArrayList<>();

        AccUserAccount userAccount = accUserAccountMapper.selectByPrimaryKey(reqDTO.getUserId());

        Map<String, Object> params = BeansUtils.transBean2Map(reqDTO);
        params.put("type", UserAttentionTypeEnum.ATTENTION_DOCTOR.getCode());
        List<AccUserAttention> userAttentions = accUserAttentionMapper.selectAttentionsPage(params);
        for (AccUserAttention userAttention : userAttentions) {
            AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(userAttention.getAttentionId());

            GetAttentionDoctorRespDTO attentionInfo = new GetAttentionDoctorRespDTO();
            attentionInfo.setDoctorId(doctorAccount.getId());
            attentionInfo.setHeadImg(systemResource.getApiFileUri() + doctorAccount.getHeadImg());
            attentionInfo.setRealName(doctorAccount.getRealName());
            attentionInfo.setHosName(doctorAccount.getHosName());
            attentionInfo.setHosDepartment(doctorAccount.getHosDepartment());
            attentionInfo.setPosition(DoctorPositionEnum.getDescFromCode(doctorAccount.getPosition()));

            AccDoctorAttention searchAD = new AccDoctorAttention();
            searchAD.setDoctorId(doctorAccount.getId());
            searchAD.setUserId(userAttention.getUserId());
            boolean isDoctorAttention = accDoctorAttentionMapper.selectCountBySelective(searchAD) > 0;
            attentionInfo.setRelation(RelationTypeUtil.getABRelation(true, isDoctorAttention));

            if (null != userAccount.getAttendingDoctor() && userAccount.getAttendingDoctor() == doctorAccount.getId().intValue()) {
                attentionInfo.setAttending(true);
            } else {
                attentionInfo.setAttending(false);
            }

            rsList.add(attentionInfo);
        }

        return rsList;
    }

    @Override
    public List<GetAttentionUserRespDTO> getAttentionUserList(GetAttentionListReqDTO reqDTO) throws ApiServiceException {
        List<GetAttentionUserRespDTO> rsList = new ArrayList<>();

        Map<String, Object> params = BeansUtils.transBean2Map(reqDTO);
        params.put("type", UserAttentionTypeEnum.ATTENTION_USER.getCode());
        List<AccUserAttention> userAttentions = accUserAttentionMapper.selectAttentionsPage(params);
        for (AccUserAttention userAttention : userAttentions) {
            AccUserAccount userAccount = accUserAccountMapper.selectByPrimaryKey(userAttention.getAttentionId());

            GetAttentionUserRespDTO attentionInfo = new GetAttentionUserRespDTO();
            attentionInfo.setUserId(userAccount.getId());
            attentionInfo.setHeadImg(systemResource.getApiFileUri() + userAccount.getHeadImg());
            attentionInfo.setRealName(userAccount.getRealName());

            AccUserAttention searchAU = new AccUserAttention();
            searchAU.setUserId(userAccount.getId());
            searchAU.setAttentionId(userAttention.getUserId());
            searchAU.setType(UserAttentionTypeEnum.ATTENTION_USER.getCode());
            boolean isUserAttention = accUserAttentionMapper.selectCountBySelective(searchAU) > 0;

            attentionInfo.setRelation(RelationTypeUtil.getABRelation(true, isUserAttention));

            // 关注用户最新互助圈
            AccMutualCircle mutualCircle = accMutualCircleMapper.selectUserLastCircle(userAccount.getId());
            attentionInfo.setContent(null == mutualCircle ? "" : ScrawlUtils.aikStringOmit(mutualCircle.getContent()));

            rsList.add(attentionInfo);
        }

        return rsList;
    }

    @Override
    public Map<String, Object> getDoctorIntroduction(Integer doctorId, Integer userId) throws ApiServiceException {
        if (null == doctorId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Map<String, Object> introduction = new HashMap<>();

        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(doctorId);

        introduction.put("headImg", systemResource.getApiFileUri() + doctorAccount.getHeadImg());
        introduction.put("realName", doctorAccount.getRealName());
        introduction.put("department", doctorAccount.getHosDepartment());
        introduction.put("position", DoctorPositionEnum.getDescFromCode(doctorAccount.getPosition()));
        introduction.put("hospital", doctorAccount.getHosName());
        introduction.put("starLevel", doctorAccount.getStarLevel());
        introduction.put("price", doctorAccount.getPrice());
        introduction.put("answersCount", answerService.getDoctorAnswersCount(doctorId));

        AccDoctorAttention searchAD = new AccDoctorAttention();
        searchAD.setDoctorId(doctorAccount.getId());
        searchAD.setUserId(userId);
        boolean isDoctorAttention = accDoctorAttentionMapper.selectCountBySelective(searchAD) > 0;

        introduction.put("relation", RelationTypeUtil.getABRelation(true, isDoctorAttention));

        // 获取医生简介
        introduction.put("introduction", doctorAccount.getSkill());

        return introduction;
    }

    @Override
    public void cancelAttentionDoctor(Integer doctorId, Integer userId) throws ApiServiceException {
        if (null == doctorId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccUserAttention searchUA = new AccUserAttention();
        searchUA.setUserId(userId);
        searchUA.setAttentionId(doctorId);
        searchUA.setType(UserAttentionTypeEnum.ATTENTION_DOCTOR.getCode());

        List<AccUserAttention> userAttentions = accUserAttentionMapper.selectBySelective(searchUA);

        for (AccUserAttention userAttention : userAttentions) {
            accUserAttentionMapper.deleteByPrimaryKey(userAttention.getId());
        }
    }

    @Override
    public void attentionDoctor(Integer doctorId, Integer userId) throws ApiServiceException {
        if (null == doctorId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccUserAttention searchUA = new AccUserAttention();
        searchUA.setUserId(userId);
        searchUA.setAttentionId(doctorId);
        searchUA.setType(UserAttentionTypeEnum.ATTENTION_DOCTOR.getCode());
        boolean isAttention = accUserAttentionMapper.selectCountBySelective(searchUA) > 0;
        if (!isAttention) {
            AccUserAttention userAttention = new AccUserAttention();
            userAttention.setUserId(userId);
            userAttention.setAttentionId(doctorId);
            userAttention.setType(UserAttentionTypeEnum.ATTENTION_DOCTOR.getCode());
            userAttention.setCreateTime(new Date());

            accUserAttentionMapper.insertSelective(userAttention);

            // 医聊提示
            AikDoctorTips doctorTip = new AikDoctorTips();
            doctorTip.setDoctorId(doctorId);
            doctorTip.setUserId(userId);
            doctorTip.setTipsType(DoctorTipsTypeEnum.NEW_FRIEND.getCode());
            doctorTip.setRelationId(userAttention.getId());
            doctorTip.setTipsMessage("您有新的朋友关注");
            doctorTipsService.addDoctorTips(doctorTip);
        }

        // 成为该医生患者
        doctorRelationService.addDoctorSick(userId, doctorId);
    }

    @Override
    public void cancelAttentionUser(Integer cancelUserId, Integer userId) throws ApiServiceException {
        if (null == cancelUserId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccUserAttention searchUA = new AccUserAttention();
        searchUA.setUserId(userId);
        searchUA.setAttentionId(cancelUserId);
        searchUA.setType(UserAttentionTypeEnum.ATTENTION_USER.getCode());

        List<AccUserAttention> userAttentions = accUserAttentionMapper.selectBySelective(searchUA);

        for (AccUserAttention userAttention : userAttentions) {
            accUserAttentionMapper.deleteByPrimaryKey(userAttention.getId());
        }
    }

    @Override
    public void attentionUser(Integer attentionUserId, Integer userId) throws ApiServiceException {
        if (null == attentionUserId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccUserAttention searchUA = new AccUserAttention();
        searchUA.setUserId(userId);
        searchUA.setAttentionId(attentionUserId);
        searchUA.setType(UserAttentionTypeEnum.ATTENTION_USER.getCode());
        boolean isAttention = accUserAttentionMapper.selectCountBySelective(searchUA) > 0;
        if (!isAttention) {
            AccUserAttention userAttention = new AccUserAttention();
            userAttention.setUserId(userId);
            userAttention.setAttentionId(attentionUserId);
            userAttention.setType(UserAttentionTypeEnum.ATTENTION_USER.getCode());
            userAttention.setCreateTime(new Date());

            accUserAttentionMapper.insertSelective(userAttention);
        }
    }
}
