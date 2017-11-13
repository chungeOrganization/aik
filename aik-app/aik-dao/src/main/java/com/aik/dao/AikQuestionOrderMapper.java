package com.aik.dao;

import com.aik.model.AikQuestionOrder;
import com.aik.model.StoUserOrder;
import com.aik.vo.AikQuestionOrderVo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface AikQuestionOrderMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikQuestionOrder record);

    int insertSelective(AikQuestionOrder record);

    AikQuestionOrder selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikQuestionOrder record);

    int updateByPrimaryKey(AikQuestionOrder record);

    AikQuestionOrder selectByPrimaryKeyForUpdate(Integer id);

    Map<String, Object> selectProcessingDetailById(Integer id);

    int selectCountByParams(Map<String, Object> params);

    int selectSickCountByParams(Map<String, Object> params);

    AikQuestionOrder selectHomeOpenQuestion(Integer doctorId);

    List<Map<String, Object>> selectDoctorOrders(Map<String, Object> params);

    List<Map<String, Object>> selectDoctorMyOrders(Map<String, Object> params);

    List<Map<String, Object>> selectOpenQuestionOrders(Map<String, Object> params);

    List<AikQuestionOrder> selectUserOrders(Map<String, Object> params);

    List<Map<String, Object>> selectSickOrders(Map<String, Object> params);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AikQuestionOrder> findAll(AikQuestionOrder record);

    /**
     * 分页查询数据
     * @return
     */
    Page<AikQuestionOrderVo> findByPage(AikQuestionOrder record);
}