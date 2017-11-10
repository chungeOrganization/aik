package com.aik.service;

/**
 * Description:
 * Created by as on 2017/8/17.
 */
public interface SysSettingService {

    /**
     * 获取医生-首页 图片
     *
     * @return 返回图片
     */
    String getDoctorHomeImg();

    /**
     * 医聊-新的朋友 默认图片
     * @return
     */
    String getNewFriendHeadImg();

    /**
     * 获取客服电话
     *
     * @return 客服电话
     */
    String getServicePhone();
}
