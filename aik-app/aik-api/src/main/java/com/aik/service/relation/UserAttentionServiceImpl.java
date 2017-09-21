package com.aik.service.relation;

import com.aik.assist.ErrorCodeEnum;
import com.aik.assist.RelationTypeUtil;
import com.aik.dao.AccDoctorAccountMapper;
import com.aik.dao.AccDoctorAttentionMapper;
import com.aik.dao.AccUserAccountMapper;
import com.aik.dao.AccUserAttentionMapper;
import com.aik.enums.DoctorPositionEnum;
import com.aik.enums.UserAttentionTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccDoctorAccount;
import com.aik.model.AccDoctorAttention;
import com.aik.model.AccUserAccount;
import com.aik.model.AccUserAttention;
import com.aik.resource.SystemResource;
import com.aik.service.question.AnswerService;
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

    @Override
    public Integer getUserAttentionCount(Integer userId) throws ApiServiceException {
        AccUserAttention searchAU = new AccUserAttention();
        searchAU.setUserId(userId);

        return accUserAttentionMapper.selectCountBySelective(searchAU);
    }

    @Override
    public List<Map<String, Object>> getUserAttentionList(Map<String, Object> params) throws ApiServiceException {
        List<Map<String, Object>> rsList = new ArrayList<>();

        List<AccUserAttention> userAttentions = accUserAttentionMapper.selectAttentionsPage(params);
        for (AccUserAttention userAttention : userAttentions) {
            Map<String, Object> attentionInfo = new HashMap<>();
            if (userAttention.getType() == UserAttentionTypeEnum.ATTENTION_DOCTOR.getCode()) {
                AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(userAttention.getAttentionId());

                attentionInfo.put("id", doctorAccount.getId());
                attentionInfo.put("headImg", systemResource.getApiFileUri() + doctorAccount.getHeadImg());
                attentionInfo.put("realName", doctorAccount.getRealName());
                attentionInfo.put("department", doctorAccount.getHosDepartment());
                attentionInfo.put("position", DoctorPositionEnum.getDescFromCode(doctorAccount.getPosition()));
                attentionInfo.put("hospital", doctorAccount.getHosName());

                AccDoctorAttention searchAD = new AccDoctorAttention();
                searchAD.setDoctorId(doctorAccount.getId());
                searchAD.setUserId(userAttention.getUserId());
                boolean isDoctorAttention = accDoctorAttentionMapper.selectCountBySelective(searchAD) > 0;

                attentionInfo.put("relation", RelationTypeUtil.getABRelation(true, isDoctorAttention));
            } else {
                AccUserAccount userAccount = accUserAccountMapper.selectByPrimaryKey(userAttention.getAttentionId());

                attentionInfo.put("id", userAccount.getId());
                attentionInfo.put("headImg", systemResource.getApiFileUri() + userAccount.getHeadImg());
                attentionInfo.put("realName", userAccount.getRealName());
                // TODO:
                attentionInfo.put("news", "最新消息？");

                AccUserAttention searchAU = new AccUserAttention();
                searchAU.setUserId(userAccount.getId());
                searchAU.setAttentionId(userAttention.getUserId());
                searchAU.setType(UserAttentionTypeEnum.ATTENTION_USER.getCode());
                boolean isUserAttention = accUserAttentionMapper.selectCountBySelective(searchAU) > 0;

                attentionInfo.put("relation", RelationTypeUtil.getABRelation(true, isUserAttention));
            }
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
        introduction.put("answersCount", answerService.getDoctorAnswersCount(doctorId));

        AccDoctorAttention searchAD = new AccDoctorAttention();
        searchAD.setDoctorId(doctorAccount.getId());
        searchAD.setUserId(userId);
        boolean isDoctorAttention = accDoctorAttentionMapper.selectCountBySelective(searchAD) > 0;

        introduction.put("relation", RelationTypeUtil.getABRelation(true, isDoctorAttention));

        // TODO：获取医生简介
        introduction.put("introduction", "简介信息");

        // TODO：获取第二执业地点
        introduction.put("secondPractice", "第二执业地点");

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
        }
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
