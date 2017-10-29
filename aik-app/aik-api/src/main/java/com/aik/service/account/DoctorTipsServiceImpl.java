package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.assist.RelationTypeUtil;
import com.aik.dao.AccDoctorAttentionMapper;
import com.aik.dao.AccUserAccountMapper;
import com.aik.dao.AccUserAttentionMapper;
import com.aik.dao.AikDoctorTipsMapper;
import com.aik.enums.DoctorTipsCheckStatusEnum;
import com.aik.enums.DoctorTipsTypeEnum;
import com.aik.enums.SexEnum;
import com.aik.enums.UserAccountUserTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.AccDoctorAttention;
import com.aik.model.AccUserAccount;
import com.aik.model.AccUserAttention;
import com.aik.model.AikDoctorTips;
import com.aik.resource.SystemResource;
import com.aik.service.SysSettingService;
import com.aik.util.DateUtils;
import com.aik.util.ScrawlUtils;
import org.joda.time.DateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * Description:
 * Created by as on 2017/8/14.
 */
@Service
public class DoctorTipsServiceImpl implements DoctorTipsService {

    private static final Logger logger = LoggerFactory.getLogger(DoctorTipsServiceImpl.class);

    private AikDoctorTipsMapper aikDoctorTipsMapper;

    private AccUserAttentionMapper accUserAttentionMapper;

    private AccUserAccountMapper accUserAccountMapper;

    private AccDoctorAttentionMapper accDoctorAttentionMapper;

    private SystemResource systemResource;

    private SysSettingService sysSettingService;

    @Autowired
    public void setAikDoctorTipsMapper(AikDoctorTipsMapper aikDoctorTipsMapper) {
        this.aikDoctorTipsMapper = aikDoctorTipsMapper;
    }

    @Autowired
    public void setAccUserAttentionMapper(AccUserAttentionMapper accUserAttentionMapper) {
        this.accUserAttentionMapper = accUserAttentionMapper;
    }

    @Autowired
    public void setAccUserAccountMapper(AccUserAccountMapper accUserAccountMapper) {
        this.accUserAccountMapper = accUserAccountMapper;
    }

    @Autowired
    public void setAccDoctorAttentionMapper(AccDoctorAttentionMapper accDoctorAttentionMapper) {
        this.accDoctorAttentionMapper = accDoctorAttentionMapper;
    }

    @Autowired
    public void setSystemResource(SystemResource systemResource) {
        this.systemResource = systemResource;
    }

    @Autowired
    public void setSysSettingService(SysSettingService sysSettingService) {
        this.sysSettingService = sysSettingService;
    }

    @Override
    public List<Map<String, Object>> getDoctorTipsList(Integer doctorId) throws ApiServiceException {
        List<Map<String, Object>> rsList = new ArrayList<>();
        // 新的朋友
        AikDoctorTips friendSearchDT = new AikDoctorTips();
        friendSearchDT.setDoctorId(doctorId);
        friendSearchDT.setTipsType(DoctorTipsTypeEnum.NEW_FRIEND.getCode());
        friendSearchDT.setIsCheck(DoctorTipsCheckStatusEnum.NOT_CHECK.getCode());
        List<AikDoctorTips> friendTipsList = aikDoctorTipsMapper.selectBySelective(friendSearchDT);
        Map<String, Object> friendTips = new HashMap<>();
        friendTips.put("headImg", sysSettingService.getNewFriendHeadImg());
        friendTips.put("name", "新的朋友");
        friendTips.put("message", "您有新的粉丝，点击查看");
        if (null != friendTipsList.get(0)) {
            friendTips.put("createDate", DateUtils.aikPersonaliseDate(friendTipsList.get(0).getCreateDate()));
        } else {
            friendTips.put("createDate", "");
        }
        friendTips.put("redNum", friendTipsList.size());
        rsList.add(friendTips);

        // question tips
        List<Map<String, Object>> questionTipsList = aikDoctorTipsMapper.selectQuestionTipsByDoctorId(doctorId);
        rsList.addAll(questionTipsList);

        return rsList;
    }

    @Override
    public List<Map<String, Object>> checkNewFriendTips(Integer doctorId) throws ApiServiceException {
        List<Map<String, Object>> rsList = new ArrayList<>();

        // 新的朋友
        AikDoctorTips friendSearchDT = new AikDoctorTips();
        friendSearchDT.setDoctorId(doctorId);
        friendSearchDT.setTipsType(DoctorTipsTypeEnum.NEW_FRIEND.getCode());
        friendSearchDT.setIsCheck(DoctorTipsCheckStatusEnum.NOT_CHECK.getCode());
        List<AikDoctorTips> friendTipsList = aikDoctorTipsMapper.selectBySelective(friendSearchDT);
        for (AikDoctorTips doctorTips : friendTipsList) {
            AccUserAttention userAttention = accUserAttentionMapper.selectByPrimaryKey(doctorTips.getRelationId());
            AccUserAccount userAccount = accUserAccountMapper.selectByPrimaryKey(userAttention.getUserId());

            Map<String, Object> map = new HashMap<>();
            map.put("headImg", userAccount.getHeadImg());
            map.put("realName", userAccount.getRealName());
            map.put("sex", SexEnum.getDescFromCode(userAccount.getSex()));
            map.put("userType", UserAccountUserTypeEnum.getDescFromCode(userAccount.getUserType()));
            map.put("userId", userAccount.getId());

            // 医生是否关注患者
            AccDoctorAttention searchAD = new AccDoctorAttention();
            searchAD.setDoctorId(doctorId);
            searchAD.setUserId(userAccount.getId());
            boolean isDoctorRsick = accDoctorAttentionMapper.selectCountBySelective(searchAD) > 0;
            map.put("relationType", RelationTypeUtil.getABRelation(isDoctorRsick, true));

            rsList.add(map);

            // 标记为已读
            doctorTips.setIsCheck(DoctorTipsCheckStatusEnum.IS_CHECKED.getCode());
            doctorTips.setUpdateDate(new Date());
            aikDoctorTipsMapper.updateByPrimaryKeySelective(doctorTips);
        }

        return rsList;
    }

    @Override
    public void checkQuestionTips(Integer tipsId) throws ApiServiceException {
        AikDoctorTips aikDoctorTips = aikDoctorTipsMapper.selectByPrimaryKey(tipsId);
        if (null != aikDoctorTips && aikDoctorTips.getIsCheck() == DoctorTipsCheckStatusEnum.NOT_CHECK.getCode()) {
            aikDoctorTips.setIsCheck(DoctorTipsCheckStatusEnum.IS_CHECKED.getCode());
            aikDoctorTips.setUpdateDate(new Date());

            aikDoctorTipsMapper.updateByPrimaryKeySelective(aikDoctorTips);
        }
    }

    @Override
    public void deleteTips(Integer tipsId) throws ApiServiceException {
        if (null == tipsId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        aikDoctorTipsMapper.deleteByPrimaryKey(tipsId);
    }

    @Override
    public List<Map<String, Object>> getHomeDoctorTips(Integer doctorId) throws ApiServiceException {
        if (null == doctorId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        List<Map<String, Object>> doctorTips = aikDoctorTipsMapper.selectHomeDoctorTips(doctorId);
        for (Map<String, Object> map : doctorTips) {
            if (null != map.get("createDate")) {
                Date date = (Date) map.get("createDate");
                map.put("createDate", new DateTime(date.getTime()).toString("yyyy-MM-dd"));
            }
        }

        return doctorTips;
    }

    @Override
    public Integer getDoctorTipsCount(Integer doctorId) throws ApiServiceException {
        Map<String, Object> params = new HashMap<>();
        params.put("doctorId", doctorId);
        params.put("isCheck", DoctorTipsCheckStatusEnum.NOT_CHECK.getCode());

        return aikDoctorTipsMapper.selectCountByParams(params);
    }

    @Override
    public void addDoctorTips(AikDoctorTips doctorTip) throws ApiServiceException {
        doctorTip.setCreateDate(new Date());
        aikDoctorTipsMapper.insertSelective(doctorTip);
    }
}
