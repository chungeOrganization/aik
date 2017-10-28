package com.aik.dto.response.doctor;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public class SickDetailRespDTO {

    private Integer sickId;

    private Integer userId;

    private String realName;

    private String headImg;

    private Byte userType;

    private Byte sex;

    private String remark;

    private Integer groupId;

    private Byte relation;

    public Integer getSickId() {
        return sickId;
    }

    public void setSickId(Integer sickId) {
        this.sickId = sickId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
    }

    public String getHeadImg() {
        return headImg;
    }

    public void setHeadImg(String headImg) {
        this.headImg = headImg;
    }

    public Byte getUserType() {
        return userType;
    }

    public void setUserType(Byte userType) {
        this.userType = userType;
    }

    public Byte getSex() {
        return sex;
    }

    public void setSex(Byte sex) {
        this.sex = sex;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Integer getGroupId() {
        return groupId;
    }

    public void setGroupId(Integer groupId) {
        this.groupId = groupId;
    }

    public Byte getRelation() {
        return relation;
    }

    public void setRelation(Byte relation) {
        this.relation = relation;
    }
}
