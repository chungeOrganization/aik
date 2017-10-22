package com.aik.model;

import java.util.Date;

public class SysLogisticsCompany {
    private Integer id;

    private String companyName;

    private String officialPhone;

    private String logisticsImg;

    private Date createDate;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getOfficialPhone() {
        return officialPhone;
    }

    public void setOfficialPhone(String officialPhone) {
        this.officialPhone = officialPhone == null ? null : officialPhone.trim();
    }

    public String getLogisticsImg() {
        return logisticsImg;
    }

    public void setLogisticsImg(String logisticsImg) {
        this.logisticsImg = logisticsImg == null ? null : logisticsImg.trim();
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
}