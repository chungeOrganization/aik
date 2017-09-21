package com.aik.service.question;

import com.aik.exception.ApiServiceException;

import java.util.List;
import java.util.Map;

/**
 * Description:
 * Created by as on 2017/9/9.
 */
public interface IllTypeService {

    /**
     * 获取疾病类型（树结构）
     *
     * @return 疾病类型
     * @throws ApiServiceException Api服务异常
     */
    List<Map<String, Object>> getIllTypeTree() throws ApiServiceException;
}
