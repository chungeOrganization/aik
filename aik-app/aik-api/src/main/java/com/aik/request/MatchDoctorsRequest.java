package com.aik.request;

/**
 * Description:
 * Created by as on 2017/9/10.
 */
public class MatchDoctorsRequest extends PageRequest {

    private String hosName;

    private String illFirstLv;

    private String illSecondLv;

    private Byte sortType;

    public String getHosName() {
        return hosName;
    }

    public void setHosName(String hosName) {
        this.hosName = hosName;
    }

    public String getIllFirstLv() {
        return illFirstLv;
    }

    public void setIllFirstLv(String illFirstLv) {
        this.illFirstLv = illFirstLv;
    }

    public String getIllSecondLv() {
        return illSecondLv;
    }

    public void setIllSecondLv(String illSecondLv) {
        this.illSecondLv = illSecondLv;
    }

    public Byte getSortType() {
        return sortType;
    }

    public void setSortType(Byte sortType) {
        this.sortType = sortType;
    }
}
