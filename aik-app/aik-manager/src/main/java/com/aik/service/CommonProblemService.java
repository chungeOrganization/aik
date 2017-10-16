package com.aik.service;

import com.aik.model.AikCommonProblem;
import com.github.pagehelper.Page;

/**
 * Description:
 * Created by as on 2017/10/15.
 */
public interface CommonProblemService {

    /**
     * 获取常见问题分页信息
     *
     * @param commonProblem 参数
     * @return 常见问题分页信息
     */
    Page<AikCommonProblem> findPage(AikCommonProblem commonProblem);

    /**
     * 获取常见问题
     *
     * @param id 常见问题id
     * @return 常见问题
     */
    AikCommonProblem findCommonProblem(Integer id);

    /**
     * 编辑常见问题
     *
     * @param commonProblem 常见问题
     * @return true false
     */
    boolean editCommonProblem(AikCommonProblem commonProblem);

    /**
     * 新增常见问题
     *
     * @param commonProblem 常见问题
     * @return true false
     */
    boolean addCommonProblem(AikCommonProblem commonProblem);

    /**
     * 删除常见问题
     *
     * @param id 常见问题id
     * @return true false
     */
    boolean deleteCommonProblem(Integer id);
}
