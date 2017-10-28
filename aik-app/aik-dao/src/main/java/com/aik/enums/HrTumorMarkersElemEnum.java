package com.aik.enums;

/**
 * Description:
 * Created by as on 2017/10/28.
 */
public enum HrTumorMarkersElemEnum {
    AFP("afp", "甲胎蛋白(AFP)", "mg/L"),
    CEA("cea", "癌胚抗原(CEA)", "mg/L"),
    CA19("ca19", "糖类抗原CA19-9", "mg/L"),
    PG("pg", "胃蛋白酶原I和II", "mg/L"),
    CA72("ca72", "糖类抗原CA72-4", "mg/L"),
    SCC("scc", "鳞癌相关抗原(SCC)", "mg/L"),
    ;
    private String field;
    private String elemName;
    private String unit;

    HrTumorMarkersElemEnum(String field, String elemName, String unit) {
        this.field = field;
        this.elemName = elemName;
        this.unit = unit;
    }

    public String getField() {
        return field;
    }

    public String getElemName() {
        return elemName;
    }

    public String getUnit() {
        return unit;
    }
}
