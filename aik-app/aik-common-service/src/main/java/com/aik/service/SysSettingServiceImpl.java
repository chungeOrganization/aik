package com.aik.service;

import com.aik.resource.SystemResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Description: TODO:获取配置信息
 * Created by as on 2017/8/17.
 */
@Service
public class SysSettingServiceImpl implements SysSettingService {

    private static final Logger logger = LoggerFactory.getLogger(SysSettingServiceImpl.class);

    @Resource
    private SystemResource systemResource;

    @Override
    public String getDoctorHomeImg() {
        // TODO：获取配置图片
        return systemResource.getApiFileUri() + "doctor\\doctor-homepage.png";
    }

    @Override
    public String getNewFriendHeadImg() {
        return systemResource.getApiFileUri() + "doctor\\new-friend.png";
    }

    @Override
    public String getServicePhone() {
        return "0755-12345678";
    }
}
