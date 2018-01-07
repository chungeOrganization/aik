package com.aik.service.account;

import com.aik.assist.ErrorCodeEnum;
import com.aik.assist.RelationTypeUtil;
import com.aik.common.Constants;
import com.aik.dao.*;
import com.aik.dto.IssueCircleDTO;
import com.aik.dto.request.user.GetAttentionUserCirclesReqDTO;
import com.aik.dto.response.user.CirclesRespDTO;
import com.aik.enums.DelFlagEnum;
import com.aik.enums.MutualCircleEnum;
import com.aik.enums.UserAttentionTypeEnum;
import com.aik.enums.UserFileTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.resource.SystemResource;
import com.aik.util.BeansUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Beans;
import java.util.*;

/**
 * Description:
 * Created by as on 2017/9/8.
 */
@Service
public class CircleServiceImpl implements CircleService {

    private static final Logger logger = LoggerFactory.getLogger(CircleServiceImpl.class);

    private AccMutualCircleMapper accMutualCircleMapper;

    private AccUserAttentionMapper accUserAttentionMapper;

    private AccUserFileMapper accUserFileMapper;

    private AccCircleLikeMapper accCircleLikeMapper;

    private AccCircleCommentMapper accCircleCommentMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setAccMutualCircleMapper(AccMutualCircleMapper accMutualCircleMapper) {
        this.accMutualCircleMapper = accMutualCircleMapper;
    }

    @Autowired
    public void setAccUserAttentionMapper(AccUserAttentionMapper accUserAttentionMapper) {
        this.accUserAttentionMapper = accUserAttentionMapper;
    }

    @Autowired
    public void setAccUserFileMapper(AccUserFileMapper accUserFileMapper) {
        this.accUserFileMapper = accUserFileMapper;
    }

    @Autowired
    public void setAccCircleLikeMapper(AccCircleLikeMapper accCircleLikeMapper) {
        this.accCircleLikeMapper = accCircleLikeMapper;
    }

    @Autowired
    public void setAccCircleCommentMapper(AccCircleCommentMapper accCircleCommentMapper) {
        this.accCircleCommentMapper = accCircleCommentMapper;
    }

    @Override
    public void userIssueCircle(IssueCircleDTO issueCircleDTO) throws ApiServiceException {
        if (null == issueCircleDTO || null == issueCircleDTO.getUserId() ||
                StringUtils.isBlank(issueCircleDTO.getContent())) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccMutualCircle mutualCircle = new AccMutualCircle();
        mutualCircle.setUserId(issueCircleDTO.getUserId());
        mutualCircle.setContent(issueCircleDTO.getContent());
        mutualCircle.setCreateDate(new Date());

        accMutualCircleMapper.insertSelective(mutualCircle);

        List<String> circleFiles = issueCircleDTO.getCircleFiles();
        if (null != circleFiles && circleFiles.size() > 0) {
            for (String fileUrl : circleFiles) {
                AccUserFile userFile = new AccUserFile();
                userFile.setUserId(issueCircleDTO.getUserId());
                userFile.setType(UserFileTypeEnum.CIRCLE_FILE.getCode());
                userFile.setFileUrl(fileUrl);
                userFile.setRelationId(mutualCircle.getId());
                userFile.setCreateDate(new Date());

                accUserFileMapper.insertSelective(userFile);
            }
        }
    }

    @Override
    public List<Map<String, Object>> getChoicenessCircles(Map<String, Object> params, Integer userId) throws ApiServiceException {
        if (null == params || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        params.put("isChoiceness", MutualCircleEnum.CHOICENESS.getCode());
        List<Map<String, Object>> choicenessCircles = accMutualCircleMapper.selectByParams(params);
        fillCircleDetail(choicenessCircles, userId);
        return choicenessCircles;
    }


    @Override
    public List<Map<String, Object>> getUserCircles(Map<String, Object> params, Integer userId) throws ApiServiceException {
        if (null == params || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        List<Integer> circlesUserIds = new ArrayList<>();
        circlesUserIds.add(userId);
        circlesUserIds.add(Constants.SYSTEM_USER_ID);
        // 获取用户好友
        AccUserAttention searchUA = new AccUserAttention();
        searchUA.setUserId(userId);
        searchUA.setType(UserAttentionTypeEnum.ATTENTION_USER.getCode());
        List<AccUserAttention> userAttentions = accUserAttentionMapper.selectBySelective(searchUA);
        for (AccUserAttention userAttention : userAttentions) {
            circlesUserIds.add(userAttention.getAttentionId());
        }

        params.put("userIdList", circlesUserIds);
        List<Map<String, Object>> circles = accMutualCircleMapper.selectByParams(params);
        fillCircleDetail(circles, userId);
        return circles;
    }

    @Override
    public void userLikeCircle(Integer circleId, Integer userId) throws ApiServiceException {
        if (null == circleId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        // 是否已点赞
        AccCircleLike searchCL = new AccCircleLike();
        searchCL.setCircleId(circleId);
        searchCL.setLikerId(userId);
        boolean isLiked = accCircleLikeMapper.selectCountBySelective(searchCL) > 0;
        if (!isLiked) {
            AccCircleLike circleLike = new AccCircleLike();
            circleLike.setCircleId(circleId);
            circleLike.setLikerId(userId);
            circleLike.setCreateDate(new Date());

            accCircleLikeMapper.insertSelective(circleLike);
        }
    }

    @Override
    public void userCancelLikeCircle(Integer circleId, Integer userId) throws ApiServiceException {
        if (null == circleId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        AccCircleLike searchCL = new AccCircleLike();
        searchCL.setCircleId(circleId);
        searchCL.setLikerId(userId);
        List<AccCircleLike> circleLikeList = accCircleLikeMapper.selectBySelective(searchCL);

        for (AccCircleLike circleLike : circleLikeList) {
            accCircleLikeMapper.deleteByPrimaryKey(circleLike.getId());
        }
    }

    @Override
    public void userCommentCircle(AccCircleComment circleComment) throws ApiServiceException {
        if (null == circleComment) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        circleComment.setCreateDate(new Date());
        accCircleCommentMapper.insertSelective(circleComment);
    }

    @Override
    public List<CirclesRespDTO> getAttentionUserCircles(GetAttentionUserCirclesReqDTO reqDTO, Integer userId) throws ApiServiceException {
        if (null == reqDTO || null ==  reqDTO.getUserId()) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        Map<String, Object> params = BeansUtils.transBean2Map(reqDTO);
        List<Map<String, Object>> circleMaps = accMutualCircleMapper.selectByParams(params);
        List<CirclesRespDTO> circles = BeansUtils.transListMap2ListBean(circleMaps, CirclesRespDTO.class);

        for (CirclesRespDTO circle : circles) {
            if (StringUtils.isNotBlank(circle.getHeadImg())) {
                circle.setHeadImg(systemResource.getApiFileUri() + circle.getHeadImg());
            }

            // 关注状态
            AccUserAttention searchAU = new AccUserAttention();
            searchAU.setUserId(circle.getUserId());
            searchAU.setAttentionId(userId);
            searchAU.setType(UserAttentionTypeEnum.ATTENTION_USER.getCode());
            boolean isUserAttention = accUserAttentionMapper.selectCountBySelective(searchAU) > 0;
            circle.setRelation(RelationTypeUtil.getABRelation(true, isUserAttention));

            // 互助圈文件
            AccUserFile searchUF = new AccUserFile();
            searchUF.setUserId(circle.getUserId());
            searchUF.setType(UserFileTypeEnum.CIRCLE_FILE.getCode());
            searchUF.setRelationId(circle.getId());
            searchUF.setDelflag(DelFlagEnum.NOT_DELETED.getCode());
            List<String> circleFiles = accUserFileMapper.selectFilesBySelective(searchUF);
            for (int i = 0; i < circleFiles.size(); i++) {
                circleFiles.set(i, systemResource.getApiFileUri() + circleFiles.get(i));
            }
            circle.setFiles(circleFiles);

            // 点赞总数
            AccCircleLike searchCL = new AccCircleLike();
            searchCL.setCircleId(circle.getId());
            int likeCount = accCircleLikeMapper.selectCountBySelective(searchCL);
            circle.setLickCount(likeCount);

            // 是否已点赞
            searchCL.setLikerId(userId);
            boolean isLiked = accCircleLikeMapper.selectCountBySelective(searchCL) > 0;
            circle.setLiked(isLiked);

            // 评论总数
            AccCircleComment searchCC = new AccCircleComment();
            searchCC.setCircleId(circle.getId());
            int commentCount = accCircleCommentMapper.selectCountBySelective(searchCC);
            circle.setCommentCount(commentCount);
        }

        return circles;
    }

    private void fillCircleDetail(List<Map<String, Object>> circles, Integer userId) {
        for (Map<String, Object> circle : circles) {
            Integer circleUserId = Integer.valueOf(circle.get("userId").toString());
            Integer circleId = Integer.valueOf(circle.get("id").toString());

            // 是否已关注
            AccUserAttention searchAU = new AccUserAttention();
            searchAU.setUserId(userId);
            searchAU.setAttentionId(circleUserId);
            searchAU.setType(UserAttentionTypeEnum.ATTENTION_USER.getCode());
            boolean isUserAttention = accUserAttentionMapper.selectCountBySelective(searchAU) > 0;

            circle.put("isUserAttention", isUserAttention);

            // 互助圈文件
            AccUserFile searchUF = new AccUserFile();
            searchUF.setUserId(circleUserId);
            searchUF.setType(UserFileTypeEnum.CIRCLE_FILE.getCode());
            searchUF.setRelationId(circleId);
            searchUF.setDelflag(DelFlagEnum.NOT_DELETED.getCode());
            List<String> circleFiles = accUserFileMapper.selectFilesBySelective(searchUF);
            for (int i = 0; i < circleFiles.size(); i++) {
                circleFiles.set(i, systemResource.getApiFileUri() + circleFiles.get(i));
            }
            circle.put("circleFiles", circleFiles);

            // 点赞总数
            AccCircleLike searchCL = new AccCircleLike();
            searchCL.setCircleId(circleId);
            int likeCount = accCircleLikeMapper.selectCountBySelective(searchCL);
            circle.put("likeCount", likeCount);

            // 是否已点赞
            searchCL.setLikerId(userId);
            boolean isLiked = accCircleLikeMapper.selectCountBySelective(searchCL) > 0;
            circle.put("isLiked", isLiked);

            // 评论总数
            AccCircleComment searchCC = new AccCircleComment();
            searchCC.setCircleId(circleId);
            int commentCount = accCircleCommentMapper.selectCountBySelective(searchCC);
            circle.put("commentCount", commentCount);
        }
    }
}
