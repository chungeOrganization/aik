package com.aik.dao;

import java.util.List;

import com.aik.model.AikCarousel;
import com.github.pagehelper.Page;

public interface AikCarouselMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AikCarousel record);

    int insertSelective(AikCarousel record);

    AikCarousel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AikCarousel record);

    int updateByPrimaryKey(AikCarousel record);
    
    /**
     * 获取所有数据
     * @return
     */
    List<AikCarousel> findAll(AikCarousel record);

    /**
     * 分页查询数据
     * @return
     */
    Page<AikCarousel> findByPage(AikCarousel record);
}