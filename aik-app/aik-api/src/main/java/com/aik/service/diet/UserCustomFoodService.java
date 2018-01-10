package com.aik.service.diet;

import com.aik.model.DietUserCustomFood;

/**
 * Desc: 用户自定义食物接口
 * Create by as on 2018/1/11
 */
public interface UserCustomFoodService {
    /**
     * 添加自定义食物
     *
     * @param reqVO VO
     */
    void add(DietUserCustomFood reqVO);
}
