package com.aik.dao;

import com.aik.model.AikCarousel;

public interface AikCarouselMapper {
    int deleteByPrimaryKey(Long id);

    int insert(AikCarousel record);

    int insertSelective(AikCarousel record);

    AikCarousel selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(AikCarousel record);

    int updateByPrimaryKey(AikCarousel record);
}