package com.aik.service;

import com.aik.model.AikQuestion;
import com.aik.model.StoUserOrder;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface QuestionManageService {

    
    
    /**
     * 根据主键删除问题明细信息
     * @param id 问题明细id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Integer id) throws Exception;
    
    /**
     * 新增问题明细信息
     *
     * @param StoGoods 问题明细信息
     * @throws Exception 异常
     */
    void save(AikQuestion aikQuestion) throws Exception;
    
    /**
     * 修改问题明细信息
     *
     * @param StoGoods 问题明细信息
     * @throws Exception 异常
     */
    void update(AikQuestion aikQuestion) throws  Exception;
    
    /**
     * 查询问题明细信息根据主键
     *
     * @param id 问题明细id
     * @throws Exception 异常
     */
    AikQuestion findById(Integer id) throws  Exception;
    
    /**
     * 查询所有问题明细信息
     * @param StoGoods
     * @return
     * @throws Exception
     */
    List<AikQuestion> findAll(AikQuestion aikQuestion) throws Exception;
    
    /**
     * 问题明细信息分页查询
     */
    Page<AikQuestion> findPage(AikQuestion aikQuestion, PageUtils pageUtils) throws  Exception;
    
    
    
}
