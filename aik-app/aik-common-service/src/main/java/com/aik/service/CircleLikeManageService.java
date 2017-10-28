package com.aik.service;

import com.aik.model.AccCircleComment;
import com.aik.model.AccCircleLike;
import com.aik.model.AikHealthRecord;
import com.aik.model.AikHrBloodSugar;
import com.aik.model.AikQuestionOrder;
import com.aik.model.StoUserOrder;
import com.aik.util.PageUtils;
import com.aik.vo.AccCircleLikeVo;
import com.aik.vo.AikHealthRecordVo;
import com.aik.vo.StoUserOrderVo;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface CircleLikeManageService {

    
    /**
     * 根据主键删除互助圈-点赞
     * @param id 商品订单id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增互助圈-点赞
     *
     * @param aikHrBloodSugar 互助圈-点赞
     * @throws Exception 异常
     */
    void save(AccCircleLike accCircleLike) throws Exception;
    
    /**
     * 修改互助圈-点赞
     *
     * @param StoGoods 互助圈-点赞
     * @throws Exception 异常
     */
    void update(AccCircleLike accCircleLike) throws  Exception;
    
    /**
     * 查询互助圈-点赞根据主键
     *
     * @param id 商品订单id
     * @throws Exception 异常
     */
    AccCircleLike findById(Integer id) throws  Exception;
    
    /**
     * 查询所有互助圈-点赞
     * @param aikQuestionOrder
     * @return
     * @throws Exception
     */
    List<AccCircleLikeVo> findAll(AccCircleLike accCircleLike) throws Exception;
    
}
