package com.aik.service;

import com.aik.model.AikCommonProblem;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/8/13.
 */
public interface CommonProblemService {

    /**
     * 获取常见问题
     *
     * @param params 参数
     * @return 常见问题列表
     */
    List<Map<String, Object>> getCommonProblems(Map<String, Object> params);

    /**
     * 获取常见问题详情
     *
     * @param problemId 问题id
     * @return 问题详情
     */
    AikCommonProblem getCommonProblemDetail(Integer problemId);
}
