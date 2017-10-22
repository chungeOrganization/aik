package com.aik.service;

import com.aik.model.AikCarousel;
import com.aik.util.PageUtils;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * Description:
 * Created by as on 2017/9/7.
 */
public interface CarouselManageService {

    
    
    /**
     * 根据主键删除轮播图信息
     * @param id 食物id
     * @throws Exception 异常
     */
    void deleteByPrimaryKey(Long id) throws Exception;
    
    /**
     * 新增轮播图
     *
     * @param dietFood 轮播图信息
     * @throws Exception 异常
     */
    void save(AikCarousel aikCarousel) throws Exception;
    
    /**
     * 修改轮播图信息
     *
     * @param dietFood 轮播图信息
     * @throws Exception 异常
     */
    void update(AikCarousel aikCarousel) throws  Exception;
    
    /**
     * 查询轮播图信息根据主键
     *
     * @param id 食物id
     * @throws Exception 异常
     */
    AikCarousel findById(Long id) throws  Exception;
    
    /**
     * 查询所有轮播图信息
     * @param dietFood
     * @return
     * @throws Exception
     */
    List<AikCarousel> findAll(AikCarousel aikCarousel) throws Exception;
    
    /**
     * 轮播图信息分页查询
     */
    Page<AikCarousel> findPage(AikCarousel aikCarousel, PageUtils pageUtils) throws  Exception;
    
    
    
}
