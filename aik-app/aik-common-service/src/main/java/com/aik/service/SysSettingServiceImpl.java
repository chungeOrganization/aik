package com.aik.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * Description:
 * Created by as on 2017/8/17.
 */
@Service
public class SysSettingServiceImpl implements SysSettingService {

    private static final Logger logger = LoggerFactory.getLogger(SysSettingServiceImpl.class);

    @Override
    public String getDoctorHomeImg() {
        // TODO：获取配置图片
        return "test.jpg";
    }
}
