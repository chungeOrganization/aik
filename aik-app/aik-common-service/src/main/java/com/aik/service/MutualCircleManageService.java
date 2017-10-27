package com.aik.service;

import com.aik.model.AccCircleComment;
import com.aik.model.AccMutualCircle;
import com.aik.model.AikHealthRecord;
import com.aik.model.StoUserOrder;
import com.aik.util.PageUtils;
import com.aik.vo.AccCircleCommentVo;
import com.aik.vo.AccMutualCircleVo;
import com.aik.vo.AikHealthRecordVo;
import com.aik.vo.StoUserOrderVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface MutualCircleManageService {

    
    /**
     * 根据主键删除互助圈
     * @param id 商品订单id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增互助圈
     *
     * @param accMutualCircle 互助圈
     * @throws Exception 异常
     */
    void save(AccMutualCircle accMutualCircle) throws Exception;
    
    /**
     * 修改互助圈
     *
     * @param StoGoods 互助圈
     * @throws Exception 异常
     */
    void update(AccMutualCircle accMutualCircle) throws  Exception;
    
    /**
     * 查询互助圈根据主键
     *
     * @param id 商品订单id
     * @throws Exception 异常
     */
    AccMutualCircle findById(Integer id) throws  Exception;
    
    /**
     * 查询所有互助圈-评论
     * @param aikQuestionOrder
     * @return
     * @throws Exception
     */
    List<AccMutualCircleVo> findAll(AccMutualCircle accMutualCircle) throws Exception;
    
    /**
     * 互助圈分页查询
     */
    Page<AccMutualCircleVo> findPage(AccMutualCircleVo accMutualCircleVo, PageUtils pageUtils) throws  Exception;
    
    
    
}
