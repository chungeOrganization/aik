package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.assist.RelationTypeUtil;
import com.aik.dao.*;
import com.aik.dto.request.doctor.DoctorTipsListReqDTO;
import com.aik.dto.response.doctor.DoctorTipsListRespDTO;
import com.aik.dto.response.doctor.FriendTipsRespDTO;
import com.aik.dto.response.doctor.QuestionTipsRespDTO;
import com.aik.enums.DoctorTipsCheckStatusEnum;
import com.aik.enums.DoctorTipsTypeEnum;
import com.aik.enums.SexEnum;
import com.aik.enums.UserAccountUserTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.resource.SystemResource;
import com.aik.service.SysSettingService;
import com.aik.util.BeansUtils;
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

    private AccUserAccountMapper accUserAccountMapper;

    private AccDoctorAttentionMapper accDoctorAttentionMapper;

    private SystemResource systemResource;

    private SysSettingService sysSettingService;

    private AikDoctorSickMapper aikDoctorSickMapper;

    @Autowired
    public void setAikDoctorTipsMapper(AikDoctorTipsMapper aikDoctorTipsMapper) {
        this.aikDoctorTipsMapper = aikDoctorTipsMapper;
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

    @Autowired
    public void setAikDoctorSickMapper(AikDoctorSickMapper aikDoctorSickMapper) {
        this.aikDoctorSickMapper = aikDoctorSickMapper;
    }

    @Override
    public DoctorTipsListRespDTO getDoctorTipsList(DoctorTipsListReqDTO reqDTO) throws ApiServiceException {
        DoctorTipsListRespDTO respDTO = new DoctorTipsListRespDTO();

        // 新的朋友
        AikDoctorTips friendSearchDT = new AikDoctorTips();
        friendSearchDT.setDoctorId(reqDTO.getDoctorId());
        friendSearchDT.setTipsType(DoctorTipsTypeEnum.NEW_FRIEND.getCode());
        friendSearchDT.setIsCheck(DoctorTipsCheckStatusEnum.NOT_CHECK.getCode());
        List<AikDoctorTips> friendTipsList = aikDoctorTipsMapper.selectBySelective(friendSearchDT);

        FriendTipsRespDTO friendTips = new FriendTipsRespDTO();
        friendTips.setHeadImg(sysSettingService.getNewFriendHeadImg());
        friendTips.setRedNum(friendTipsList.size());
        friendTips.setMessage(friendTipsList.size() > 0 ? "您有新的粉丝，点击查看" : "");
        if (friendTipsList.size() > 0) {
            friendTips.setTipsTime(DateUtils.aikPersonaliseDate(friendTipsList.get(0).getCreateDate()));
        } else {
            friendTips.setTipsTime("");
        }
        respDTO.setFriendTips(friendTips);

        // question tips list
        List<Map<String, Object>> questionTipsList = aikDoctorTipsMapper.selectQuestionTipsByDoctorId(BeansUtils.transBean2Map(reqDTO));
        for (Map<String, Object> questionTips : questionTipsList) {
            questionTips.put("createDate", DateUtils.aikPersonaliseDate((Date) questionTips.get("createDate")));
            if (null != questionTips.get("headImg")) {
                questionTips.put("headImg", systemResource.getApiFileUri() + questionTips.get("headImg").toString());
            }
            if (null != questionTips.get("message")) {
                questionTips.put("message", ScrawlUtils.aikStringOmit(questionTips.get("message").toString()));
            }
        }
        List<QuestionTipsRespDTO> questionTipsResp = BeansUtils.transListMap2ListBean(questionTipsList, QuestionTipsRespDTO.class);
        respDTO.setQuestionTipsList(questionTipsResp);

        return respDTO;
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
            AccUserAccount userAccount = accUserAccountMapper.selectByPrimaryKey(doctorTips.getUserId());
            AikDoctorSick doctorSick = aikDoctorSickMapper.selectByDoctorIdAndUserId(doctorId, doctorTips.getUserId());

            Map<String, Object> map = new HashMap<>();
            map.put("headImg", systemResource.getApiFileUri() + userAccount.getHeadImg());
            map.put("realName", userAccount.getRealName());
            map.put("sex", SexEnum.getDescFromCode(userAccount.getSex()));
            map.put("userType", UserAccountUserTypeEnum.getDescFromCode(userAccount.getUserType()));
            map.put("userId", userAccount.getId());
            map.put("sickId", doctorSick.getId());

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

//        AikDoctorTips updateTips = new AikDoctorTips();
//        updateTips.setId(tipsId);
//        updateTips.setIsCheck(DoctorTipsCheckStatusEnum.IS_CHECKED.getCode());
//        updateTips.setUpdateDate(new Date());
//        aikDoctorTipsMapper.updateByPrimaryKeySelective(updateTips);
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

            if (Byte.valueOf(map.get("tipsType").toString()) == DoctorTipsTypeEnum.NEW_FRIEND.getCode()) {
                map.put("tipsMessage", "有一位新朋友关注了您，请去查看");
            } else if (Byte.valueOf(map.get("tipsType").toString()) == DoctorTipsTypeEnum.NEW_QUESTION.getCode()) {
                AikDoctorSick doctorSick = aikDoctorSickMapper.selectByDoctorIdAndUserId(doctorId,
                        Integer.valueOf(map.get("userId").toString()));

                map.put("tipsMessage", String.format("%s给您留言，请去查看", doctorSick.getRemark()));
            }

            map.remove("userId");
        }

        return doctorTips;
    }

    @Override
    public Integer getDoctorTipsCount(Integer doctorId) throws ApiServiceException {
        AikDoctorTips params = new AikDoctorTips();
        params.setDoctorId(doctorId);
        params.setIsCheck(DoctorTipsCheckStatusEnum.NOT_CHECK.getCode());

        return aikDoctorTipsMapper.selectCountBySelective(params);
    }

    @Override
    public void addDoctorTips(AikDoctorTips doctorTip) throws ApiServiceException {
        doctorTip.setCreateDate(new Date());
        aikDoctorTipsMapper.insertSelective(doctorTip);
    }

    @Override
    public void clearOrderTips(Integer orderId) throws ApiServiceException {
        aikDoctorTipsMapper.clearOrderTips(orderId);
    }
}
