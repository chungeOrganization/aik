package com.aik.service.question;

import com.aik.assist.ErrorCodeEnum;
import com.aik.bean.userside.AnswerWithQuestion;
import com.aik.bean.userside.DoctorInfo;
import com.aik.bean.userside.QuestionOrderDetail;
import com.aik.dao.*;
import com.aik.enums.AnswerTypeEnum;
import com.aik.enums.DoctorPositionEnum;
import com.aik.enums.QuestionTypeEnum;
import com.aik.enums.UserFileTypeEnum;
import com.aik.exception.ApiServiceException;
import com.aik.model.*;
import com.aik.request.PageRequest;
import com.aik.resource.SystemResource;
import com.aik.response.FreeQODetailResponse;
import com.aik.response.FreeQOListDetailResponse;
import com.aik.util.BeansUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/9.
 */
@Service
public class FreeQuestionOrderServiceImpl implements FreeQuestionOrderService {

    private static final Logger logger = LoggerFactory.getLogger(FreeQuestionOrderServiceImpl.class);

    private AikFreeQuestionOrderMapper aikFreeQuestionOrderMapper;

    private AikFreeQuestionOrderViewMapper aikFreeQuestionOrderViewMapper;

    private AikQuestionOrderMapper aikQuestionOrderMapper;

    private AccDoctorAccountMapper accDoctorAccountMapper;

    private AikAnswerMapper aikAnswerMapper;

    private AikQuestionMapper aikQuestionMapper;

    private AccUserFileMapper accUserFileMapper;

    @Resource
    private SystemResource systemResource;

    @Autowired
    public void setAikFreeQuestionOrderMapper(AikFreeQuestionOrderMapper aikFreeQuestionOrderMapper) {
        this.aikFreeQuestionOrderMapper = aikFreeQuestionOrderMapper;
    }

    @Autowired
    public void setAikFreeQuestionOrderViewMapper(AikFreeQuestionOrderViewMapper aikFreeQuestionOrderViewMapper) {
        this.aikFreeQuestionOrderViewMapper = aikFreeQuestionOrderViewMapper;
    }

    @Autowired
    public void setAikQuestionOrderMapper(AikQuestionOrderMapper aikQuestionOrderMapper) {
        this.aikQuestionOrderMapper = aikQuestionOrderMapper;
    }

    @Autowired
    public void setAccDoctorAccountMapper(AccDoctorAccountMapper accDoctorAccountMapper) {
        this.accDoctorAccountMapper = accDoctorAccountMapper;
    }

    @Autowired
    public void setAikAnswerMapper(AikAnswerMapper aikAnswerMapper) {
        this.aikAnswerMapper = aikAnswerMapper;
    }

    @Autowired
    public void setAikQuestionMapper(AikQuestionMapper aikQuestionMapper) {
        this.aikQuestionMapper = aikQuestionMapper;
    }

    @Autowired
    public void setAccUserFileMapper(AccUserFileMapper accUserFileMapper) {
        this.accUserFileMapper = accUserFileMapper;
    }

    @Override
    public List<FreeQOListDetailResponse> getFreeQuestionOrders(PageRequest request) throws ApiServiceException {

        List<Map<String, Object>> freeQuestionOrderList = aikFreeQuestionOrderMapper.selectOrdersByParams(BeansUtils.transBean2Map(request));

        List<FreeQOListDetailResponse> listResponse = BeansUtils.transListMap2ListBean(freeQuestionOrderList, FreeQOListDetailResponse.class);
        for (FreeQOListDetailResponse response : listResponse) {
            response.setDoctorHeadImg(systemResource.getApiFileUri() + response.getDoctorHeadImg());

            // 获取查看总数
            AikFreeQuestionOrderView searchFQ = new AikFreeQuestionOrderView();
            searchFQ.setFreeOrderId(response.getFreeOrderId());
            int viewCount = aikFreeQuestionOrderViewMapper.selectCountBySelective(searchFQ);
            response.setViewCount(viewCount);
        }

        return listResponse;
    }

    @Override
    public FreeQODetailResponse getFreeQuestionOrderDetail(Integer freeOrderId, Integer userId) throws ApiServiceException {
        if (null == freeOrderId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }

        FreeQODetailResponse response = new FreeQODetailResponse();

        AikFreeQuestionOrder freeQuestionOrder = aikFreeQuestionOrderMapper.selectByPrimaryKey(freeOrderId);
        if (null == freeQuestionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004010);
        }

        // 是否已分享
        AikFreeQuestionOrderView searchFQ = new AikFreeQuestionOrderView();
        searchFQ.setFreeOrderId(freeOrderId);
        searchFQ.setUserId(userId);
        // 目前直接通过是否已经查看过问题判断是否已经分享，分享后直接置为已查看
        boolean isShare = aikFreeQuestionOrderViewMapper.selectCountBySelective(searchFQ) > 0;
        response.setShare(isShare);
        response.setFreeEndTime(freeQuestionOrder.getFreeEndTime());

        // 问题订单详情
        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(freeQuestionOrder.getOrderId());
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }
        QuestionOrderDetail questionOrderDetail = new QuestionOrderDetail();
        questionOrderDetail.setIllName(questionOrder.getIllName());
        questionOrderDetail.setQuestionDescription(questionOrder.getDescription());
        questionOrderDetail.setQuestionDate(questionOrder.getCreateDate());
        // 获取图片信息
        AccUserFile searchAU = new AccUserFile();
        searchAU.setUserId(questionOrder.getUserId());
        searchAU.setType(UserFileTypeEnum.QUESTION_FILE.getCode());
        searchAU.setRelationId(questionOrder.getId());
        List<String> questionFiles = accUserFileMapper.selectFilesBySelective(searchAU);
        for (int i = 0; i < questionFiles.size(); i++) {
            questionFiles.set(i, systemResource.getApiFileUri() + questionFiles.get(i));
        }
        questionOrderDetail.setQuestionFiles(questionFiles);

        response.setQuestionOrderDetail(questionOrderDetail);

        // 医生信息
        AccDoctorAccount doctorAccount = accDoctorAccountMapper.selectByPrimaryKey(questionOrder.getDoctorId());
        if (null == doctorAccount) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004011);
        }
        DoctorInfo doctorInfo = new DoctorInfo();
        doctorInfo.setId(doctorAccount.getId());
        doctorInfo.setHeadImg(systemResource.getApiFileUri() + doctorAccount.getHeadImg());
        doctorInfo.setRealName(doctorAccount.getRealName());
        doctorInfo.setHosDepartment(doctorAccount.getHosDepartment());
        doctorInfo.setHosName(doctorAccount.getHosName());
        doctorInfo.setPosition(DoctorPositionEnum.getDescFromCode(doctorAccount.getPosition()));
        doctorInfo.setStarLevel(doctorAccount.getStarLevel());
        doctorInfo.setPrice(doctorAccount.getPrice());
        // 回答数量
        AikAnswer searchAA = new AikAnswer();
        searchAA.setDoctorId(doctorAccount.getId());
        searchAA.setType(AnswerTypeEnum.INITIAL.getCode());
        int answerCount = aikAnswerMapper.selectCountBySelective(searchAA);
        doctorInfo.setAnswerCount(answerCount);

        response.setDoctorInfo(doctorInfo);

        // 问题查看量
        searchFQ = new AikFreeQuestionOrderView();
        searchFQ.setFreeOrderId(freeOrderId);
        int viewCount = aikFreeQuestionOrderViewMapper.selectCountBySelective(searchFQ);
        response.setViewCount(viewCount);

        // 回答-追问列表（如果未分享，该内容为空）
        if (isShare) {
            List<AnswerWithQuestion> answerWithQuestionList = new ArrayList<>();

            AikQuestion searchAQ = new AikQuestion();
            searchAQ.setOrderId(questionOrder.getId());
            List<AikQuestion> questionsList = aikQuestionMapper.selectBySelective(searchAQ);

            for (AikQuestion aikQuestion : questionsList) {
                // 评分类型过滤
                if (aikQuestion.getType() == QuestionTypeEnum.GRADE.getCode()) {
                    continue;
                }

                AnswerWithQuestion answerWithQuestion = new AnswerWithQuestion();
                answerWithQuestion.setQuestion(aikQuestion.getDescription());
                answerWithQuestion.setQuestionTime(aikQuestion.getCreateDate());

                AikAnswer aikAnswer = aikAnswerMapper.selectByQuestionId(aikQuestion.getId());
                if (null != aikAnswer) {
                    answerWithQuestion.setAnswer(aikAnswer.getAnswer());
                    answerWithQuestion.setAnswerTime(aikAnswer.getCreateDate());
                }

                answerWithQuestionList.add(answerWithQuestion);
            }

            response.setAnswerWithQuestionList(answerWithQuestionList);
        }

        return response;
    }

    @Override
    public void sharedFreeQuestionOrder(Integer freeOrderId, Integer userId) throws ApiServiceException {
        if (null == freeOrderId || null == userId) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1000002);
        }
        AikFreeQuestionOrderView searchFQ = new AikFreeQuestionOrderView();
        searchFQ.setFreeOrderId(freeOrderId);
        searchFQ.setUserId(userId);
        boolean isShare = aikFreeQuestionOrderViewMapper.selectCountBySelective(searchFQ) > 0;
        if (isShare) {
            return;
        }

        AikFreeQuestionOrderView addFQ = new AikFreeQuestionOrderView();
        addFQ.setFreeOrderId(freeOrderId);
        addFQ.setUserId(userId);
        addFQ.setCreateDate(new Date());

        aikFreeQuestionOrderViewMapper.insertSelective(addFQ);
    }
}
