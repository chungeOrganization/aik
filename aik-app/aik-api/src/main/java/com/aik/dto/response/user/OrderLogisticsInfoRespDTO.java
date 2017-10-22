package com.aik.dto.response.user;

import java.util.Date;
import java.util.List;

/**
 * Description:
 * Created by as on 2017/10/23.
 */
public class OrderLogisticsInfoRespDTO {

    private String logisticsCompany;

    private String expressNum;

    private Date placeTime;

    private String officialPhone;

    private String logisticsImg;

    private List<String> trackInfos;

    public String getLogisticsCompany() {
        return logisticsCompany;
    }

    public void setLogisticsCompany(String logisticsCompany) {
        this.logisticsCompany = logisticsCompany;
    }

    public String getExpressNum() {
        return expressNum;
    }

    public void setExpressNum(String expressNum) {
        this.expressNum = expressNum;
    }

    public Date getPlaceTime() {
        return placeTime;
    }

    public void setPlaceTime(Date placeTime) {
        this.placeTime = placeTime;
    }

    public String getOfficialPhone() {
        return officialPhone;
    }

    public void setOfficialPhone(String officialPhone) {
        this.officialPhone = officialPhone;
    }

    public String getLogisticsImg() {
        return logisticsImg;
    }

    public void setLogisticsImg(String logisticsImg) {
        this.logisticsImg = logisticsImg;
    }

    public List<String> getTrackInfos() {
        return trackInfos;
    }

    public void setTrackInfos(List<String> trackInfos) {
        this.trackInfos = trackInfos;
    }
}
