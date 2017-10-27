package com.aik.dao;

import com.aik.model.AccMutualCircle;
import com.aik.model.AikHealthRecord;
import com.aik.model.StoUserOrder;
import com.aik.vo.AccMutualCircleVo;
import com.aik.vo.AikHealthRecordVo;
import com.aik.vo.StoUserOrderVo;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface AikHealthRecordMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AikHealthRecord record);

    int insertSelective(AikHealthRecord record);

    AikHealthRecord selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AikHealthRecord record);

    int updateByPrimaryKey(AikHealthRecord record);

    AikHealthRecord selectLastRecordByUserId(Integer userId);

    List<Map<String, Object>> selectByParams(Map<String, Object> params);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AikHealthRecordVo> findAll(AikHealthRecord record);
    
    /**
     * 分页查询数据
     * @return
     */
    Page<AikHealthRecordVo> findByPage(AikHealthRecordVo record);
    
    
}