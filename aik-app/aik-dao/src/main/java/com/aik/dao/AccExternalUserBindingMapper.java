package com.aik.dao;

import com.aik.model.AccExternalUserBinding;
import org.apache.ibatis.annotations.Param;

public interface AccExternalUserBindingMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(AccExternalUserBinding record);

    int insertSelective(AccExternalUserBinding record);

    AccExternalUserBinding selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(AccExternalUserBinding record);

    int updateByPrimaryKey(AccExternalUserBinding record);

    AccExternalUserBinding selectByPlatformAndOpenId(@Param("platform") String platform, @Param("openId") String openId);
}