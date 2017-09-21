package com.aik.service.question;

import com.aik.assist.ErrorCodeEnum;
import com.aik.bean.userside.AnswerWithQuestion;
import com.aik.bean.userside.DoctorInfo;
import com.aik.bean.userside.QuestionOrderDetail;
import com.aik.dao.*;
import com.aik.enums.DoctorPositionEnum;
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
        // TODO:目前直接通过是否已经查看过问题判断是否已经分享，分享后直接置位已查看
        boolean isShare = aikFreeQuestionOrderViewMapper.selectCountBySelective(searchFQ) > 0;
        response.setShare(isShare);

        // 问题订单详情
        AikQuestionOrder questionOrder = aikQuestionOrderMapper.selectByPrimaryKey(freeQuestionOrder.getOrderId());
        if (null == questionOrder) {
            throw new ApiServiceException(ErrorCodeEnum.ERROR_CODE_1004001);
        }
        QuestionOrderDetail questionOrderDetail = new QuestionOrderDetail();
        questionOrderDetail.setIllName(questionOrder.getIllName());
        questionOrderDetail.setQuestionDescription(questionOrder.getDescription());
        questionOrderDetail.setQuestionDate(questionOrder.getCreateDate());
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
        response.setDoctorInfo(doctorInfo);

        // 问题查看量
        searchFQ = new AikFreeQuestionOrderView();
        searchFQ.setFreeOrderId(freeOrderId);
        int viewCount = aikFreeQuestionOrderViewMapper.selectCountBySelective(searchFQ);
        response.setViewCount(viewCount);

        // 回答-追问列表（如果未分享，该内容为空）
        if (isShare) {
            List<AnswerWithQuestion> answerWithQuestionList = new ArrayList<>();

            AikAnswer searchAA = new AikAnswer();
            searchAA.setOrderId(questionOrder.getId());
            List<AikAnswer> answers = aikAnswerMapper.selectBySelective(searchAA);

            for (AikAnswer answer : answers) {
                AnswerWithQuestion answerWithQuestion = new AnswerWithQuestion();
                answerWithQuestion.setAnswer(answer.getAnswer());
                answerWithQuestion.setAnswerTime(answer.getCreateDate());

                // 获取追问信息
                AikQuestion question = aikQuestionMapper.selectByAnswerId(answer.getId());
                if (null != question) {
                    answerWithQuestion.setQuestion(question.getDescription());
                    answerWithQuestion.setQuestionTime(question.getCreateDate());
                }

                answerWithQuestionList.add(answerWithQuestion);
            }

            response.setAnswerWithQuestionList(answerWithQuestionList);
        }

        return response;
    }
}
