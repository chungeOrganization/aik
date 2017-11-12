package com.aik.dao;

import com.aik.model.AikQuestion;
import com.aik.model.AikQuestionOrder;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface AikQuestionMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikQuestion record);

    int insertSelective(AikQuestion record);

    AikQuestion selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikQuestion record);

    int updateByPrimaryKey(AikQuestion record);

    AikQuestion selectOriginalQuestionByOrderId(Integer orderId);

    List<AikQuestion> selectBySelective(AikQuestion record);

    AikQuestion selectByAnswerId(Integer answerId);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AikQuestion> findAll(AikQuestion record);

    /**
     * 分页查询数据
     * @return
     */
    Page<AikQuestion> findByPage(AikQuestion record);

    /**
     * 获取患者最新提问（针对医生）
     * @param userId 用户id
     * @param doctorId 医生id
     * @return 提问
     */
    AikQuestion selectSickLastQuestionByDoctorId(@Param("userId") Integer userId, @Param("doctorId") Integer doctorId);

    /**
     * 获取咨询订单最新提问
     * @param orderId
     * @param doctorId
     * @return
     */
    AikQuestion selectLastQuestionByDoctorId(@Param("orderId") Integer orderId, @Param("doctorId") Integer doctorId);
}